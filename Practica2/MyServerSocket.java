import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket extends ServerSocket {

    MySocket mySocket;
    
    public MyServerSocket(int port) throws IOException {

        super(port);
    }

    @Override
    public MySocket accept() {

        try {
            mySocket = new MySocket(super.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mySocket;
    }

    public void close() {
        
        try {
            super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
