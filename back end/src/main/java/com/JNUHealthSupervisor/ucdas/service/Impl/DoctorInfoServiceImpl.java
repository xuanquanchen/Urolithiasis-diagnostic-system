package com.JNUHealthSupervisor.ucdas.service.Impl;

import org.springframework.stereotype.Service;

import com.JNUHealthSupervisor.ucdas.DAO.Impl.DoctorInfoDAOImpl;
import com.JNUHealthSupervisor.ucdas.VO.doctor.DoctorInfoVO;
import com.JNUHealthSupervisor.ucdas.domain.doctor.DoctorInfo;
import com.JNUHealthSupervisor.ucdas.service.DoctorInfoService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 医生账户信息业务
 * @author Chen Yixing
 * @version 1.0.0
 */
@Service
public class DoctorInfoServiceImpl implements DoctorInfoService {

  @Autowired
  private DoctorInfoDAOImpl doctorInfoDAOImpl;

  @Override
  public DoctorInfoVO getInfo(String userId) {
    DoctorInfo doctorInfo = doctorInfoDAOImpl.getInfo(userId);
    if (doctorInfo.getIsAvailable()) {
      return new DoctorInfoVO(doctorInfo.getUserId(), doctorInfo.getUserName(), doctorInfo.getPhoneNum(), doctorInfo.getAvatarUrl());
    }
    return null;
  }
  
}
