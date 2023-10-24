import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Columnas {

    public static void main(String[] args){
        int c
        try{
            Kbd.setRaw();
            /**
             * report size of text arte in charts: CSI 18 t
             * should return ESC [ 8; rows; columns; t
             */
            
/**
 * 
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh","-c","tput cols");
            Process process= processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String Line;
            while((Line=reader.readLine())!=null){
                int columns=Integer.parseInt(Line);
                System.out.println("Tamaño de columnas del terminal:"+ columns);
            }
            */

            //Metodo Con sequencia de escape 1:
            System.out.print("\033[18t");
            Scanner sc= new Scanner(System.in);
            sc.skip("\033\\[8;\\d+;(\\d+)t");
            c=Integer.parseInt(sc.match().group(1));

            //Metodo con sequencia de escape 2:
            System.out.print("\033[18t");
            Scanner sc= new Scanner(System.in);
            sc.skip("\033\\[8;+").useDelimiter(";");
            c=Integer.parseInt(sc.match().group(1));
            sc.nextInt();
            sc.skip(";").useDelimiter("t");
            c=sc.nextInt();        
            sc.skip("t");

        }finally{
            Kbd.unsetRaw();
        }
        System.out.println("COLUMNS="+c);
    }
    
}
