import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server implements Runnable {

  public static Map<String, MySocket> clientsMap = new HashMap<String, MySocket>(); // diccionari de parells
                                                                                    // (nick,socket)
  private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private static final Lock r = rwl.readLock(); // lock de lectura
  private static final Lock w = rwl.writeLock(); // lock d'escriptura

  public MySocket mySocket;
  public static boolean validUser = false; // boolean per si nom d'usuari introduit es valid
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

        while (!validUser) { // fins que no tinguem un nom d'usuari valid
          clientSocket.printLine("Nom d'usuari: ");
          name = clientSocket.readLine();

          if (usedNickName(name)) { // comprovem que no existeixi el nom dins del diccionari
            clientSocket.printLine(" " + name + " ja existeix, escull un altre nom d'usuari: ");

          } else {
            putClient(name, clientSocket); // afegim el nou socket al diccionari
            new Thread(new Server(name, clientSocket)).start(); // nou thread del fill del servidor que aten al client
                                                                // nou
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
      used = clientsMap.containsKey(name); // comprovem que el nick no estigui ja en el diccionari
    } finally {
      r.unlock();
    }
    return used;
  }

  public static void putClient(String name, MySocket clientSocket) {
    w.lock();
    try {
      clientsMap.put(name, clientSocket); // afegim el nou socket al diccionari amb un nom valid
    } finally {
      w.unlock();
    }
  }

  public static void closeClient(String nickName) {
    r.lock();
    try {
      clientsMap.get(nickName).close(); // tanquem el socket
    } finally {
      r.unlock();
    }
  }

  public static void removeClient(String nickName) {
    w.lock();
    try {
      clientsMap.remove(nickName); // borrem el socket del diccionari
    } finally {
      w.unlock();
    }
  }

  public static void sendOthers(String nickName) {
    String line;
    while ((line = clientsMap.get(nickName).readLine()) != null) {
      r.lock();
      try {
        for (HashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) { // per tots els usuaris del xat
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
}