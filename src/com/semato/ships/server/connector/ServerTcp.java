package com.semato.ships.server.connector;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTcp extends Thread{
    private volatile static ServerTcp instance;
    private ServerSocket serverSocket;
    private boolean serverIsRunning;
    private static volatile int countUsers;

    private ServerTcp(){
    }

    public static ServerTcp getInstance(){
        if (instance == null) {
            synchronized (ServerTcp.class) {
                if (instance == null) {
                    instance = new ServerTcp();
                }
            }
        }
        return instance;
    }

    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("ServerTcp has been started");
            serverIsRunning = true;
            while (serverIsRunning) {
                new RequestHandler(serverSocket.accept()).start();
            }
            stopServer();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void stopServer() throws IOException {
        if (serverIsRunning) {
            System.out.println("Server has been stopped");
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
