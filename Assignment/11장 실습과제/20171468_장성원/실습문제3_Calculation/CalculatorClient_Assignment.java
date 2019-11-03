/*
 * Author: Eliot Jang
 * last_modified_at: 2019-11-02T20:15:00+09:00
 */
package chapter11;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorClient_Assignment extends Frame implements ActionListener { 
	private TextField enter;
	private TextArea display;
	private DatagramPacket sendPacket, recvPacket;
	private DatagramSocket theSocket;
	private InetAddress ia;
	private Label calc;
	byte buffer[] = null;
	
	public CalculatorClient_Assignment() {
		super("Calculator");
		display = new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		display.setEditable(false);
		add(display,BorderLayout.CENTER);
		Panel pnl = new Panel(new BorderLayout());
		calc = new Label("Input equation");
		enter = new TextField(30);
		enter.addActionListener(this);
		pnl.add(calc,BorderLayout.WEST);
		pnl.add(enter,BorderLayout.EAST);
		add(pnl,BorderLayout.SOUTH);
		addWindowListener(new WinListener());
		setSize(500,400);
		setVisible(true);
		try {
			theSocket = new DatagramSocket(4000);
			ia = InetAddress.getLocalHost();
		} catch(SocketException se) {
			se.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			enter.setText("");
			display.append("\nAnswer: " + e.getActionCommand()+"\n");
			buffer = new byte[8192];
			buffer = e.getActionCommand().getBytes();
			sendPacket = new DatagramPacket(buffer, buffer.length, ia, 5000);
			theSocket.send(sendPacket);
			buffer = new byte[8192]; 
			recvPacket = new DatagramPacket(buffer, buffer.length); 
			theSocket.receive(recvPacket); 
			display.append(new String(buffer,0,buffer.length)+"\n");
		} catch(SocketException se) {
			se.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		CalculatorClient_Assignment udp = new CalculatorClient_Assignment();
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}
} 
