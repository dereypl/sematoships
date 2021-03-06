package com.semato.ships.server.connector;

import com.semato.ships.global.payload.*;
import com.semato.ships.server.User;

import java.io.*;
import java.net.Socket;
import java.util.Random;

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
                Object request = inObj.readObject();
                if (request instanceof StartGameRequest) {
                    StartGameRequest startGameRequest = (StartGameRequest) request;
                    this.user = new User(startGameRequest.getNick(), startGameRequest.getMyBoard());
                    System.out.println(user.getUsername() + ": " + startGameRequest.getName());
                    PairingService.getInstance().addRequestHandler(this);
                    while(enemy == null){
                        Thread.sleep(100);
                    }
                    outObj.writeObject(new StartGameResponse(enemy.getUsername(), enemy.getBoard(), enemy.isUserTurn()));
                }

                if (request instanceof BoardRequest){
                    System.out.println(user.getUsername() + ": " +((BoardRequest) request).getName());
                    BoardRequest boardRequest = (BoardRequest) request;
                    enemy.setBoard(boardRequest.getEnemyBoard());
                    user.setUserTurn(false);
                    enemy.setUserTurn(true);
                    while(!user.isUserTurn()){
                        Thread.sleep(100);
                    }
                    outObj.writeObject(new BoardResponse(user.getBoard()));
                }

                if (request instanceof EmptyRequest){
                    System.out.println(user.getUsername() + ": " + ((EmptyRequest) request).getName());
                    while(!user.isUserTurn()){
                        Thread.sleep(100);
                    }
                    outObj.writeObject(new BoardResponse(user.getBoard()));
                }

                if (request instanceof EndConnectionRequest) {
                    EndConnectionRequest endConnectionRequest = (EndConnectionRequest) request;
                    if(user != null ) {
                        System.out.println(user.getUsername() + ": " + endConnectionRequest.getName());
                    }
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
