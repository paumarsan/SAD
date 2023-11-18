package Practica2;

import java.net.*;
import java.util.*;
import java.io.*;

public class MyServerSocket extends ServerSocket {
    private Map <String, Socket> conexions;

    public MyServerSocket(int port) throws IOException{
        super(port);
        conexions=new HashMap<String, Socket>();
    }

    public String read_nomusuari(Socket socket) throws IOException{
        InputStream input=socket.getInputStream();
        return new BufferedReader(new InputStreamReader(input)).readLine();
    }

    public String read_client(String usuari) throws IOException{
        InputStream input=conexions.get(usuari).getInputStream();
        return new BufferedReader(new InputStreamReader(input)).readLine();
    }
    
    public String read_all() throws IOException {

        for (String key : conexions.keySet()) { 
            String text = read_client(key); 
            if (text != null) {
                return text; 
            }
        }
        return null;
    }

    public void write_client(String username, String text) throws IOException {

        OutputStream output = conexions.get(username).getOutputStream();
        new PrintWriter(output, true).println(text);
    }

    public void write_all(String text) throws IOException {

        for (String key : conexions.keySet()) {
            write_client(key, text);
        }
    }

    public void write_allmenysun(String client, String text) throws IOException {

        for (String key : conexions.keySet()) { 
            if (client != key) {
                write_client(key, text);
            }
        }
    }


    public boolean afegir_usuari(String nomusuari, Socket socket) {

        for (Map.Entry<String, Socket> entry : conexions.entrySet()) { 
            if (nomusuari.equals(entry.getKey())) { 
                return false;
            }
        }
        conexions.put(nomusuari, socket);
        return true;
    }

     public void delete_user(String nomusuari) throws IOException {

        try {

            write_all(nomusuari + " has left the chat."); 
            conexions.remove(nomusuari); 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read_teclat() throws IOException {

        BufferedReader teclat_reader = new BufferedReader(new InputStreamReader(System.in));
        String text;
        while ((text = teclat_reader.readLine())!=null) {
            this.write_all("ADMIN: " + text);
        }
    }

    public void client_to_server(String username) throws IOException {
        String text;
        while ((text = this.read_client(username)) != null) {
            System.out.println(username + ": " + text);
            this.write_allmenysun(username, username + ": " + text);
        }
    }

}
