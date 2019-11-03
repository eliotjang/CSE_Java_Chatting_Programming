/*
 * Author: Eliot Jang
 * last_modified_at: 2019-11-02T15:30:00+09:00
 */
package chapter11;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class One2OneServer_Assignment extends Frame implements ActionListener{
	public TextArea display;
	public DatagramPacket sendPacket,receivePacket;
	public DatagramSocket socket;
	public TextField text;
	public Label chats;
	public One2OneServer_Assignment() {
		super("Server");
		Panel pnl = new Panel(new BorderLayout());
		display = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display,BorderLayout.CENTER);
		chats = new Label("chatting");
		text = new TextField(30);
		text.addActionListener(this);
		pnl.add(chats,BorderLayout.WEST);
		pnl.add(text,BorderLayout.EAST);
		add(pnl,BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(500,500);
		setVisible(true);
		try {
			socket = new DatagramSocket(5009);
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
				display.append("\nClient Message: "+message+"\n");
				
				
			}catch(IOException io) {
				display.append(io.toString()+"\n");
				io.printStackTrace();
			}
		}
	
	}
	public void actionPerformed(ActionEvent ae) {
		try {
			text.setText("");
			display.append("\nsend message: "+ae.getActionCommand()+"\n");
			String s = ae.getActionCommand();
			byte[] data = s.getBytes();
			sendPacket = new DatagramPacket(data,data.length,InetAddress.getLocalHost(),4000);
			socket.send(sendPacket);
			display.append("packet send complete\n");
		}catch(IOException exception) {
			display.append(exception.toString()+"\n");
			exception.printStackTrace();
		}
	}
	public static void main(String args[]) {
		One2OneServer_Assignment s = new One2OneServer_Assignment();
		s.waitForPacket();
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
}