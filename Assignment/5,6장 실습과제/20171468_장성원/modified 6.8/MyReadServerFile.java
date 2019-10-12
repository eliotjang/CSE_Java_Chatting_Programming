/* coded by eliotjang
 * last_modified_at: 2019.10.12
 */
package chapter6;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


public class MyReadServerFile extends Frame implements ActionListener{
	private TextField enter;
	private TextArea contents, info;
	public MyReadServerFile() {
		super("read host file");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL을 입력하세요.(키보드 입력 시 사라집니다)" );
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
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE);
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
			contents.append("프로토콜: " + u.getProtocol() +"\n");
			contents.append("호스트 이름: " + u.getHost() +"\n");
			contents.append("포트 번호: " + u.getPort() +"\n"); // u.getLocalPort() 는 안된다.
			contents.append("파일 이름: " + u.getFile() +"\n");
			contents.append("경로: " + u.getPath() +"\n");
			contents.append("앵커정보: " + u.getRef() +"\n");
			contents.append("해시코드: " + u.hashCode() +"\n");
	
			Object o = u.getContent(); // 어떤 객체인지 확인
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			
			// URL이 가리키는 객체에 따라 내용이나 유형 출력
			info.setText( "파일을 읽는 중...\n\n\n");
			
			/* 어떤 유형의 내용이 나오는지 미리 확인 후, 코드 작성.
			 * info.append(conn.getContentType());
			 */
			if (o.getClass().getName().contains("URLImageSource")) {
			//매개인자에 image를 넣어도 가능. Because "image" includes "URLImageSource"
				info.append("§콘텐츠 유형§\n");
				info.append("image.URLImageSource\n\n");
				info.append("§반환 객체§\n");
				info.append(o.getClass().getName());
			}
			else if(conn.getContentType().contains("audio")) {
				info.append("§콘텐츠 유형§\n");
				info.append(conn.getContentType() + "\n\n");
				info.append("§반환 객체§\n");
				info.append(conn.toString());
			}
			else if(conn.getContentType().contains("video")) {
				info.append("§콘텐츠 유형§\n");
				info.append(conn.getContentType() + "\n\n");
				info.append("§반환 객체§\n");
				info.append(conn.toString());
			}
			else if(o.getClass().getName().contains("InputStream")) {
				info.append("§텍스트 파일§\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null)
					info.append(line);
				info.append("\n\n§반환 객체§\n" + o.getClass().getName());
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
