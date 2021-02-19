package com.tracemeifyoucan.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    @PostMapping("/publish")
    public String publish(@RequestBody PublishRequest publishRequest) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(publishRequest));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Published "+publishRequest.getId();
    }
}
