package socket2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class b2ClientHandler extends Thread {
    private BufferedReader in;
    private DataOutputStream out;
    private ArrayList<b2ClientHandler> clients;

    public b2ClientHandler(BufferedReader in, DataOutputStream out, ArrayList<b2ClientHandler> clients) {
        this.in = in;
        this.out = out;
        this.clients = clients;
        clients.add(this);
        start();
    }

    public void send(String message) {
        try {
            out.writeBytes(message + "\n");
            out.flush(); // Ensure data is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (b2ClientHandler client : clients) {
            client.send(message);
        }
    }

    public void run() {
        while (true) {
            try {
                String message = in.readLine();
                if (message == null) { // Client disconnected
                    break;
                }
                System.out.println(message);
                broadcast(message);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}