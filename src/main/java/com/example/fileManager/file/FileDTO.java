package com.example.fileManager.file;

import java.nio.file.Path;

public class FileDTO {

    private Path name;
    private Path absolutePath;

    private Path relativePath;
    private boolean isDirectory;
    private Long size;

    public FileDTO(Path name, Path absolutePath, Path relativePath, boolean isDirectory, Long size) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.relativePath = relativePath;
        this.isDirectory = isDirectory;
        this.size = size;
    }

    public Path getName() {
        return name;
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public Long getSize() {
        return size;
    }
}
