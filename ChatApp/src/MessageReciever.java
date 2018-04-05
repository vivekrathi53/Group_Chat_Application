/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class MessageReciever extends Chatwindow implements Runnable{

    private Socket clientSocket1;
    public MessageReciever(Socket clientSocket1) {
        this.clientSocket1 = clientSocket1;
       this.messagedisplaybox.append("\noyehoe");
    }

    
    @Override 
    public void run(){
        try {
            //r.run();
            handlerecieving(clientSocket1);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void handlerecieving(Socket clientSocket1) throws IOException, InterruptedException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        String line;
        System.out.println("Messages recieving");
        while((line = reader.readLine())!=null)
        {
            System.out.println("Message Recieved");
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            System.out.println(line);
            Chatwindow.messagedisplaybox.append("\n"+line);
        }
    }
}