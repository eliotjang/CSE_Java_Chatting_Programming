package chapter12;

import java.net.*;
import java.io.*;
import java.util.*;

public class GetMIMEHeader {
	public static void main(String args[]) {
		URL u;
		URLConnection uc;
		if(args.length == 0) {
			System.out.println("URL을 입력하세요!");
			return;
		}
		for(int i=0; i<args.length; i++) {
			try {
				u = new URL(args[i]);
				uc = u.openConnection(); // urlconnection 객체 생성
				System.out.println("컨텐츠 유형 : " + uc.getContentType());
				System.out.println("컨텐츠 인코딩 : " +uc.getContentEncoding());
				System.out.println("문서전송날짜 : " + new Date(uc.getDate()));
				System.out.println("최종수정날짜  : " + new Date(uc.getLastModified()));
				System.out.println("문서만기날짜 : " + new Date(uc.getExpiration()));
				System.out.println("문서길이 : " + uc.getContentLength());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
