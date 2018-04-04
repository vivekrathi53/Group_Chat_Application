package chattingappfaeature;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.Socket;
public class Serverfilesender  {
	  public static void main(String args[]){    
           try{    
                byte b[]=new byte[20002];
                Socket sr=new Socket("localhost",6777);
                InputStream is=sr.getInputStream();
             FileOutputStream fout=new FileOutputStream("E:\\english\\Preposition.txt");    
             is.read(b,0,b.length); //converting string into byte array    
             fout.write(b, 0, b.length);
             System.out.println("success...");    
            }catch(Exception e){System.out.println(e);}    
      }

   
}

