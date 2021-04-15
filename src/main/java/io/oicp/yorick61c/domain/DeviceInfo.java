package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("device_info")

public class DeviceInfo {
    @TableId(type = IdType.AUTO)
    private Integer deviceId;
    private String deviceType;
    private String deviceName;
    private String deviceAlias;
    private String deviceCompany;
    private String deviceModel;
    private String deviceSerialNum;
    private String usingStatus;
    private String runningStatus;
    private String bindResident;
    private String residentPhoneNum;
    private String storeTime;

}
