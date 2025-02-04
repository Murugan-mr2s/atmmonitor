package com.example.service;

import com.example.config.MediaStorage;
import com.example.exception.MediaException;
import com.example.exception.MediaFileNotFoundExcepiton;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@Service
public class MediaStorageService {


    private final MediaStorage mediaStorage;

    public MediaStorageService(MediaStorage mediaStorage) {
        this.mediaStorage = mediaStorage;
        System.out.println("media base- "+mediaStorage);
        if (this.mediaStorage.location.isBlank()) {
            throw new MediaException("File upload location can not be Empty.");
        }

        try {
            Files.createDirectories(Paths.get(this.mediaStorage.location));
        }
        catch ( IOException ex) {
            throw  new MediaException("Could not initialize storage");
        }
    }


    public String storeMedia(MultipartFile file) {

      try {
          if (file.isEmpty())
              throw new MediaException("Failed to store empty file.");
          Path storagePath = Paths.get(mediaStorage.location);
          if (!file.getOriginalFilename().isBlank()) {
              System.out.println(file.getOriginalFilename());
              String[] nameExt = file.getOriginalFilename().split("\\.");
              String newName = nameExt[0] + "_" + Instant.now().toEpochMilli() +"."+nameExt[1];
              Path destinationFile = storagePath.resolve(Paths.get(newName)).normalize().toAbsolutePath();
              Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
              return newName;
          }
      }
      catch (IOException ex) {
            throw  new MediaException("Error while copying media file");
      }

      return "";
    }

    public Resource downloadMedia(String filename) {

        try {
            Path storagePath=Paths.get(mediaStorage.location).resolve(filename);
            Resource resource = new UrlResource(storagePath.toUri());
            if (!resource.exists() || !resource.isReadable())
                throw new MediaFileNotFoundExcepiton("Could not read file: $filename");
            return resource;

        } catch (IOException ex) {
            throw new MediaFileNotFoundExcepiton("media not found");
        }
    }

}
