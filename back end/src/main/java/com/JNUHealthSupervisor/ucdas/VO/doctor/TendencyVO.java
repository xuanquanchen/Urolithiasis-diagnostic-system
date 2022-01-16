package com.JNUHealthSupervisor.ucdas.VO.doctor;

import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 今日总趋势预测VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("总趋势预测结果")
public class TendencyVO implements Serializable {
  
  @ApiModelProperty("今日各结石类型变化")
  private float[] todayFlow;

  @ApiModelProperty("结石类型总计")
  private int[] totalType;

  @ApiModelProperty("近一周结石类型变化")
  private int[][] weeklyTendency;

  public TendencyVO(float[] todayFlow, int[] totalType, int[][] weeklyTendency) {
    this.todayFlow = todayFlow;
    this.totalType = totalType;
    this.weeklyTendency = weeklyTendency;
  }

}
