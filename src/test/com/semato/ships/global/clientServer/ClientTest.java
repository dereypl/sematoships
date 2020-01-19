package test.com.semato.ships.global.clientServer;

import com.semato.ships.global.payload.EndConnectionRequest;
import com.semato.ships.global.payload.Response;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.Socket;


public class ClientTest {
    private static ClientTest clientTest;
    private Socket clientSocket;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;

    private void startConnection(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        this.outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inObj = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void stopConnection() throws IOException {
        outObj.writeObject(new EndConnectionRequest());
        this.inObj.close();
        this.outObj.close();
        this.clientSocket.close();
    }

    @Test
    public void startGameTest() throws IOException, ClassNotFoundException, InterruptedException {
        startConnection("localhost", 5000);
        outObj.writeObject(new StartGameRequest("Tomek"));
        StartGameResponse response = (StartGameResponse) inObj.readObject();
        System.out.println(response.getResponseName());
        wait(10000);
        stopConnection();
        //outObj.writeObject("somethingElse");
        //StartGameResponse response = (StartGameResponse) inObj.readObject();
        //System.out.println(response.getResponseName());
    }
}
