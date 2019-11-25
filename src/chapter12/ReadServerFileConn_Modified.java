package chapter12;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ReadServerFileConn_Modified extends Frame implements ActionListener {
	private TextField enter;
	private TextArea contents;
	
	public ReadServerFileConn_Modified() {
		super("Read Host File");
		setLayout( new BorderLayout() );
		enter = new TextField( "URL를 입력하세요!");
		enter.addActionListener(this);
		add( enter, BorderLayout.NORTH );
		contents = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		add ( contents, BorderLayout.CENTER );
		addWindowListener(new WinListener());
		setSize(800, 500);
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e) {
		URL url;
		URLConnection urlconn;
		HttpURLConnection con;
		InputStream is;
		BufferedReader input;
		String line;
		StringBuffer buffer = new StringBuffer();
		String location = e.getActionCommand();
		try {
			url = new URL(location);
			urlconn = url.openConnection();
			con = (HttpURLConnection)url.openConnection();
			String headertype = urlconn.getContentType();
			is = urlconn.getInputStream();
			input = new BufferedReader(new InputStreamReader(is));
			contents.setText("Reading File....");
			line = "Header Type : " + headertype + "\n";
			buffer.append(line);
			line = "Response Code : " + con.getResponseCode() + "\n";
			buffer.append(line);
			line = "Response Message : " + con.getResponseMessage() + "\n";
			buffer.append(line);
			while( (line=input.readLine()) != null ) {
				buffer.append(line).append('\n');
			}
			contents.setText(buffer.toString());
			input.close();
		}catch (MalformedURLException mal) {
			mal.printStackTrace();
		}catch (IOException io) {
			io.printStackTrace();
		}catch (Exception ex) {
			contents.setText("Read Only Host Computer File");
		}
	}
	public static void main(String args[]) {
		ReadServerFileConn_Modified read = new ReadServerFileConn_Modified();
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
}
