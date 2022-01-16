package com.JNUHealthSupervisor.ucdas.DAO.Impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.JNUHealthSupervisor.ucdas.DAO.UserInfoDAO;
import com.JNUHealthSupervisor.ucdas.domain.BasicAccount;
import com.JNUHealthSupervisor.ucdas.domain.user.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 普通用户账户信息数据库接口(only for user)
 * @author Chen Yixing
 * @version 1.0.0
 */
@Repository
public class UserInfoDAOImpl implements UserInfoDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Override
  public BasicAccount getAccount(String userId) {
    RowMapper<BasicAccount> rowMapper = new BeanPropertyRowMapper<BasicAccount>(BasicAccount.class);
    return jdbcTemplate.queryForObject("SELECT userId,pw,isAvailable FROM user.info WHERE userId=?", rowMapper, userId);
  }

  @Override
  public UserInfo getInfo(String userId) {
    RowMapper<UserInfo> rowmapper = new BeanPropertyRowMapper<UserInfo>(UserInfo.class);
    return jdbcTemplate.queryForObject("SELECT userId,userName,phoneNum,isAvailable,avatarUrl,isVip FROM user.info WHERE userId=?", rowmapper, userId);
  }
}
