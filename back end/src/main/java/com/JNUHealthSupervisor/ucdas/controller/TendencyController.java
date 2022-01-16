package com.JNUHealthSupervisor.ucdas.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;

import com.JNUHealthSupervisor.ucdas.VO.ResultVO;
import com.JNUHealthSupervisor.ucdas.VO.StatusVO;
import com.JNUHealthSupervisor.ucdas.service.Impl.TendencyServiceImpl;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 访问结石变化趋势业务的请求接口(only for doctor)
 * @session userType: -1[游客] 0[用户] 1[医生]
 * @session userId: `游客${aes加密后的hex}`[游客] userId[用户|医生]
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@RestController
@Api(tags = "结石变化趋势业务接口")
@RequestMapping("/api/tendency")
public class TendencyController {

  @Autowired
  private TendencyServiceImpl tendencyServiceImpl;

  /**
   * 访问今日结石变化趋势接口
   * @return success then return{@code TendencyVO}
   * while error return the error message
   * @see TendencyVO
   */
  @GetMapping("today")
  @ApiOperation(value = "今日趋势", notes = "无参数要求")
  public StatusVO getTodayTendency(HttpServletResponse response, @ApiIgnore HttpSession session) {

    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    } else if (!session.getAttribute("userType").equals(1)) {
      /**userType不为1说明用户不是医生端用户 */
      return new StatusVO(0, "用户没有权限！");
    }

    /**返回今日趋势变化 */
    try {
      return new ResultVO(200, "获取成功", tendencyServiceImpl.getTodayTendency());
    } catch (Exception e) {
      log.error("获取今日趋势接口错误[TendencyController.getTodayTendency]{}", e.toString());
      return new StatusVO(0, "未知错误，请重试！");
    }
  }

  /**对全局设定的访问错误进行处理 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ InvalidFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
  public StatusVO handler(Exception e) {
    log.warn("Bad request[TendencyController]{}", e.toString());
    return new StatusVO(0, "非法请求，参数错误");
  }
  
}
