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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ExplorerController {

    private final String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";

    @Autowired
    ExplorerService explorerService;

    @GetMapping(value = "/get-file" /*produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    public @ResponseBody ResponseEntity<Resource> getFile(@RequestParam(required = false) String relative) throws IOException {

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
}
