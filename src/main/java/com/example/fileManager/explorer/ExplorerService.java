package com.example.fileManager.explorer;

import com.example.fileManager.file.FileDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExplorerService {

    FileDTO fileDTO;

/*    public Set<String> getFolderContent(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }*/

/*    public Set<File> getFolderContent(String dir) throws IOException {
            Set<File> files = Files.list(Paths.get(dir))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .collect(Collectors.toSet());
            File file = files.stream().toList().get(0);
            //files.size();
        System.out.println(file);
            return files;
    }*/

/*    public List<String> getFolderContent(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }
    }*/

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

}
