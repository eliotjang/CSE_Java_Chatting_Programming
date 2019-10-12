/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T03:10:00+09:00
 */
package chapter5;

import java.net.InetAddress;
import java.net.UnknownHostException;
/*
 * same as following code
 * import java.net.*;
 */

public class GetManyIPAddress {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("usage: put hostName in Program Arguments");
			System.exit(1);
		}
		try {
			InetAddress[] addresses = InetAddress.getAllByName(args[0]);
			//use iterations for output all
			for( int i = 0 ; i < addresses.length ; i++ ) {
				System.out.println(addresses[i]);
				// same as following code: System.out.println(addresses[i].toString());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
