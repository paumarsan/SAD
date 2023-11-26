package Practica2;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {

        // args[0] = ip, args[1] = port, args[2] = username
        try (MySocket socket_usuari = new MySocket(args[0], Integer.parseInt(args[1]), args[2])) {

            new Thread(new Runnable() { // Nou thread per enviar els missatges escrits per el client al server
                @Override
                public void run() {

                    try {
                        socket_usuari.teclat_to_server(); // Llegim el que hi ha al teclat per enviar-ho
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }).start();

            // Main thread per llegir els misstges del server
            socket_usuari.server_to_client();

        } catch (UnknownHostException exception) { 

            exception.printStackTrace();

        } catch (IOException exception) {

            exception.printStackTrace();
        }
    }
}
