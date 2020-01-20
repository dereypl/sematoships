package com.semato.ships.server.connector;

import com.semato.ships.global.payload.EndConnectionRequest;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import com.semato.ships.server.User;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread {
    private Socket connection;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private User user;
    private User enemy;


    public RequestHandler(Socket connection) {
        this.connection = connection;
        ServerTcp.increaseCountOfUsers();
    }

    @Override
    public void run() {
        try {
            System.out.println("Currently connect users: " + ServerTcp.getCountUsers());
            outObj = new ObjectOutputStream(connection.getOutputStream());
            inObj = new ObjectInputStream(connection.getInputStream());

            boolean isTerminated = false;

            while (!isTerminated) {
                Object object = inObj.readObject();
                if (object instanceof StartGameRequest) {
                    StartGameRequest startGameRequest = (StartGameRequest) object;
                    this.user = new User(startGameRequest.getNick(), startGameRequest.getMyBoard());
                    PairingService.getInstance().addRequestHandler(this);
                    while(enemy == null){
                        Thread.sleep(100);
                    }
                    outObj.writeObject(new StartGameResponse(enemy.getUsername(), enemy.getBoard()));
                }

                if (object instanceof EndConnectionRequest) {
                    EndConnectionRequest endConnectionRequest = (EndConnectionRequest) object;
                    System.out.println(endConnectionRequest.getMessageName());
                    isTerminated = true;
                }

            }

            endConnection();
        } catch (IOException | InterruptedException ex) {
            ServerTcp.decreaseCountOfUsers();
            System.err.println(ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void endConnection() throws IOException {
        ServerTcp.decreaseCountOfUsers();
        outObj.close();
        inObj.close();
        connection.close();
    }

    public RequestHandler setEnemy(User enemy){
        this.enemy = enemy;
        return this;
    }

    public User getUser() {
        return user;
    }
}
