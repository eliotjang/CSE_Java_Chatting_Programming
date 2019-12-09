package chattingprogramming;

import java.awt.*;
import java.awt.event.*;

public class DisplayRoom extends Frame implements ActionListener, KeyListener, MouseListener
{
   
   private Button dr_btClear; // ��ȭ�� â ȭ�� �����
   private Button dr_btLogout; // �α׾ƿ� ���� ��ư
   private Button dr_security;
   public TextArea dr_taContents; // ��ȭ�� ���� ����Ʈâ
   public List dr_lstMember; // ��ȭ�� ������

   public TextField dr_tfInput; // ��ȭ�� �Է��ʵ�

   public Label dr_ID;
   public ChatClient dr_client;
   public static ClientThread dr_thread;

   String id,whisper;
   public DisplayRoom(ChatClient c, ClientThread client, String title){
      super(title);
      setLayout(new BorderLayout());

     
      Panel northpanel = new Panel();
      northpanel.setLayout(new FlowLayout());
      dr_ID = new Label();
     
      northpanel.add(dr_ID);//����� ID ����
      dr_ID.setText("ID:" + client.id);
      id = client.id;
      dr_btClear = new Button("ȭ�������"); 
      dr_btClear.addActionListener(this);
      northpanel.add(dr_btClear);
   
      dr_btLogout = new Button("����ϱ�");
      dr_btLogout.addActionListener(this);
      northpanel.add(dr_btLogout);
      dr_security = new Button("����������");//�ӼӸ� ��ư
      dr_security.addActionListener(this);
      northpanel.add(dr_security);
      
      Panel centerpanel = new Panel();
      centerpanel.setLayout(new FlowLayout());
      dr_taContents = new TextArea(10, 27);
      dr_taContents.setEditable(false);
      centerpanel.add(dr_taContents);
           
      
      dr_lstMember = new List(10);
      dr_lstMember.addMouseListener(this);
      centerpanel.add(dr_lstMember);

      Panel southpanel = new Panel();
      southpanel.setLayout(new FlowLayout());
      dr_tfInput = new TextField(41);
      dr_tfInput.addKeyListener(this);
      
      southpanel.add(dr_tfInput);
      
      
      add("North", northpanel);
      add("Center", centerpanel);
      add("South", southpanel);

      dr_client = c;
      dr_thread = client; // ClientThread Ŭ������ �����Ѵ�.

      

      addWindowListener(new WinListener());
      
   }

   class WinListener extends WindowAdapter
   {
      public void windowClosing(WindowEvent we){
    	 dr_thread.roomexit(id);
         System.exit(0); 
      }
   }

   public void actionPerformed(ActionEvent ae){
      Button b = (Button)ae.getSource();
      if(b.getLabel().equals("ȭ�������")) {
    	  dr_taContents.setText("");

      }else if(b.getLabel().equals("����ϱ�")){
    	  dr_thread.requestQuitRoom(id);
    	  dispose();//��ȭâ����
    	  dr_client.setVisible(true);
      // �α׾ƿ� ó�� ��ƾ
      }
      else if(b.getLabel().equals("����������")) {
    	  if(whisper==null) {   		  
    		  MessageBox msgBox = new  MessageBox(this, "�ӼӸ� ����", "����ڸ� �����ϼ���"); 
    		  msgBox.show();
    	  }
          else{
        	  String message = dr_tfInput.getText();
    		  dr_thread.requestwhisper(id,whisper,message);
    		  dr_tfInput.setText("");
    	  }
      }
   }

   public void keyPressed(KeyEvent ke){
      if(ke.getKeyChar() == KeyEvent.VK_ENTER){
         String words = dr_tfInput.getText(); 
         dr_thread.requestSendWords(words); 
      }
   }

   public void keyReleased(KeyEvent ke){}
   public void keyTyped(KeyEvent ke){}

@Override
public void mouseClicked(MouseEvent e) {
	whisper = dr_lstMember.getSelectedItem(); 
}

@Override
public void mousePressed(MouseEvent e) {
	
}

@Override
public void mouseReleased(MouseEvent e) {
	
}

@Override
public void mouseEntered(MouseEvent e) {
	
}

@Override
public void mouseExited(MouseEvent e) {	
}

}
