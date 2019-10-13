/*
 * author: eliotjang
 * last_modified_at: 2019-10-13T15:07:00+09:00
 */
package chapter5;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetLocalIPAddress {

	public static void main(String[] args) {
		try {
			InetAddress laddr = InetAddress.getLocalHost();
			byte[] addr = laddr.getAddress();
			// initial value
			System.out.print("inital value: ");
			for (byte b : addr) {
				if (b != -64) System.out.print(".");
				// Just for not output last dot.
				System.out.print(b);
			}
			System.out.println("\n¢Ñadd 256");
			// modified value
			System.out.print("modified value: ");
			for (byte b : addr) {
				if (b != -64) System.out.print(".");
				int result = b < 0 ? b + 256 : b;
				System.out.print(result);
			}
			/*
			 * For-Loop is same as follwing code: for(int i = 0 ; i < addr.length ; i++ ) {}
			 * For-Each-Loop is more intuitive.
			 */
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
