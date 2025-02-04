package com.example.schedulers;

import com.example.config.AtmDeviceInfo;
import com.example.model.AtmDataInf;
import com.example.model.AtmStatus;
import com.example.model.PingModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Queue;

@Component
public class PingObserver {

    private final Queue<AtmDataInf> queue;
    private final AtmDeviceInfo atmDeviceInfo;

    public PingObserver(Queue<AtmDataInf> queue, AtmDeviceInfo atmDeviceInfo) {
        this.queue = queue;
        this.atmDeviceInfo = atmDeviceInfo;
    }

    @Scheduled(initialDelay =10000 , fixedRate = 60000)
    void pingScheduling() {

        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = localDateTime.format(formatter);

        System.out.println(  formattedDate );
        AtmDataInf data = new PingModel(atmDeviceInfo.getName() ,
                atmDeviceInfo.getId(),
                formattedDate,
                Math.random() < 0.9 ? AtmStatus.ON : AtmStatus.OFF );
        queue.add(data);
    }


}
