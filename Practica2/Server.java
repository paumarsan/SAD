import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    
    private static final ConcurrentHashMap<String, MySocket> chm = new ConcurrentHashMap<String, MySocket>();

    public static void main(String[] args) {
        
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        ExecutorService pool = Executors.newFixedThreadPool(50);
        
        while(true) {
            pool.execute(new Task(ss.accept()));
        }
    }

    public static class Task implements Runnable {
        private MySocket sc;
        private String name;
        private String msg;
        public Task(MySocket s) {
            sc = s;
        }
        @Override
        public void run() {
            while(true) {
                try {
                    sc.println("Introdueix nom d'usuari: ");
                    name = sc.readLine();
                    if (!chm.containsKey(name)) {
                        chm.put(name, sc);
                        System.out.println("USUARI: " + name);
                        for(MySocket bs : chm.values()) {
                            bs.println("--- " + name + " s'ha unit al chat" + " ---");
                        }
                        break;
                    } else {
                        sc.println("Nom d'usuari agafat. Proba altre nom.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            while(sc != null) {
                try {
                    while((msg = sc.readLine()) != null) {
                        for(MySocket bs : chm.values()) {
                            if (sc != bs) {
                                bs.println(name + ": " + msg);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            System.out.println("USUARI DESCONNECTAT: " + name);
            chm.remove(name);
            try {
                for(MySocket bs : chm.values()) {
                    if (sc != bs) {
                        bs.println("--- " + name + " ha abandonat el chat" + " ---");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
