package com.example.fileManager.config;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConfigService {

    ConfigDTO configDTO = new ConfigDTO();
    public String setRootFolder(String rootFolder) throws IOException, JSONException {
        try {
            //Check if path string has slash at ent. If not add slash
            if(!rootFolder.endsWith("\\")){
                rootFolder = rootFolder.concat("\\");
            }
            configDTO.updateConfig("rootFolder", rootFolder);
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "success";
    }
}
