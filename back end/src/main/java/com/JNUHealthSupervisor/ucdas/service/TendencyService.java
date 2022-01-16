package com.JNUHealthSupervisor.ucdas.service;

import com.JNUHealthSupervisor.ucdas.VO.doctor.TendencyVO;

public interface TendencyService {

  /**
   * 返回今日的近一周结石类型变化趋势
   * @return {@code TendencyVO}
   * @see TendencyVO
   */
  public TendencyVO getTodayTendency();

  /**
   * 刷新今日结石类型变化趋势
   * @return {@code boolean} 刷新结果
   */
  public boolean refreshTendency();

}
