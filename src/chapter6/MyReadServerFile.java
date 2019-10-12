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
		super("ȣ��Ʈ ���� �б�");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL�� �Է��ϼ���!" );
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
		// ����ȣ��Ʈ ���� ���
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
			
			contents.setText( "������ �д� ���Դϴ�....");
			//while (())
			contents.append("\n" + "���������� " + u.getProtocol() +"\n");
			contents.append("ȣ��Ʈ �̸��� " + u.getHost() +"\n");
			contents.append("��Ʈ ��ȣ�� " + u.getPort() +"\n"); // u.getLocalPort() �� �ȵȴ�.
			contents.append("���� �̸��� " + u.getFile() +"\n");
			contents.append("��δ� " + u.getPath() +"\n");
			contents.append("��Ŀ������ " + u.getRef() +"\n");
			contents.append("�ؽ��ڵ�� " + u.hashCode() +"\n");
			//urlstring = br.readLine();
			//u = new URL(urlstring);
			Object o = u.getContent(); // � ��ü����
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
			/*System.out.println("���������� " + u.getProtocol());
			System.out.println("ȣ��Ʈ �̸��� " + u.getHost() );
			System.out.println("��Ʈ ��ȣ�� " + u.getPort());
			System.out.println("���� �̸��� " + u.getFile());
			System.out.println("��δ� " + u.getPath());
			System.out.println("��Ŀ������ " + u.getRef());
			System.out.println("�ؽ��ڵ�� " + u.hashCode());*/
		}catch (IOException ioe) {
			System.out.println(ioe.toString());
		}
		// URL�� ����Ű�� ��ü�� ���� �����̳� ���� ���
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
