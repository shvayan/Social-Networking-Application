package org.example.helloapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService implements IFileStorageService{

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public String storeFile(MultipartFile file, String folderName) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Create folder path like uploads/profile
        Path folderPath = Paths.get(uploadDir, folderName);

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // Unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = folderPath.resolve(fileName);
        // uploads/profile/1234567890_filename.jpg

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Copy the file to the target location, replacing existing file with the same name



        return folderName + "/" + fileName;
    }
}
