package com.JNUHealthSupervisor.ucdas.DAO;

import com.JNUHealthSupervisor.ucdas.VO.FeaturesRecordVO;
import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;

public interface UserRecordDAO {

  /**
   * 普通用户诊断记录非筛选查询(only for user)
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchAll(String userId, int currentPage, int pageSize);

  /**
   * 普通用户诊断记录按患者姓名查询(only for user)
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByName(String userId, String name, int currentPage, int pageSize);

  /**
   * 普通用户诊断记录诊断时间查询(only for user)
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByTime(String userId, String beginTime, String endTime, int currentPage, int pageSize);

  /**
   * 普通用户诊断记录按患者姓名及诊断时间查询(only for user)
   * @return {@code FeaturesRecordVO}
   * @see FeaturesRecordVO
   */
  public FeaturesRecordVO searchByNameAndTime(String userId, String name, String beginTime, String endTime, int currentPage, int pageSize);
  
  /**
   * 记录普通用户诊断记录(only for user)
   * @return {@code int} 数据库改动条数
   */
  public int record(String userId, FeaturesDTO features, double[] possibilties);

  /**
   * 记录游客诊断记录(only for tourist)
   * @return {@code int} 数据库改动条数
   */
  public int recordForTourist(String userId, FeaturesDTO features, double[] possibilties);

}
