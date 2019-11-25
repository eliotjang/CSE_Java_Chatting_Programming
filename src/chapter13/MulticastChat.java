/*package chapter13;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class MulticastChat implements Runnable, WindowListener, ActionListener {
	protected InetAddress group;
	protected int port;
	protected Frame frame;
	protected Panel p1;
	protected Panel p2;
	protected Panel p3;
	protected TextField input;
	protected TextField idinput;
	protected Label recv;
	protected Label send;
	protected Label userid;
	protected Button ok;
	protected TextArea output;
	protected TextArea output2;
	protected Thread listener;
	protected MulticastSocket socket;
	protected DatagramPacket outgoing, incoming;
	protected String ID;
	protected boolean check = false;

	public MulticastChat(InetAddress group, int port) {
		this.group = group;
		this.port = port;
		initAWT();
	}

	protected void initAWT() {
		frame = new Frame("��Ƽĳ��Ʈ ä�� [ȣ��Ʈ : " + group.getHostAddress() + " , " + port + "]");
		frame.addWindowListener(this);
		p1 = new Panel(new BorderLayout());
		p2 = new Panel(new BorderLayout());
		p3 = new Panel(new BorderLayout());
		output = new TextArea();
		output.setEditable(false);
		output2 = new TextArea();
		output2.setEditable(false);
		input = new TextField();
		idinput = new TextField();
		input.addActionListener(this);
		recv = new Label("���� �޼��� ����");
		send = new Label("�۽� �޼��� ����");
		userid = new Label("����� ID");
		ok = new Button("Ȯ��");
		ok.addActionListener(this);
		frame.setLayout(new BorderLayout());
		p1.add(recv, "North");
		p1.add(output, "Center");
		p2.add(send, "North");
		p2.add(output2, "Center");
		p3.add(ok, "East");
		p3.add(idinput, "Center");
		p3.add(userid, "West");
		p3.add(input, "South");
		frame.add(p2, "North");
		frame.add(p1, "Center");
		frame.add(p3, "South");
		frame.pack();
	}

	public synchronized void start() throws IOException {
		if (listener == null) {
			initNet();
			listener = new Thread(this);
			listener.start(); // �����带 �����Ѵ�.(run() �޼ҵ� ����)
			frame.setVisible(true);
		}
	}

	protected void initNet() throws IOException {
		socket = new MulticastSocket(port);
		socket.setTimeToLive(1);
		socket.joinGroup(group);
		outgoing = new DatagramPacket(new byte[1], 1, group, port);
		incoming = new DatagramPacket(new byte[65508], 65508);
	}

	public synchronized void stop() throws IOException {
		frame.setVisible(false);
		if (listener != null) {
			listener.interrupt();
			listener = null;
			try {
				socket.leaveGroup(group);
			} finally {
				socket.close();
				System.exit(0);
			}
		}
	}

	public void windowOpened(WindowEvent we) {
		input.requestFocus();
	}

	public void windowClosing(WindowEvent we) {
		try {
			stop();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void windowClosed(WindowEvent we) {
	}

	public void windowIconified(WindowEvent we) {
	}

	public void windowDeiconified(WindowEvent we) {
	}

	public void windowActivated(WindowEvent we) {
	}

	public void windowDeactivated(WindowEvent we) {
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().compareTo("Ȯ��") == 0) {
			ID = idinput.getText();
			byte[] utf;
			try {
				String temp=ID+"���� �����ϼ̽��ϴ�.";
				utf = temp.getBytes("UTF8");
				outgoing.setData(utf);
				outgoing.setLength(utf.length);
				socket.send(outgoing);
				idinput.setEnabled(false);
				check = true;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (check) {
				try {
					String temp;
					temp=ID+" : "+ae.getActionCommand();
					byte[] utf = temp.getBytes("UTF8");
					outgoing.setData(utf);
					outgoing.setLength(utf.length);
					socket.send(outgoing);
					output2.setText(output2.getText() + input.getText() + "\n");
					input.setText(""); // �ؽ�Ʈ �ʵ��� ������ �����.
				} catch (IOException e) {
					System.out.println(e);
					handleIOException(e);
				}
			}
		}
	}

	protected synchronized void handleIOException(IOException e) {
		try {
			stop();
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				incoming.setLength(incoming.getData().length);
				socket.receive(incoming);
				String message = new String(incoming.getData(), 0, incoming.getLength(), "UTF8");
				output.append(message + "\n");
			}
		} catch (IOException e) {
			handleIOException(e);
		}
	}

	public static void main(String args[]) throws IOException {
		if ((args.length != 1) || (args[0].indexOf(":") < 0)) // ��Ƽĳ��Ʈ�ּ�:��Ʈ��ȣ ���·� �Է��� �ؾ���.
			throw new IllegalArgumentException("�߸��� ��Ƽĳ��Ʈ �ּ��Դϴ�.");
		int idx = args[0].indexOf(":");
		InetAddress group = InetAddress.getByName(args[0].substring(0, idx));
		int port = Integer.parseInt(args[0].substring(idx + 1));
		MulticastChat chat = new MulticastChat(group, port);
		chat.start();
	}
}*/