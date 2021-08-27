package com.hw7;

import java.io.IOException;

public class ServerStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "C:\\Users\\admin\\Downloads\\Seminar_27\\src\\content";
        Server server = new Server(5000, "127.0.0.1", path);
        server.run();
    }
}
