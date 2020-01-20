package com.semato.ships.server.connector;

import java.util.LinkedList;

public class PairingService extends Thread {

    private volatile static PairingService instance;

    private LinkedList<RequestHandler> requestHandlerList;

    private PairingService() {
        requestHandlerList = new LinkedList<>();
    }

    public static PairingService getInstance() {
        if (instance == null) {
            synchronized (PairingService.class) {
                if (instance == null) {
                    instance = new PairingService();
                }
            }
        }
        return instance;
    }

    public synchronized void addRequestHandler(RequestHandler requestHandler) {
        requestHandlerList.add(requestHandler);
    }

    @Override
    public void run() {
        System.out.println("PairingService has been run!");
        while (true) {
            if (requestHandlerList.size() >= 2) {
                RequestHandler requestHandler1 = requestHandlerList.pop();
                RequestHandler requestHandler2 = requestHandlerList.pop();
                requestHandler1.setEnemy(requestHandler2.getUser());
                requestHandler2.setEnemy(requestHandler1.getUser());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
