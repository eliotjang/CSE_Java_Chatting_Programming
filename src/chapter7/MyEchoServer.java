package chapter7;

import java.io.*;
import java.net.*;

public class MyEchoServer {

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
			//theServer.setReuseAddress(true);
			
			while(true) {
				Socket connection = null;
				Echo client = null;		
				connection = theServer.accept();
				client = new Echo(connection);
				client.start();							
			}
		} catch (UnknownHostException e) {
			System.err.println(e.toString());
		} catch (IOException e) {
			System.err.println(e.toString());
		} 
	}

}

class Echo extends Thread{
	Socket connection;
	InputStream is;
	BufferedReader reader;
	OutputStream os;
	BufferedWriter writer;
	String theLine;
	
	public Echo(Socket connection) {
		try {
		this.connection = connection;
	    InetAddress Address = InetAddress.getLocalHost();
	    SocketAddress addr = new InetSocketAddress(Address,8011);
	    System.out.println("SO_REUSEADDR is enabled: " + connection.getReuseAddress());
	    connection.setReuseAddress(true);
	    System.out.println("SO_REUSEADDR is enabled: " + connection.getReuseAddress());
	   // connection.bind(addr);
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		/*InputStream is;
		BufferedReader reader;
		OutputStream os;
		BufferedWriter writer;
		String theLine;*/
		
		try {
			is = connection.getInputStream();
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