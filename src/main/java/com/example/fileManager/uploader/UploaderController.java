package com.example.fileManager.uploader;

import com.example.fileManager.explorer.ExplorerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;

@Controller
public class UploaderController {

    UploaderService uploaderService = new UploaderService();
    ExplorerService explorerService = new ExplorerService();

    @GetMapping(path = "/home")
    public ModelAndView getTestData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");

        String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";
        try {
            mv.getModel().put("files",explorerService.getFolderContent(FOLDER_PATH));
            //System.out.println(explorerService.getFolderContent(FOLDER_PATH));

        }
        catch (IOException e){}
        return mv;
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "File is empty");
            return "redirect:/home";
        }

        try{
            uploaderService.uplodad(file);
            attributes.addFlashAttribute("success", "Successfully upload file: "+file.getOriginalFilename());
        }
        catch (IOException e){};
        return "redirect:/home";
    }
}