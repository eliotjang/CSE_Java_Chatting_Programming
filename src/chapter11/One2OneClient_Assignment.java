/*
 * Author: Eliot Jang
 * last_modified_at: 2019-11-02T15:30:00+09:00
 */
package chapter11;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class One2OneClient_Assignment extends Frame implements ActionListener{
	private TextField enter;
	private TextArea display;
	private DatagramPacket sendPacket,receivePacket;
	private DatagramSocket socket;
	private Label chats;
	public One2OneClient_Assignment() {
		super("Client");
		display = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display,BorderLayout.CENTER);
		Panel pnl = new Panel(new BorderLayout());
		chats = new Label("chatting");
		enter = new TextField(30);
		enter.addActionListener(this);
		pnl.add(chats,BorderLayout.WEST);
		pnl.add(enter, BorderLayout.EAST);
		add(pnl,BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(500,500);
		setVisible(true);
		try {
			socket = new DatagramSocket(4000);
		}catch(SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}
	public void waitForPacket() {
		while(true) {
			try {	
				byte data[] = new byte[100];
				receivePacket = new DatagramPacket(data,data.length);
				socket.receive(receivePacket);
				String message = new String(receivePacket.getData(),0,receivePacket.getLength());
				display.append("\nServer Message: "+message+"\n");
			}catch(IOException exception) {
				display.append(exception.toString()+"\n");
				exception.printStackTrace();
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		try {
			enter.setText("");
			display.append("\nsend message: "+e.getActionCommand()+"\n");
			String s = e.getActionCommand();
			byte[] data = s.getBytes();
			sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),5000);
			socket.send(sendPacket);
			display.append("packet send complete\n");
		}catch(IOException exception) {
			display.append(exception.toString()+"\n");
			exception.printStackTrace();
		}
	}
	public static void main(String args[]) {
		One2OneClient_Assignment u = new One2OneClient_Assignment();
		u.waitForPacket();
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}


