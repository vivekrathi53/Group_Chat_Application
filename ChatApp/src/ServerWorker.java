

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
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
public class ServerWorker extends Server implements Runnable {

    private final Socket clientSocket1;

    public ServerWorker(Socket clientSocket1) {
        this.clientSocket1 = clientSocket1;
    }

    @Override 
    public void run(){
        try {
            //r.run();
            handleClientSocket(clientSocket1);
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int Sendmessages(String clientname,String message)
    {
        int onlineClientsCount= i;
        System.out.println("ServerWorker.Sendmessages() "+onlineClientsCount);
        for(int i=0;i<onlineClientsCount;i++)
        {
            System.out.println("inside "+clientSocket[i]+"\n"+clientsnames[i]);
            if(clientname.equals(clientsnames[i]))
            {
                try {
                    OutputStream outputStream=clientSocket[i].getOutputStream();
                    String messageline = message;
                    outputStream.write(messageline.getBytes());
                    return 1;
                } catch (IOException ex) {
                    System.out.println("Error in generating outputstream");
                }
                
            }
        }
        return 0;
    }
    private void handleClientSocket(Socket clientSocket1) throws IOException, InterruptedException{
        InputStream inputStream = clientSocket1.getInputStream();
        //OutputStream outputStream = clientSocket2.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine())!=null)
        {
            System.out.println(line);
            int n= line.length();
            for(int i=0,flag=0;line.charAt(i)!=':'&&i<n;i++)
            {
                if(line.charAt(i)=='~')
                {
                    i++;
                    String clientname;
                    int j=i;
                    for(;line.charAt(i)!='~'&&line.charAt(i)!=':';)
                    {
                        i++;
                    }
                    clientname=line.substring(j,i);
                    i=j-1;
                    System.out.println(line.substring(0,i)+" "+clientname);
                    if(Sendmessages(clientname,line.substring(0, i))==0)
                    {
                        System.out.println("Client not online");
                    }
                    if(Sendmessages(clientname,line.substring(0, i))==1)
                    {
                        System.out.println("message sent");
                    }
                    i--;
                    flag=1;
                }
                
            }
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            //outputStream.write(msg.getBytes());    
        }
        clientSocket1.close();
    }
}