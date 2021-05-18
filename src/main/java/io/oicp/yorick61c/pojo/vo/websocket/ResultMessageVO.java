package io.oicp.yorick61c.pojo.vo.websocket;


/*
* 服务端 -> 客户端
* */

import lombok.Data;

@Data
public class ResultMessageVO {

    private boolean isSystem;

    private String fromName;

    private String avatar;

    //如果是系统消息，是数组。
    private Object message;

}
