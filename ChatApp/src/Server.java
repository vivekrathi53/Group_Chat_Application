
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Server {
    public static Socket[] clientSocket = new Socket[500];
    public static String[] clientsnames = new String[500];
    public static int i;
    public static void main(String[] Args) throws IOException{
        try {
            int port = 8818;
            ServerSocket serverSocket = new ServerSocket(port);
            i=0;
            while(true){
                System.out.println("About to accept the connection...");
                clientSocket[i]=new Socket();
                clientSocket[i] = serverSocket.accept();
                System.out.println("Accepted connection from"+clientSocket[i]);
                ServerWorker worker = new ServerWorker(clientSocket[i]);
                Thread t2 = new Thread(worker);
                clientsnames[i]="Vivek";
                OutputStream output = clientSocket[i].getOutputStream();
                output.write("Hello User".getBytes());
                System.out.println(++i);
                t2.start();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

