package com.semato.ships.server.connector;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTcp {

    private ServerSocket serverSocket;
    private boolean serverIsRunning;
    private static volatile int countUsers;

    public void runServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            serverIsRunning = true;
            while (serverIsRunning) {
                new RequestHandler(serverSocket.accept()).start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void stop() throws IOException {
        if (serverIsRunning) {
            System.out.println("Server stoped");
            serverIsRunning = false;
            serverSocket.close();
        }
    }

    public synchronized static void increaseCountOfUsers(){
        countUsers++;
    }

    public synchronized static void decreaseCountOfUsers(){
        countUsers--;
    }

    public static int getCountUsers(){
        return countUsers;
    }
}
