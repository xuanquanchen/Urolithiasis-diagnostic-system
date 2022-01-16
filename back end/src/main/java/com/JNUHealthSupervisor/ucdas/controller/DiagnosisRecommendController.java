package com.JNUHealthSupervisor.ucdas.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import springfox.documentation.annotations.ApiIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.JNUHealthSupervisor.ucdas.VO.PredictResultVO;
import com.JNUHealthSupervisor.ucdas.VO.ResultVO;
import com.JNUHealthSupervisor.ucdas.VO.StatusVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;
import com.JNUHealthSupervisor.ucdas.service.Impl.DiagnosisRecordServiceImpl;
import com.JNUHealthSupervisor.ucdas.service.Impl.ModelPredictServiceImpl;
import com.JNUHealthSupervisor.ucdas.utils.AesUtil;
import com.JNUHealthSupervisor.ucdas.utils.ValidList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 访问模型业务诊断的请求接口
 * @session userType: -1[游客] 0[用户] 1[医生]
 * @session userId: `游客${aes加密后的hex}`[游客] userId[用户|医生]
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@RestController
@Api(tags = "模型预测接口")
@RequestMapping("/api/diagnosis")
public class DiagnosisRecommendController {

  @Autowired
  private ModelPredictServiceImpl modelPredictServiceImpl;

  @Autowired
  private DiagnosisRecordServiceImpl diagnosisRecordServiceImpl;

  /**
   * 单次诊断访问接口(for user and doctor)
   * @param features {@code FeaturesDTO}
   * @return success then return{@code List<PredictResultVO>}
   * while error return the error message
   * @see FeaturesDTO
   * @see PredictResultVO
   */
  @PostMapping(value = "recommand")
  @ApiOperation(value = "模型预测", notes = "请求模型参数，见模型FeaturesDTO")
  public StatusVO singleRecommand(@RequestBody @Valid FeaturesDTO features,BindingResult bindingResult,HttpServletResponse response,@ApiIgnore HttpSession session) {
    
    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    } else if (session.getAttribute("userType").equals(-1)) {
      /**userType为-1说明用户是游客 */
      return new StatusVO(0, "用户没有权限！");
    }

