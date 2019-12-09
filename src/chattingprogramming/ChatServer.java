package chattingprogramming;

import java.io.*;
import java.net.*;

public class ChatServer
{
   public static final int cs_port = 2777;
   public static final int cs_maxclient=10;


   public static void main(String args[]){
      try{
         ServerSocket ss_socket = new ServerSocket(cs_port);
         while(true){
            Socket sock = null;
            ServerThread client = null; 
            try{
               sock = ss_socket.accept(); 
               client = new ServerThread(sock); 
               client.start();
            }catch(IOException e){
               System.out.println(e);
               try{
                  if(sock != null)
                     sock.close();
               }catch(IOException e1){
                  System.out.println(e);
               }finally{
                  sock = null;
               }
            }
         }
      }catch(IOException e){
       
      }
   }
}