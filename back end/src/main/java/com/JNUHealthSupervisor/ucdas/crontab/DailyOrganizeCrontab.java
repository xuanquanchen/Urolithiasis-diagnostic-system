package com.JNUHealthSupervisor.ucdas.crontab;

public interface DailyOrganizeCrontab {

  /**
   * 每日00:00:00进行今日趋势的记录创建
   */
  public void createTendency();

  /**
   * 每小时的10分、40分以及23:59:30刷新一次今日趋势记录，防止与0时发生重叠
   */
  public void calculateTendency();

}