    /**校验前端请求格式是否满足FeaturesDTO定义的参数格式，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[DiagnosisRecommendController.singleRecommand]{}", new ObjectMapper().writeValueAsString(features));
      } catch (JsonProcessingException e) {
        log.warn("非法请求[DiagnosisRecommendController.singleRecommand]{}", features.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**尝试返回模型预测结果 */
    PredictResultVO result = null;
    try {
      result = modelPredictServiceImpl.getModelPredict(features);
    } catch (RestClientException e) {
      log.error("模型预测错误[ModelPredictService.getModelPredict]{}", e.toString());
      return new StatusVO(0, "模型业务出错，请联系管理员");
    } catch (Exception e) {
      log.error("模型预测错误[ModelPredictService.getModelPredict]{}", e.toString());
      return new StatusVO(0, "发生错误，已进行记录");
    }
    /**对本次诊断信息进行记录 */
    diagnosisRecordServiceImpl.record(session.getAttribute("userId").toString(), session.getAttribute("userType").toString(), features, result.getResult());
    return new ResultVO(200, "获取成功", result);
  }

  /**
   * 多次诊断访问接口(only for doctor)
   * @param featureList {@code ValidList<FeaturesDTO>}
   * @return success then return{@code List<List<PredictResultVO>>}
   * while error return the error message
   * @see ValidList
   * @see FeaturesDTO
   * @see PredictResultVO
   */
  @PostMapping(value = "recommand/multi")
  @ApiOperation(value = "多次模型预测", notes = "请求模型参数数组，见模型FeaturesDTO")
  public StatusVO multiRecommand(@RequestBody @Valid ValidList<FeaturesDTO> featureList,BindingResult bindingResult,HttpServletResponse response,@ApiIgnore HttpSession session) {
    
    /**从session池读取用户信息，userType为null说明用户未登录 */
    if (session.getAttribute("userType") == null) {
      response.setStatus(401);
      return null;
    } else if (!session.getAttribute("userType").equals(1)) {
      /**userType不为1说明用户不是医生端用户 */
      return new StatusVO(0, "用户没有权限！");
    }

    /**校验前端请求格式是否满足ValidList<FeaturesDTO>，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[DiagnosisRecommendController.multiRecommand]{}", new ObjectMapper().writeValueAsString(featureList));
      } catch (JsonProcessingException e) {
        log.warn("非法请求[DiagnosisRecommendController.multiRecommand]{}", featureList.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**尝试返回多个模型预测结果 */
    List<PredictResultVO> results = null;
    try {
      results = modelPredictServiceImpl.getModelPredict(featureList);
      /**对本次诊断信息进行记录 */
      for (int i=0; i<results.size(); i++) {
        diagnosisRecordServiceImpl.record(session.getAttribute("userId").toString(), session.getAttribute("userType").toString(), featureList.get(i), results.get(i).getResult());
      }
    } catch (RestClientException e){
      log.error("模型预测错误[ModelPredictService.getModelPredict]{}", e.toString());
      return new StatusVO(0, "模型业务出错，请联系管理员");
    } catch (Exception e) {
      log.error("模型预测错误[ModelPredictService.getModelPredict]{}", e.toString());
      return new StatusVO(0, "发生错误，已进行记录");
    }
    return new ResultVO(200, "获取成功", results);
  }

  /**
   * 游客诊断访问接口
   * @param features {@code FeaturesDTO}
   * @return success then return{@code List<PredictResultVO>}
   * while error return the error message
   * @see FeaturesDTO
   * @see PredictResultVO
   */
  @PostMapping(value = "recommand/tourist")
  @ApiOperation(value = "游客模型预测", notes = "请求模型参数，见模型FeaturesDTO")
  public StatusVO singleRecommandForTourist(@RequestBody @Valid FeaturesDTO features,BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response,@ApiIgnore HttpSession session) {
    
    /**从session池读取游客信息，userType不为1说明游客未登录 */
    if (session.getAttribute("userType") == null || !session.getAttribute("userType").equals(-1)) {
      response.setStatus(401);
      return null;
    }

    /**校验前端请求格式是否满足FeaturesDTO，并对错误转换为json打印 */
    if (bindingResult.hasErrors()) {
      try {
        log.warn("非法请求[DiagnosisRecommendController.singleRecommandForTourist]{}", new ObjectMapper().writeValueAsString(features));
      } catch (JsonProcessingException e) {
        log.warn("非法请求[DiagnosisRecommendController.singleRecommandForTourist]{}", features.toString());
      }
      return new StatusVO(0, bindingResult.getFieldError().getDefaultMessage());
    }

    /**尝试返回模型预测结果 */
    PredictResultVO result = null;
    try {
      result = modelPredictServiceImpl.getModelPredict(features);
    } catch (Exception e) {
      log.error("模型预测错误[ModelPredictService.getAllModelPredict]{}", e.toString());
      return new StatusVO(0, "发生错误，已进行记录");
    }
    /**生成用户名 */
    String userName = "ERRORID";
    try {
      userName = Base64.encodeBase64String(AesUtil.encrypt(request.getHeader("x-real-ip"), "fYp15(5xw%n6Z%Su"));
    } catch(Exception e) {
      log.error("游客id生成错误[DiagnosisRecommendController.singleRecommandForTourist]{}", e.toString());
    }
    if (userName.length() >= 45) {
      userName = userName.substring(0, 45);
    }
    /**对本次诊断信息进行记录 */
    diagnosisRecordServiceImpl.record(userName, "-1", features, result.getResult());
    return new ResultVO(200, "获取成功", result);
  }

  /**对全局设定的访问错误进行处理 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({ InvalidFormatException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
  public StatusVO handler(Exception e) {
    log.warn("Bad request[DiagnosisRecommendController]{}", e.toString());
    return new StatusVO(0, "非法请求，参数错误");
  }

}
