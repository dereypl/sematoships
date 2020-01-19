package com.semato.ships.server.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread {
    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;


    protected RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(connection.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            boolean isTerminated = false;
            String inputLine;

            while(!isTerminated){
                inputLine = in.readLine();
                String[] request = inputLine.split(";");
                for (int i = 0; i < request.length; i++) {
                    System.out.println("request[" + i + "] " + request[i]);
                }

                switch (request[0]) {
                    case ".": {
                        out.println("bye");
                        isTerminated = true;
                        break;
                    }
                    default: {
                        out.println("request not found!");
                    }
                }
            }

            in.close();
            out.close();
            connection.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
