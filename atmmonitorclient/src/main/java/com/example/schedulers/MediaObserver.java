package com.example.schedulers;

import com.example.config.AtmDeviceInfo;
import com.example.model.AtmDataInf;
import com.example.model.MediaModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Queue;

@Component
public class MediaObserver {

    private final Queue<AtmDataInf> queue;
    private final AtmDeviceInfo atmDeviceInfo;

    public MediaObserver(Queue<AtmDataInf> queue, AtmDeviceInfo atmDeviceInfo) {
        this.queue = queue;
        this.atmDeviceInfo = atmDeviceInfo;
    }

    @Scheduled(initialDelay =10000 , fixedRate = 120000)
    void mediaScheduling() {

        /*
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = localDateTime.format(formatter);
        */

        /*
        String [] files=null;
        Resource resource=new ClassPathResource("vsamples/");
        try {
            File file = resource.getFile();
            files =file.list();
        } catch (IOException ex) {
            System.out.println(" video samples empty");
        }

        if(files !=null) {
            System.out.println(files.length);
        }*/

        String formattedStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());

        int vid = (int) (Math.random()*4); // randomly choose 1 one video
        AtmDataInf data = new MediaModel(atmDeviceInfo.getName() ,
                atmDeviceInfo.getId(),
                "vsamples/v-clip"+vid +".mp4",
                formattedStamp);

        queue.add(data);
    }


}
