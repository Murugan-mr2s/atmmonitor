package com.example.service;

import com.example.controller.request.AtmPingModelRequest;
import com.example.controller.request.MediaModelRequest;
import com.example.controller.request.ServiceModelRequest;
import com.example.controller.response.AtmMediaModelResponse;
import com.example.controller.response.AtmPingModelResponse;
import com.example.controller.response.AtmServiceModelResponse;
import com.example.model.*;
import com.example.repository.AtmMediaRepository;
import com.example.repository.AtmPingRepository;
import com.example.repository.AtmServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AtmClientService {

    private final AtmPingRepository repository;
    private final AtmMediaRepository mediarepository;
    private final AtmServiceRepository serviceRepository;
    private final MediaStorageService mediaStorageService;

    public AtmClientService(AtmPingRepository repository,
                            AtmMediaRepository mediarepository,
                            AtmServiceRepository serviceRepository,
                            MediaStorageService mediaStorageService) {
        this.repository = repository;
        this.mediarepository = mediarepository;
        this.serviceRepository = serviceRepository;
        this.mediaStorageService = mediaStorageService;
    }

    public void addNewPing(AtmPingModelRequest request) {

        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        AtmPingModel pingmodel= AtmPingModel.builder()
                .bank(request.bank())
                .atmid(request.atmid())
                .observed_at(LocalDateTime.parse(request.observe_at(),formatter))
                .status(String.valueOf(request.status().equals("ON") ? AtmPingStatus.ON : AtmPingStatus.OFF))
                .created_at( LocalDateTime.parse( localDateTime.format(formatter) )   )
                .build();

        repository.save(pingmodel);
    }


    public List<AtmPingModelResponse> getAllPings() {

       return repository.findAll()
               .stream()
               .map( atmPingModel ->
                                new AtmPingModelResponse(
                                        atmPingModel.getBank(),
                                        atmPingModel.getAtmid(),
                                        atmPingModel.getStatus(),
                                        atmPingModel.getObserved_at(),
                                        atmPingModel.getCreated_at())
        ).toList();

    }


    public void addNewMedia(MultipartFile file, MediaModelRequest request) {

        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        String filepath  = mediaStorageService.storeMedia(file);
        System.out.println(filepath);

        if ( ! filepath.isBlank() ) {
            AtmMediaModel mediaModel = AtmMediaModel.builder()
                    .bank(request.bank())
                    .atmid(request.atmid())
                    .path(filepath)
                    .observed_at(LocalDateTime.parse(request.observe_at(),formatter))
                    .created_at( LocalDateTime.parse( localDateTime.format(formatter) )   )
                    .build();

            mediarepository.save(mediaModel);
        }
    }

    public List<AtmMediaModelResponse> getAllMedias() {

        return mediarepository.findAll()
                .stream()
                .map( model ->
                        new AtmMediaModelResponse(
                                model.getBank(),
                                model.getAtmid(),
                                model.getPath(),
                                model.getObserved_at(),
                                model.getCreated_at())
                ).toList();
    }


    public void addNewService(ServiceModelRequest request) {

        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        AtmServiceModel model = AtmServiceModel.builder()
                .bank(request.bank())
                .atmid(request.atmid())
                .userid(request.userid())
                .type( request.serviceType() )
                .status( request.serviceStatus().equals("SUCCESS") ? AtmServiceStatus.SUCCESS : AtmServiceStatus.FAILURE )
                .observed_at(LocalDateTime.parse(request.observe_at(),formatter))
                .created_at( LocalDateTime.parse( localDateTime.format(formatter) ) )
                .build();

        serviceRepository.save(model);
    }

    public List<AtmServiceModelResponse> getAllServices() {

       return serviceRepository.findAll().stream()
                .map( model ->
                                new AtmServiceModelResponse(
                                        model.getBank(), model.getAtmid(), model.getUserid(),
                                        model.getType(), model.getStatus(), model.getObserved_at(),
                                        model.getCreated_at()
                                )
                        ).toList();
    }
}
