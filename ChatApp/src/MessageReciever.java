/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class MessageReciever implements Runnable{

    private Chatwindow currentWindow;
    public MessageReciever(Chatwindow window) {
        this.currentWindow = window;
       
   }

    
    @Override 
    public void run(){
        try {
            //r.run();
            handlerecieving();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handlerecieving() throws IOException, InterruptedException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(currentWindow.getSocket().getInputStream()));
        String line;
        System.out.println("Messages recieving");
        while((line = reader.readLine())!=null)
        {
            System.out.println("Message Recieved");
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            System.out.println(line);
            currentWindow.messagedisplaybox.append(line+"\n");
        }
    }
}