package chapter5;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHostInfor extends Frame implements ActionListener {
	TextField hostname;
	Button getinfor;
	TextArea display;

	public static void main(String[] args) {
		GetHostInfor host = new GetHostInfor("InetAddress 클래스");
		host.setVisible(true);
	}
	
	public GetHostInfor(String str) {
		super(str);
		addWindowListener(new WinListener());
		setLayout(new BorderLayout());
		Panel inputpanel = new Panel();
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add("North", new Label("호스트 이름:"));
		hostname = new TextField("", 30);
		getinfor = new Button("호스트 정보 얻기");
		inputpanel.add("Center", hostname);
		inputpanel.add("South", getinfor);
		getinfor.addActionListener(this);
		add("North", inputpanel);
		
		Panel outputpanel = new Panel();
		outputpanel.setLayout(new BorderLayout());
		display = new TextArea("", 24, 40);
		display.setEditable(false);
		outputpanel.add("North", new Label("인터넷 주소"));
		outputpanel.add("Center", display);
		add("Center", outputpanel);
		setLocationRelativeTo(null); // GUI를 모니터 중앙에 배치
		setSize(500, 500);
	}
	
	public void actionPerformed(ActionEvent e) {
		String name = hostname.getText();
		try {
			InetAddress inet = InetAddress.getByName(name);
			String ip = inet.getHostName()+"\n";
			display.append(ip);
			ip = inet.getHostAddress()+"\n";
			display.append(ip);
		} catch(UnknownHostException ue) {
			String ip = name+": 해당 호스트가 없습니다.\n";
			display.append(ip);
		}
	}
	
	class WinListener extends WindowAdapter {
		
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
