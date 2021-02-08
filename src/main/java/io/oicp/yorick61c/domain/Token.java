package io.oicp.yorick61c.domain;

import lombok.Data;

@Data
public class Token {
    private Integer code;
    private String token;
    private String msg;
}
