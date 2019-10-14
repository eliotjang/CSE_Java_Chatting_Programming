/*
 * author: eliotjang
 * last_modified_at: 2019-10-14T22:08:00+09:00
 */

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
	TextArea display, classview;

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
		
		Panel classpanel = new Panel();
		classpanel.setLayout(new BorderLayout());
		classview = new TextArea("", 24, 40);
		classview.setEditable(false);
		classpanel.add("North", new Label("클래스 유형"));
		classpanel.add("Center", classview);
		add("South", classpanel);
		
		setLocationRelativeTo(null); // GUI를 모니터 중앙에 배치
		setSize(400, 600);
	}
	
	public void actionPerformed(ActionEvent e) {
		String name = hostname.getText();
		try {
			InetAddress[] inet = InetAddress.getAllByName(name);
			InetAddress maininet = InetAddress.getByName(name);
			for ( int i=0 ; i < inet.length ; i++) {
				display.append(inet[i].toString()+"\n");
			}
			classview.setText("");
			char result = ipClass(maininet.getAddress());
			classview.append(Character.toString(result));
		} catch(UnknownHostException ue) {
			String ip = name+": 해당 호스트가 없습니다.\n";
			display.append(ip);
		}
	}
	
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte < 128 ? 'A' : highByte < 192 ? 'B' : highByte < 224 ? 'C' : highByte < 240 ? 'D' : 'E');
		/*
		 * NetWorkAddress of highByte
		 * A: 0~126, B: 128~191, C: 192~223, D: 224~239, E: 240~255
		 */
	}
	
	class WinListener extends WindowAdapter {
		
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}

}
