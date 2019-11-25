package chapter13;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MulticastC extends Frame implements ActionListener, KeyListener {
	
   TextArea display;
   TextField wtext, ltext;
   Label mlbl, wlbl, loglbl;
   Button close;
   
   DatagramSocket client;
   Panel plabel, logout;
   StringBuffer ClientData;
   String data;
   String ID;
   
   DatagramPacket ReceivePacket, SendPacket;
   MulticastSocket socket;
   InetAddress group;
   String hostname = "localhost";
   boolean logon = false;
	
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001;
   private static final int REQ_SENDWORDS = 1021;
   private static final int LOGOUT = 1031;
	
   public MulticastC() {
      super("클라이언트");

      mlbl = new Label("채팅 상태를 보여줍니다.");
      add(mlbl, BorderLayout.NORTH);

      display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
      display.setEditable(false);
      add(display, BorderLayout.CENTER);

      Panel ptotal = new Panel(new BorderLayout());
 
      Panel pword = new Panel(new BorderLayout());
      
      wlbl = new Label("대화말");
      wtext = new TextField(30); 
      wtext.addKeyListener(this); 
      pword.add(wlbl, BorderLayout.WEST);
      pword.add(wtext, BorderLayout.CENTER);
      ptotal.add(pword, BorderLayout.NORTH);

      plabel = new Panel(new BorderLayout());
      loglbl = new Label("로그온");
      ltext = new TextField(20); 
      ltext.addActionListener(this);
      plabel.add(loglbl, BorderLayout.WEST);
      plabel.add(ltext, BorderLayout.CENTER);
      ptotal.add(plabel, BorderLayout.CENTER);
      
      logout = new Panel(new BorderLayout());
      close = new Button("log out");
      close.addActionListener(this);
      logout.add(close, BorderLayout.CENTER);
      
      ptotal.add(logout, BorderLayout.SOUTH);
      logout.setVisible(false);

      add(ptotal, BorderLayout.SOUTH);

      addWindowListener(new WinListener());
      setSize(400,250);
      setVisible(true);
   }
	
   public void runClient() throws IOException {
     client = new DatagramSocket();
     SendPacket = new DatagramPacket(new byte[65508], 65508);
     ReceivePacket = new DatagramPacket(new byte[1], 1, InetAddress.getByName(hostname), 5000);
     mlbl.setText("멀티캐스트 채팅 서버에 가입 요청합니다.");
     
     System.out.println(logon);
   }
   
   public static void main(String args[]) {
	   MulticastC c = new MulticastC();
      try {
		c.runClient();
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
		
   public void actionPerformed(ActionEvent ae){
      if (ae.getSource() == close) {
    	  plabel.setVisible(true);
    	  
          logout.setVisible(false);
    	  plabel.removeAll();
    	  
    	  
          plabel.add(loglbl, BorderLayout.WEST);
          plabel.add(ltext, BorderLayout.EAST);
          
          plabel.validate();
    	  logon = false;
    	  
    	  ClientData.setLength(0);
    	  ClientData.append(LOGOUT);
    	  
    	  ClientData.append(SEPARATOR);
    	  ClientData.append(ID);
    	  
		 @SuppressWarnings("unused")
		byte[] buffer;
		try {
			buffer = new String(ClientData).getBytes("UTF8");
			client.send(ReceivePacket);
			
			socket.leaveGroup(group);
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 display.append("로그아웃\n");
		ID = "";
		ltext.setVisible(true);
    	  
      }
      else {
    	  ID = ltext.getText();
    	  ltext.setText("");
    	  
          if(ID.equals("") != true) {
             mlbl.setText(ID + "(으)로 로그인 하였습니다.");
          
          try {
        	  data = REQ_LOGON+SEPARATOR+ID;
        	  
              byte[] buf = data.getBytes();
              
        	  ReceivePacket.setData(buf);
        	  ReceivePacket.setLength(buf.length);
        	  client.send(ReceivePacket);
        	  SendPacket.setLength(SendPacket.getData().length);
        	  client.receive(SendPacket);
        	  
        	  
        	  String message = new String(SendPacket.getData());
              display.append("logon" + ID +"\r\n");
        	  
        	  StringTokenizer st = new StringTokenizer(message, SEPARATOR);
        	  String addr = st.nextToken();
        	  group = InetAddress.getByName(addr);
        	  
        	  
        	  String port = st.nextToken();
              display.append("멀티캐스트 채팅그룹의 주소는 " + addr+ ":" + port + "입니다.");
              display.append("\r\n");
              
              int mp = 15;
              if (st.hasMoreTokens())
            	  mp = Integer.parseInt(port);
              
              
              socket = new MulticastSocket(mp);
              socket.setTimeToLive(1);
              socket.joinGroup(group);
              
              R_msg servert = new R_msg(socket);
              servert.start();
              
              logon = true;
              logout.setVisible(true);
              
              plabel.removeAll();
              plabel.add(close, BorderLayout.CENTER);
              plabel.validate();
              
           } catch(Exception e) {
              e.printStackTrace();
           }
          }
          else {
        	  mlbl.setText("다시 로그인 하세요!!!");
          }
      }
   
   }
	
		
   class WinListener extends WindowAdapter {
      public void windowClosing(WindowEvent e){
    	  display.append("로그아웃\n");
    	  ID = "";
    	  ltext.setVisible(true);
         System.exit(0);
      }
   }
   
   class R_msg extends Thread{
	   MulticastSocket ms;
	   @SuppressWarnings("unused")
	private static final String SEPARATOR = "|";
	   public R_msg(MulticastSocket s) {
			   ms = s;

	   }
	   public void run(){
		  
			   while(true) {
			       SendPacket = new DatagramPacket(new byte[65508], 65508);
				   try {
					ms.receive(SendPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}
				   display.append(new String(SendPacket.getData()) + "\r\n");
			   }
		   
	   }
   }

   public void keyPressed(KeyEvent ke) {
      if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
         String message = new String();
         message = wtext.getText();
         
         System.out.println("for test..");
       
         if (ID == null) {
            mlbl.setText("다시 로그인 하세요!!!");
            wtext.setText("");
         } else {
            	data = REQ_SENDWORDS+SEPARATOR+ID+SEPARATOR+message;
               byte[] buf = null;
			try {
				buf = new String(data).getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
               ReceivePacket.setData(buf);
               ReceivePacket.setLength(buf.length);
               try {
				client.send(ReceivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}

            	wtext.setText("");
            
         }
      }
   }

   public void keyReleased(KeyEvent ke) {
   }

   public void keyTyped(KeyEvent ke) {
   }
}