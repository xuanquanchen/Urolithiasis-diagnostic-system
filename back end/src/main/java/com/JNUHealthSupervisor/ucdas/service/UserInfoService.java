package com.JNUHealthSupervisor.ucdas.service;

import com.JNUHealthSupervisor.ucdas.VO.user.UserInfoVO;

public interface UserInfoService {

  /**
   * 获取普通用户账户信息
   * @return {@code UserInfoVO}
   * @see UserInfoVO
   */
  public UserInfoVO getInfo(String userId);
  
}
