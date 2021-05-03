package io.oicp.yorick61c.domain;


import lombok.Data;
/*
* 客户端 -> 服务端
* */
@Data
public class MsgDTO {
    // 接收者
    private String toName;
    // 发送的消息
    private String message;
}
