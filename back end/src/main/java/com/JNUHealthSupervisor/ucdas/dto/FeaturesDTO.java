package com.JNUHealthSupervisor.ucdas.dto;

import lombok.Data;
import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 模型特征前端参数定义类，进行BindingResult校验
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("模型特征参数定义")
public class FeaturesDTO implements Serializable {

  @ApiModelProperty(value = "姓名")
  @Pattern(regexp = "^.{1,15}$", message = "参数错误，NAME为1到15字符的长度。")
  private String name;

  @ApiModelProperty(value = "性别")
  @NotNull(message = "非法请求，SEX参数为空。")
  @Range(min = 0, max = 1, message = "非法请求，SEX参数错误。")
  private Integer sex;

  @ApiModelProperty(value = "年龄")
  @NotNull(message = "非法请求，AGE参数为空。")
  @Range(min = 1, max = 120, message = "非法请求，AGE参数错误。")
  private Integer age;

  @ApiModelProperty(value = "婚姻状况")
  @NotNull(message = "非法请求，MARRIALSTATUS参数错误。")
  @Range(min = 0, max = 1, message = "非法请求，MARRIALSTATUS参数错误。")
  private Integer marrialStatus;

  @ApiModelProperty(value = "出生地")
  @NotNull(message = "非法请求，BIRTHPLACE参数为空。")
  @Range(min = 0, max = 46, message = "非法请求，BIRTHPLACE参数错误。")
  private Integer birthPlace;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，C_C参数为空。")
  @Range(min = 0, max = 6, message = "非法请求，C_C参数错误。")
  private Integer c_C;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，FEVER参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，FEVER参数错误。")
  private Integer fever;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，NAUSEA参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，NAUSEA参数错误。")
  private Integer nausea;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，GH参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，GH参数错误。")
  private Integer gh;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，DM参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，DM参数错误。")
  private Integer dm;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，NH参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，NH参数错误。")
  private Integer nh;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，CE参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，CE参数错误。")
  private Integer ce;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，CR参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，CR参数错误。")
  private Integer cr;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，HB参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，HB参数错误。")
  private Integer hb;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，BUN参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，BUN参数错误。")
  private Integer bun;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，SMOKING参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，SMOKING参数错误。")
  private Integer smoking;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，ALCOHOL参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，ALCOHOL参数错误。")
  private Integer alcohol;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，OPIUM参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，OPIUM参数错误。")
  private Integer opium;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，BP参数为空。")
  @Range(min = 0, max = 3, message = "非法请求，BP参数错误。")
  private Integer bp;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，FH参数为空。")
  @Range(min = 0, max = 2, message = "非法请求，FH参数错误。")
  private Integer fh;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，DISEASEDIAGNOSIS参数为空。")
  @Range(min = 0, max = 7, message = "非法请求，DISEASEDIAGNOSIS参数错误。")
  private Integer diseaseDiagnosis;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，URICACID参数为空。")
  @Range(min = 0, max = 3, message = "非法请求，URICACID参数错误。")
  private Integer uricAcid;

  @ApiModelProperty(value = "参数")
  @NotNull(message = "非法请求，CA参数为空。")
  @Range(min = 0, max = 3, message = "非法请求，CA参数错误。")
  private Integer ca;

}
