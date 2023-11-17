package Practica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket {
    private BufferedReader reader;
    private PrintWriter writer;
    private String nomusuari;

    public MySocket(String host, int port, String usuari) throws IOException{
        super(host,port);
        this.nomusuari=usuari;
        this.reader=new BufferedReader(new InputStreamReader(this.getInputStream()));
        this.writer=new PrintWriter(this.getOutputStream());
        this.send_nomusuari();
    }

    public String get_nomusuari(){
        return this.nomusuari;
    }

    public void send_nomusuari(){
        this.writer.println(nomusuari);
    }

    public void teclat_to_server() throws IOException{
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String text;
        while ((text = this.reader.readLine())!=null){
            this.writer.println(text);
            text=lector.readLine();
        }
    }

    public void server_to_client() throws IOException{
        String text;
        while((text = this.reader.readLine())!=null){
            System.out.println(text);
            text = this.reader.readLine();
        }
    }
}
