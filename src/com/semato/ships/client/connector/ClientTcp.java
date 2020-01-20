package com.semato.ships.client.connector;

import com.semato.ships.global.payload.EndConnectionRequest;

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

}
