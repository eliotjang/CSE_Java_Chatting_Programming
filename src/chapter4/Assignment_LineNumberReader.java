// coded by eliotjang
// 2019.09.29
package chapter4;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Assignment_LineNumberReader extends Frame implements ActionListener {
	String filename;
	FileReader fr;
	FileReader fr_modified;
	FileWriter fw;
	BufferedReader br;
	BufferedWriter bw;
	PrintWriter pw;
	LineNumberReader r;
	private TextField tfile; // 문자열을 읽어오기 위해 선언
	private Button input, output; // 이벤트 처리를 위해 선언
	private TextArea tadata; // 줄처리 후 출력을 위해 선언
	// Label은 굳이 선언할 이유가 없음.
	public Assignment_LineNumberReader() {
		super("add LineNumber");
		setBounds(700,350,350,350); // 사용자가 파일이름 입력 용이
		setLayout( new FlowLayout());
		add( new Label("file name"));
		add( tfile = new TextField(15));
		add( input = new Button("input"));
		input.addActionListener(this); // 위문장의 add함수와 순서 변경시 에러. 이유는 값 '0'이 들어가기 때문.
		add( output = new Button("modified new file"));
		output.addActionListener(this);
		add( tadata = new TextArea(13, 45));
		addWindowListener(new WinListener());
		setVisible(true);
	}
	public void addLineNumber() {
		try {
			String line;
			filename = tfile.getText();
			fr = new FileReader(filename);
			fw = new FileWriter("numbered_" +filename);
			bw = new BufferedWriter(fw);
			r = new LineNumberReader(fr);
			while((line=r.readLine()) != null) {
				bw.write(r.getLineNumber() + ": " + line + "\r\n");
			}
			bw.flush(); // 버퍼의 내용을 전송해야 한다.
			fr.close();
			r.close();
		}catch (FileNotFoundException fnfe) { // FileNotFoundException은 IOException에 속하지만 작성하는 것이 좋은 예외처리이다. 
			System.err.println(fnfe.toString());
		}catch (IOException ioe) {
			System.err.println(ioe.toString());
		}
	}
	public void printModifiedFile() {
		try {
			String text;
			fr_modified = new FileReader("numbered_" +filename);
			br = new BufferedReader(fr_modified);
			while((text=br.readLine()) != null) {
				tadata.append(text+"\r\n");
			}
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.toString());
		}catch(IOException ioe) {
			System.err.println(ioe.toString());
		}
	}
	public void actionPerformed(ActionEvent ae) {
		String ac = ae.getActionCommand();
		
		if(ac.equals("input")) {
			addLineNumber();
		} else if (ac.equals("modified new file")) {
			printModifiedFile();
		} else System.err.println(ae.toString());
		
	}
	class WinListener extends WindowAdapter{
		public void windowClosing(WindowEvent we) {
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new Assignment_LineNumberReader();
	}
}
