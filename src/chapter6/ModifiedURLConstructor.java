package chapter6;

import java.net.*;
import java.io.*;

public class ModifiedURLConstructor {
	public static void main(String[] args) {
		URL u;
		String urlstring;
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("URL�� �Է��Ͻʽÿ�~");
		try {
			urlstring = br.readLine();
			u = new URL(urlstring);
			System.out.println("���������� " + u.getProtocol());
			System.out.println("ȣ��Ʈ �̸��� " + u.getHost() );
			System.out.println("���� �̸��� " + u.getFile());
			System.out.println("��δ� " + u.getPath());
			System.out.println("��Ŀ������ " + u.getRef());
			System.out.println("�ؽ��ڵ�� " + u.hashCode());
		}catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}
