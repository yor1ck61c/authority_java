package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("alert_data")
public class AlertInfo {
    private Integer userId;
    private Date alertTime;
    private String alertType;
    private String alertCycle;
    private String alertIntro;
}
