package com.example.demo.utils;

public enum FilePath {
    FreeBoard("C:/spg_free_board_file/"),
	NoticeBoard("C:/spg_notice_board_file/"),
	EduBoard("C:/spg_edu_board_file");

    private final String filePath;

    public String getFilePath() {
        return this.filePath;
    }

    FilePath(String filePath) {
        this.filePath = filePath;
    }
}
