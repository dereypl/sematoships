package com.semato.ships.server.connector;

import com.semato.ships.global.payload.EndConnectionRequest;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread {
    private Socket connection;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;

    protected RequestHandler(Socket connection) {
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
                if(object instanceof StartGameRequest){
                    StartGameRequest startGameRequest = (StartGameRequest) object;
                    System.out.println(startGameRequest.getNick());
                    outObj.writeObject(new StartGameResponse());
                }

                if(object instanceof EndConnectionRequest){
                    EndConnectionRequest endConnectionRequest = (EndConnectionRequest) object;
                    System.out.println(endConnectionRequest.getRequestName());
                    isTerminated = true;
                }


            }

            endConnection();
        } catch (IOException ex) {
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
}
