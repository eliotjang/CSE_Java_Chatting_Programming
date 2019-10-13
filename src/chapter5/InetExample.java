/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T21:34:00+09:00
 */
package chapter5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetExample {

	public static void main(String[] args) {
		String hostName;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		printLocalAddress();
		try {
			do {
				if ((hostName = br.readLine()) != null)
					printRemoteAddress(hostName);
			} while (hostName!=null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Exit Program..");
			System.exit(0);
		}
	}

	static void printLocalAddress() {
		try {
			InetAddress laddr = InetAddress.getLocalHost();
			System.out.println("Local Host Name: " + laddr.getHostName());
			System.out.println("Local Host IP Address: " + laddr.getHostAddress());
			System.out.println("Local Host class: " + ipClass(laddr.getAddress()));
			System.out.println("Local Host InetAddress: " + laddr.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	static void printRemoteAddress(String hostName) {
		try {
			InetAddress machine = InetAddress.getByName(hostName);
			System.out.println("Remote Host Name: " + machine.getHostName());
			System.out.println("Remote Host IP Address: " + machine.getHostAddress());
			System.out.println("Remote Host class: " + ipClass(machine.getAddress()));
			System.out.println("Remote Host InetAddress: " + machine.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	static char ipClass(byte[] ip) {
		int highByte = 0xff & ip[0];
		return(highByte < 128 ? 'A' : highByte < 192 ? 'B' : highByte < 224 ? 'C' : highByte < 240 ? 'D' : 'E');
	}

}
