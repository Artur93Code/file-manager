package com.example.fileManager.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ExplorerController {

    private final String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";

    @Autowired
    ExplorerService explorerService;

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

    @GetMapping(value = "/get-file" /*produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    public @ResponseBody ResponseEntity<Resource> downloadFile(@RequestParam(required = false) String relative) throws IOException {

        File file = new File("c:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\"+relative);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        ///Set up downloading filename to ASCII charset
        ByteBuffer filenameBytes = StandardCharsets.US_ASCII.encode(file.getName());
        String downloadName = StandardCharsets.US_ASCII.decode(filenameBytes).toString();


        //headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        //headers.add("Pragma", "no-cache");
        //headers.add("Expires", "0");

        HttpHeaders headers = new HttpHeaders();
        //the line below must be added iw we want download the file. Otherwise te file will be shown in browswer (if the browswer supports file extension)
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downloadName + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile[] files, RedirectAttributes attributes, @RequestParam(required = false) String relative) {

        List<String> uploadedFilesNames = new ArrayList<>();

        // check if file is empty
        if (files.length==0) {
            attributes.addFlashAttribute("error", "File is empty");
            return "redirect:/home";
        }

        //try{

            Arrays.asList(files).stream().forEach(file -> {
                try {
                    explorerService.uplodad(file, relative);
                    uploadedFilesNames.add(file.getOriginalFilename());
                }
                catch (IOException e){
                    System.out.println(e);
                }
            });


            attributes.addFlashAttribute("success", "Successfully upload file(s): "+uploadedFilesNames);
        //}
        //catch (IOException e){};
        return "redirect:/home";
    }

    @PostMapping(value="/create")
    public ModelAndView create(RedirectAttributes attributes, @RequestParam String relative, @RequestParam String folderName, Model model){
        ModelAndView mv = new ModelAndView();

        try {
            String folderRelativePath=relative+"\\"+folderName;
            explorerService.createFolder(FOLDER_PATH, folderRelativePath);
            attributes.addFlashAttribute("success", "Folder "+folderName+" created!");

            //model.addAttribute("files",explorerService.getFolderContent(FOLDER_PATH, relative));
        }
        catch (IOException e){
            attributes.addFlashAttribute("error", e.getMessage());
            //return "redirect:/home";
        }
        mv.getModel().put("relative", relative);
        mv.setViewName("redirect:/home");
        return mv;
    }

    @PostMapping(value="/delete")
    public ModelAndView delete(RedirectAttributes attributes, @RequestParam String name, @RequestParam String current, Model model){
        ModelAndView mv = new ModelAndView();

        try {
            String folderRelativePath=current+"\\"+name;
            explorerService.deleteFolderOrFile(FOLDER_PATH, folderRelativePath);
            attributes.addFlashAttribute("success", "Folder "+name+" deleted!");

            //model.addAttribute("files",explorerService.getFolderContent(FOLDER_PATH, relative));
        }
        catch (IOException e){
            attributes.addFlashAttribute("error", e.getMessage());
            //return "redirect:/home";
        }
        mv.getModel().put("relative", current);
        mv.setViewName("redirect:/home");
        return mv;
    }
}
