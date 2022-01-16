package com.JNUHealthSupervisor.ucdas.service.Impl;

import org.springframework.stereotype.Service;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.UserInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.VO.user.UserInfoVO;
import com.JNUHealthSupervisor.ucdas.domain.user.UserInfo;
import com.JNUHealthSupervisor.ucdas.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 普通用户账户信息业务
 * @author Chen Yixing
 * @version 1.0.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Autowired
  private UserInfoDAOImpl userInfoDAOImpl;

  @Override
  public UserInfoVO getInfo(String userId) {
    UserInfo userInfo = userInfoDAOImpl.getInfo(userId);
    if (userInfo.getIsAvailable()) {
      return new UserInfoVO(userInfo.getUserId(), userInfo.getUserName(), userInfo.getPhoneNum(), userInfo.getAvatarUrl(), userInfo.getIsVip());
    }
    return null;
  }
}
