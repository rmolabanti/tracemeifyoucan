package sample.multimodule.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sample.multimodule.service.CollectData;



@RestController
public class CollectorIMPL {

    @Autowired
    RestTemplate restTemplate;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/collect")
    public String collect(@RequestBody CollectData collectData) throws JsonProcessingException {
        String response = null;
        try {
           restTemplate.getForObject("http://localhost:8080/reserve",Object.class).toString();
            System.out.print("Governer Response " + response);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Published "+collectData.getName();
    }
}