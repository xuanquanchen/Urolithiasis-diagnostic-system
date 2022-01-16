package com.JNUHealthSupervisor.ucdas.VO;

import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 单个模型预测结果VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("单个模型预测结果")
public class PredictResultVO implements Serializable {

  @ApiModelProperty("模型预测结果")
  private double[] result;

  public PredictResultVO (double[] result) {
    this.result = result;
  }

}
