package com.example.config;

import com.example.model.AtmDataInf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.LinkedList;
import java.util.Queue;

@Configuration
public class AtmWebClientConfig {


    private final  AtmDeviceInfo atmDeviceInfo;

    public AtmWebClientConfig(AtmDeviceInfo atmDeviceInfo) {
        this.atmDeviceInfo = atmDeviceInfo;
    }

    @Bean
    RestClient restClient() {

        return RestClient.builder()
                .baseUrl(atmDeviceInfo.getUrl())
                .defaultHeader("Authorization","Bearer "+atmDeviceInfo.getToken())
                .build();
    }

    @Bean
    Queue<AtmDataInf> atmQueue() {
        return new LinkedList<AtmDataInf>() ;
    }

}
