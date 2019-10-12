/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T02:46+09:00
 */

package chapter5;

import java.net.*;

public class GetIPAddress {

	public static void main(String[] args) { 
		if(args.length != 1) {
			System.err.println("usage: put hostName in Program Arguments!");
			System.exit(1);
		}
		try {
			InetAddress address = InetAddress.getByName(args[0]);
			System.out.println("getHostName: " + address.getHostName());
			System.out.println("getAddressName: " + address.getHostAddress());
			System.out.println("toString: " + address.toString());
			/*
			 * same as following code
			 * System.out.println(address);
			 * because InetAddress return form is "host address/ip address"
			 */
		} catch (UnknownHostException e) {
			// includes message amount: getMessage() < toString() < printStackTrace()
			e.printStackTrace();
		}
	}

}
