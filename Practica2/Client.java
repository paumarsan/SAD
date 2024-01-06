import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

  public static void main(String[] args) {
        
        MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));
        new Thread() {
            public void run() {
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                try {
                    while((line = in.readLine()) != null) {
                        sc.println(line);
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            } 
        }.start();
    
   new Thread() {
            public void run() {
                String line;
                while((line = sc.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }.start();
    }
}
