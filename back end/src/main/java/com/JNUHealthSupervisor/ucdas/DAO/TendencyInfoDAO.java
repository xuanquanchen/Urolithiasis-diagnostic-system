package com.JNUHealthSupervisor.ucdas.DAO;

import java.util.List;

import com.JNUHealthSupervisor.ucdas.domain.doctor.Tendency;

public interface TendencyInfoDAO {

  /**
   * 创建每日结石基础变化数据库记录
   * @param tendency 结石基础变化记录
   * @return {@code int}数据库改动条数
   * @see Tendency
   */
  public int create(Tendency tendency);

  /**
   * 刷新每日结石变化数据库记录
   * @param tendency 结石变化记录
   * @param currentTime 当前时间
   * @return {@code int}数据库改动条数
   * @see Tendency
   */
  public int refresh(Tendency tendency, String currentTime);

  /**
   * 获取今日各类型结石总计
   * @param currentTime 当前时间
   * @return {@code int[4]}各类型结石总计
   */
  public int[] censusAll(String currentTime);

  /**
   * 读取某日结石变化数据库记录
   * @param time 读取时间
   * @return {@code Tendency}
   * @see Tendency
   */
  public Tendency read(String time);

  /**
   * 读取某段时间结石变化数据库记录
   * @param beginTime 开始时间
   * @param endTime 结束时间
   * @return {@code List<Tendency>}
   * @see Tendency
   */
  public List<Tendency> readByTimePeriod(String beginTime, String endTime);
  
}
