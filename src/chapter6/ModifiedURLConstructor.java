package chapter6;

import java.net.*;
import java.io.*;

public class ModifiedURLConstructor {
	public static void main(String[] args) {
		URL u;
		String urlstring;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("URL을 입력하십시오~");
		try {
			urlstring = br.readLine();
			u = new URL(urlstring);
			System.out.println("프로토콜은 " + u.getProtocol());
			System.out.println("호스트 이름은 " + u.getHost() );
			System.out.println("파일 이름은 " + u.getFile());
			System.out.println("경로는 " + u.getPath());
			System.out.println("앵커정보는 " + u.getRef());
			System.out.println("해시코드는 " + u.hashCode());
		}catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
