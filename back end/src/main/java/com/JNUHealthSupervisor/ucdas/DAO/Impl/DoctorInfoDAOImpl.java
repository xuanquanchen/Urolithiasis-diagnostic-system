package com.JNUHealthSupervisor.ucdas.DAO.Impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.JNUHealthSupervisor.ucdas.DAO.DoctorInfoDAO;
import com.JNUHealthSupervisor.ucdas.domain.BasicAccount;
import com.JNUHealthSupervisor.ucdas.domain.doctor.DoctorInfo;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 医生账户信息数据库接口(only for doctor)
 * @author Chen Yixing
 * @version 1.0.0
 */
@Repository
public class DoctorInfoDAOImpl implements DoctorInfoDAO{

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Override
  public BasicAccount getAccount(String userId) {
    RowMapper<BasicAccount> rowMapper = new BeanPropertyRowMapper<BasicAccount>(BasicAccount.class);
    return jdbcTemplate.queryForObject("SELECT userId,pw,isAvailable FROM doctor.info WHERE userId=?", rowMapper, userId);
  }

  @Override
  public DoctorInfo getInfo(String userId) {
    RowMapper<DoctorInfo> rowmapper = new BeanPropertyRowMapper<DoctorInfo>(DoctorInfo.class);
    return jdbcTemplate.queryForObject("SELECT userId,userName,phoneNum,isAvailable,avatarUrl FROM doctor.info WHERE userId=?", rowmapper, userId);
  }

}
