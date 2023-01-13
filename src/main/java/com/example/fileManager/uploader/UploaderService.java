package com.example.fileManager.uploader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploaderService{

    private final String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";
    //private final String FOLDER_PATH = "C:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";

    public void uplodad(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();
        System.out.println(filePath);
        file.transferTo(new File(filePath));
    }
}
