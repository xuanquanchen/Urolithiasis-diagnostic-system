package com.JNUHealthSupervisor.ucdas.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * 基本账户信息数据持续层对象
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
public class BasicAccount implements Serializable {
  
  private String userId;
  
  private String pw;

  private Boolean isAvailable;

}
