package com.JNUHealthSupervisor.ucdas.domain.doctor;

import lombok.Data;
import java.io.Serializable;

/**
 * 各类型结石变化数据持续层对象
 * @author Chen Yixing
 * @version 1.0.0
 */
@Data
public class Tendency implements Serializable {

  private String createTime;

  private Integer todayA;

  private Integer todayB;

  private Integer todayC;

  private Integer todayD;

  private Integer totalA;

  private Integer totalB;

  private Integer totalC;

  private Integer totalD;

  private Float growthA;

  private Float growthB;

  private Float growthC;

  private Float growthD;

}
