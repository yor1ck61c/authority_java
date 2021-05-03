package io.oicp.yorick61c.ws;


import io.oicp.yorick61c.domain.MsgDTO;
import io.oicp.yorick61c.mapper.UserMapper;
import io.oicp.yorick61c.utils.JsonUtil;
import io.oicp.yorick61c.utils.MessageUtils;
import io.oicp.yorick61c.utils.SpringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/dialog", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {

    /*
    * 用来存储每一个客户端对象对应的ChatEndPoint对象
    * 只需要一个这样的容器，所以定义成静态的
    * 为了保证线程安全，定义成并发hashmap实现类
    * */

    private static final Map<String, ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();

    /*
    * 声明Session对象，通过该对象可以发送消息给指定用户
    * 该Session非HttpSession,是WebSocket中的
    * 由于每个客户端都需要一个Session对象，不能定义成静态的。
    * */

    private Session session;

    private HttpSession httpSession;

    @OnOpen
    // 连接建立时被调用
    public void onOpen(Session session, EndpointConfig config) {

        // 将局部的session对象赋值给成员session
        this.session = session;

        // 将建立连接的用户存入onlineUsers Map中
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String username = (String) httpSession.getAttribute("username");
        onlineUsers.put(username,this);

        /*
         * 将当前在线用户的用户名推送给所有客户端。
         * 1.获取消息
         * 2.调用方法进行系统消息的推送
         * */
        String msg = MessageUtils.getMsg(true, null, getNames(), null);
        broadcastAllUsers(msg);

    }

    private Set<String> getNames() {
        return onlineUsers.keySet();
    }

    private void broadcastAllUsers(String msg) {
        try {
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndPoint chatEndPoint = onlineUsers.get(name);
                chatEndPoint.session.getBasicRemote().sendText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    // 接收到客户端消息时被调用
    public void onMessage(String msg, Session session) throws IOException {
//        将json字符串转为对象
        MsgDTO msgDTO = JsonUtil.jsonToPojo(msg, MsgDTO.class);
//        获取当前登录的用户
        String fromName = (String) httpSession.getAttribute("username");

//        获取即将接收消息的用户
        assert msgDTO != null;
        String toName = msgDTO.getToName();
//        获取消息数据
        String data = msgDTO.getMessage();

        UserMapper user = SpringUtil.getBean(UserMapper.class);
//        获取推送给指定用户的消息格式的数据
        String toMsg = MessageUtils.getMsg(false, fromName, data, user.findUserByUsername(fromName).getAvatar());
        String fromMsg = MessageUtils.getMsg(false, fromName, data, user.findUserByUsername(toName).getAvatar());
        ChatEndPoint toUser = onlineUsers.get(toName);
        toUser.session.getBasicRemote().sendText(toMsg);
        this.session.getBasicRemote().sendText(fromMsg);
    }

    @OnClose
    // 连接关闭时被调用
    public void onClose(Session session) {
        String username = (String) httpSession.getAttribute("username");
        onlineUsers.remove(username);
        String offLineMsg = MessageUtils.getMsg(true, null, getNames(), null);
        broadcastAllUsers(offLineMsg);
    }
}


