package com.semato.ships.server;

import com.semato.ships.server.connector.PairingService;
import com.semato.ships.server.connector.ServerTcp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(ServerTcp.getInstance());
        executorService.submit(PairingService.getInstance());
        executorService.shutdown();
    }
}
