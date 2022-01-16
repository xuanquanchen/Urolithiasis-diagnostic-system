package com.JNUHealthSupervisor.ucdas.service;

import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;

public interface DiagnosisRecordService {

  /**
   * 记录所有用户记录预测信息
   * @param userId 用户名
   * @param userType 用户类型
   * @param features 用户诊断数据
   * @param possibilties 单一模型诊断结果
   * @see FeaturesDTO
   */
  public void record(String userId, String userType, FeaturesDTO features, double[] possibilties);

  /**
   * 医生对自己曾经的诊断进行查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchForDoctor(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 医生对所有普通用户的诊断进行查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchUserForDoctor(String name, String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 普通用户对自己曾经的诊断进行查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchForUser(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize);

}
