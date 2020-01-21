package com.semato.ships.client.connector;

import com.semato.ships.client.Context;
import com.semato.ships.client.ui.wrapper.WrapperController;
import com.semato.ships.global.Board;
import com.semato.ships.global.payload.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTcp {

    private volatile static ClientTcp clientTcp;
    private Socket clientSocket;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;

    public static ClientTcp getInstance(){
        if (clientTcp == null) {
            synchronized (ClientTcp.class) {
                if (clientTcp == null) {
                    clientTcp = new ClientTcp();
                }
            }
        }

        return clientTcp;
    }

    private ClientTcp() {
    }

    public void startConnection(String serverName, int port) {
        try {
            System.out.println("starting connection...");
            clientSocket = new Socket(serverName, port);
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error while starting connection");
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        if(isConnect()) {
            try {

                System.out.println("closing connection...");
                outObj.writeObject(new EndConnectionRequest());
                outObj.close();
                inObj.close();
                this.clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error while closing connection");
                e.printStackTrace();
            }
        }
    }

    private boolean isConnect(){
        return (clientSocket != null && clientSocket.isConnected());
    }

    public ObjectOutputStream getOutObj(){
        return this.outObj;
    }

    public ObjectInputStream getInObj(){
        return this.inObj;
    }

    public StartGameResponse sendStartGameRequest(String nick) {
        try {
            outObj.writeObject(new StartGameRequest(nick, Context.getInstance().getMyBoard()));
            return (StartGameResponse) inObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BoardResponse sendBoard(){
        try {
            Board boardE = Context.getInstance().getEnemyBoard();
            outObj.writeObject(new BoardRequest(boardE));
            return (BoardResponse) inObj.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BoardResponse sendEmptyRequest() {
        try {
            outObj.writeObject(new EmptyRequest());
            return (BoardResponse) inObj.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
