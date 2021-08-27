package com.hw7.network;

import java.io.File;

public class DownloadFileException extends RuntimeException {
    private final File file;

    public DownloadFileException(String path, String message) {
        super(message);
        file = new File(path);
    }

    public DownloadFileException(String path) {
        super("");
        file = new File(path);
    }

    public File getFile() {
        return file;
    }
}
