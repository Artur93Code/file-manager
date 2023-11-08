package com.example.fileManager.config;


import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ConfigController {

    //@Autowired
    ConfigDTO configDTO = new ConfigDTO();
    ConfigService configService = new ConfigService();
    @GetMapping(value = "/config")
    public ModelAndView getFolderContent(Model model) throws IOException, JSONException {

        ModelAndView mv = new ModelAndView();
        System.out.println(configDTO.getConfig());

        mv.getModel().put("rootFolder", configDTO.getConfig().get("rootFolder"));
        mv.setViewName("config");
        return mv;
    }
    @PostMapping(value = "/config-edit")
    public ModelAndView setRootFolder(RedirectAttributes attributes, @RequestParam String rootFolder) throws IOException, JSONException {

        ModelAndView mv = new ModelAndView();
        if (configService.setRootFolder(rootFolder).equals("success")){
            attributes.addFlashAttribute("success", "Root Folder changed! To apply changes you must reset app");
        }
        else {
            attributes.addFlashAttribute("error", "Error. Something went wrong");
        }
        mv.getModel().put("rootFolder", configDTO.getConfig().get("rootFolder"));
        mv.setViewName("redirect:/config");
        return mv;
    }
}
