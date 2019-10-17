package chapter7;

import java.io.*;
import java.net.*;

public class EchoServer {
	
	class Echo extends Thread{
		
		public Echo(Socket connection) {
			InputStream is;
			BufferedReader reader;
			OutputStream os;
			BufferedWriter writer;
			String theLine;
			
			try {
				reader = new BufferedReader(new InputStreamReader(is));
				os = connection.getOutputStream();
				writer = new BufferedWriter(new OutputStreamWriter(os));
				
				while((theLine = reader.readLine()) != null) {
					System.out.println(theLine);
					writer.write(theLine + '\r' + '\n');
					writer.flush();
				} 
			} catch(IOException e) {
				System.err.println(e.toString());
			} finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (IOException e) {
						System.err.println(e.toString());
					}
				}
			}
		
		}
	}

	public static void main(String[] args) {
		ServerSocket theServer;
		/*Socket theSocket = null;
		InputStream is;
		BufferedReader reader;
		OutputStream os;
		BufferedWriter writer;
		String theLine;*/
		try {
			theServer = new ServerSocket(7);
			
			while(true) {
				Socket connection = null;
				Echo client = null;
				try {
					connection = theServer.accept();
					Echo client = new Echo(connection);
					client.start();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				
			}
		} catch (UnknownHostException e) {
			System.err.println(e.toString());
		} catch (IOException e) {
			System.err.println(e.toString());
		} 
	}

}