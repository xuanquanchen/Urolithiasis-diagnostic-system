package com.JNUHealthSupervisor.ucdas.VO.doctor;

import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 医生信息VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("医生信息")
public class DoctorInfoVO implements Serializable {

  @ApiModelProperty("用户id")
  private String userId;
  
  @ApiModelProperty("用户昵称")
  private String nickName;
  
  @ApiModelProperty("用户手机号")
  private String phoneNum;

  @ApiModelProperty("用户头像地址")
  private String avatarUrl;

  public DoctorInfoVO (String userId, String nickName, String phoneNum, String avatarUrl) {
    this.userId = userId;
    this.nickName = nickName;
    this.phoneNum = phoneNum;
    this.avatarUrl = avatarUrl;
  }

}
