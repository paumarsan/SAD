/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author virtual
 */
public class EditableBufferedReader extends BufferedReader {
    
    private static final int ESCAPE_KEY=27;
    private static final int BRACKET_KEY=91;
    
    private boolean isRawmode=false;
    private boolean overwriteMode=false;
    private StringBuilder line;
    private int cursorPosition;
    
    public EditableBufferedReader(Reader in) {
        super(in);
    }
    
    public void setRaw() throws IOException{
        if(!isRawmode){
            ProcessBuilder pb = new ProcessBuilder("stty -echo raw");
            Process process = pb.start();
            isRawmode=true;
        }
    }
    public void unsetRaw() throws IOException{
        if(isRawmode){
            ProcessBuilder pb = new ProcessBuilder("stty -echo cooked");
            Process process = pb.start();
            isRawmode=false;
        }
    }
    
    @Override
    public int read() throws IOException{
       int c=super.read();
       if(c==ESCAPE_KEY){
           int nextchar= super.read();
           if(nextchar==BRACKET_KEY){
               int action= super.read();
               this.realitzarSequenciaEscape(action);
           }
       }else{
           realitzarCaracterNormal(c);
       }
       return c;
           
    }
    
    private void realitzarSequenciaEscape(int action){
        switch (action){
            case 'C': //flecha dreta
                
                
        }
        
    }
    
    private void realitzarCaracterNormal(int c){
        
    }
    
    public String readLine() throws IOException{
        StringBuilder line = new StringBuilder();
        int c;
        try{
            while((c=read())!=-1){
                if(c=='\r'){
                    
                }else if (c== )
                
            }
        }
    }
    

    
    
    
}
