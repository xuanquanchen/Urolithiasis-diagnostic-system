package com.JNUHealthSupervisor.ucdas.utils;

/**
 * 数组处理工具类
 * @author Chen Yixing
 * @version 1.0.0
 */
public class ArrayUtil {
  
  /**
   * 返回数组最大值索引(only for double)
   * @return int 数组最大值索引
   */
  public static int getMaxIndexForDoubles(double[] arrays) {
    int maxIndex = 0;
    for (int iter=1;iter<arrays.length;iter++) {
      if (Double.compare(arrays[maxIndex], arrays[iter]) == -1) {
        maxIndex = iter;
      }
    }
    return maxIndex;
  }

}
