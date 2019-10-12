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
		super("ȣ��Ʈ ���� �б�");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL�� �Է��ϼ���!" );
		enter.addActionListener(this);
		add( enter, BorderLayout.NORTH );
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( contents, BorderLayout.CENTER);
		info= new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add( info, BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(800, 600);
		setLocationRelativeTo(null); // ȭ�� �߾ӿ� ��ġ
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e ) {
		String urlstring;
		// ����ȣ��Ʈ ���� ���
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
			
			
			contents.append("���������� " + u.getProtocol() +"\n");
			contents.append("ȣ��Ʈ �̸��� " + u.getHost() +"\n");
			contents.append("��Ʈ ��ȣ�� " + u.getPort() +"\n"); // u.getLocalPort() �� �ȵȴ�.
			contents.append("���� �̸��� " + u.getFile() +"\n");
			contents.append("��δ� " + u.getPath() +"\n");
			contents.append("��Ŀ������ " + u.getRef() +"\n");
			contents.append("�ؽ��ڵ�� " + u.hashCode() +"\n");
			//urlstring = br.readLine();
			//u = new URL(urlstring);
			Object o = u.getContent(); // � ��ü����
			if (o.getClass().getName().contains("InputStream")) {
				info.setText( "������ �д� ���Դϴ�....\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null) {
					info.append(line);
				}
			}else if (o.getClass().getName().contains("java.awt.Image")) {
				info.setText("�̹��� �����Դϴ�");
				//bi = ImageIO.read(u);
				//InputStream jai = (InputStream) o;
				//reader = new BufferedReader(new InputStreamReader(jai));
				//while((line=reader.readLine()) != null) {
				//	System.out.println(line);
				}
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
