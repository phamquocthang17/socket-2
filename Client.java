package socket2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String number;
            while ((number = in.readLine()) != null) {
                System.out.println("Received from server: " + number);
            }

            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
