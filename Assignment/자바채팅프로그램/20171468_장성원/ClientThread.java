package chattingprogramming;

import java.io.*;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class ClientThread extends Thread
{
   
   private ChatClient  ct_client; 
   private Socket ct_sock; 
   private DataInputStream ct_in; 
   private DataOutputStream ct_out; 
   private StringBuffer ct_buffer; 
   private Thread thisThread;
   private DisplayRoom room;

   private static final String SEPARATOR = "|";
   private static final String DELIMETER = "`";

   
   private static final int REQ_LOGON = 1001;
   private static final int REQ_ENTERROOM = 1011;
   private static final int REQ_SENDWORDS = 1021;
   private static final int REQ_LOGOUT = 1031;
   private static final int REQ_QUITROOM = 1041;
   private static final int REQ_EXIT = 1050;
   private static final int REQ_EXITROOM = 1051;
   
   private static final int REC_ROOMLIST = 2050;
   private static final int REC_LOGONLIST = 2051;
   private static final int REC__WHISPERMESSAGE = 3001;

   private static final int YES_LOGON = 2001;
   private static final int NO_LOGON = 2002;
   private static final int YES_ENTERROOM = 2011;
   private static final int NO_ENTERROOM = 2012;
   private static final int MDY_USERIDS = 2013;
   private static final int YES_SENDWORDS = 2021;
   private static final int NO_SENDWORDS = 2022;
   private static final int YES_LOGOUT = 2031;
   private static final int NO_LOGOUT = 2032;
   private static final int YES_QUITROOM = 2041;
   private static final int roomstate = 2050;
   private static final int YES_EXIT = 2060;
   private static final int NO_EXIT = 20601;
   private static final int YES_WHISPERMESSAGE = 2070;
   private static final int NO_WHISPERMESSAGE = 2071;
  
   private static final int MSG_ALREADYUSER = 3001;
   private static final int MSG_SERVERFULL = 3002;
   private static final int MSG_CANNOTOPEN = 3011;

   private static MessageBox msgBox, logonbox;

   public String id;
   public ClientThread(ChatClient client) {
      try{
         ct_sock = new Socket(InetAddress.getLocalHost(), 2777);
         ct_in = new DataInputStream(ct_sock.getInputStream());
         ct_out = new DataOutputStream(ct_sock.getOutputStream());
         ct_buffer = new StringBuffer(4096);
         thisThread = this;
         ct_client = client; 
      }catch(IOException e){
         MessageBoxLess msgout = new MessageBoxLess(client, "에러", "You cannot enter in Server..");
         msgout.show();
      }
   }

   public void run(){

      try{
         Thread currThread = Thread.currentThread();
         while(currThread == thisThread){ 
            String recvData = ct_in.readUTF();
            StringTokenizer st = new StringTokenizer(recvData, SEPARATOR);
            int command = Integer.parseInt(st.nextToken());
            switch(command){

            
               case YES_LOGON:{
                  logonbox.dispose();
                  ct_client.cc_tfStatus.setText("Long compelte :D");
                  ct_client.cc_btLogon.setEnabled(false);
                  ct_client.cc_tfLogon.setEditable(false);
                  
                  String date = st.nextToken(); 
                  ct_client.cc_tfDate.setText(date);
                  id = st.nextToken();
                
                  break;
               }

               
               case NO_LOGON:{
                  int errcode = Integer.parseInt(st.nextToken());
                  ct_client.cc_tfLogon.setText("");
                  if(errcode == MSG_ALREADYUSER){
                     logonbox.dispose();
                     msgBox = new MessageBox(ct_client, "로그인 오류", "duplicated ID");
                     msgBox.show();
                  }else if(errcode == MSG_SERVERFULL){
                     logonbox.dispose();
                     msgBox = new MessageBox(ct_client, "로그인 오류", "Full Room");
                     msgBox.show();
                  }
                  break;
               }
               case REC_LOGONLIST:{
            	   ct_client.cc_lstMember.clear();
            	   String ids = st.nextToken(); 
            	   System.out.println(ids);
                   StringTokenizer users = new StringTokenizer(ids, DELIMETER);
                   while(users.hasMoreTokens()){
                      ct_client.cc_lstMember.add(users.nextToken());
                   }
            	   break;
               }

               case YES_ENTERROOM:{
                  ct_client.dispose();
                  room = new DisplayRoom(ct_client,this, "대화방");
                  room.pack();
                  room.show();
                  break;
               }

               case NO_ENTERROOM:{
                  int roomerrcode = Integer.parseInt(st.nextToken());
                  if(roomerrcode == MSG_CANNOTOPEN){
                     msgBox = new MessageBox(ct_client, "대화방입장 오류", "Not logon ID");
                     msgBox.show();
                  }   
                  break;
               }

               case roomstate:{
            	   String message = st.nextToken();
            	   room.dr_taContents.append(message);
            	   break;
               }
               
            
               case MDY_USERIDS:{
                  room.dr_lstMember.clear(); 
                  String ids = st.nextToken(); 
                  StringTokenizer roomusers = new StringTokenizer(ids, DELIMETER);
                  while(roomusers.hasMoreTokens()){
                     room.dr_lstMember.add(roomusers.nextToken());
                  }
                  break;
               }

               
               case YES_SENDWORDS:{
                  String id = st.nextToken(); 
                  try{
                     String data = st.nextToken();
                     room.dr_taContents.append(id+" : "+data+"\n");
                  }catch(NoSuchElementException e){}
                  room.dr_tfInput.setText(""); 
                  break;
               }

              
               case YES_LOGOUT:{
            	   ct_client.cc_lstMember.clear();
            	   ct_client.cc_btLogon.setEnabled(true);
            	   ct_client.cc_tfLogon.setEditable(true);
            	   ct_client.cc_tfLogon.setText("");
            	   ct_client.cc_tfStatus.setText("ID를 입력하세요!");
            	   ct_client.cc_tfDate.setText("...");
            	   
            	   
            	   id=null;
            	   MessageBox msgBox = new MessageBox(ct_client, "완료", "Logout complete");
            	   msgBox.show();
                  break;
               }
               case YES_WHISPERMESSAGE:{
            	   String message = st.nextToken();
            	   room.dr_taContents.append(message);
            	   break;
               }
               case NO_WHISPERMESSAGE:{
            	   String errormessage = st.nextToken();
            	   room.dr_taContents.append(errormessage);
            	   break;
               }
               
               case YES_QUITROOM:{
            	   room.dr_lstMember.clear();
            	   System.out.println("st.countTokens()"+st.countTokens());
            	   if(st.countTokens()>0) {
            		   String ids = st.nextToken(); 
                       StringTokenizer roomusers = new StringTokenizer(ids, DELIMETER);
                       while(roomusers.hasMoreTokens()){
                           room.dr_lstMember.add(roomusers.nextToken());
                        }
            	   }
            	  
                  break;
               }
               case YES_EXIT:{
            	   try {
            		   id = "";
            		   ct_in.close();
                       ct_out.close();
                       ct_sock.close();
                       System.out.println("RETURN\n");
                       System.exit(0);
            	   }catch(IOException e) {
            		   e.printStackTrace();
            	   }
                   
                   
               }

            } 

            Thread.sleep(200);

         } 


      }catch(InterruptedException e){
         System.out.println(e);
         release();

      }catch(IOException e){
         System.out.println(e);
         release();
      }
   }

   // 네트워크 자원을 해제한다.
   public void release(){ 
	   if(id!=null) {//로그인상태 일때
		   MessageBox msgBox = new MessageBox(ct_client, "경고", "Logon state yes");
    	   msgBox.show();
	   }
	   else {
		   try {
			   ct_buffer.setLength(0);   
		       ct_buffer.append(REQ_EXIT);
		       ct_buffer.append(SEPARATOR);
		       send(ct_buffer.toString());
		   }catch(IOException e) {
			   e.printStackTrace();
		   }
		   
	   }
   };

   
   public void requestLogon(String id) {
      try{
         logonbox = new MessageBox(ct_client, "로그온", "Loging...");
         logonbox.show();
         ct_buffer.setLength(0);  
         ct_buffer.append(REQ_LOGON);
         ct_buffer.append(SEPARATOR);
         ct_buffer.append(id);
         send(ct_buffer.toString());   
      }catch(IOException e){
         System.out.println(e);
      }
   }

   
   public void requestEnterRoom(String id) {
      try{
         ct_buffer.setLength(0);   // EnterRoom 패킷을 생성한다.
         ct_buffer.append(REQ_ENTERROOM);
         ct_buffer.append(SEPARATOR);
         ct_buffer.append(id);
         send(ct_buffer.toString());   // EnterRoom 패킷을 전송한다.
      }catch(IOException e){
         System.out.println(e);
      }
   }
   public void requestQuitRoom(String id) {
	      try{
	         ct_buffer.setLength(0);   
	         ct_buffer.append(REQ_QUITROOM);
	         ct_buffer.append(SEPARATOR);
	         ct_buffer.append(id);
	         send(ct_buffer.toString());   
	      }catch(IOException e){
	         System.out.println(e);
	      }
	   }

   public void requestSendWords(String words) {
      try{
         ct_buffer.setLength(0);   
         ct_buffer.append(REQ_SENDWORDS);
         ct_buffer.append(SEPARATOR);
         ct_buffer.append(ct_client.msg_logon);
         ct_buffer.append(SEPARATOR);
         ct_buffer.append(words);
         send(ct_buffer.toString());  
      }catch(IOException e){
         System.out.println(e);
      }
   }
   public void requestLogout(String id) {
	   try{
		   	 System.out.println("requestLogout id"+ id);
	         ct_buffer.setLength(0);   
	         ct_buffer.append(REQ_LOGOUT);//로그아웃 타이틀
	         ct_buffer.append(SEPARATOR);
	         ct_buffer.append(id);
	         send(ct_buffer.toString());   
	      }catch(IOException e){
	         System.out.println(e);
	      }
   }
   public void roomexit(String id) {
	  try {
		  ct_buffer.setLength(0);   
	       ct_buffer.append(REQ_EXITROOM);
	       ct_buffer.append(SEPARATOR);
	       ct_buffer.append(id);
	       send(ct_buffer.toString()); 
	  }catch(IOException e) {
		  e.printStackTrace();
	  }
   }

   public void requestwhisper(String userid, String whisperid, String meg) {
	   try {
		   ct_buffer.setLength(0);   
	       ct_buffer.append(REC__WHISPERMESSAGE);
	       ct_buffer.append(SEPARATOR);
	       ct_buffer.append(id);
	       ct_buffer.append(SEPARATOR);
	       ct_buffer.append(whisperid);
	       ct_buffer.append(SEPARATOR);
	       ct_buffer.append(meg);
	       send(ct_buffer.toString()); 
	   }catch(IOException e) {
		   e.printStackTrace();
	   }
   }
   // 클라이언트에서 메시지를 전송한다.
   private void send(String sendData) throws IOException {
      ct_out.writeUTF(sendData);
      ct_out.flush();
   }
}