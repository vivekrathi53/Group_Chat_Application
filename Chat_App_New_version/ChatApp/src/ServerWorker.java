

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Vivek Rathi
 */
public class ServerWorker extends Server implements Runnable {

    private final Socket clientSocket1;
    public String un=null;
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
        for(int j=0;j<onlineClientsCount;j++)
        {
            System.out.println("inside "+clientSocket[j]+clientsnames[j]);
            if(clientname.equals(clientsnames[j]))
            {
                try {
//                    OutputStream outputStream=clientSocket[j].getOutputStream();
//                    String messageline = message;
//                    outputStream.write(messageline.getBytes());
//                    outputStream.
                    PrintWriter out =new PrintWriter(clientSocket[j].getOutputStream(),true);
                    out.println(un+"-->"+message);
                    return 1;
                } catch (IOException ex) {
                    System.out.println("Error in generating outputstream");
                }
                
            }
        }
        return 0;
    }
    void RecieveFile()
    {
        try {
            FileInputStream fin = new FileInputStream("F:\\java projects\\Group_Chat_Application-master\\ChatApp\\src\\Server Recieved Files\\a1.txt");
            byte b[] = new byte[999999999];
            fin.read(b,0,b.length);
            System.out.println("File Recieved");
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void getuserslist()
    {
        System.out.println("out of try)");
        try
        {
            Class.forName("java.sql.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/chatApp","root","password");
            Statement stmt=con.createStatement();
            System.out.println("connection done");
            String q1="select username from currentusers;";
            ResultSet rs=stmt.executeQuery(q1);
            System.out.println("query exceuted");
            String message="";
            while(rs.next())
            {
                message=message+rs.getString("username")+"-";
            }
            System.out.println(message);
            message=message.substring(0,message.length()-1);
            System.out.println(message);
            PrintWriter out=null;
            out = new PrintWriter(clientSocket1.getOutputStream(),true);
            out.println(message);
            System.out.println("user list sent");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    int loginverification(String line)
    {
        String data[];
        data=line.split("~");
        un = data[1];
        String pass = data[2];
        try {
            Class.forName("java.sql.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/chatApp","root","password");
            Statement stmt=con.createStatement();
            String q1="select password from currentusers where username='"+(un)+"';";
            ResultSet rs=stmt.executeQuery(q1);
            rs.next();
            String p=rs.getString("password");
            if(p.equals(pass))
            {
                
                return 1;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    int createnewuser(String line)
    {
        String[] data;
        data=line.split("-");
        try
        {
            Class.forName("java.sql.Driver");
            Connection con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost/chatApp","root","password");
            Statement stmt=(Statement)con.createStatement();
            String q1="insert into currentusers values('"+(data[1])+"','"+(data[2])+"','"+(data[3])+"','"+(data[4])+"','"+(data[5])+"','"+(data[6])+"');";
            stmt.executeUpdate(q1);
            return 1;
        }
        catch(Exception e)
                {
                    return 0;
                }  
    }
    private void handleClientSocket(Socket clientSocket1) throws IOException, InterruptedException{
        InputStream inputStream = clientSocket1.getInputStream();
        //OutptStream outputStream = clientSocket2.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine())!=null)
        {
            System.out.println(line);
            int n= line.length();
            if(n>22&&line.substring(0,22).equals("!!LOGIN_VERIFICATION!!")) 
                {
                    if(loginverification(line)==1)
                    {
                        PrintWriter out =new PrintWriter(clientSocket1.getOutputStream(),true);
                        out.println("!!1!!");
                        continue;
                    }
                    if(loginverification(line)==0)
                    {
                        PrintWriter out =new PrintWriter(clientSocket1.getOutputStream(),true);
                        out.println("!!0!!");
                        continue;
                    }
                    
                }
            if(n==16&&line.substring(0,16).equals("!!FILEINCOMING!!")) 
            {
                RecieveFile();
                continue;
            }
            if(n>16&&line.substring(0,17).equals("!!CREATENEWUSER!!")) 
            {
                if(createnewuser(line)==0)
                {
                    destroyme(this);
                }
                if(createnewuser(line)==1)
                {
                    continue;
                }
                continue;
            }
            if(n>=16&&line.substring(0,16).equals("!!GETUSERSLIST!!"))
            {
                getuserslist();
                continue;
            }
            for(int k=0,flag=0;line.charAt(i)!=':'&&k<n;k++)
            {
                
                if(line.charAt(k)=='~')
                {
                    k++;
                    String clientname;
                    int j=k;
                    for(;line.charAt(k)!='~'&&line.charAt(k)!=':';)
                    {
                        k++;
                    }
                    clientname=line.substring(j,k);
                    k=j-1;
                    System.out.println(line.substring(0,k)+" "+clientname);
                    int response = Sendmessages(clientname,line.substring(0, k));
                    if(response==0)
                    {
                        System.out.println("Client not online");
                    }
                    if(response==1)
                    {
                        System.out.println("message sent");
                    }
                    //k--;
                    flag=1;
                }
                
            }
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            //outputStream.write(msg.getBytes()); 
            line=null;
        }
        clientSocket1.close();
    }
}