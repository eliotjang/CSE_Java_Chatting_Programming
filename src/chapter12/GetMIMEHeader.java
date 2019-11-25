package chapter12;

import java.net.*;
import java.io.*;
import java.util.*;

public class GetMIMEHeader {
	public static void main(String args[]) {
		URL u;
		URLConnection uc;
		if(args.length == 0) {
			System.out.println("URL�� �Է��ϼ���!");
			return;
		}
		for(int i=0; i<args.length; i++) {
			try {
				u = new URL(args[i]);
				uc = u.openConnection(); // urlconnection ��ü ����
				System.out.println("������ ���� : " + uc.getContentType());
				System.out.println("������ ���ڵ� : " +uc.getContentEncoding());
				System.out.println("�������۳�¥ : " + new Date(uc.getDate()));
				System.out.println("����������¥  : " + new Date(uc.getLastModified()));
				System.out.println("�������⳯¥ : " + new Date(uc.getExpiration()));
				System.out.println("�������� : " + uc.getContentLength());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
