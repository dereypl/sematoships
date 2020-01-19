package com.semato.ships.server;

import com.semato.ships.server.connector.ServerTcp;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        ServerTcp server = new ServerTcp();
        try {
            server.runServer(4999);
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
