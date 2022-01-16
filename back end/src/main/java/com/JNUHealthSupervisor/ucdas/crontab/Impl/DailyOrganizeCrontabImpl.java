package com.JNUHealthSupervisor.ucdas.crontab.Impl;

import java.util.Date;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.TendencyInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.crontab.DailyOrganizeCrontab;
import com.JNUHealthSupervisor.ucdas.domain.doctor.Tendency;
import com.JNUHealthSupervisor.ucdas.service.Impl.TendencyServiceImpl;

import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 每日任务：定时计算结石类型记录的变化趋势
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@Component
public class DailyOrganizeCrontabImpl implements DailyOrganizeCrontab {

  @Autowired
  private TendencyInfoDAOImpl tendencyInfoDAOImpl;

  @Autowired
  private TendencyServiceImpl tendencyServiceImpl;

  @Override
  @Scheduled(cron = "0 0 0 * * ?")
  public void createTendency() {
    try {
      Tendency yesterdayTendency = tendencyInfoDAOImpl.read(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
      if(tendencyInfoDAOImpl.create(yesterdayTendency) != 0){
        log.info("基础趋势记录[DailyOrganizeCrontab.createTendency]创建表单成功。");
      } else {
        log.error("基础趋势记录[DailyOrganizeCrontab.createTendency]无数据库改动记录。");
      }
    } catch (Exception e) {
      log.error("基础趋势记录[DailyOrganizeCrontab.createTendency]{}", e.toString());
    }
  }

  @Override
  @Scheduled(cron = "0 10/30 * * * ?")
  @Scheduled(cron = "30 59 23 * * ?")
  public void calculateTendency() {
    if (tendencyServiceImpl.refreshTendency()) {
      log.info("基础趋势记录[DailyOrganizeCrontab.calculateTendency]更新表单成功。");
    } else {
      log.error("基础趋势记录[DailyOrganizeCrontab.calculateTendency]更新表单失败。");
    }
  }

}
