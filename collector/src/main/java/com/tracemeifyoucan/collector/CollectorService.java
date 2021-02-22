package com.tracemeifyoucan.collector;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CollectorService {

    public void callGoApi(){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8106/api/v1/dummy",String.class);
        System.out.println(response);
    }
}
