/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T15:40:00+09:00
 */
package chapter5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.UnknownHostException;
// import should be declare in order super class

public class GetNameAddress {

	public static void main(String[] args) {
		String hostName;
		/*
		 * InetAddress addr = null;
		 * this code is not good way for use Conditional Statements.
		 * type InetAddress should accessed in a static way
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			if ((hostName = br.readLine()) != null) {
				InetAddress addr = InetAddress.getByName(hostName);
				System.out.println("Host Name: " + addr.getHostName());
				System.out.println("Host Address: " + addr.getHostAddress());
			}

			InetAddress laddr = InetAddress.getLocalHost();
			System.out.println("Local Host Name: " + laddr.getHostName());
			System.out.println("Local Host Address: " + laddr.getHostAddress());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
