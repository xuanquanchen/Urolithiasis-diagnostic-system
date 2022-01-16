package com.JNUHealthSupervisor.ucdas.service;

public interface AccountVerifyService {
  
  /**
   * 对前端加密的登录信息进行解密
   * @param ul 用户名长度
   * @param pl 加密key的序号
   * @param rsa 密文
   * @return {@code String[3]:}[0]用户类型[1]用户名[2]密码 {@code null:}解密出错
   */
  public String[] accountDecrypt(Integer ul, Integer pl, String rsa);

  /**
   * 校验医生端用户是否存在、密码是否有误
   * @param userId 用户名
   * @param password 密码
   * @return {@code Boolean} null:校验出错
   */
  public Boolean checkDoctorAccount(String userId, String password);

  /**
   * 校验用户端用户是否存在、密码是否有误
   * @param userId 用户名
   * @param password 密码
   * @return {@code Boolean} null:校验出错
   */
  public Boolean checkUserAccount(String userId, String password);

}
