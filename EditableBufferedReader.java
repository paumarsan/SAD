/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private static final int LEFT_KEY=68;
    private static final int RIGHT_KEY=67;
    private static final int HOME_KEY=72;
    private static final int END_KEY=70;
    private static final int INS_KEY=50;
    private static final int DEL_KEY=51;
    private static final int BACKSPACE_KEY=127;


    private boolean isRawmode=false;
    private Line line;
    
    public EditableBufferedReader(Reader in) {
        super(in);
        line= new Line();
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
            case LEFT_KEY: //flecha dreta
            line.moveLeft();
            break;
            case RIGHT_KEY:
            line.moveRight(); 
            break;   
            case HOME_KEY:
            line.moveToBeginning();
            break;   
            case END_KEY:
            line.moveToEnd(); 
            break;   
            case INS_KEY:
            line.toggleInsertMode();
            break;
            case DEL_KEY:
            line.backspace();
            break;
                
                
        }
        
    }
    
    private void realitzarCaracterNormal(int c){
        if(c==BACKSPACE_KEY){
            line.backspace();
        }else{
        line.insert((char) c);
        }
    }
    
    public String readLine() throws IOException{
        return line.getText();
    }
}
