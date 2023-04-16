package com.example.fileManager.config;


import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class ConfigDTO {


    ClassPathResource staticDataResource = new ClassPathResource("data/config.json");

    public JSONObject getConfig() throws IOException, JSONException {
        String jsonString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);
        JSONObject config = new JSONObject(jsonString);
        return config;
    }

}
