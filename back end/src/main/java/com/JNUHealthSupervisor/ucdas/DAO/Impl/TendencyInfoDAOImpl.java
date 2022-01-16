package com.JNUHealthSupervisor.ucdas.DAO.Impl;

import java.util.Map;

import com.JNUHealthSupervisor.ucdas.DAO.TendencyInfoDAO;
import com.JNUHealthSupervisor.ucdas.domain.doctor.Tendency;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 医生端结石类型变化趋势数据库接口(only for doctor)
 * @author Chen Yixing
 * @version 1.0.0
 */
@Repository
public class TendencyInfoDAOImpl implements TendencyInfoDAO {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int create(Tendency tendency) {
    return jdbcTemplate.update("INSERT INTO doctor.tendency(totalA,totalB,totalC,totalD) VALUES(?,?,?,?)",
      tendency.getTotalA(),
      tendency.getTotalB(),
      tendency.getTotalC(),
      tendency.getTotalD()
    );
  }

  @Override
  public int refresh(Tendency tendency, String currentTime) {
    return jdbcTemplate.update("UPDATE doctor.tendency SET todayA=?,todayB=?,todayC=?,todayD=?,totalA=?,totalB=?,totalC=?,totalD=?,growthA=?,growthB=?,growthC=?,growthD=? WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT 1",
      tendency.getTodayA(),
      tendency.getTodayB(),
      tendency.getTodayC(),
      tendency.getTodayD(),
      tendency.getTotalA(),
      tendency.getTotalB(),
      tendency.getTotalC(),
      tendency.getTotalD(),
      tendency.getGrowthA(),
      tendency.getGrowthB(),
      tendency.getGrowthC(),
      tendency.getGrowthD(),
      currentTime
    );
  }

  @Override
  public int[] censusAll(String currentTime) {
    /**从今日00:00:00开始查询 */
    String beginTime = currentTime.replaceFirst("\\d{2}:\\d{2}:\\d{2}$", "00:00:00");
    int[] census = new int[4];
    Map<String, Object> totalTypes = jdbcTemplate.queryForMap("SELECT SUM(CASE predict WHEN 0 THEN 1 ELSE 0 END) typeA,SUM(CASE predict WHEN 1 THEN 1 ELSE 0 END) typeB,SUM(CASE predict WHEN 2 THEN 1 ELSE 0 END) typeC,SUM(CASE predict WHEN 3 THEN 1 ELSE 0 END) typeD "
      +"FROM ((SELECT * FROM doctor.dignos_record WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) "
      +"UNION ALL (SELECT * FROM user.dignos_record WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?) "
      +"UNION ALL (SELECT * FROM user.tourist_record WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=?)) as totalType", beginTime, currentTime, beginTime, currentTime, beginTime, currentTime);
    census[0] = totalTypes.get("typeA")==null? 0 : Integer.parseInt(String.valueOf(totalTypes.get("typeA")));
    census[1] = totalTypes.get("typeB")==null? 0 : Integer.parseInt(String.valueOf(totalTypes.get("typeB")));
    census[2] = totalTypes.get("typeC")==null? 0 : Integer.parseInt(String.valueOf(totalTypes.get("typeC")));
    census[3] = totalTypes.get("typeD")==null? 0 : Integer.parseInt(String.valueOf(totalTypes.get("typeD")));
    return census;
  }

  @Override
  public Tendency read(String time) {
    RowMapper<Tendency> rowMapper = new BeanPropertyRowMapper<Tendency>(Tendency.class);
    return jdbcTemplate.queryForObject("SELECT * FROM doctor.tendency WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC LIMIT 1", rowMapper, time);
  }

  @Override
  public List<Tendency> readByTimePeriod(String beginTime, String endTime) {
    RowMapper<Tendency> rowMapper = new BeanPropertyRowMapper<Tendency>(Tendency.class);
    return jdbcTemplate.query("SELECT * FROM doctor.tendency WHERE DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')>=? AND DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s')<=? ORDER BY createTime DESC", rowMapper, beginTime, endTime);
  }
  
}
