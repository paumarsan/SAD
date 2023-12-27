import java.io.*;
import java.net.*;

public class MySocket{
  Socket socket;
  BufferedReader reader;
  PrintWriter printer;

  public MySocket(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
  public MySocket(String host, int port) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public String readLine() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }
  
  public void printLine(String line) {
        printer.println(line);
        printer.flush();
    }

  public void close() {
        try {
            reader.close();
            printer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
