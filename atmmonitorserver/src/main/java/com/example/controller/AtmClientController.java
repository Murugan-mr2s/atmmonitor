package com.example.controller;

import com.example.controller.request.MediaModelRequest;
import com.example.controller.request.AtmPingModelRequest;
import com.example.controller.request.ServiceModelRequest;
import com.example.controller.response.AtmMediaModelResponse;
import com.example.controller.response.AtmPingModelResponse;
import com.example.controller.response.AtmServiceModelResponse;
import com.example.exception.MediaException;
import com.example.service.AtmClientService;
import com.example.service.MediaStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/atms")
public class AtmClientController {

    private final AtmClientService atmClientService;
    private final MediaStorageService mediaStorageService;

    public AtmClientController(AtmClientService atmClientService,
                               MediaStorageService mediaStorageService) {

        this.atmClientService = atmClientService;
        this.mediaStorageService = mediaStorageService;
    }

    @PreAuthorize("{hasRole('ATM')}")
    @PostMapping("/pings")
    @ResponseStatus(HttpStatus.CREATED)
    public void pingHandler(@RequestBody AtmPingModelRequest pingRequest) {
        System.out.println(pingRequest.toString());
        atmClientService.addNewPing(pingRequest);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/pings")
    public List<AtmPingModelResponse> getAllPings() {
         return atmClientService.getAllPings();
    }


    @PreAuthorize("{hasRole('ATM')}")
    @PostMapping("/medias")
    @ResponseStatus(HttpStatus.CREATED)
    public void mediaHandler(@RequestPart("file") MultipartFile file,
                             @RequestPart("mediadata") MediaModelRequest request ) {
        atmClientService.addNewMedia(file, request);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/medias")
    public List<AtmMediaModelResponse> getAllMedias() {
        return atmClientService.getAllMedias();
    }


    @PreAuthorize("{hasRole('ATM')}")
    @PostMapping("/services")
    @ResponseStatus(HttpStatus.CREATED)
    public void serviceHandler(@RequestBody ServiceModelRequest request) {
        System.out.println(request.toString());
        atmClientService.addNewService(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/services")
    public List<AtmServiceModelResponse> getAllService() {
        return atmClientService.getAllServices();
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/medias/{filename}")
    public ResponseEntity<Resource> downloadMedias(@PathVariable("filename") String filename) {

        Resource resource=mediaStorageService.downloadMedia(filename);
        long len=0;
        try{
             len= resource.contentLength();
        } catch (IOException ex) {
            throw new MediaException("media -no content");
        }
        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE   , "video/mp4");
        headers.add(HttpHeaders.CONTENT_LENGTH, len+"");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${resource.filename}"  );
        return ResponseEntity.ok().headers(headers).body(resource);
    }


}
