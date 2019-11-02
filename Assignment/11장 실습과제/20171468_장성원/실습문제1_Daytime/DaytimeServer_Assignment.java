/*
 * Author: Eliot Jang
 * last_modified_at: 2019-11-02T14:30:00+09:00
 */
package chapter11;

import java.io.*;
import java.net.*;
import java.util.Date;

public class DaytimeServer_Assignment {
   public final static int daytimeport=5099;
   public final static int BUFFER_SIZE=9000;
   public static void main(String args[]) {
      DatagramSocket theSocket;
      String message;
      byte[] buffer = new byte[BUFFER_SIZE];
      try {
         theSocket = new DatagramSocket(daytimeport);
         
            try {
               while(true) {
               DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
               theSocket.receive(incoming);
               
               Date now = new Date();
               message = now.toString()+"\r\n";
               System.out.println(incoming.getAddress()+ "와 연결되었습니다.");
               System.out.println(message);
               buffer = message.getBytes();
               DatagramPacket outgoing = new DatagramPacket(buffer, buffer.length, incoming.getAddress(), incoming.getPort());
               theSocket.send(outgoing);
               }
            }catch(IOException e) {
               System.out.println(e);
            }finally {
               if(theSocket != null)theSocket.close();
            }
               
      }catch(IOException e) {
         System.out.println(e);
         
      }
   }
}      
         
         
         
      