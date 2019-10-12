// coded by eliotjang
// 2019.10.10
package chapter6;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class MyReadServerFile extends Frame implements ActionListener{
	private TextField enter;
	private TextArea contents, info;
	public MyReadServerFile() {
		super("호스트 파일 읽기");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL을 입력하세요!" );
		enter.addActionListener(this);
		add( enter, BorderLayout.NORTH );
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( contents, BorderLayout.CENTER);
		info= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( info, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(800, 600);
		setLocationRelativeTo(null); // 화면 중앙에 배치
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e ) {
		String urlstring;
		// 원격호스트 정보 출력
		URL u;
		InputStream is;
		BufferedReader br, reader;
		BufferedImage bi;
		String line;
		
		
		//StringBuffer buffer = new StringBuffer();
		//br = new BufferedReader(new InputStreamReader(System.in));
		try {
			urlstring = e.getActionCommand();
			u = new URL(urlstring);
			//location = br.readLine();
			
			//is = u.openStream();
			//br = new BufferedReader(new InputStreamReader(is));
			
			
			contents.append("프로토콜은 " + u.getProtocol() +"\n");
			contents.append("호스트 이름은 " + u.getHost() +"\n");
			contents.append("포트 번호는 " + u.getPort() +"\n"); // u.getLocalPort() 는 안된다.
			contents.append("파일 이름은 " + u.getFile() +"\n");
			contents.append("경로는 " + u.getPath() +"\n");
			contents.append("앵커정보는 " + u.getRef() +"\n");
			contents.append("해시코드는 " + u.hashCode() +"\n");
			//urlstring = br.readLine();
			//u = new URL(urlstring);
			Object o = u.getContent(); // 어떤 객체인지
			if (o.getClass().getName().contains("InputStream")) {
				info.setText( "파일을 읽는 중입니다....\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null) {
					info.append(line);
				}
			}else if (o.getClass().getName().contains("java.awt.Image")) {
				info.setText("이미지 파일입니다");
				//bi = ImageIO.read(u);
				//InputStream jai = (InputStream) o;
				//reader = new BufferedReader(new InputStreamReader(jai));
				//while((line=reader.readLine()) != null) {
				//	System.out.println(line);
				}
			}catch (IOException ioe) {
			System.out.println(ioe.toString());
		}
		// URL이 가리키는 객체에 따라 내용이나 유형 출력
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
