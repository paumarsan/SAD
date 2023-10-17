import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ReadInts {
    static class MyDataInputStream extends DataInputStream{
        public MyDataInputStream(InputStream in) {
            super(in);
            //TODO Auto-generated constructor stub
        }

        /** reads little methods to datainputstream */
        short readShortLittle() throws IOException{
            short s;
            s= (short) in.read();
            return Short.reverseBytes(s);
        }

        int readIntLittle() throws IOException{
            int i;
            i=(int)in.read();
            return Integer.reverseBytes(i);
        }

        long readLongLittle() throws IOException{
            long l;
            l= (long)in.read();
            return Long.reverseBytes(l);
            
        }
    }
    public static void main(String[] args) throws IOException{
        DataInputStream in = new DataInputStream(System.in);
        int i;
        while(true){
            try {
                i=in.readInt();
            } catch (EOFException e) {
                break;
            }
            System.out.println(i);
        }
        
    }
}
