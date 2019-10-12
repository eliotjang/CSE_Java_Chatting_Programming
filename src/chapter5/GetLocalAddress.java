/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T03:00:00+09:00
 */
package chapter5;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetLocalAddress {

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address);
			// form: computer host name/ip address
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
