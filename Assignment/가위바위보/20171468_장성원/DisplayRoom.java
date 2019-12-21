package chattingprogramming;

import java.awt.*;
import java.awt.event.*;

public class DisplayRoom extends Frame implements ActionListener, KeyListener, MouseListener
{
   
   private Button dr_btClear; // 대화말 창 화면 지우기
   private Button dr_btLogout; // 로그아웃 실행 버튼
   private Button dr_security;
   public TextArea dr_taContents; // 대화말 내용 리스트창
   public List dr_lstMember; // 대화방 참가자

   public TextField dr_tfInput; // 대화말 입력필드

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
     
      northpanel.add(dr_ID);//사용자 ID 띄우기
      dr_ID.setText("ID:" + client.id);
      id = client.id;
      dr_btClear = new Button("화면지우기"); 
      dr_btClear.addActionListener(this);
      northpanel.add(dr_btClear);
   
      dr_btLogout = new Button("퇴실하기");
      dr_btLogout.addActionListener(this);
      northpanel.add(dr_btLogout);
      dr_security = new Button("쪽지보내기");//귓속말 버튼
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
      dr_thread = client; // ClientThread 클래스와 연결한다.

      

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
      if(b.getLabel().equals("화면지우기")) {
    	  dr_taContents.setText("");

      }else if(b.getLabel().equals("퇴실하기")){
    	  dr_thread.requestQuitRoom(id);
    	  dispose();//대화창끄기
    	  dr_client.setVisible(true);
      // 로그아웃 처리 루틴
      }
      else if(b.getLabel().equals("쪽지보내기")) {
    	  if(whisper==null) {   		  
    		  MessageBox msgBox = new  MessageBox(this, "귓속말 에러", "사용자를 선택하세요"); 
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
