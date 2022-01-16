package com.JNUHealthSupervisor.ucdas.VO;

import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口状态信息包VO
 * {@code code} 状态码，200为正常
 * {@code msg} 状态信息
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("状态结果")
public class StatusVO implements Serializable {

  @ApiModelProperty("状态码")
  private Integer code;

  @ApiModelProperty("状态信息")
  private String msg;

  public StatusVO(int status, String msg) {
    this.code = status;
    this.msg = msg;
  }

}
