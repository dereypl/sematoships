package test.com.semato.ships.global.clientServer;

import com.semato.ships.global.payload.EndConnectionRequest;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ClientTest {
    private static ClientTest clientTest;
    private Socket clientSocket;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;

    @BeforeEach
    private void startConnection(TestInfo testInfo) throws IOException {
        if(testInfo.getTags().contains("skipInit"))
        {
            return ;
        }
        startConnection();
    }

    private void startConnection() throws IOException {

        this.clientSocket = new Socket("localhost", 5000);
        this.outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inObj = new ObjectInputStream(clientSocket.getInputStream());
    }

    @AfterEach
    private void stopConnection(TestInfo testInfo) throws IOException {
        if(testInfo.getTags().contains("skipInit"))
        {
            return ;
        }
        stopConnection();
    }

    private void stopConnection() throws IOException {
        outObj.writeObject(new EndConnectionRequest());
        outObj.close();
        inObj.close();
        this.clientSocket.close();
    }


    @Test
    public void startGameByOneUser() throws IOException, ClassNotFoundException, InterruptedException {
        this.outObj.writeObject(new StartGameRequest("Tomek"));
        StartGameResponse response = (StartGameResponse) inObj.readObject();
        System.out.println(response.getResponseName());
        //outObj.writeObject("somethingElse");
        //StartGameResponse response = (StartGameResponse) inObj.readObject();
        //System.out.println(response.getResponseName());
    }


    @Test
    @Tag("skipInit")
    public void startGameByFewUser() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int finalI = i;
            Runnable parallelTask = () -> {
                try {
                    Socket clientSocket = new Socket("localhost", 5000);
                    ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream inObj = new ObjectInputStream(clientSocket.getInputStream());
                    outObj.writeObject(new StartGameRequest("User" + finalI));
                    StartGameResponse response = (StartGameResponse) inObj.readObject();
                    System.out.println(response.getResponseName());
                    outObj.writeObject(new EndConnectionRequest());
                    inObj.close();
                    outObj.close();
                    stopConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };
            executorService.submit(parallelTask);
        }

        try {
            executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}

