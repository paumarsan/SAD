import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class WriteInts{



    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        DataOutputStream out = new DataOutputStream(System.out);
        int i;
        while(sc.hasNextInt()){
            i = sc.nextInt();
            out.writeInt(i);
        }
        sc.close();
        out.flush();
    }
}