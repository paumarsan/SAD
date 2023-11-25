package Practica2;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        // args[0] = port
        try (MyServerSocket server_socket = new MyServerSocket(Integer.parseInt(args[0]))) {
            System.out.println("Server listening on port " + server_socket.getLocalPort());
            new Thread(new Runnable() { //Thread per a enviar els missatges escrits per el server als clients
                @Override
                public void run() {

                    try {

                        server_socket.read_teclat();

                    } catch (IOException exception) { 

                        exception.printStackTrace();
                    }
                }
            }).start();

            while (true) { //Loop infinit per acceptar noves conexions de clients

                // accept() retorna un socket de usuari amb la informacio del client
                Socket user_socket = server_socket.accept(); // Esperem la conexio
                // read_name() retorna el nom d'usari del client
                String username = server_socket.read_nomusuari(user_socket);

                // add_user() retorna tru si el nom d'usuari no esta en ús
                if (server_socket.afegir_usuari(username, user_socket)) {

                    System.out.println("User " + username + " connected");
                    server_socket.write_all(username + " has joined the chat");

                    new Thread(new Runnable() { // Thread per llegir els missatges dels clients

                        @Override
                        public void run() { 

                            try {

                                server_socket.client_to_server(username);
                                server_socket.delete_user(username);

                            } catch (IOException exception) { // If there is an error, print the stack trace

                                System.out.println(username + " has left the chat");

                            }
                        }
                    }).start(); 

                } else { //Si l'usuari esta en us

                    OutputStream output = user_socket.getOutputStream();
                    new PrintWriter(output, true).println("Username already in use");
                    user_socket.close();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
