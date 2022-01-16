package com.JNUHealthSupervisor.ucdas.utils;

import java.util.List;

import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;

/**
 * 特征信息转化工具类
 * @author Chen Yixing
 * @version 1.0.0
 */
public class FeatureConvertUtil {

  /**
   * 将特征类对象封装为所需数组
   * @param features {@code FeaturesDTO}特征信息
   * @param num 特征数量
   * @return {@code int[]} 转换结果
   * @see FeaturesDTO
   */
  public static int[] convertToArray(FeaturesDTO features, int num){
    int[] values = new int[num];
    values[0] = features.getSex();
    values[1] = features.getAge();
    values[2] = features.getMarrialStatus();
    values[3] = features.getBirthPlace();
    values[4] = features.getC_C();
    values[5] = features.getFever();
    values[6] = features.getNausea();
    values[7] = features.getGh();
    values[8] = features.getDm();
    values[9] = features.getNh();
    values[10] = features.getCe();
    values[11] = features.getCr();
    values[12] = features.getHb();
    values[13] = features.getBun();
    values[14] = features.getSmoking();
    values[15] = features.getAlcohol();
    values[16] = features.getOpium();
    values[17] = features.getBp();
    values[18] = features.getFh();
    values[19] = features.getDiseaseDiagnosis();
    values[20] = features.getUricAcid();
    values[21] = features.getCa();
    return values;
  }

  /**
   * 将特征类对象封装为所需数组
   * @param features {@code List<FeaturesDTO>}特征信息
   * @param num 特征数量
   * @return {@code int[][]} 转换结果
   * @see FeaturesDTO
   */
  public static int[][] convertToArray(List<FeaturesDTO> features, int num){
    int[][] values = new int[features.size()][];
    for (int i=0; i<features.size(); i++) {
      values[i] = convertToArray(features.get(i), num);
    }
    return values;
  }
}
