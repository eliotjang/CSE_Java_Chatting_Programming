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
		// �ؽ�Ʈ�ʵ忡�� URL�� Ű����� �޾Ƶ��� ��, ������ �ִ� �ؽ�Ʈ�ʵ� ���� ���� 
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
		setLocationRelativeTo(null); // GUI�� ����� �߾ӿ� ��ġ
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
			// ����ȣ��Ʈ ���� ���
			contents.setText("�׿���ȣ��Ʈ ������\n\n");
			contents.append("���������� " + u.getProtocol() +"\n");
			contents.append("ȣ��Ʈ �̸��� " + u.getHost() +"\n");
			contents.append("��Ʈ ��ȣ�� " + u.getPort() +"\n"); // u.getLocalPort() �� �ȵȴ�.
			contents.append("���� �̸��� " + u.getFile() +"\n");
			contents.append("��δ� " + u.getPath() +"\n");
			contents.append("��Ŀ������ " + u.getRef() +"\n");
			contents.append("�ؽ��ڵ�� " + u.hashCode() +"\n");
	
			Object o = u.getContent(); // � ��ü����
			
			// URL�� ����Ű�� ��ü�� ���� �����̳� ���� ���
			info.setText( "������ �д� ��...\n\n");
			if (o.getClass().getName().contains("Image")) {
				info.append("���̹��� ���ϡ�\n");
				System.out.println("��ȯ�� ��ü�� " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("Video")) {
				info.append("�׺��� ���ϡ�\n");
				System.out.println("��ȯ�� ��ü�� " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("Audio")) {
				info.append("�׿���� ���ϡ�\n");
				System.out.println("��ȯ�� ��ü�� " + o.getClass().getName());
			}
			else if(o.getClass().getName().contains("InputStream")) {
				info.append("���ؽ�Ʈ ���ϡ�\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null)
					info.append(line);
				System.out.println("��ȯ�� ��ü�� " + o.getClass().getName());
			}
			else { System.err.println(o.toString() + " �� ���� ���Ͽ���"); }
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
