package rockScissorsPaper;

import java.awt.*;

import java.net.*;

import java.io.*;

import java.awt.event.*;

public class GameClient extends Frame implements ActionListener{

  private TextArea msgView=new TextArea();

  private Button kawi, bawi, bo;        // 가위, 바위, 보에 대한 버튼

  private DataInputStream reader;

  private DataOutputStream writer;

  public static int KAWI=0;              // 가위를 나타내는 상수

  public static int BAWI=1;              // 바위를 나타내는 상수

  public static int BO=2;                // 보를 나타내는 상수

  Socket socket;

  public GameClient(String title){            // 생성자

    super(title);

    msgView.setEditable(false);

 

    // 필요한 버튼 객체를 생성하고 배치한다.

    kawi=new Button("가위");

    bawi=new Button("바위");

    bo=new Button("보");

    add(msgView,"Center");

    Panel p=new Panel();

    p.add(kawi); p.add(bawi); p.add(bo);

    add(p, "South");

 

    // 버튼들의 이벤트를 처리한다.

    kawi.addActionListener(this);

    bawi.addActionListener(this);

    bo.addActionListener(this);

 

    pack();

  }

  private void connect(){

    try{

      msgView.append("서버와의 연결을 시도합니다.\n");

      socket=new Socket("127.0.0.1", 7777);

      msgView.append("게임을 시작합니다.\n");

     

      // 소켓의 입·출력 스트림을 얻는다.

      reader=new DataInputStream(socket.getInputStream());

      writer=new DataOutputStream(socket.getOutputStream());

    }catch(Exception e){

      msgView.append("연결 실패..");

    }

  }

  public void actionPerformed(ActionEvent ae){  // 액션 이벤트 처리

    // 사용자가 선택한 것과 서버가 선택한 것.

    int player=-1, server=-1;         // -1은 선택되지 않은 상태를 나타낸다.

 

    // 사용자가 누른 버튼에 해당하는 값을 기억한다.

    if(ae.getSource()==kawi)

      player=KAWI;

    else if(ae.getSource()==bawi)

      player=BAWI;

    else if(ae.getSource()==bo)

      player=BO;

 

    // 다른 컨트롤에서 발생한 이벤트이면 메소드를 빠져 나온다.

    if(player==-1)return;

   

    try{

      // "OK"를 서버로 전송한다.

      writer.writeUTF("OK");

      writer.flush();

 

      // 서버의 응답을 얻는다. 0~2 사이의 정수

      server=reader.readInt();

    }catch(IOException ie){}

   

    // 승부 결과를 계산하여 msgView에 나타낸다.

    if(player==server)msgView.append("비겼습니다.\n");

    else if(player>server || server-player==2)msgView.append("이겼습니다.\n");

    else msgView.append("졌습니다.\n");

   

  }

 

  public static void main(String[] args){

    GameClient client=new GameClient("가위바위보 게임");

    client.setVisible(true);

    client.connect();

  }

}