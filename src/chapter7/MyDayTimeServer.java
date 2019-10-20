package chapter7;

import java.io.*;
import java.net.*;
import java.util.Date;

public class MyDayTimeServer {
	
	public static void main(String[] args) {
		ServerSocket theServer;
		Socket theSocket = null;
		InputStream is;
		BufferedWriter writer;
		BufferedReader reader;
		String str;
		str = null;
		try {
			theServer = new ServerSocket(50001);
			theServer.setReuseAddress(true);
			
			while(true) {
				try {
					theSocket = theServer.accept();
					
					OutputStream os = theSocket.getOutputStream();
					writer = new BufferedWriter(new OutputStreamWriter(os));
					
					Date date = new Date();
					writer.write(date.toString()+"\r\n");
					writer.flush();
					theSocket.shutdownOutput();
					is = theSocket.getInputStream();
					reader = new BufferedReader(new InputStreamReader(is));
					str = reader.readLine();
					
					System.out.println(str);
					theSocket.close();
					
				}catch(IOException e) {
					System.out.println(e);
				}finally {
					try {
						if(theSocket != null) theSocket.close();
					}catch(IOException e) {
						System.out.println(e);
					}
				}
			}
		}catch(IOException e) {
			System.out.println(e);
		}
	}
}