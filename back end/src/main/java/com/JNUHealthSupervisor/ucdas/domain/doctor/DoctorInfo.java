package com.JNUHealthSupervisor.ucdas.domain.doctor;

import lombok.Data;
import java.io.Serializable;

/**
 * 医生账户信息数据持续层对象
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
public class DoctorInfo implements Serializable {

  private String userId;

  private String userName;
  
  private String phoneNum;

  private String avatarUrl;

  private Boolean isAvailable;

}
