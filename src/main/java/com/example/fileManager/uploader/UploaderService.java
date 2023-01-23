package com.example.fileManager.uploader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploaderService{

    private final String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";
    //private final String FOLDER_PATH = "C:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";

    public void uplodad(MultipartFile file, String relativePath) throws IOException {
        if(relativePath!=null){
            relativePath=relativePath.concat("\\");
        }

        String filePath = FOLDER_PATH+relativePath+file.getOriginalFilename();
        file.transferTo(new File(filePath));
    }
}
