package com.tracemeifyoucan.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApplicationService {
    public static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    ObjectMapper mapper = new ObjectMapper();


    public void simpleRequestProcessor() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Entity entity = restTemplate.getForObject("http://localhost:8081/collect",Entity.class);
        entity.setId(123);
       // Entity rEntity = restTemplate.postForObject("http://localhost:8082/publish",entity,Entity.class);
       // logger.info("Response {}",mapper.writeValueAsString(rEntity));
    }
}
