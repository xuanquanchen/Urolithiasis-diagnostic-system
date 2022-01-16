package com.JNUHealthSupervisor.ucdas.service;

import java.util.List;

import com.JNUHealthSupervisor.ucdas.VO.PredictResultVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;

public interface ModelPredictService {

  /**
   * 返回所有模型预测结果
   * @param featuresDTO 用户诊断信息
   * @return {@code PredictResultVO}
   * @see PredictResultVO
   * @throws Exception
   */
  public PredictResultVO getModelPredict(FeaturesDTO featuresDTO) throws Exception;

  /**
   * 返回所有模型预测结果
   * @param featuresDTO 用户诊断信息
   * @return {@code List<PredictResultVO>}
   * @see PredictResultVO
   * @throws Exception
   */
  public List<PredictResultVO> getModelPredict(List<FeaturesDTO> featuresDTO) throws Exception;

}
