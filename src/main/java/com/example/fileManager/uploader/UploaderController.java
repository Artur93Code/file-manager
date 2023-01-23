package com.example.fileManager.uploader;

import com.example.fileManager.explorer.ExplorerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploaderController {

    UploaderService uploaderService = new UploaderService();
    ExplorerService explorerService = new ExplorerService();


    @GetMapping(value = {"/home","/home/{folderName}"})
    public ModelAndView getTestData(@RequestParam(required = false) String relative) {
        ModelAndView mv = new ModelAndView();

        if(relative!=null && !relative.isEmpty())
        {
            Path path = Paths.get(relative);
            Path parent = path.getParent();
            mv.getModel().put("parent", parent);
            System.out.println("Parent: "+parent);
            System.out.println("Relative: "+relative);
            mv.getModel().put("relative", relative);
        }

        System.out.println(relative);

        String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";
        try {
            mv.setViewName("home");
            mv.getModel().put("files",explorerService.getFolderContent(FOLDER_PATH, relative));



            //mv.getModel().put("relative", relativePath);
            //System.out.println(explorerService.getFolderContent(FOLDER_PATH));

        }
        catch (IOException e){}
        return mv;
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes, @RequestParam(required = false) String relative) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("error", "File is empty");
            return "redirect:/home";
        }

        try{
            uploaderService.uplodad(file, relative);
            attributes.addFlashAttribute("success", "Successfully upload file: "+file.getOriginalFilename());
        }
        catch (IOException e){};
        return "redirect:/home";
    }
}
