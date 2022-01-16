package com.JNUHealthSupervisor.ucdas.VO.user;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 游客信息VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("游客用户信息")
public class TouristInfoVO {
  
  @ApiModelProperty("用户昵称")
  private String nickName;

}
