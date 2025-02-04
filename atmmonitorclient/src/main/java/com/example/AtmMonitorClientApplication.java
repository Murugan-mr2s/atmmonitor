package com.example;

import com.example.config.AtmDeviceInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(AtmDeviceInfo.class)
public class AtmMonitorClientApplication {

    public static  void  main(String[] args) {
        SpringApplication.run(AtmMonitorClientApplication.class,args);
    }


    @Bean
    CommandLineRunner commandLineRunner( AtmDeviceInfo atmDeviceInfo ) {

        return args -> {
            System.out.println( atmDeviceInfo.getName()  + ":" + atmDeviceInfo.getUrl());
        };

    }
}
