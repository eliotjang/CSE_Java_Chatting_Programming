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
		enter = new TextField( "URL�� �Է��ϼ���.(Ű���� �Է� �� ������ϴ�)" );
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
		contents= new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE);
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
			contents.append("��������: " + u.getProtocol() +"\n");
			contents.append("ȣ��Ʈ �̸�: " + u.getHost() +"\n");
			contents.append("��Ʈ ��ȣ: " + u.getPort() +"\n"); // u.getLocalPort() �� �ȵȴ�.
			contents.append("���� �̸�: " + u.getFile() +"\n");
			contents.append("���: " + u.getPath() +"\n");
			contents.append("��Ŀ����: " + u.getRef() +"\n");
			contents.append("�ؽ��ڵ�: " + u.hashCode() +"\n");
	
			Object o = u.getContent(); // � ��ü���� Ȯ��
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			
			// URL�� ����Ű�� ��ü�� ���� �����̳� ���� ���
			info.setText( "������ �д� ��...\n\n\n");
			
			/* � ������ ������ �������� �̸� Ȯ�� ��, �ڵ� �ۼ�.
			 * info.append(conn.getContentType());
			 */
			if (o.getClass().getName().contains("URLImageSource")) {
			//�Ű����ڿ� image�� �־ ����. Because "image" includes "URLImageSource"
				info.append("�������� ������\n");
				info.append("image.URLImageSource\n\n");
				info.append("�׹�ȯ ��ü��\n");
				info.append(o.getClass().getName());
			}
			else if(conn.getContentType().contains("audio")) {
				info.append("�������� ������\n");
				info.append(conn.getContentType() + "\n\n");
				info.append("�׹�ȯ ��ü��\n");
				info.append(conn.toString());
			}
			else if(conn.getContentType().contains("video")) {
				info.append("�������� ������\n");
				info.append(conn.getContentType() + "\n\n");
				info.append("�׹�ȯ ��ü��\n");
				info.append(conn.toString());
			}
			else if(o.getClass().getName().contains("InputStream")) {
				info.append("���ؽ�Ʈ ���ϡ�\n");
				is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null)
					info.append(line);
				info.append("\n\n�׹�ȯ ��ü��\n" + o.getClass().getName());
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
