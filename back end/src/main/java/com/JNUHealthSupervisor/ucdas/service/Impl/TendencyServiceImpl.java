package com.JNUHealthSupervisor.ucdas.service.Impl;

import java.util.Date;
import java.util.List;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.TendencyInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.VO.doctor.TendencyVO;
import com.JNUHealthSupervisor.ucdas.domain.doctor.Tendency;
import com.JNUHealthSupervisor.ucdas.service.TendencyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 结石类型变化趋势业务
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@Service
public class TendencyServiceImpl implements TendencyService {

  @Autowired
  private TendencyInfoDAOImpl tendencyInfoDAOImpl;

  @Override
  public TendencyVO getTodayTendency() {
    /**定义统一日期格式转换器 */
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date now = new Date();
    /**读取一周变化趋势 */
    List<Tendency> tendencies = tendencyInfoDAOImpl.readByTimePeriod(
      dateFormat.format(new Date(now.getTime() - 7*24*3600*1000L)).replaceFirst("\\d{2}:\\d{2}:\\d{2}$", "12:00:00"),
      dateFormat.format(now)
    );
    /**读取今日趋势 */
    Tendency todayTendency = tendencies.get(0);
    /**记录一周基本趋势 */
    int[][] weeklyTendency = new int[4][7];
    for (int iter=0;iter<tendencies.size();iter++) {
      weeklyTendency[0][iter] = tendencies.get(iter).getTodayA();
      weeklyTendency[1][iter] = tendencies.get(iter).getTodayB();
      weeklyTendency[2][iter] = tendencies.get(iter).getTodayC();
      weeklyTendency[3][iter] = tendencies.get(iter).getTodayD();
    }
    return new TendencyVO(new float[]{todayTendency.getGrowthA(), todayTendency.getGrowthB(), todayTendency.getGrowthC(), todayTendency.getGrowthD()},
      new int[]{todayTendency.getTotalA(), todayTendency.getTotalB(), todayTendency.getTotalC(), todayTendency.getTotalD()}, weeklyTendency);
  }

  @Override
  public boolean refreshTendency() {
    try {
      /**定义统一日期格式转换器 */
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      Date now = new Date();
      /**获取当前时间、昨日时间（12点算起，因为是createTime所以保守<12点） */
      String currentTime = dateFormat.format(now);
      String yesterdayTime = dateFormat.format(new Date(now.getTime() - 1*24*3600*1000L)).replaceFirst("\\d{2}:\\d{2}:\\d{2}$", "12:00:00");
      /**获取今日各类型结石总计 */
      int[] todayTypeNum = tendencyInfoDAOImpl.censusAll(currentTime);
      /**获取昨日趋势变化 */
      Tendency tendency = tendencyInfoDAOImpl.read(yesterdayTime);
      /**计算昨日统计的各类型总计、日增加总计 */
      int[] yesterdayTotal = new int[]{tendency.getTotalA(), tendency.getTotalB(), tendency.getTotalC(), tendency.getTotalD()};
      int[] yesterdayTypeNum = new int[]{tendency.getTodayA(), tendency.getTodayB(), tendency.getTodayC(), tendency.getTodayD()};
      /**放入今日各类型结石总计 */
      tendency.setTodayA(todayTypeNum[0]);
      tendency.setTodayB(todayTypeNum[1]);
      tendency.setTodayC(todayTypeNum[2]);
      tendency.setTodayD(todayTypeNum[3]);
      /**放入各类型结石总计 */
      tendency.setTotalA(yesterdayTotal[0] + todayTypeNum[0]);
      tendency.setTotalB(yesterdayTotal[1] + todayTypeNum[1]);
      tendency.setTotalC(yesterdayTotal[2] + todayTypeNum[2]);
      tendency.setTotalD(yesterdayTotal[3] + todayTypeNum[3]);
      /**计算今日各类型结石变化比例，除以0时设为1 */
      if (yesterdayTypeNum[0] == 0) {
        tendency.setGrowthA((float) 1);
      } else {
        tendency.setGrowthA(new BigDecimal(todayTypeNum[0]-yesterdayTypeNum[0]).divide(new BigDecimal(yesterdayTypeNum[0]), 2, RoundingMode.HALF_UP).floatValue());
      }
      if (yesterdayTypeNum[1] == 0) {
        tendency.setGrowthB((float) 1);
      } else {
        tendency.setGrowthB(new BigDecimal(todayTypeNum[1]-yesterdayTypeNum[1]).divide(new BigDecimal(yesterdayTypeNum[1]), 2, RoundingMode.HALF_UP).floatValue());
      }
      if (yesterdayTypeNum[2] == 0) {
        tendency.setGrowthC((float) 1);
      } else {
        tendency.setGrowthC(new BigDecimal(todayTypeNum[2]-yesterdayTypeNum[2]).divide(new BigDecimal(yesterdayTypeNum[2]), 2, RoundingMode.HALF_UP).floatValue());
      }
      if (yesterdayTypeNum[3] == 0) {
        tendency.setGrowthD((float) 1);
      } else {
        tendency.setGrowthD(new BigDecimal(todayTypeNum[3]-yesterdayTypeNum[3]).divide(new BigDecimal(yesterdayTypeNum[3]), 2, RoundingMode.HALF_UP).floatValue());
      }
      /**刷新数据库记录 */
      if (tendencyInfoDAOImpl.refresh(tendency, currentTime) == 1) {
        return true;
      }
    } catch (Exception e) {
      log.error("结石类型变化趋势业务出错[TendencyService.refreshTendency]{}", e.toString());
    }
    return false;
  }

}
