package socket2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class b2Client {
    private String username;

    public static void main(String[] args) throws IOException {
        new b2Client();
    }

    public b2Client() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        username = scanner.nextLine();
        Socket socket = new Socket("localhost", 8088);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Thread to receive messages from the server
        new Thread(() -> {
            while (true) {
                try {
                    String message = in.readLine();
                    if (message != null) {
                        System.out.println(message);
                    } else {
                        System.out.println("Server disconnected");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread to send messages from the client
        new Thread(() -> {
            while (true) {
                try {
                    String message = scanner.nextLine();
                    if (message != null) {
                        out.writeBytes(username + ": " + message + "\n");
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
