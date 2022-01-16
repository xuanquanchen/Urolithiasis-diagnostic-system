package com.JNUHealthSupervisor.ucdas.service.Impl;

import lombok.extern.slf4j.Slf4j;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.DoctorInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.DAO.Impl.UserInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.domain.BasicAccount;
import com.JNUHealthSupervisor.ucdas.service.AccountVerifyService;
import com.JNUHealthSupervisor.ucdas.utils.AesUtil;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Service;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * 账户信息校验业务
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@Service
public class AccountVerifyServiceImpl implements AccountVerifyService {

  @Autowired
  private DoctorInfoDAOImpl doctorInfoDAOImpl;

  @Autowired
  private UserInfoDAOImpl userInfoDAOImpl;
  
  @Override
  public String[] accountDecrypt(Integer ul, Integer pl, String rsa) {
    try{
      /**获取密钥 */
      String key = getKey(pl);
      /**尝试解密 */
      String decrypted = AesUtil.decrypt(Base64.decodeBase64(rsa), key);
      /**用户名过长 */
      if (ul >= decrypted.length()) {
        return null;
      }
      return new String[]{decrypted.substring(0, 1), decrypted.substring(1, ul+1), decrypted.substring(ul+1)};
    } catch (Exception e) {
      log.error("解密出错[AccountVerifyService.accountDecrypt]{}", e.toString());
      return null;
    }
  }

  @Override
  public Boolean checkDoctorAccount(String userId, String password) {
    try {
      /**进行数据库查询 */
      BasicAccount user = doctorInfoDAOImpl.getAccount(userId);
      /**账户不可用 */
      if (!user.getIsAvailable()) {
        return false;
      }
      /**检查密码是否有误 */
      if (AesUtil.decrypt(HexUtils.fromHexString(user.getPw()), "x%SxyA4naBOT6+xY").equals(password)) {
        return true;
      } else {
        return false;
      }
    } catch (EmptyResultDataAccessException e) {
      /**找不到用户名 */
      return false;
    } catch (Exception e) {
      log.error("账户校验业务出错[AccountVerifyService.checkDoctorAccount]{}", e.toString());
      return null;
    }
  }

  @Override
  public Boolean checkUserAccount(String userId, String password) {
    try {
      /**进行数据库查询 */
      BasicAccount user = userInfoDAOImpl.getAccount(userId);
      /**账户不可用 */
      if (!user.getIsAvailable()) {
        return false;
      }
      /**检查密码是否有误 */
      if (AesUtil.decrypt(HexUtils.fromHexString(user.getPw()), "x%SxyA4naBOT6+xY").equals(password)) {
        return true;
      } else {
        return false;
      }
    } catch (EmptyResultDataAccessException e) {
      /**找不到用户名 */
      return false;
    } catch (Exception e) {
      log.error("账户校验业务出错[AccountVerifyService.checkDoctorAccount]{}", e.toString());
      return null;
    }
  }

  /**
   * 获取密钥
   * @param pl 密钥序号
   * @return {@code String} 密钥
   */
  private String getKey(Integer pl){
    switch(pl){
      case 0x00:
        return "6c72eb20b0270b21";
      case 0x01:
        return "9770cd5f69af4c3f";
      case 0x02:
        return "5475a79972e00ab9";
      case 0x03:
        return "1bce2a8d8d4cdee6";
      case 0x04:
        return "eb19eca22327bbea";
      case 0x05:
        return "846d58ca0b04b2ca";
      case 0x06:
        return "6730d305996c98f4";
      case 0x07:
        return "d499b685e28f149f";
      case 0x08:
        return "cf40d0cc0330fa77";
      case 0x09:
        return "c1febab486fbb3f4";
      default:
        return null;
    }
  }

}
