package com.example.fileManager.explorer;

import com.example.fileManager.config.ConfigDTO;
import com.example.fileManager.file.FileDTO;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExplorerService {

    FileDTO fileDTO;

    ConfigDTO configDTO = new ConfigDTO();

    //private final String FOLDER_PATH = "\\\\192.168.1.207\\c$\\Users\\Artur\\Downloads\\Udostępnione\\Zdjęcia\\";
    private final String FOLDER_PATH = configDTO.getConfig().get("rootFolder").toString();

    //neceessary for FOLDER_PATH String
    public ExplorerService() throws JSONException, IOException {
    }


    //Download all files and folders from the selected location and suit up them to the pre-created DTO format
    public List<FileDTO> getFolderContent(String dir, String relativePath) throws IOException {
        if(relativePath==null)
            relativePath="\\";
        try (Stream<Path> stream = Files.list(Paths.get(dir, relativePath))) {
            List<Path> contentListPath = stream.collect(Collectors.toList());
            List<FileDTO> contentListDTO = new ArrayList<>();
            for(Path path : contentListPath)
            {
                Path pathAbsolute = Paths.get(dir+relativePath+"\\"+path.getFileName());
                Path pathBase = Paths.get(dir);
                Path pathRelative = pathBase.relativize(pathAbsolute);

                contentListDTO.add(new FileDTO(
                        path.getFileName(),
                        pathAbsolute,
                        pathRelative,
                        Files.isDirectory(path),
                        Files.size(path)));
/*                System.out.println(path.getFileName());
                System.out.println(Paths.get(dir));
                System.out.println(Files.isDirectory(path));
                System.out.println(Files.size(path));*/
            }
            return contentListDTO;
        }
    }

    public void createFolder(String folderPath, String relativePath) throws IOException
    {
        Path path = Paths.get(folderPath,relativePath);
        if(!Files.exists(path)){
            Files.createDirectory(path);
            //System.out.println("Folder created!");
            //return "Folder created!";
        }
        else{
            //System.out.println("Folder already exist");
            throw new IOException("Folder already exist");
            //return "Folder already exist";
        }
        //return "Folder created!";
    }

    public void deleteFolderOrFile(String folderPath, String relativePath) throws IOException
    {
        Path path = Paths.get(folderPath,relativePath);

        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        //return "Folder created!";
    }

    public void uplodad(MultipartFile file, String relativePath) throws IOException
    {

        if(relativePath!=null){
            relativePath=relativePath.concat("\\");
        }

        String filePath = FOLDER_PATH+relativePath+file.getOriginalFilename();
        file.transferTo(new File(filePath));
    }

}
