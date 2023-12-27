import java.net.*;

public class MyServerSocket extend ServerSocket {
    MySocket socket;

    public MyServerSocket(int port) throws Exception {
        super(port);
    }

    @Override
    public MySocket accept() {
        try {
            socket = new MySocket(super.accept());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void close() {
        try {
            super.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
