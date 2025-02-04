package com.example.schedulers;

import com.example.model.AtmDataInf;
import com.example.model.AtmSerivceModel;
import com.example.model.MediaModel;
import com.example.model.PingModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Queue;

@Service
public class AtmClientDispatcher {

    private final RestClient restClient;
    private final Queue<AtmDataInf> queue;

    public AtmClientDispatcher(RestClient restClient, Queue<AtmDataInf> queue) {
        this.restClient = restClient;
        this.queue = queue;
    }


    @Scheduled(initialDelay =10000,fixedRate = 10000)
    void despatcher() {

        AtmDataInf  data= queue.poll();

        if ( data !=null ) {

            if (data instanceof PingModel pingData) {
                //System.out.println( pingData );
                restClient.post().uri("/pings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(pingData)
                        .retrieve()
                        .toBodilessEntity();;

            }
            else if ( data instanceof MediaModel  mediaModel) {
                System.out.println( mediaModel );
                LinkedMultiValueMap<String,Object> multiValueMap= new LinkedMultiValueMap<>();
                multiValueMap.add("file",new ClassPathResource(mediaModel.path()));
                multiValueMap.add("mediadata",mediaModel);
                restClient.post().uri("/medias")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(multiValueMap)
                        .retrieve()
                        .toBodilessEntity();
                /*
                restClient.post().uri("/medias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mediaModel)
                        .retrieve()
                        .toBodilessEntity();*/

            }
            else if ( data instanceof AtmSerivceModel atmSerivceModel) {

                 //System.out.println( atmSerivceModel );
                 restClient.post().uri("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(atmSerivceModel)
                        .retrieve()
                        .toBodilessEntity();
            }


        }


    }
}
