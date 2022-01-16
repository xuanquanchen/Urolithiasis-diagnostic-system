package com.JNUHealthSupervisor.ucdas.DAO.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.JNUHealthSupervisor.ucdas.DAO.UserRecordDAO;
import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.domain.FeaturesRecord;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;
import com.JNUHealthSupervisor.ucdas.utils.ArrayUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * 用户端诊断记录数据库接口(only for user|tourist)
 * @author Chen Yixing
 * @version 1.0.0
 */
@Repository
public class UserRecordDAOImpl implements UserRecordDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public FeaturesRecordVO searchAll(String userId, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE userId=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByName(String userId, String name, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE userId=? AND name LIKE ? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, "%"+name+"%", pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByTime(String userId, String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE userId=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByNameAndTime(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE userId=? AND name LIKE ? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, "%"+name+"%", beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public int record(String userId, FeaturesDTO features, double[] possibilties) {
    return jdbcTemplate.update("INSERT INTO user.dignos_record(userId,name,sex,age,marrialStatus,birthPlace,C_C,fever,nausea,gh,dm,nh,ce,cr,hb,bun,smoking,alcohol,opium,bp,fh,diseaseDiagnosis,uricAcid,ca,typeA,typeB,typeC,typeD,predict) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
      userId,
      features.getName(),
      features.getSex(),
      features.getAge(),
      features.getMarrialStatus(),
      features.getBirthPlace(),
      features.getC_C(),
      features.getFever(),
      features.getNausea(),
      features.getGh(),
      features.getDm(),
      features.getNh(),
      features.getCe(),
      features.getCr(),
      features.getHb(),
      features.getBun(),
      features.getSmoking(),
      features.getAlcohol(),
      features.getOpium(),
      features.getBp(),
      features.getFh(),
      features.getDiseaseDiagnosis(),
      features.getUricAcid(),
      features.getCa(),
      possibilties[0],
      possibilties[1],
      possibilties[2],
      possibilties[3],
      ArrayUtil.getMaxIndexForDoubles(possibilties)
    );
  }

  @Override
  public int recordForTourist(String userId, FeaturesDTO features, double[] possibilties) {
    return jdbcTemplate.update("INSERT INTO user.tourist_record(userId,name,sex,age,marrialStatus,birthPlace,C_C,fever,nausea,gh,dm,nh,ce,cr,hb,bun,smoking,alcohol,opium,bp,fh,diseaseDiagnosis,uricAcid,ca,typeA,typeB,typeC,typeD,predict) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
      userId,
      features.getName(),
      features.getSex(),
      features.getAge(),
      features.getMarrialStatus(),
      features.getBirthPlace(),
      features.getC_C(),
      features.getFever(),
      features.getNausea(),
      features.getGh(),
      features.getDm(),
      features.getNh(),
      features.getCe(),
      features.getCr(),
      features.getHb(),
      features.getBun(),
      features.getSmoking(),
      features.getAlcohol(),
      features.getOpium(),
      features.getBp(),
      features.getFh(),
      features.getDiseaseDiagnosis(),
      features.getUricAcid(),
      features.getCa(),
      possibilties[0],
      possibilties[1],
      possibilties[2],
      possibilties[3],
      ArrayUtil.getMaxIndexForDoubles(possibilties)
    );
  }
  
}
