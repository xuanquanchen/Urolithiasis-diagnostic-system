package com.JNUHealthSupervisor.ucdas.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.DoctorRecordDAOImpl;
import com.JNUHealthSupervisor.ucdas.DAO.Impl.UserRecordDAOImpl;
import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;
import com.JNUHealthSupervisor.ucdas.service.DiagnosisRecordService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 诊断结果记录业务
 * @author Chen Yixing
 * @version 1.0.0
 */
@Slf4j
@Service
public class DiagnosisRecordServiceImpl implements DiagnosisRecordService {

  @Autowired
  private DoctorRecordDAOImpl doctorRecordDAOImpl;

  @Autowired
  private UserRecordDAOImpl userRecordDAOImpl;

  @Override
  public FeaturesRecordVO searchForDoctor(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize) {
    if ((beginTime == null || endTime == null) && (name == null || name.trim().isEmpty())) {
      return doctorRecordDAOImpl.searchAll(userId, currentPage, pageSize);
    } else if (name == null || name.trim().isEmpty()) {
      return doctorRecordDAOImpl.searchByTime(userId, beginTime, endTime, currentPage, pageSize);
    } else if (beginTime == null || endTime == null) {
      return doctorRecordDAOImpl.searchByName(userId, name, currentPage, pageSize);
    } else {
      return doctorRecordDAOImpl.searchByNameAndTime(userId, name, beginTime, endTime, currentPage, pageSize);
    }
  }

  @Override
  public FeaturesRecordVO searchUserForDoctor(String name, String beginTime, String endTime, int currentPage, int pageSize) {
    if ((beginTime == null || endTime == null) && (name == null || name.trim().isEmpty())) {
      return doctorRecordDAOImpl.searchUserAll(currentPage, pageSize);
    } else if (name == null || name.trim().isEmpty()) {
      return doctorRecordDAOImpl.searchUserByTime(beginTime, endTime, currentPage, pageSize);
    } else if (beginTime == null || endTime == null) {
      return doctorRecordDAOImpl.searchUserByName(name, currentPage, pageSize);
    } else {
      return doctorRecordDAOImpl.searchUserByNameAndTime(name, beginTime, endTime, currentPage, pageSize);
    }
  }

  @Override
  public FeaturesRecordVO searchForUser(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize) {
    if ((beginTime == null || endTime == null) && name == null) {
      return userRecordDAOImpl.searchAll(userId, currentPage, pageSize);
    } else if (name == null) {
      return userRecordDAOImpl.searchByTime(userId, beginTime, endTime, currentPage, pageSize);
    } else if (beginTime == null || endTime == null) {
      return userRecordDAOImpl.searchByName(userId, name, currentPage, pageSize);
    } else {
      return userRecordDAOImpl.searchByNameAndTime(userId, name, beginTime, endTime, currentPage, pageSize);
    }
  }

  @Override
  public void record(String userId, String userType, FeaturesDTO features, double[] possibilties) {
    try {
      /**记录数据库修改记录，默认置0 */
      int status = 0;
      if (userType.equals("1")) {
        status = doctorRecordDAOImpl.record(userId, features, possibilties);
      } else if (userType.equals("0")) {
        status = userRecordDAOImpl.record(userId, features, possibilties);
      } else if (userType.equals("-1")) {
        status = userRecordDAOImpl.recordForTourist(userId, features, possibilties);
      }
      if (status == 0) {
        log.error("记录预测信息失败[DiagnosisRecordService.record]用户{}-{}|数据库更新失败", userType, userId);
      }
    } catch (Exception e) {
      log.error("记录预测信息失败[DiagnosisRecordService.record]{}", e.toString());
    }
  }
  
}
