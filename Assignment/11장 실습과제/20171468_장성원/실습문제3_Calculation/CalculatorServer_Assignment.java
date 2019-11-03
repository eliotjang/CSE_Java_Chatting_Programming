/*
 * Author: Eliot Jang
 * last_modified_at: 2019-11-02T20:15:00+09:00
 */
package chapter11;

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.util.StringTokenizer; 

public class CalculatorServer_Assignment { 
	public static void main(String[] args) throws IOException 
	{ 
		DatagramSocket socket = new DatagramSocket(5000); 
		byte[] buf = null; 
		DatagramPacket recvPacket = null; 
		DatagramPacket sendPacket = null; 
		while (true) 
		{ 
			buf = new byte[8192]; 
			recvPacket = new DatagramPacket(buf, buf.length); 
			socket.receive(recvPacket); 

			String input = new String(buf, 0, buf.length); 
			input=input.trim(); 
			System.out.println("Equation Received:- " + input); 

			int result; 

			StringTokenizer st = new StringTokenizer(input); 
			int oprnd1 = Integer.parseInt(st.nextToken()); 
			String operation = st.nextToken(); 
			int oprnd2 = Integer.parseInt(st.nextToken()); 

			if (operation.equals("+")) 
				result = oprnd1 + oprnd2; 

			else if (operation.equals("-")) 
				result = oprnd1 - oprnd2; 

			else if (operation.equals("*")) 
				result = oprnd1 * oprnd2; 

			else
				result = oprnd1 / oprnd2; 

			System.out.println("Sending the result..."); 
			String res = Integer.toString(result); 

			buf = res.getBytes(); 
			int port = recvPacket.getPort(); 

			sendPacket = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), port); 
			socket.send(sendPacket); 
		} 
	} 
} 
