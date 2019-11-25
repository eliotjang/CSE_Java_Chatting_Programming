package chapter13;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MulticastS extends Frame {
   TextArea display;
   Label info;
   String address = "239.255.10.10";
   DatagramPacket RecievePacket, SendPacket, mpacket;
   int port = 5000;
   boolean logon = false;
   
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int LOGOUT = 1031;
	
   public MulticastS() {
      super("서버");
      info = new Label();
      add(info, BorderLayout.NORTH);
      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
      
      display.setEditable(false);
      
      
      add(display, BorderLayout.CENTER);
      addWindowListener(new WinListener());
      
      setSize(300,300);
      setVisible(true);
   }
	
   @SuppressWarnings("resource")
   public void runServer() throws IOException {
	   DatagramSocket socket = null;
	   MulticastSocket server = null;
	   
	   
	   RecievePacket = new DatagramPacket(new byte[1], 1);
	   SendPacket = new DatagramPacket(new byte[65508], 65508);
	
	
	   socket = new DatagramSocket(5000);
	   while(true) {
		   SendPacket.setLength(SendPacket.getData().length);
		   socket.receive(SendPacket);
		   String message = new String (SendPacket.getData(), 0, SendPacket.getLength());
		   StringTokenizer st = new StringTokenizer(message, SEPARATOR);
		   
		   
		   int command = Integer.parseInt(st.nextToken());
		   
		   switch(command) {
               case REQ_LOGON : {
                  String ID = st.nextToken();
                  display.append("클라이언트가 " + ID + "(으)로 로그인 하였습니다.\r\n");
                  InetAddress cadr = SendPacket.getAddress();
                  int cp = SendPacket.getPort();
                  
                  String text = address + "|" + port;
                  
                  
                  
                  byte[] buf = text.getBytes();
                  RecievePacket.setData(buf);
                  RecievePacket.setLength(buf.length);
                  RecievePacket.setAddress(cadr);
                  RecievePacket.setPort(cp);
                  
                  socket.send(RecievePacket);
                  logon = true;
                  server = new MulticastSocket(15);
                break;
               }
               case REQ_SENDWORDS : { 
                  String ID = st.nextToken();
                  String text = st.nextToken();
                  
                  display.append(ID + " : " + text + "\r\n");
                  String rcv = ID + " : " + text + "\r\n";
                  byte[] buf = rcv.getBytes("UTF8");
                  
                  RecievePacket = new DatagramPacket(buf, buf.length, InetAddress.getByName(address), 15);
          		  server.send(RecievePacket);
          		  
                  break;
               }
               case LOGOUT : {
            	   String ID = st.nextToken();
            	   display.append(ID+"(이)가 로그아웃을 했습니다.\r\n");  
            	   
            	   logon = false;
            	   break;
               }
		   }
	   }
	   
   }
		
   public static void main(String args[]) throws IOException {
	  MulticastS s = new MulticastS();
      s.runServer();
   }
		
   class WinListener extends WindowAdapter {
      public void windowClosing(WindowEvent e) {
         System.exit(0);
      }
   }
}
