package com.JNUHealthSupervisor.ucdas.service;

import com.JNUHealthSupervisor.ucdas.VO.doctor.DoctorInfoVO;

public interface DoctorInfoService {

  /**
   * 获取医生账户信息
   * @return {@code DoctorInfoVO}
   * @see DoctorInfoVO
   */
  public DoctorInfoVO getInfo(String userId);

}
