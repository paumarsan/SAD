import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    private BufferedReader bufferedReader;
    private MySocket mySocket;
    private ClientGUI clientGUI;

    public Client() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        clientGUI = new ClientGUI(this);
    }

    public void connectToServer(String nick, String host, int port) {
        mySocket = new MySocket(nick, host, port);
        mySocket.writeLine(nick);

        new Thread(() -> {
            try {
                while (true) {
                    String message = mySocket.readLine();
                    clientGUI.appendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        if (mySocket != null) {
            mySocket.writeLine(message);
            clientGUI.appendMessage(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            String nick = JOptionPane.showInputDialog("Introdueix el teu nom:");
            client.connectToServer(nick, "localhost", 40000);
        });
    }
}
