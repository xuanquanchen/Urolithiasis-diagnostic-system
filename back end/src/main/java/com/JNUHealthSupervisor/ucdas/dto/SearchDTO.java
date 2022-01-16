package com.JNUHealthSupervisor.ucdas.dto;

import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Range;

/**
 * 诊断记录搜索前端参数定义类，进行BindingResult校验
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("诊断记录搜索参数定义")
public class SearchDTO implements Serializable {

  @ApiModelProperty("患者姓名")
  @Pattern(regexp = "^.{1,15}$", message = "参数错误，NAME为1到15字符的长度。")
  private String name;

  @ApiModelProperty("开始时间")
  @Pattern(regexp = "^\\d{4}(|-\\d{2}(|-\\d{2}(| \\d{2}(|:\\d{2}(|:\\d{2})))))$", message = "参数错误，BEGINTIME格式错误。")
  private String beginTime;

  @ApiModelProperty("截止时间")
  @Pattern(regexp = "^\\d{4}(|-\\d{2}(|-\\d{2}(| \\d{2}(|:\\d{2}(|:\\d{2})))))$", message = "参数错误，ENDTIME格式错误。")
  private String endTime;

  @ApiModelProperty("当前页")
  @NotNull(message = "参数错误，CURRENTPAGE为空。")
  @Range(min = 1, message = "参数错误，CURRENTPAGE必须大于1。")
  private Integer currentPage;

  @ApiModelProperty("页长度")
  @NotNull(message = "参数错误，PAGESIZE为空。")
  @Range(min = 1, message = "参数错误，PAGESIZE必须大于1。")
  private Integer pageSize;

}
