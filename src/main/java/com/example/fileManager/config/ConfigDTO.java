package com.example.fileManager.config;


import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class ConfigDTO {


    ClassPathResource staticDataResource = new ClassPathResource("data/config.json");
    File jsonFile = new File("data/config.json");

    public JSONObject getConfig() throws IOException, JSONException {
        //String jsonString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);
        String jsonString = IOUtils.toString(jsonFile.toURI(), StandardCharsets.UTF_8);
        JSONObject config = new JSONObject(jsonString);
        return config;
    }

    public void updateConfig(String key, String value) throws IOException, JSONException {
        String jsonString = IOUtils.toString(jsonFile.toURI(), StandardCharsets.UTF_8);
        JSONObject config = new JSONObject(jsonString);
        config.remove(key);
        config.put(key,value);

        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.write(config.toString());
        fileWriter.close();
    }

}
