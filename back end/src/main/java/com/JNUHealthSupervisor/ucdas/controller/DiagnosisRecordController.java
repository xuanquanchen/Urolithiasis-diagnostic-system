package com.JNUHealthSupervisor.ucdas.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.JNUHealthSupervisor.ucdas.VO.ResultVO;
import com.JNUHealthSupervisor.ucdas.VO.StatusVO;
import com.JNUHealthSupervisor.ucdas.dto.SearchDTO;
import com.JNUHealthSupervisor.ucdas.service.Impl.DiagnosisRecordServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 访问模型记录查询的请求接口(not for tourist)
 * @session userType: -1[游客] 0[用户] 1[医生]
 * @session userId: `游客${aes加密后的hex}`[游客] userId[用户|医生]
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@RestController
@Api(tags = "模型记录查询接口")
@RequestMapping("/api/record")
public class DiagnosisRecordController {

  @Autowired
  private DiagnosisRecordServiceImpl diagnosisRecordServiceImpl;

  /**
   * 普通用户诊断记录查询(only for user)
   * @param searchInfo {@code SearchDTO}
   * @return success then return{@code FeaturesRecordVO}
   * while error return the error message
   * @see SearchDTO
   * @see FeaturesRecordVO
   */
  @PostMapping(value = "search")
  @ApiOperation(value = "用户诊断记录查询", notes = "请求模型参数，见模型SearchDTO")
  public StatusVO searchForUser(@RequestBody @Valid SearchDTO searchInfo, BindingResult bindingResult, HttpServletResponse response, @ApiIgnore HttpSession session) {

    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    } else if (!session.getAttribute("userType").equals(0)) {
      /**userType不为0说明用户不是普通用户 */
      return new StatusVO(0, "用户没有权限！");
    }

    /**校验前端请求格式是否满足SearchDTO，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[DiagnosisRecordController.searchForUser]{}", new ObjectMapper().writeValueAsString(searchInfo));
      } catch (JsonProcessingException e) {
        log.warn("非法请求[DiagnosisRecordController.searchForUser]{}", searchInfo.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**返回查询结果 */
    try {
      return new ResultVO(200, "获取成功", diagnosisRecordServiceImpl.searchForUser(session.getAttribute("userId").toString(), searchInfo.getName(), searchInfo.getBeginTime(), searchInfo.getEndTime(), searchInfo.getCurrentPage(), searchInfo.getPageSize()));
    } catch (Exception e) {
      log.error("记录查询接口错误[DiagnosisRecordController.searchForUser]{}", e.toString());
      return new StatusVO(0, "搜索出错了！");
    }
  }

  /**
   * 医生诊断记录查询(only for doctor)
   * @param user {@code Boolean} 是否查询所有用户记录
   * @param searchInfo {@code SearchDTO}
   * @return success then return{@code FeaturesRecordVO}
   * while error return the error message
   * @see SearchDTO
   * @see FeaturesRecordVO
   */
  @PostMapping(value = "search/doctor")
  @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "是否查询所有用户记录", type = "query", required = false)})
  @ApiOperation(value = "医生查询", notes = "请求用户标识及模型参数，见模型SearchDTO")
  public StatusVO searchForDoctor(@RequestParam(value = "user", required = false) Boolean user, @RequestBody @Valid SearchDTO searchInfo, BindingResult bindingResult, HttpServletResponse response, @ApiIgnore HttpSession session) {
    
    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    } else if (!session.getAttribute("userType").equals(1)) {
      /**userType不为0说明用户不是普通用户 */
      return new StatusVO(0, "用户没有权限！");
    }

    /**校验前端请求格式是否满足SearchDTO，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[DiagnosisRecordController.searchForDoctor]{}", new ObjectMapper().writeValueAsString(searchInfo));
      } catch (JsonProcessingException e) {
        log.error("非法请求[DiagnosisRecordController.searchForDoctor]{}", searchInfo.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**返回查询结果 */
    try {
      if (user == null || !user) {
        return new ResultVO(200, "获取成功", diagnosisRecordServiceImpl.searchForDoctor(session.getAttribute("userId").toString(), searchInfo.getName(), searchInfo.getBeginTime(), searchInfo.getEndTime(), searchInfo.getCurrentPage(), searchInfo.getPageSize()));
      } else {
        return new ResultVO(200, "获取成功", diagnosisRecordServiceImpl.searchUserForDoctor(searchInfo.getName(), searchInfo.getBeginTime(), searchInfo.getEndTime(), searchInfo.getCurrentPage(), searchInfo.getPageSize()));
      }
    } catch (Exception e) {
      log.error("记录查询接口错误[DiagnosisRecordController.searchForDoctor]{}", e.toString());
      return new StatusVO(0, "搜索出错了！");
    }
  }

  /**对全局设定的访问错误进行处理 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ InvalidFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
  public StatusVO handler(Exception e) {
    log.warn("Bad request[DiagnosisRecordController]{}", e.toString());
    return new StatusVO(0, "非法请求，参数错误");
  }

}
