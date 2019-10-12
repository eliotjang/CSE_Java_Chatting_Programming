package chapter6;

import java.io.*;
import java.net.*;
import java.awt.Image;

public class ModifiedGetObject {
	public static void main(String[] args) {
		/*URL u;
		String line, urlstring;
		BufferedReader br, reader; //use BufferedInputStream is good~
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("URL을 입력하십시오~");
		try {
			urlstring = br.readLine();
			u = new URL(urlstring);
			Object o = u.getContent(); // 어떤 객체인지
			if (o.getClass().getName().contains(urlstring)) {
				InputStream is = (InputStream) o;
				reader = new BufferedReader(new InputStreamReader(is));
				while((line=reader.readLine()) != null) {
					System.out.println(line);
				}
			}else if (o.getClass().getName().contains(urlstring)) {
				java.awt.Image jai = (java.awt.Image) o;
				reader = new BufferedReader(new InputStreamReader(jai));
				while((line=reader.readLine()) != null) {
					System.out.println(line);
				}
			}else {
				
			}
		} catch(IOException e) {
			System.err.println(e.toString());
		}*/
	}
}
