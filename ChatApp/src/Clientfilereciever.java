package chattingappfaeature;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public  class Clientfilereciever 
{
 public static void main(String args[]){    
          try{    
              ServerSocket s=new ServerSocket(6777);
              Socket sr=s.accept();
            FileInputStream fin=new FileInputStream("E:\\english\\Preposition Rules-2.txt");    
            byte b[]=new byte[20002]; 
            System.out.println("Failure");  
            fin.read(b,0,b.length);
            OutputStream os=sr.getOutputStream();
            os.write(b, 0, b.length);
          }catch(Exception e){System.out.println(e);}    
         }    
}

