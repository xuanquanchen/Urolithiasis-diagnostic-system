package com.JNUHealthSupervisor.ucdas.DAO;

import com.JNUHealthSupervisor.ucdas.domain.BasicAccount;
import com.JNUHealthSupervisor.ucdas.domain.user.UserInfo;

public interface UserInfoDAO {

  /**
   * 基础账户信息获取（用户名、密码、账户可用性）
   * @return {@code BasicAccount}
   * @see BasicAccount
   */
  public BasicAccount getAccount(String userId);

  /**
   * 账户信息获取
   * @return {@code UserInfo}
   * @see UserInfo
   */
  public UserInfo getInfo(String userId);

}
