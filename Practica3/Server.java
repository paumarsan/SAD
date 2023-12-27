import java.io.IOException;
import java.util.*;
public class Server implements Runnable {

  public static Map<String, MySocket> clientsMap = new HashMap<String, MySocket>(); 
  private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private static final Lock r = rwl.readLock();
  private static final Lock w = rwl.writeLock(); 

  public MySocket mySocket;
  public static boolean validUser = false; 
  public String nick;

  public Server(String nickName, MySocket mySocket) {
    this.mySocket = mySocket;
    this.nick = nickName;
  }

  public static void main(String[] args) {
    MyServerSocket server = null;
    try {
      server = new MyServerSocket(5000);
      MySocket clientSocket;
      String name;
      while (true) {
        clientSocket = server.accept();
        while (!validUser) {
          clientSocket.printLine("Introdueix el nom d'usuari: ");
          name = clientSocket.readLine();
          if (usedNickName(name)) {
            clientSocket.printLine(" " + name + " ja existeix, escull un altre nom d'usuari: ");
          } else {
            putClient(name, clientSocket); 
            new Thread(new Server(name, clientSocket)).start();      
            validUser = true;
            clientSocket.printLine("........ " + name + " t'has unit al xat ........");
            System.out.println("........ " + name + " s'ha unit al xat ........");
          }
        }
        validUser = false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      server.close();
      ;
    }
  }

  @Override
  public void run() {
    sendOthers(nick);
    closeClient(nick);
    removeClient(nick);
  }

  public static boolean usedNickName(String name) {
    boolean used;
    r.lock();
    try {
      used = clientsMap.containsKey(name); 
    } finally {
      r.unlock();
    }
    return used;
  }

  public static void putClient(String name, MySocket clientSocket) {
    w.lock();
    try {
      clientsMap.put(name, clientSocket); 
    } finally {
      w.unlock();
    }
  }

  public static void closeClient(String nickName) {
    r.lock();
    try {
      clientsMap.get(nickName).close(); 
    } finally {
      r.unlock();
    }
  }

  public static void removeClient(String nickName) {
    w.lock();
    try {
      clientsMap.remove(nickName); 
    } finally {
      w.unlock();
    }
  }

  public static void sendOthers(String nickName) {
    String line;
    while ((line = clientsMap.get(nickName).readLine()) != null) {
      r.lock();
      try {
        for (HashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) { 
          if (!entry.getKey().equals(nickName)) {
            entry.getValue().printLine(nickName + " : " + line);
          }
        }
      } finally {
        r.unlock();
      }
    }
    System.out.println("........ " + nickName + " ha sortit del xat ........");
  }
  
  public Set<String> getClientsMapList(){
    return clientsMap.keySet();
  }
}
