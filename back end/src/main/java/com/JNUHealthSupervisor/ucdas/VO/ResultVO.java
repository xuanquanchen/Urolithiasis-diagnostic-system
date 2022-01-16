package com.JNUHealthSupervisor.ucdas.VO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口返回数据二层VO
 * 用于包装数据及状态
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("返回结果")
@EqualsAndHashCode(callSuper = true)
public class ResultVO extends StatusVO {

  @ApiModelProperty("返回数据")
  private Object data;

  public ResultVO(int status, String msg, Object data) {
    super(status, msg);
    this.data = data;
  }

}
