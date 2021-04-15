package io.oicp.yorick61c.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("contract_info")
public class ContractInfo {
    private String contractNum;
    private String firstParty;
    private String secondParty;
    private Date contractStartTime;
    private Date contractEndTime;
    private String packageName;
    private String firmedDisease;
    private String examItems;
    private String teamName;
    private String deviceSerialNum; //设备序列号
    private String contractTemplate; //合同模板
    private String paid; //是否缴费
    private String buildFileRemark;
    private String contractStatus;
    private Date buildFileTime;
}
