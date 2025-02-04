package com.example.service;

import com.example.controller.request.AtmPingModelRequest;
import com.example.controller.request.MediaModelRequest;
import com.example.controller.request.ServiceModelRequest;
import com.example.controller.response.AtmMediaModelResponse;
import com.example.controller.response.AtmPingModelResponse;
import com.example.controller.response.AtmServiceModelResponse;
import com.example.model.*;
import com.example.repository.ATMRepository;
import com.example.repository.AtmMediaRepository;
import com.example.repository.AtmPingRepository;
import com.example.repository.AtmServiceRepository;
import com.example.utils.AtmTimeStamp;
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
    private final ATMRepository atmRepository;


    public AtmClientService(AtmPingRepository repository,
                            AtmMediaRepository mediarepository,
                            AtmServiceRepository serviceRepository,
                            MediaStorageService mediaStorageService,
                            ATMRepository atmRepository) {
        this.repository = repository;
        this.mediarepository = mediarepository;
        this.serviceRepository = serviceRepository;
        this.mediaStorageService = mediaStorageService;
        this.atmRepository = atmRepository;
    }

    public void addNewPing(AtmPingModelRequest request) {
        
        ATM atm = atmRepository.findByAtmId(request.atmid()).orElseThrow(() -> new RuntimeException("atmid not found"));

        AtmPingModel pingmodel= AtmPingModel.builder()
                .atm(atm)
                .observed_at(AtmTimeStamp.string2Stamp(request.observe_at()))
                .status(String.valueOf(request.status().equals("ON") ? AtmPingStatus.ON : AtmPingStatus.OFF))
                .created_at(AtmTimeStamp.getStamp())
                .build();

        repository.save(pingmodel);
    }


    public List<AtmPingModelResponse> getAllPings() {

       return repository.findAll()
               .stream()
               .map( atmPingModel ->
                                new AtmPingModelResponse(
                                        atmPingModel.getAtm().getBank(),
                                        atmPingModel.getAtm().getAtmid(),
                                        atmPingModel.getStatus(),
                                        atmPingModel.getObserved_at(),
                                        atmPingModel.getCreated_at())
        ).toList();

    }


    public void addNewMedia(MultipartFile file, MediaModelRequest request) {

        ATM atm = atmRepository.findByAtmId(request.atmid()).orElseThrow(() -> new RuntimeException("atmid not found"));
        String filepath  = mediaStorageService.storeMedia(file);
        System.out.println(filepath);

        if ( ! filepath.isBlank() ) {
            AtmMediaModel mediaModel = AtmMediaModel.builder()
                    .atm(atm)
                    .path(filepath)
                    .observed_at(AtmTimeStamp.string2Stamp(request.observe_at()))
                    .created_at( AtmTimeStamp.getStamp() )
                    .build();

            mediarepository.save(mediaModel);
        }
    }

    public List<AtmMediaModelResponse> getAllMedias() {

        return mediarepository.findAll()
                .stream()
                .map( model ->
                        new AtmMediaModelResponse(
                                model.getAtm().getBank(),
                                model.getAtm().getAtmid(),
                                model.getPath(),
                                model.getObserved_at(),
                                model.getCreated_at())
                ).toList();
    }


    public void addNewService(ServiceModelRequest request) {

        ATM atm = atmRepository.findByAtmId(request.atmid()).orElseThrow(() -> new RuntimeException("atmid not found"));

        AtmServiceModel model = AtmServiceModel.builder()
                .atm(atm)
                .userid(request.userid())
                .type( request.serviceType() )
                .status( request.serviceStatus().equals("SUCCESS") ? AtmServiceStatus.SUCCESS : AtmServiceStatus.FAILURE )
                .observed_at( AtmTimeStamp.string2Stamp(request.observe_at()))
                .created_at( AtmTimeStamp.getStamp() )
                .build();

        serviceRepository.save(model);
    }

    public List<AtmServiceModelResponse> getAllServices() {

       return serviceRepository.findAll().stream()
                .map( model ->
                                new AtmServiceModelResponse(
                                        model.getAtm().getBank(),model.getAtm().getAtmid(),
                                        model.getUserid(),
                                        model.getType(), model.getStatus(), model.getObserved_at(),
                                        model.getCreated_at()
                                )
                        ).toList();
    }
}
