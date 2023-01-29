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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

}
