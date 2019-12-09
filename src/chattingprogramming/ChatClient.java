package chattingprogramming;

import java.awt.*;
import java.awt.event.*;

public class ChatClient extends Frame implements ActionListener
{
   
   public TextField cc_tfLogon; 
   public Button cc_btLogon; 
   private Button cc_btEnter; 
   private Button cc_btLogout; 

   public TextField cc_tfStatus; 
   public TextField cc_tfDate; 
   public List cc_lstMember; 

   public static ClientThread cc_thread;
   public static ChatClient client;
   public String msg_logon="";

   public ChatClient(String str){
      super(str);
      setLayout(new BorderLayout());

      
      Panel bt_panel = new Panel();
      bt_panel.setLayout(new FlowLayout());
      cc_btLogon = new Button("�α׿½���");
      cc_btLogon.addActionListener(this);
      bt_panel.add(cc_btLogon);
      
      cc_tfLogon = new TextField(10);
      cc_tfLogon.addActionListener(this);
      
      bt_panel.add(cc_tfLogon);
      
      cc_btEnter = new Button("��ȭ������");
      cc_btEnter.addActionListener(this);
      bt_panel.add(cc_btEnter);
      
      cc_btLogout = new Button("�α׾ƿ�");
      cc_btLogout.addActionListener(this);
      bt_panel.add(cc_btLogout);
      add("Center", bt_panel);


      Panel roompanel = new Panel(); 
      roompanel.setLayout(new BorderLayout());

      Panel northpanel = new Panel();
      northpanel.setLayout(new FlowLayout());
      cc_tfStatus = new TextField("ID�� ���� �Է��ϼž��մϴ�.,",43); 
      												
      cc_tfStatus.setEditable(false);
      northpanel.add(cc_tfStatus);
      
      Panel centerpanel = new Panel();
      centerpanel.setLayout(new FlowLayout());
      centerpanel.add(new Label("�α׿� �ð��� "));
      cc_tfDate = new TextField(" ... ",30);
      cc_tfDate.setEditable(false);
      centerpanel.add(cc_tfDate);

      Panel southpanel = new Panel();
      southpanel.setLayout(new FlowLayout());
      southpanel.add(new Label("�α׿� ����� ���"));
      cc_lstMember = new List(10);
      southpanel.add(cc_lstMember);

      roompanel.add("North", northpanel);
      roompanel.add("Center", centerpanel);
      roompanel.add("South", southpanel);
      add("North", roompanel);


      addWindowListener(new WinListener());
   }

   class WinListener extends WindowAdapter
   {
      public void windowClosing(WindowEvent we){
    	  cc_thread.release();
      }
   }

  
   public void actionPerformed(ActionEvent ae){
      
      if(ae.getSource().equals(cc_btLogon)||ae.getSource().equals(cc_tfLogon)){
         msg_logon = cc_tfLogon.getText(); 
         System.out.println("msg_logon:"+msg_logon+"\n");
         if(!msg_logon.contains(" ")){
            cc_thread.requestLogon(msg_logon); 
         }
         else if(msg_logon.contains(" ")) {
        	 cc_tfLogon.setText("");
        	 MessageBox msgBox = new  MessageBox(this, "�α׿� ����", "line error");
             msgBox.show();
         }
         else{
            MessageBox msgBox = new  MessageBox(this, "�α׿� ����", "id input error");
            msgBox.show();
         }
      }else if(ae.getSource().equals(cc_btEnter)){

         // ��ȭ�� ���� �� ���� ó�� ��ƾ
         msg_logon = cc_tfLogon.getText(); 
         if(!msg_logon.equals("")){
            cc_thread.requestEnterRoom(msg_logon); 
         }else{
            MessageBox msgBox = new MessageBox(this, "�α׿�", "logon first..");
            msgBox.show();
         }

      }else if(ae.getSource().equals(cc_btLogout)){//�α׾ƿ� ��ư�� ������ �� �ش�
    	  if(!msg_logon.equals("")){
    		  cc_thread.requestLogout(msg_logon); 
    		  msg_logon="";
           }else{
              MessageBox msgBox = new MessageBox(this, "�α׾ƿ�", "you are not in logon");
              msgBox.show();
           }
    	 
      }
   }

   public static void main(String args[]){
      client = new ChatClient("�ڹ�ä�� programming");
      client.setSize(450, 400);
      client.show();

      try{
         cc_thread = new ClientThread(client); 
         cc_thread.start(); 
      }catch(Exception e){
         System.out.println(e);
      }
   }
}
