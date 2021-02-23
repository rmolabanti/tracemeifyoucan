package com.tracemeifyoucan.collector;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@XRayEnabled
public class CollectorService {

    public String callGoApi(int time){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8106/api/v1/dummy/"+time,String.class);
        System.out.println(response);
        return response;
    }
}
