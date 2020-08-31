package com.example.demo.utils;

public enum FilePath {
    FreeBoard("C:/spg_file/");

    private final String filePath;

    public String getFilePath() {
        return this.filePath;
    }

    FilePath(String filePath) {
        this.filePath = filePath;
    }
}
