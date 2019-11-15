package chapter9;

//Step 4
//Ŭ���̾�Ʈ ���� ä�ÿ��� Ư�� Ŭ���̾�Ʈ���� �ӼӸ� ����
//������ �������� �ӼӸ� ����
///w ������̵� ��ȭ��
//package Client;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWhisperC extends Frame implements ActionListener, KeyListener {

	TextArea display;
	TextField wtext, ltext;
	Label mlbl, wlbl, loglbl;
	Button loginb, temp;
	BufferedWriter output;
	BufferedReader input, br;
	Socket client;
	StringBuffer clientdata;
	String serverdata;
	String ID;
	boolean check = true, flag = true;

	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_LOGOUT = 1002;
	private static final int REQ_QUIT = 1003;

	public ChatWhisperC() {
		super("Ŭ���̾�Ʈ");

		mlbl = new Label("ä�� ���¸� �����ݴϴ�.");
		add(mlbl, BorderLayout.NORTH);

		display = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display, BorderLayout.CENTER);

		Panel ptotal = new Panel(new BorderLayout());

		Panel pword = new Panel(new BorderLayout());
		wlbl = new Label("��ȭ��");
		wtext = new TextField(30); // ������ �����͸� �Է��ϴ� �ʵ�
		wtext.addKeyListener(this); // �Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
		pword.add(wlbl, BorderLayout.WEST);
		pword.add(wtext, BorderLayout.EAST);
		ptotal.add(pword, BorderLayout.CENTER);
		Panel plabel = new Panel(new BorderLayout());
		loglbl = new Label("�α׿�");
		loginb = new Button("Ȯ��");
		ltext = new TextField(30); // ������ �����͸� �Է��ϴ� �ʵ�
		loginb.addActionListener(this); // �Էµ� �����͸� �۽��ϱ� ���� �̺�Ʈ ����
		plabel.add(loglbl, BorderLayout.WEST);
		plabel.add(ltext, BorderLayout.CENTER);
		plabel.add(loginb, BorderLayout.EAST);
		ptotal.add(plabel, BorderLayout.SOUTH);

		add(ptotal, BorderLayout.SOUTH);

		addWindowListener(new WinListener());
		setSize(350, 250);
		setVisible(true);
	}

	public void runClient() {
		try {
			client = new Socket(InetAddress.getLocalHost(), 5000);
			mlbl.setText("����� �����̸� : " + client.getInetAddress().getHostName());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			clientdata = new StringBuffer(2048);
			mlbl.setText("���� �Ϸ� ����� ���̵� �Է��ϼ���.");
			while (true) {
				serverdata = input.readLine();
				display.append(serverdata + "\r\n");
				output.flush();
				check = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().compareTo("Ȯ��") == 0) {
			if (!(ltext.getText().equals(""))) {
				ID = ltext.getText();
				try {
					clientdata.setLength(0);
					clientdata.append(REQ_LOGON);
					clientdata.append(SEPARATOR);
					clientdata.append(ID);
					output.write(clientdata.toString() + "\r\n");
					output.flush();
					while (check)
						;
					if (serverdata.compareTo("(����)�ߺ��� ID") == 0) {
						mlbl.setText("�ߺ��� ID �Դϴ�. �ٽ� �Է����ּ���.");
						ltext.setText("");
						check = true;
					} else {
						mlbl.setText(ID + "(��)�� �α��� �Ͽ����ϴ�.");
						ltext.setVisible(false);
						flag = false;
						loginb.setLabel("�α׾ƿ�");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (ae.getActionCommand().compareTo("�α׾ƿ�") == 0) {
			ID = ltext.getText();
			try {
				clientdata.setLength(0);
				clientdata.append(REQ_LOGOUT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
				wtext.setText("");
				ltext.setText("");
				ltext.setVisible(true);
				loginb.setLabel("Ȯ��");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		ChatWhisperC c = new ChatWhisperC();
		c.runClient();
	}

	class WinListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			try {
				clientdata.setLength(0);
				clientdata.append(REQ_QUIT);
				clientdata.append(SEPARATOR);
				clientdata.append(ID);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
			String message = wtext.getText();
			StringTokenizer st = new StringTokenizer(message, " ");
			if (flag) {
				mlbl.setText("�ٽ� �α��� �ϼ���!!!");
				wtext.setText("");
			} else {
				try {
					if (st.nextToken().equals("/w")) {
						message = message.substring(3); // ��/w���� �����Ѵ�.
						String WID = st.nextToken();
						String Wmessage = st.nextToken();
						while (st.hasMoreTokens()) { // ���鹮�� ������ ���� ��ȭ���߰�
							Wmessage = Wmessage + " " + st.nextToken();
						}
						clientdata.setLength(0);
						clientdata.append(REQ_WISPERSEND);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(WID);
						clientdata.append(SEPARATOR);
						clientdata.append(Wmessage);
						output.write(clientdata.toString() + "\r\n");
						output.flush();
						wtext.setText("");
					} else {
						clientdata.setLength(0);
						clientdata.append(REQ_SENDWORDS);
						clientdata.append(SEPARATOR);
						clientdata.append(ID);
						clientdata.append(SEPARATOR);
						clientdata.append(message);
						output.write(clientdata.toString() + "\r\n");
						output.flush();
						wtext.setText("");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}
}