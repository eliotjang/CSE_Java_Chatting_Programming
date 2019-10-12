// coded by eliotjang
// 2019.10.10
package chapter6;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.awt.Image;


public class MyReadServerFile extends Frame implements ActionListener{
	private TextField enter;
	private TextArea contents, info;
	public MyReadServerFile() {
		super("호스트 파일 읽기");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL을 입력하세요!" );
		enter.addActionListener(this);
		// 텍스트필드에서 URL을 키보드로 받아들일 때, 기존에 있던 텍스트필드 내용 제거 
		enter.addKeyListener(new KeyListener() {
		    public void keyTyped(KeyEvent e) {enter.setText("");}
		    public void keyReleased(KeyEvent e) {enter.setText("");}
		    public void keyPressed(KeyEvent e) {enter.setText("");}
		});
		add( enter, BorderLayout.NORTH );
		info= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( info, BorderLayout.CENTER);
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( contents, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(800, 650);
		setLocationRelativeTo(null); // GUI를 모니터 중앙에 배치
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e ) {
		String urlstring, line;
		URL u;
		InputStream is;
		BufferedReader reader;
		
		try {
			urlstring = e.getActionCommand();
			u = new URL(urlstring);
			// 원격호스트 정보 출력
			contents.setText("§원격호스트 정보§\n\n");
			contents.append("프로토콜은 " + u.getProtocol() +"\n");
			contents.append("호스트 이름은 " + u.getHost() +"\n");
			contents.append("포트 번호는 " + u.getPort() +"\n"); // u.getLocalPort() 는 안된다.
			contents.append("파일 이름은 " + u.getFile() +"\n");
			contents.append("경로는 " + u.getPath() +"\n");
			contents.append("앵커정보는 " + u.getRef() +"\n");
			contents.append("해시코드는 " + u.hashCode() +"\n");
	
			Object o = u.getContent(); // 어떤 객체인지
			
			// URL이 가리키는 객체에 따라 내용이나 유형 출력
			info.setText( "파일을 읽는 중...\n\n");
			if (o.getClass().getName().contains("Image")) {
				info.append("§이미지 파일§\n");
				System.out.println("반환된 객체는 " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("Video")) {
				info.append("§비디오 파일§\n");
				System.out.println("반환된 객체는 " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("Audio")) {
				info.append("§오디오 파일§\n");
				System.out.println("반환된 객체는 " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("InputStream")) {
				info.append("§텍스트 파일§\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null)
					info.append(line);
				System.out.println("반환된 객체는 " + o.getClass().getName());
			}
			else { System.err.println(o.toString() + " 를 읽지 못하였음"); }
		}catch(IOException ioe) {
			System.out.println(ioe.toString());
		}
	}
	public static void main(String[] args) {
		MyReadServerFile read = new MyReadServerFile();
	}
	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
