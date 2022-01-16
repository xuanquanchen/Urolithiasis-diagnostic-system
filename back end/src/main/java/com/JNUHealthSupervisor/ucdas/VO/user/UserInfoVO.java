package com.JNUHealthSupervisor.ucdas.VO.user;

import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 普通用户信息VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("普通用户信息")
public class UserInfoVO implements Serializable {

  @ApiModelProperty("用户id")
  private String userId;
  
  @ApiModelProperty("用户昵称")
  private String nickName;
  
  @ApiModelProperty("用户手机号")
  private String phoneNum;

  @ApiModelProperty("用户头像地址")
  private String avatarUrl;

  @ApiModelProperty("用户标识")
  private Boolean isVip;

  public UserInfoVO (String userId, String nickName, String phoneNum, String avatarUrl, Boolean isVip) {
    this.userId = userId;
    this.nickName = nickName;
    this.phoneNum = phoneNum;
    this.avatarUrl = avatarUrl;
    this.isVip = isVip;
  }

}
