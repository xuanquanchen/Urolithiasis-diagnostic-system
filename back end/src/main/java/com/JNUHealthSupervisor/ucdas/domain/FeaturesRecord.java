package com.JNUHealthSupervisor.ucdas.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * 诊断记录数据持续层对象
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
public class FeaturesRecord implements Serializable {
  
  private String name;

  private String createTime;
  
  private Integer sex;
  
  private Integer age;
  
  private Integer marrialStatus;

  private Integer birthPlace;

  private Integer C_C;

  private Integer fever;

  private Integer nausea;

  private Integer gh;

  private Integer dm;

  private Integer nh;

  private Integer ce;

  private Integer cr;

  private Integer hb;

  private Integer bun;

  private Integer smoking;

  private Integer alcohol;

  private Integer opium;

  private Integer bp;

  private Integer fh;

  private Integer diseaseDiagnosis;

  private Integer uricAcid;

  private Integer ca;

  private Double typeA;

  private Double typeB;

  private Double typeC;

  private Double typeD;

  private Integer predict;

  private Integer reality;

}
