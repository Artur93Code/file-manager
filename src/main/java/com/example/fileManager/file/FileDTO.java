package com.example.fileManager.file;

import java.nio.file.Path;

public class FileDTO {

    private Path name;
    private Path path;
    private boolean isDirectory;
    private Long size;

    public FileDTO(Path name, Path path, boolean isDirectory, Long size) {
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
        this.size = size;
    }

    public Path getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public Long getSize() {
        return size;
    }
}
