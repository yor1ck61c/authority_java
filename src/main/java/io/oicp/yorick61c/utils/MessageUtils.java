package io.oicp.yorick61c.utils;

import io.oicp.yorick61c.pojo.vo.websocket.ResultMessageVO;

public class MessageUtils {


    public static String getMsg(boolean isSystemMsg, String fromName, Object msg, String avatar){

        try {
            ResultMessageVO result = new ResultMessageVO();
            result.setSystem(isSystemMsg);
            result.setMessage(msg);

            if (avatar != null) {
                result.setAvatar(avatar);
            }
            if (fromName != null) {
                result.setFromName(fromName);
            }
            return JsonUtil.obj2String(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
