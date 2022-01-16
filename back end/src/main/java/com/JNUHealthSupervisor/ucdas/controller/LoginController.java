package com.JNUHealthSupervisor.ucdas.controller;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.buf.HexUtils;
import springfox.documentation.annotations.ApiIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.JNUHealthSupervisor.ucdas.VO.ResultVO;
import com.JNUHealthSupervisor.ucdas.VO.StatusVO;
import com.JNUHealthSupervisor.ucdas.VO.user.UserInfoVO;
import com.JNUHealthSupervisor.ucdas.dto.UserLoginDTO;
import com.JNUHealthSupervisor.ucdas.service.Impl.AccountVerifyServiceImpl;
import com.JNUHealthSupervisor.ucdas.utils.AesUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 用户登录业务请求接口
 * @session userType: -1[游客] 0[用户] 1[医生]
 * @session userId: `游客${aes加密后的hex}`[游客] userId[用户|医生]
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@RestController
@Api(tags = "登录业务接口")
@RequestMapping("/api/log")
public class LoginController {

  @Autowired
  private AccountVerifyServiceImpl accountVerifyServiceImpl;

  /**
   * 非游客登录接口
   * @param user {@code UserLoginDTO}
   * @return {@code StatusVO}
   * @see UserLoginDTO
   * @see StatusVO
   */
  @PostMapping("in")
  @ApiOperation(value = "登录校验", notes = "请求ul,pl,pw")
  public StatusVO login(@Valid @RequestBody UserLoginDTO user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, @ApiIgnore HttpSession session) {
    /**从session池读取用户信息，userType为null说明用户未登录，为-1则允许游客进行登录 */
    if (session.getAttribute("userType") != null && !session.getAttribute("userType").equals(-1) ) {
      return new StatusVO(200, "重复登录!");
    }

    /**校验前端请求格式是否满足UserLoginDTO定义的参数格式，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[LoginController.login]{}", new ObjectMapper().writeValueAsString(user));
      } catch (JsonProcessingException e) {
        log.warn("非法请求[LoginController.login]{}", user.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**对前端加密的账号密码进行解密，解密出错返回null */
    String[] account = accountVerifyServiceImpl.accountDecrypt(user.getUl(), user.getPl(), user.getPw());
    if (account == null) {
      return new StatusVO(0, "解密出错，请重试！");
    }

    /**与数据库账户密码进行比对 */
    Boolean userExist = null;
    if (account[0].equals("0")) {
      userExist = accountVerifyServiceImpl.checkUserAccount(account[1], account[2]);
    } else if (account[0].equals("1")) {
      userExist = accountVerifyServiceImpl.checkDoctorAccount(account[1], account[2]);
    } else {
      return new StatusVO(0, "非法登录!");
    }
    /**userExist为null说明校验出错，userExist为false说明密码错误 */
    if (userExist == null) {
      return new StatusVO(0, "服务器校验出错！");
    } else if (!userExist) {
      return new StatusVO(0, "用户名或密码错误!");
    }

    /**将用户信息写入session池 */
    session.setAttribute("userId", account[1]);
    session.setAttribute("userType", Integer.valueOf(account[0]));
    log.info("用户登录: {} | {}", account[1], request.getHeader("x-real-ip"));
    return new StatusVO(200, "登录成功!");
  }

  /**
   * 游客登录接口
   * @return success then return{@code UserInfoVO}
   * while error return the error message
   */
  @PostMapping("in/tourist")
  @ApiOperation(value = "游客登录", notes = "无请求参数")
  public StatusVO loginForTourist(HttpServletRequest request, HttpServletResponse response, @ApiIgnore HttpSession session) {
    /**定义登录状态 */
    String status = "";
    try {
      /**对用户ip通过aes加密为hex后传回前端 */
      String userName = HexUtils.toHexString(AesUtil.encrypt(request.getHeader("x-real-ip"), "fYp15(5xw%n6Z%Su"));
      if (userName.length() >= 8) {
        userName = userName.substring(0, 8);
      }

      /**从session池读取用户信息，userType为-1说明用户已登录 */
      if (session.getAttribute("userType") != null && session.getAttribute("userType").equals(-1)) {
        status = "重复登录!";
        /**进行session刷新 */
        session.setAttribute("userId", "游客" + userName);
      } else {
        status = "登录成功！";
        /**将用户信息写入session池 */
        session.setAttribute("userType", -1);
        session.setAttribute("userId", "游客" + userName);
      }
      return new ResultVO(200, status, new UserInfoVO(null, "游客" + userName, null, null, false));
    } catch (Exception e) {
      log.error("接口出错[LoginController.loginForTourist]{}", e.toString());
      return new StatusVO(0, "服务出错了，请重试！");
    }
  }

  /**
   * 用户登出账户
   * @return return status：401(haven login) or 301(logout success)
   */
  @GetMapping("out")
  @ApiOperation(value = "登出", notes = "无参数要求")
  public void logout(HttpServletRequest request, HttpServletResponse response, @ApiIgnore HttpSession session){
    
    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") != null) {
      log.info("用户登出: {} | {}", session.getAttribute("userId"), request.getHeader("x-real-ip"));
      session.removeAttribute("userId");
      session.removeAttribute("userType");
      response.setStatus(301);
      return;
    }
    response.setStatus(401);
    return;
  }

  /**对全局设定的访问错误进行处理 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ InvalidFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
  public StatusVO handler(Exception e) {
    log.warn("Bad request[LoginController]{}", e.toString());
    return new StatusVO(0, "非法请求，参数错误");
  }

}
