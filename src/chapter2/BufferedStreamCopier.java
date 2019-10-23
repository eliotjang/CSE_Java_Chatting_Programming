package chapter2;

import java.io.*;

public class BufferedStreamCopier {
	
	public static void main(String args[]) throws IOException {
		FileOutputStream fout = new FileOutputStream("Exam_01.txt");
		func_one(System.in, fout);
	}
	public static void func_one(InputStream in, OutputStream out) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		while(true) {
			int data = bin.read();
			if(data==-1) break;
			bout.write(data);
		}
		bout.flush();
		//int i;
		//byte[] buffer = new byte[256];
		//while((i= in.read(buffer)) >= 0) {
		//	System.out.println(i);
		//	out.write(buffer,0,i);
	}
}
