package com.JNUHealthSupervisor.ucdas.service.Impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.JNUHealthSupervisor.ucdas.dto.FeaturesDTO;
import com.JNUHealthSupervisor.ucdas.VO.PredictResultVO;
import com.JNUHealthSupervisor.ucdas.utils.FeatureConvertUtil;
import com.JNUHealthSupervisor.ucdas.service.ModelPredictService;


/**
 * 模型预测业务接口
 * @author Chen Yixing
 * @version 1.0.0
 */
@Service
public class ModelPredictServiceImpl implements ModelPredictService {

  public PredictResultVO getModelPredict(FeaturesDTO features) throws Exception {
    
    ObjectMapper mapper = new ObjectMapper();
    int[] convertedFeatures = FeatureConvertUtil.convertToArray(features, 22);

    ResponseEntity<String> res = new RestTemplate().postForEntity(
        "http://localhost:8501/v1/models/ensemble:predict",
        "{\"instances\": [" + Arrays.toString(convertedFeatures) + "]}",
        String.class
      );
    HashMap<String, double[][]> body = mapper.readValue(res.getBody(), new TypeReference<HashMap<String, double[][]>>(){});

    return new PredictResultVO(body.get("predictions")[0]);
  }

  @Override
  public List<PredictResultVO> getModelPredict(List<FeaturesDTO> features) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    int[][] convertedFeatures = FeatureConvertUtil.convertToArray(features, 22);
    
    ResponseEntity<String> res = new RestTemplate().postForEntity(
        "http://localhost:8501/v1/models/ensemble:predict",
        "{\"instances\":" + Arrays.deepToString(convertedFeatures) + "}",
        String.class
      );
    HashMap<String, double[][]> body = mapper.readValue(res.getBody(), new TypeReference<HashMap<String, double[][]>>(){});
    double[][] predictions = body.get("predictions");
    List<PredictResultVO> result = new ArrayList<PredictResultVO>();
    for (int i=0; i<predictions.length; i++) {
      result.add(new PredictResultVO(predictions[i]));
    }

    return result;
  }

}
