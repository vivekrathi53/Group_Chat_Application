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
public class MessageReciever extends Thread{

    private final Socket clientSocket1;
    public MessageReciever(Socket clientSocket1) {
        this.clientSocket1 = clientSocket1;
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
//        OutputStream outputstream = clientSocket1.getOutputStream();
        String msg = "Recieving Messages...";
//        System.out.println(msg);
//        outputstream.write(msg.getBytes());
        Chatwindow c1 = new Chatwindow();
        while(true)
        {
            String line = reader.readLine();
            System.out.println("Message Recieved");
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            c1.messagedisplaybox.setText("\n"+line);
        }
        clientSocket1.close();
    }
}