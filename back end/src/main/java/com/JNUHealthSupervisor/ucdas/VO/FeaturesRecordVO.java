package com.JNUHealthSupervisor.ucdas.VO;

import lombok.Data;
import java.util.List;

import com.JNUHealthSupervisor.ucdas.domain.FeaturesRecord;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 诊断记录VO
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("诊断记录")
public class FeaturesRecordVO implements Serializable {
  
  @ApiModelProperty("查询结果")
  private List<FeaturesRecord> result;

  @ApiModelProperty("记录总数")
  private Integer total;

  public FeaturesRecordVO(List<FeaturesRecord> result, Integer total) {
    this.result = result;
    this.total = total;
  }

}
