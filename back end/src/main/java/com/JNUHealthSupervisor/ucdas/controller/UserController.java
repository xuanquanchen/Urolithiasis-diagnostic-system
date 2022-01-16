package com.JNUHealthSupervisor.ucdas.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;

import com.JNUHealthSupervisor.ucdas.VO.ResultVO;
import com.JNUHealthSupervisor.ucdas.VO.StatusVO;
import com.JNUHealthSupervisor.ucdas.service.Impl.DoctorInfoServiceImpl;
import com.JNUHealthSupervisor.ucdas.service.Impl.UserInfoServiceImpl;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 访问用户信息的请求接口(not for tourist)
 * @session userType: -1[游客] 0[用户] 1[医生]
 * @session userId: `游客${aes加密后的hex}`[游客] userId[用户|医生]
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@RestController
@Api(tags = "用户信息接口")
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private DoctorInfoServiceImpl doctorInfoServiceImpl;

  @Autowired
  private UserInfoServiceImpl userInfoServiceImpl;

  /**
   * 用户信息访问接口
   * @return success then return[{@code UserInfoVO}|{@code DoctorInfoVO }]
   * while error return the error message
   * @see UserInfoVO
   * @see DoctorInfoVO
   */
  @GetMapping("info")
  @ApiOperation(value = "请求用户信息", notes = "无参数要求")
  public StatusVO getUserInfo(HttpServletResponse response, @ApiIgnore HttpSession session) {

    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    }
    try {
      switch(Integer.parseInt(session.getAttribute("userType").toString())) {
        case 0:
          return new ResultVO(200, "获取成功", userInfoServiceImpl.getInfo(session.getAttribute("userId").toString()));
        case 1:
          return new ResultVO(200, "获取成功", doctorInfoServiceImpl.getInfo(session.getAttribute("userId").toString()));
        case -1:
        default:
          return new StatusVO(0, "用户没有权限！");
      }
    } catch (Exception e) {
      log.error("用户信息接口出错[UserController.getUserInfo]{}", e.toString());
      return new StatusVO(0, "未知错误，请重试！");
    }
  }

  /**对全局设定的访问错误进行处理 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ InvalidFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
  public StatusVO handler(Exception e) {
    log.warn("Bad request[UserController]{}", e.toString());
    return new StatusVO(0, "非法请求，参数错误");
  }

}
