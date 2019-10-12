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
		add( enter, BorderLayout.NORTH );
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		//info= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( contents, BorderLayout.CENTER);
		info= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( info, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(800, 600);
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e ) {
		//String location = e.getActionCommand();
		// 원격호스트 정보 출력
		URL u;
		//String urlstring;
		InputStream is;
		BufferedReader br, reader;
		String line;
		//StringBuffer buffer = new StringBuffer();
		//br = new BufferedReader(new InputStreamReader(System.in));
		String location = e.getActionCommand();
		try {
			//location = br.readLine();
			u = new URL(location);
			//is = u.openStream();
			//br = new BufferedReader(new InputStreamReader(is));
			
			contents.setText( "파일을 읽는 중입니다....");
			//while (())
			contents.append("\n" + "프로토콜은 " + u.getProtocol() +"\n");
			contents.append("호스트 이름은 " + u.getHost() +"\n");
			contents.append("포트 번호는 " + u.getPort() +"\n"); // u.getLocalPort() 는 안된다.
			contents.append("파일 이름은 " + u.getFile() +"\n");
			contents.append("경로는 " + u.getPath() +"\n");
			contents.append("앵커정보는 " + u.getRef() +"\n");
			contents.append("해시코드는 " + u.hashCode() +"\n");
			//urlstring = br.readLine();
			//u = new URL(urlstring);
			Object o = u.getContent(); // 어떤 객체인지
			if (o.getClass().getName().contains(location)) {
				InputStream is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is2));
				while((line=reader.readLine()) != null) {
					System.out.println(line);
				}
			}else if (o.getClass().getName().contains(urlstring)) {
				java.awt.Image jai = (java.awt.Image) o;
				reader = new BufferedReader(new InputStreamReader(jai));
				while((line=reader.readLine()) != null) {
					System.out.println(line);
				}
			}else {
				
			}
			/*System.out.println("프로토콜은 " + u.getProtocol());
			System.out.println("호스트 이름은 " + u.getHost() );
			System.out.println("포트 번호는 " + u.getPort());
			System.out.println("파일 이름은 " + u.getFile());
			System.out.println("경로는 " + u.getPath());
			System.out.println("앵커정보는 " + u.getRef());
			System.out.println("해시코드는 " + u.hashCode());*/
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
