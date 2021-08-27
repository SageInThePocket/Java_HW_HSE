package com.hw7;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final String contentDir;
    private final Socket connectionClient;
    private DataOutputStream outToClient = null;

    public RequestHandler(Socket socket, String contentDir) {
        connectionClient = socket;
        this.contentDir = contentDir;
    }

    public void run() {
        try {
            System.out.println("The Client " + connectionClient.getInetAddress() + ":"
                    + connectionClient.getPort() + "is connected");

            BufferedReader br = new BufferedReader(new InputStreamReader(connectionClient.getInputStream()));
            Request request = new Request(br.readLine());

            outToClient = new DataOutputStream(connectionClient.getOutputStream());

            if (request.requestType.equals("GET")) {
                getRequest(request);
            }

            outToClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getRequest(Request request) throws IOException {
        if (request.obj.equals("DATA")) {
            String[][] models = getData();
            ObjectOutputStream oos = new ObjectOutputStream(connectionClient.getOutputStream());
            oos.writeObject(models);
            oos.flush();
        } else if (request.obj.equals("FILE")) {
            String fullName = contentDir + File.separator + request.objPath;
            try {
                FileInputStream fin = new FileInputStream(fullName);
                sendFile(fin);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private String[][] getData() {
        File folder = new File(contentDir);
        File[] files = folder.listFiles();

        if (files != null) {
            String[][] models = new String[files.length][4];
            for (int i = 0; i < files.length; ++i)
                models[i] = new String[]{
                        files[i].getName(),
                        Long.toString(files[i].lastModified()),
                        Long.toString(files[i].length())
                };
            return models;
        } else
            throw new IllegalArgumentException("ContentDir does not point to a directory");
    }

    private void sendFile(FileInputStream fin) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fin.read(buffer)) != -1 )
            outToClient.write(buffer, 0, bytesRead);
        fin.close();
        System.out.print("File was send\n");
    }
}
