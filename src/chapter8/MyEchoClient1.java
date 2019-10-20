package chapter8;

import java.io.*;
import java.net.*;

public class MyEchoClient1 {
   public static void main(String args[]) {
      Socket theSocket =null;
      String host;
      InputStream is;
      BufferedReader reader , userInput;
      OutputStream os;
      BufferedWriter writer;
      String theLine;
      if(args.length >0) {
         host =args[0];
      }else {
         host="localhost";
      }
      try {
         theSocket = new Socket(host,7);
         is = theSocket.getInputStream();
         reader =new BufferedReader(new InputStreamReader(is));
         userInput = new BufferedReader(new InputStreamReader(System.in));
         os = theSocket.getOutputStream();
         writer = new BufferedWriter(new OutputStreamWriter(os));

         System.out.println(theSocket.toString());
         System.out.println("current client buffer size: "+theSocket.getSendBufferSize());
         System.out.println(theSocket.getKeepAlive());
         System.out.println(theSocket.getTcpNoDelay());
         theSocket.setSendBufferSize(100);
         theSocket.setKeepAlive(true);
         theSocket.setTcpNoDelay(true);
         System.out.println("modified client buffer size: "+theSocket.getSendBufferSize());
         System.out.println(theSocket.getKeepAlive());
         System.out.println(theSocket.getTcpNoDelay());
         
         while(true) {
            theLine =userInput.readLine();
            if(theLine.equals("quit")) break;
            writer.write(theLine + '\r'+'\n');
            writer.flush();
            //print to.String() method
            System.out.println(reader.readLine().toString());
         }
      }catch(UnknownHostException e) {
         System.err.println(args[0]+"호스트를 찾을 수 없습니다.");
      }catch(IOException e) {
         System.err.println(e);
      }finally {
         try {
            theSocket.close();
         }catch(IOException e) {
            System.out.println(e);
         }
      }
   }
}