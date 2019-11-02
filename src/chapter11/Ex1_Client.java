package chapter11;

import java.io.*;
import java.net.*;

public class Ex1_Client{
   
public final static int daytimeport=5099;
   public static void main(String[] args)throws SocketException {
      DatagramSocket theSocket = null;
      DatagramPacket sender,receiver;
      InetAddress address=null;
      byte[] buffer = new byte[9000];
      String host;
      String text="Thank you!";
      if(args.length>0) {
         host=args[0];
      }else {
         host="localhost";
      }
      try {
        address = InetAddress.getByName(host);
        theSocket = new DatagramSocket();
         sender = new DatagramPacket(buffer, buffer.length, address,daytimeport);
         theSocket.send(sender);
         receiver = new DatagramPacket(buffer, buffer.length);
         theSocket.receive(receiver);
         String message = new String(receiver.getData(),0,receiver.getLength());
         System.out.println(message);
        
      }catch(SocketException se){
          System.out.println(se);
      }catch(UnknownHostException e) {
         System.err.println(args[0]+"호스트를 찾을 수 없습니다");
      }catch(IOException e) {
         System.err.println(e);
      }
   }
}