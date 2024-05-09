package socket2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class b2Sever {
    private ArrayList<b2ClientHandler> clients = new ArrayList<>();

    public b2Sever() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("Server started");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                b2ClientHandler clientHandler = new b2ClientHandler(in, out, clients);
                clients.add(clientHandler);
                // Start the server handler to send messages from the server
                new Thread(() -> {
                    while (true) {
                        BufferedReader serverReader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            String serverMessage = serverReader.readLine();
                            if (serverMessage != null) {
                                // Prepend the server's name to the message
                                String formattedMessage = "Server: " + serverMessage;
                                clientHandler.send(formattedMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new b2Sever();
    }
}
