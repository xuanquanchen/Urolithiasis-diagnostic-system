package com.JNUHealthSupervisor.ucdas.config;

import java.util.List;
import java.util.ArrayList;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 全局swagger配置类，同时注入yml处配置
 * @author Chen Yixing
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {

  /**注入application.yml的全局配置 */
  @Value("${swagger.basePackage}")
  private String basePackage;

  @Value("${swagger.title}")
  private String title;

  @Value("${swagger.description}")
  private String description;

  @Value("${swagger.version}")
  private String version;

  @Value("${swagger.license}")
  private String license;

  @Value("${swagger.licenseUrl}")
  private String licenseUrl;

  /** 进行配置重载 */
  @Bean
  public Docket createRestApi(){
    /**默认响应码信息**/
    List<ResponseMessage> responseMessageList = new ArrayList<>();
    responseMessageList.add(new ResponseMessageBuilder().code(200).message("请求成功。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(201).message("已创建。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(400).message("客户端请求的语法错误，服务器无法理解。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(401).message("请求要求用户的身份认证。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(403).message("服务器理解请求客户端的请求，但是拒绝执行此请求。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(404).message("服务器找不到给定的资源。").build());
    responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误。").build());

    return new Docket(DocumentationType.SWAGGER_2)
      /**注入全局响应码信息 */
      .globalResponseMessage(RequestMethod.GET, responseMessageList)
      .globalResponseMessage(RequestMethod.POST, responseMessageList)
      .globalResponseMessage(RequestMethod.PUT, responseMessageList)
      .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
      .select()
      /**开始扫描入口 */
      .apis(RequestHandlerSelectors.basePackage(basePackage))
      .paths(PathSelectors.any())
      .build()
      /**应用信息 */
      .apiInfo(new ApiInfoBuilder()
        .title(title)
        .description(description)
        .version(version)
        .contact(new Contact("泌尿结石辅助诊断系统","https://health.raccooncc.top","raccoon@stu2020.jnu.edu.cn")
      )
      /**版权信息 */
      .license(license)
      .licenseUrl(licenseUrl)
      .build());
  }
  
}