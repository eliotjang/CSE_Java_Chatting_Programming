package chapter12;

import java.net.*;
import java.io.*;

public class MakeURLConnection {
	public static void main(String args[]) {
		URL u;
		URLConnection uc;
		try {
			u = new URL("http://www.gwnu.ac.kr");
			uc = u.openConnection();
			System.out.println("uc °´Ã¼ : "+uc);
		} catch(MalformedURLException me) {
			me.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
