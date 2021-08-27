package com.hw7;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private final String contentDir;
    private final int port;
    private final String address;
    private ServerSocket serverSocket;

    public Server(int port, String address, String contentDir) throws IOException {
        this.port = port;
        this.address = address;
        this.contentDir = contentDir;
        serverSocket = new ServerSocket(port, 10, InetAddress.getByName(address));
    }

    public String getContentDir() {
        return contentDir;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void run() throws IOException, InterruptedException {
        System.out.printf("Simple TCP/IP com.hw7.Server Waiting for client on port %d (enter %s:%d)\n", port, address, port);
        System.out.println("CONTENT_DIR = " + contentDir);

        ConnectionHandler connectionHandler = new ConnectionHandler(serverSocket, contentDir);
        connectionHandler.start();

        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equalsIgnoreCase("exit")) {
            // do nothing until exit...(just wait for exit)
        }
        serverSocket.close();
        connectionHandler.join();

        System.out.println("FirstImplAttemptServer finished.");
    }

    static class ConnectionHandler extends Thread {
        ServerSocket serverSocket;
        private final String contentDir;

        ConnectionHandler(ServerSocket serverSocket, String contentDir) {
            this.serverSocket = serverSocket;
            this.contentDir = contentDir;
        }

        public void run() {
            try {
                while (true) {
                    Socket connected = serverSocket.accept();
                    System.out.println("ConnectionHandler: " + connected);
                    new RequestHandler(connected, contentDir).run();
                }
            } catch (Exception ex) {
                //TODO: Сделать чета нормальное
                ex.printStackTrace();
            }
            System.out.println("ServerSocket closed");
        }
    }

}
