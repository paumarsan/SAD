import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MySocket extends Socket {

    Socket socket;
    BufferedReader buffReader;
    PrintWriter printWriter;


    public MySocket(Socket socket) {

        try {
            this.socket = socket;
            buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MySocket(String host, int port) {

        try {
            socket = new Socket(host, port);
            buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() {

        String line = null;
        try {
            line = buffReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void printLine(String line) {

        printWriter.println(line);
        printWriter.flush();
    }

    public void close() {

        try {
            buffReader.close();
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}