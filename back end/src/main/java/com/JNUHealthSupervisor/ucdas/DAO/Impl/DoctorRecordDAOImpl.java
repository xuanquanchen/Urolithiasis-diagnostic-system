package com.JNUHealthSupervisor.ucdas.DAO.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.JNUHealthSupervisor.ucdas.DAO.DoctorRecordDAO;
import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.domain.FeaturesRecord;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;
import com.JNUHealthSupervisor.ucdas.utils.ArrayUtil;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 医生端诊断记录数据库接口(only for doctor)
 * @author Chen Yixing
 * @version 1.0.0
 */
@Repository
public class DoctorRecordDAOImpl implements DoctorRecordDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public FeaturesRecordVO searchAll(String userId, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM doctor.dignos_record WHERE userId=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByName(String userId, String name, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM doctor.dignos_record WHERE userId=? AND name LIKE ? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, "%"+name+"%", pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByTime(String userId, String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM doctor.dignos_record WHERE userId=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchByNameAndTime(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("SELECT SQL_CALC_FOUND_ROWS * FROM doctor.dignos_record WHERE userId=? AND name LIKE ? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT ?,?", rowMapper, userId, "%"+name+"%", beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchUserAll(int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("(SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record) "
        +"UNION ALL (SELECT * FROM user.tourist_record) ORDER BY createTime DESC LIMIT ?,?", rowMapper, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchUserByName(String name, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("(SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE name LIKE ?) "
        +"UNION ALL (SELECT * FROM user.tourist_record WHERE name LIKE ?) ORDER BY createTime DESC LIMIT ?,?", rowMapper, "%"+name+"%", "%"+name+"%", pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchUserByTime(String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("(SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) "
        +"UNOIN ALL (SELECT * FROM user.tourist_record WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) ORDER BY createTime DESC LIMIT ?,?", rowMapper, beginTime, endTime, beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public FeaturesRecordVO searchUserByNameAndTime(String name, String beginTime, String endTime, int currentPage, int pageSize) {
    RowMapper<FeaturesRecord> rowMapper = new BeanPropertyRowMapper<FeaturesRecord>(FeaturesRecord.class);
    return new FeaturesRecordVO(
      jdbcTemplate.query("(SELECT SQL_CALC_FOUND_ROWS * FROM user.dignos_record WHERE name LIKE ? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) "
        +"UNION ALL (SELECT * FROM user.tourist_record WHERE name LIKE ? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) ORDER BY createTime DESC LIMIT ?,?", rowMapper, "%"+name+"%", beginTime, endTime, "%"+name+"%", beginTime, endTime, pageSize*(currentPage-1), pageSize),
      jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class)
    );
  }

  @Override
  public int record(String userId, FeaturesDTO features, double[] possibilties) {
    return jdbcTemplate.update("INSERT INTO doctor.dignos_record(userId,name,sex,age,marrialStatus,birthPlace,C_C,fever,nausea,gh,dm,nh,ce,cr,hb,bun,smoking,alcohol,opium,bp,fh,diseaseDiagnosis,uricAcid,ca,typeA,typeB,typeC,typeD,predict) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
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
