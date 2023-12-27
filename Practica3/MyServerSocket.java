import java.net.*;

public class MyServerSocket {
    ServerSocket serversocket;

    public MyServerSocket(){
        try{
            serversocket = new ServerSocket(8080);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MySocket accept(){
        try{
            Socket sc = serversocket.accept();            
            return new MySocket(sc);                        
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
