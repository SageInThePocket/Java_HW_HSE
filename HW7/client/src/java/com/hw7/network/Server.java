package com.hw7.network;

import com.hw7.model.FileModel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements GetRequestSender {
    private static final String GET_DATA_REQUEST = "GET DATA";
    private static final String GET_FILE_TEMPLATE = "GET FILE %s";
    private final InetAddress serverHost;
    private final int port;
    private final ArrayList<FileModel> fileModels = new ArrayList<>();
    private final ArrayList<DownloadFileListener> listeners = new ArrayList<>();
    private Downloader downloader;

    public Server(String hostName, int port) throws IOException {
        serverHost = InetAddress.getByName(hostName);
        this.port = port;
    }

    @Override
    public void getData() {
        fileModels.clear();

        try {
            Socket socket = new Socket(serverHost, port);
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println(GET_DATA_REQUEST);
            ps.flush();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String[][] models = (String[][]) ois.readObject();

            for (String[] modelInfo : models)
                fileModels.add(new FileModel(modelInfo));
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new GetDataException("Failed to get information about stored files");
        }
    }

    @Override
    public void getFile(String name, String path) {
        try {
            if (downloader != null)
                downloader.join();
            Socket socket = new Socket(serverHost, port);
            downloader = new Downloader(socket, path, name);
            downloader.start();
        } catch (FileNotFoundException e) {
            throw new DownloadFileException(path + File.separator + name, "File not found.");
        } catch (IOException e) {
            throw new DownloadFileException(path + File.separator + name, "Failed to download file.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean fileIsDownloading() {
        if (downloader == null)
            return false;
        else
            return downloader.fileIsDownloading;
    }

    public int getDownloadedBytes() {
        if (downloader == null)
            return 0;
        else
            return downloader.countOfBytes;
    }

    public ArrayList<FileModel> getFileModels() {
        return fileModels;
    }

    private static class Downloader extends Thread {
        private final String fileName;
        private final String path;
        private final Socket socket;
        private boolean isSuccessfulDownload = true;
        private boolean fileIsDownloading = true;
        private int countOfBytes = 0;

        public Downloader(Socket socket, String path, String fileName) {
            this.socket = socket;
            this.fileName = fileName;
            this.path = path;
        }

        @Override
        public void run() {
            try {
                PrintStream ps = new PrintStream(socket.getOutputStream());
                ps.printf((GET_FILE_TEMPLATE) + "%n", fileName);
                ps.flush();

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                FileOutputStream fout = new FileOutputStream(path + File.separator + fileName);
                downloadFile(fout, inputStream);
                socket.close();
            } catch (DownloadFileException e) {
                isSuccessfulDownload = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void downloadFile(FileOutputStream fout, DataInputStream inputStream) {
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fout.write(buffer);
                    countOfBytes += bytesRead;
                }
                fout.close();
            } catch (IOException e) {
                throw new DownloadFileException("Failed to download file.");
            } finally {
                fileIsDownloading = false;
                countOfBytes = 0;
            }
        }
    }
}
