package com.JNUHealthSupervisor.ucdas.dto;

import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

/**
 * 账户登录前端参数定义类，进行BindingResult校验
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
@ApiModel("账户登录参数定义")
public class UserLoginDTO implements Serializable {
  
  @NotNull(message = "非法请求")
  @ApiModelProperty(value = "用户名长度")
  @Range(min = 1, max = 100, message = "解密出错，请重试！")
  private Integer ul;

  @NotNull(message = "非法请求")
  @ApiModelProperty(value = "加密key序号")
  @Range(min = 0, max = 9, message = "解密出错，请重试！")
  private Integer pl;

  @NotEmpty(message = "非法请求")
  @ApiModelProperty(value = "加密后的密钥")
  private String pw;

}
