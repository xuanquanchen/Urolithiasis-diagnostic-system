package com.JNUHealthSupervisor.ucdas.DAO;

import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;

public interface DoctorRecordDAO {

  /**
   * 医生诊断记录非筛选查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchAll(String userId, int currentPage, int pageSize);

  /**
   * 医生诊断记录按患者姓名查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByName(String userId, String name, int currentPage, int pageSize);

  /**
   * 医生诊断记录按诊断时间查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByTime(String userId, String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 医生诊断记录按患者姓名及诊断时间查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByNameAndTime(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 医生对所有用户诊断记录无筛选查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchUserAll(int currentPage, int pageSize);

  /**
   * 医生对所有用户诊断记录按患者姓名查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchUserByName(String name, int currentPage, int pageSize);

  /**
   * 医生对所有用户诊断记录按诊断时间查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchUserByTime(String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 医生对所有用户诊断记录按患者姓名及诊断时间查询
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchUserByNameAndTime(String name, String beginTime, String endTime, int currentPage, int pageSize);
  
  /**
   * 记录医生诊断记录
   * @return {@code int} 数据库改动条数
   */
  public int record(String userId, FeaturesDTO features, double[] possibilties);

}
