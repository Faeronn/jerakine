package fr.jikosoft.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketManager {

    private static final String SERVER_URL = "localhost";
    private static final int PORT = 4443;

    public static String sendMessage(String message) {
        try (Socket socket = new Socket(SERVER_URL, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the server at " + SERVER_URL + ":" + PORT);

            // Send the message
            out.println(message);
            System.out.println("Sent to server: " + message);

            // Read the response
            String response = in.readLine();
            System.out.println("Received from server: " + response);

            return response;

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return "Error communicating with server.";
        }
    }
}
