package chattingprogramming;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerThread extends Thread 
{
   private Socket st_sock;
   private DataInputStream st_in;
   private DataOutputStream st_out;
   private StringBuffer st_buffer;
  
   private static Hashtable<String,ServerThread> logonHash; 
   private static Vector<String> logonVector;
   
   private static Hashtable<String,ServerThread> roomHash; 
   private static Vector<String> roomVector;

   private static int isOpenRoom = 0; 

   private static final String SEPARATOR = "|";
   private static final String DELIMETER = "`"; 
   private static Date starttime;  	

   public String st_ID; 			

  
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
   
   private static final int REC_GAME = 3333;
   private static final int YES_GAME = 3334;
   private static final int NO_GAME = 3335;
 
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


   static{	
      logonHash = new Hashtable<String,ServerThread>(ChatServer.cs_maxclient);
      logonVector = new Vector<String>(ChatServer.cs_maxclient); 
      
      roomHash = new Hashtable<String,ServerThread>(ChatServer.cs_maxclient);
      roomVector = new Vector<String>(ChatServer.cs_maxclient); 
   }

   public ServerThread(Socket sock){
      try{
         st_sock = sock;
         st_in = new DataInputStream(sock.getInputStream()); 
         st_out = new DataOutputStream(sock.getOutputStream());
         st_buffer = new StringBuffer(2048);
      }catch(IOException e){
         System.out.println(e);
      }
   }

   public void run(){
      try{
         while(true){
            String recvData = st_in.readUTF();
            StringTokenizer st = new StringTokenizer(recvData, SEPARATOR);
            int command = Integer.parseInt(st.nextToken());
            switch(command){

             
               case REQ_LOGON:{
                  int result;
                  String id = st.nextToken(); 
                  result = addUser(id, this);
                  st_buffer.setLength(0);
                  if(result ==0){  
                	
                	 starttime = new Date();
                	 String time = starttime.toString();
                     st_buffer.append(YES_LOGON); 
                     					
                     st_buffer.append(SEPARATOR);
                     st_buffer.append(time);
                     st_buffer.append(SEPARATOR);
                     st_buffer.append(id);
                     st_buffer.append(SEPARATOR);
                     send(st_buffer.toString());
                     
                     st_buffer.setLength(0);
                     st_buffer.append(REC_LOGONLIST);
                     st_buffer.append(SEPARATOR);
                     String userIDs = getUsers(); 
                     st_buffer.append(userIDs);
                     logonbroadcast(st_buffer.toString());
                     
                  }else{  
                     st_buffer.append(NO_LOGON); 
                     st_buffer.append(SEPARATOR);
                     st_buffer.append(result); 
                     send(st_buffer.toString());
                  }
                  break;
               }

              
               case REQ_ENTERROOM:{
                  st_buffer.setLength(0);
                  String id = st.nextToken(); 
                  if(checkUserID(id) == null){

                 
                     st_buffer.append(NO_ENTERROOM);
                     st_buffer.append(SEPARATOR);
                     st_buffer.append(MSG_CANNOTOPEN);
                     send(st_buffer.toString());  
                     break;
                  }

                  roomVector.addElement(id); 
                  roomHash.put(id, this); 

         
                  st_buffer.append(YES_ENTERROOM); 
                  send(st_buffer.toString()); 

                  st_buffer.setLength(0);
                  st_buffer.append(MDY_USERIDS); 
                  st_buffer.append(SEPARATOR);
                  String userIDs2 = getRoomUsers(); 
                  st_buffer.append(userIDs2);
                  roombroadcast(st_buffer.toString()); 
                  
                  st_buffer.setLength(0);
                  st_buffer.append(roomstate);
                  st_buffer.append(SEPARATOR);
                  String message = ">>"+id+"님 입장했습니다.\r\n"; 
                  st_buffer.append(message);
                  roombroadcast(st_buffer.toString()); 
                  break;
               }
               case REQ_QUITROOM:{
            	   String id = st.nextToken();
            	   id.replaceAll(" ", "");
            	   System.out.println("quit id="+id);
            	   roomHash.remove(id);
            	   roomVector.remove(id);
            	   ServerThread s = logonHash.get(id);
            	   
            	   st_buffer.setLength(0);
                   st_buffer.append(YES_QUITROOM);
                   st_buffer.append(SEPARATOR);
                   if(!roomVector.isEmpty()) {
                	   String userIDs = getRoomUsers(); 
                       st_buffer.append(userIDs);
                       roombroadcast(st_buffer.toString());
                   }else {
                	   send(st_buffer.toString());
                   }
                    
            	   break;
               }
               case REC_GAME:{
                   st_buffer.setLength(0);
                   //String id = st.nextToken(); 
                   /*if(checkUserID(id) == null){

                  
                      st_buffer.append(NO_GAME);
                      st_buffer.append(SEPARATOR);
                      st_buffer.append(MSG_CANNOTOPEN);
                      send(st_buffer.toString());  
                      break;
                   }*/

                   //roomVector.addElement(id); 
                   //roomHash.put(id, this); 

          
                   st_buffer.append(YES_GAME); 
                   send(st_buffer.toString()); 

                   /*st_buffer.setLength(0);
                   st_buffer.append(MDY_USERIDS); 
                   st_buffer.append(SEPARATOR);
                   String userIDs2 = getRoomUsers(); 
                   st_buffer.append(userIDs2);
                   roombroadcast(st_buffer.toString()); 
                   
                   st_buffer.setLength(0);
                   st_buffer.append(roomstate);
                   st_buffer.append(SEPARATOR);
                   String message = ">>"+id+"님 입장했습니다.\r\n"; 
                   st_buffer.append(message);
                   roombroadcast(st_buffer.toString()); 
                   break;*/
                }

              
               case REQ_SENDWORDS:{
                  st_buffer.setLength(0);
                  st_buffer.append(YES_SENDWORDS);
                  st_buffer.append(SEPARATOR);
                  String id = st.nextToken(); 
                  st_buffer.append(id);
                  st_buffer.append(SEPARATOR);
                  try{
                     String data = st.nextToken(); 
                     st_buffer.append(data);
                  }catch(NoSuchElementException e){}
                  roombroadcast(st_buffer.toString()); 
                  break;
               }

             
               case REQ_LOGOUT:{
            	   String id = st.nextToken();
            	   id.replaceAll(" ", "");
            	   System.out.println("logout ID="+id);
            	   ServerThread thread = (ServerThread)logonHash.get(id);
            	   logonVector.remove(id);
            	   logonHash.remove(id);
            	   if(logonVector.contains(id)||logonHash.containsKey(id)) {
            		   System.out.println("logout not normal...");
            	   }else {
            		   if(!logonVector.isEmpty()) {
            			   st_buffer.setLength(0);
                           st_buffer.append(REC_LOGONLIST);
                           st_buffer.append(SEPARATOR);
                           String logongroup = getUsers();
                           st_buffer.append(logongroup);
                           send(st_buffer.toString());
                           logonbroadcast(st_buffer.toString());
            		   }
            		   
            		   st_buffer.setLength(0);
                       st_buffer.append(YES_LOGOUT);
                       st_buffer.append(SEPARATOR);
                       send(st_buffer.toString());
                      
 
            	   }
                  break;
               }
               case REC__WHISPERMESSAGE:{

            	   String userid = st.nextToken();
            	   String whisperid = st.nextToken();
            	   
            	   if(userid.equals(whisperid)) {
            		   String errormessage = "Error.";
            		   st_buffer.setLength(0);
            		   st_buffer.append(NO_WHISPERMESSAGE);
            		   st_buffer.append(SEPARATOR);
            		   st_buffer.append(errormessage);
                       send(st_buffer.toString());
            	   }
            	   else {
            		   String message = st.nextToken();
            		   message = userid + " -> " +whisperid+ ":" +message +"\r\n";
            		   ServerThread client;
            		   client = (ServerThread) roomHash.get(whisperid);
            		   
            		   st_buffer.setLength(0);
            		   st_buffer.append(YES_WHISPERMESSAGE);//
            		   st_buffer.append(SEPARATOR);
            		   st_buffer.append(message);
            		   client.send(st_buffer.toString());

            	   }
            	   break;
               }
               case REQ_EXIT:{
            	   st_buffer.setLength(0);
                   st_buffer.append(YES_EXIT);
                   st_buffer.append(SEPARATOR);
                   send(st_buffer.toString());
                   release();
            	   break;
               }
               case REQ_EXITROOM:{
            	   String id = st.nextToken();
            	   roomHash.remove(id);//룸 
            	   roomVector.remove(id);
            	   logonHash.remove(id);
            	   logonVector.remove(id);
            	   
            	   
            	   st_buffer.setLength(0);
                   st_buffer.append(REC_LOGONLIST);
                   st_buffer.append(SEPARATOR);
                   String userIDs = getUsers();
                   st_buffer.append(userIDs);
                   logonbroadcast(st_buffer.toString());
                   
                   st_buffer.setLength(0);
                   st_buffer.append(MDY_USERIDS);
                   st_buffer.append(SEPARATOR);
                   String userIDs2 = getRoomUsers(); 
                   st_buffer.append(userIDs2);
                   roombroadcast(st_buffer.toString());
            	   break;
               }

              

            }

            Thread.sleep(100);
         } 

      }catch(NullPointerException e){ 
      }catch(InterruptedException e){
      }catch(IOException e){
      }
   }

   public void release(){
	   
   }

    private static synchronized int addUser(String id, ServerThread client){
      if(checkUserID(id) != null){
         return MSG_ALREADYUSER;
      }  
      if(logonHash.size() >= ChatServer.cs_maxclient){
         return MSG_SERVERFULL;
      }
      logonVector.addElement(id); 
      logonHash.put(id, client);
      client.st_ID = id;
      System.out.println("logon complete\n");
      return 0; 
   }

  
   private static ServerThread checkUserID(String id){
      ServerThread alreadyClient = null;
      alreadyClient = (ServerThread) logonHash.get(id);
      return alreadyClient;
   }

   // 로그온에 참여한 사용자 ID를 구한다.
   private String getUsers(){
      StringBuffer id = new StringBuffer();
      String ids;
      Enumeration<String> enu = logonVector.elements();
      while(enu.hasMoreElements()){
         id.append(enu.nextElement());
         id.append(DELIMETER); 
      }
      try{
         ids = new String(id);  
         ids = ids.substring(0, ids.length()-1); 
      }catch(StringIndexOutOfBoundsException e){
         return "";
      }
      return ids;
   }

   private String getRoomUsers(){
      StringBuffer id = new StringBuffer();
      String ids;
      Enumeration<String> enu = roomVector.elements();
      while(enu.hasMoreElements()){
         id.append(enu.nextElement());
         id.append(DELIMETER); 
      }
      try{
         ids = new String(id);
         ids = ids.substring(0, ids.length()-1);
      }catch(StringIndexOutOfBoundsException e){
         return "";
      }
      return ids;
   }

 
   public synchronized void roombroadcast(String sendData) throws IOException{
      ServerThread client;
      Enumeration<String> enu = roomVector.elements();
      while(enu.hasMoreElements()){
         client = (ServerThread) roomHash.get(enu.nextElement());
         client.send(sendData);
      }
   }
   public synchronized void logonbroadcast(String sendData) throws IOException{
	      ServerThread client;
	      Enumeration<String> enu = logonVector.elements();
	      while(enu.hasMoreElements()){
	         client = (ServerThread) logonHash.get(enu.nextElement());
	         client.send(sendData);
	      }
	   }

   // 데이터를 전송한다.
   public void send(String sendData) throws IOException{
      synchronized(st_out){
         st_out.writeUTF(sendData);
         st_out.flush();
      }
   }
}   
