package chapter12;

import java.net.*;
import java.io.*;

public class ReadHTMLFile {
	public static void main(String args[]) {
		String data;
		URL u;
		URLConnection uc;
		try {
			u = new URL("http://gwnu.ac.kr");
			uc = u.openConnection();
			InputStream is = uc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while((data = reader.readLine()) != null) {
				System.out.println(data); // index.html ������ ȭ�鿡 ���پ� ����Ѵ�.
			}
		} catch(MalformedURLException e) {
			System.out.println(e);
		} catch(IOException ioe) {
			System.out.println(ioe);
		}
	}
}
