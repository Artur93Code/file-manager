package com.example.fileManager.downloader;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloaderController {

/*    @GetMapping(path = "/home")
    public ModelAndView getTestData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        mv.getModel().put("data", "Welcome home man");

        return mv;
    }*/

/*    @GetMapping(
            value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody String getFile() throws IOException {
        FileInputStream in = new FileInputStream("c:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\test.png");
        File file = new File("test.png");
        OutputStream out = new FileOutputStream(file, true);
        IOUtils.copyLarge(in, out);
        in.close();
        out.close();
        return "home";
    }*/

    @GetMapping(
            value = "/get-file"
            //produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody ResponseEntity<Resource> getFile() throws IOException {
        //FileInputStream in = new FileInputStream("c:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\tekstowy.txt");
        //MediaType contentType = MediaType.IMAGE_PNG;
        //File file = new File("test.png");
        //OutputStream out = new FileOutputStream(file, true);
        //IOUtils.copyLarge(in, out);
        //in.close();
        //out.close();
        File file = new File("c:\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\zdjecie.png");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        //headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        //headers.add("Pragma", "no-cache");
        //headers.add("Expires", "0");
        //the line below must be added iw we want download the file. Otherwise te file will be shown in browswer (if the browswer supports file extension)
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getName() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
