package org.example.helloapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileStorageService {
    public String storeFile(MultipartFile file, String folderName) throws IOException;
}
