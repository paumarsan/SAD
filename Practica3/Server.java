import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static void main(String[] args) {
        Map<String, MySocket> mySocketMap = new ConcurrentHashMap<String,MySocket>();
        MyServerSocket myServerSocket = new MyServerSocket(5000);
        
        while(true){
            MySocket socket = myServerSocket.accept();
            
            new Thread() {
                public void run() {
                    try{
                    String nick = socket.readLine();
                    mySocketMap.put(nick,socket);
                    System.out.println("Connexio Realitzada");

                    String line;
                    while ((line = socket.readLine()) != null) {
                        System.out.println(line);
                        for (String n : mySocketMap.keySet()){
                            if(n!=nick){
                                mySocketMap.get(n).writeLine(nick+": "+line);
                            }
                        }  
                    }
                    System.out.println("Connexio tancada: "+nick);
                    mySocketMap.remove(nick);
                    socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                }
            }.start(); 
    }
}
}
