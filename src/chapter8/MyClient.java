package chapter8;

import java.io.*;
import java.net.*;

public class MyClient {
	
	public static void main(String[] args) {
		Socket theSocket;
		String host = "wwww.github.com/eliotjang";
		int port = 7;
		try {
			System.out.println(host+" ȣ��Ʈ�� ��Ʈ��ȣ "+port+"��  �����ϰ� �ֽ��ϴ�.");
			theSocket = new Socket(host, port);
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			theSocket.close();
		} catch (UnknownHostException e) {
			System.err.println("������ ȣ��Ʈ�� �����ϴ�");
		} catch (IOException e) {
			System.err.println("������ ������ �ʽ��ϴ�.");
		}
	}
}
