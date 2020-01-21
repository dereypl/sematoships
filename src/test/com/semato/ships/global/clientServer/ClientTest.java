package test.com.semato.ships.global.clientServer;

import com.semato.ships.global.payload.EndConnectionRequest;
import com.semato.ships.global.payload.StartGameRequest;
import com.semato.ships.global.payload.StartGameResponse;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
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
        if (testInfo.getTags().contains("skipInit")) {
            return;
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
        if (testInfo.getTags().contains("skipInit")) {
            return;
        }
        stopConnection();
    }

    private void stopConnection() throws IOException {
        outObj.writeObject(new EndConnectionRequest());
        outObj.close();
        inObj.close();
        this.clientSocket.close();
    }
}

