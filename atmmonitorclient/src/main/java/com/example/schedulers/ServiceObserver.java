package com.example.schedulers;

import com.example.config.AtmDeviceInfo;
import com.example.model.AtmDataInf;
import com.example.model.AtmSerivceModel;
import com.example.model.AtmServiceType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

@Component
public class ServiceObserver {

    private final Queue<AtmDataInf> queue;
    private final AtmDeviceInfo atmDeviceInfo;

    public ServiceObserver(Queue<AtmDataInf> queue, AtmDeviceInfo atmDeviceInfo) {
        this.queue = queue;
        this.atmDeviceInfo = atmDeviceInfo;
    }

    @Scheduled(initialDelay =30000 , fixedRate = 100000)
    void serviceScheduler() {

        String formattedStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());

        // random type of atm service used
        int sval =(int) Math.round (Math.random() *4);
        AtmServiceType type = switch(sval) {
            case 1-> AtmServiceType.BALANCE;
            case 2 -> AtmServiceType.DEPOSIT;
            case 3 -> AtmServiceType.REPORT;
            default -> AtmServiceType.WITHDRAW;
        };

        AtmDataInf data = new AtmSerivceModel(atmDeviceInfo.getName() ,
                atmDeviceInfo.getId(),
                 String.valueOf( Math.round (Math.random()* 10) ),   //random user id
                 type,
                (Math.random() < 0.9) ? "SUCCESS" : "FAILURE",  // success or failure based random 0.9
                formattedStamp);

        queue.add(data);
    }
}
