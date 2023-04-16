package com.example.fileManager.config;


import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ConfigController {

    //@Autowired
    ConfigDTO configDTO = new ConfigDTO();

    @GetMapping(value = "/config")
    public ModelAndView getFolderContent(Model model) throws IOException, JSONException {

        ModelAndView mv = new ModelAndView();
        System.out.println(configDTO.getConfig());

        mv.getModel().put("rootFolder", configDTO.getConfig().get("rootFolder"));
        mv.setViewName("config");
        return mv;
    }
}
