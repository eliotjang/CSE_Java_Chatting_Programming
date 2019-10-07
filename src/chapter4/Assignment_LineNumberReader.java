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
	private TextField tfile; // ���ڿ��� �о���� ���� ����
	private Button input, output; // �̺�Ʈ ó���� ���� ����
	private TextArea tadata; // ��ó�� �� ����� ���� ����
	// Label�� ���� ������ ������ ����.
	public Assignment_LineNumberReader() {
		super("add LineNumber");
		setBounds(700,350,350,350); // ����ڰ� �����̸� �Է� ����
		setLayout( new FlowLayout());
		add( new Label("file name"));
		add( tfile = new TextField(15));
		add( input = new Button("input"));
		input.addActionListener(this); // �������� add�Լ��� ���� ����� ����. ������ �� '0'�� ���� ����.
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
			bw.flush(); // ������ ������ �����ؾ� �Ѵ�.
			fr.close();
			r.close();
		}catch (FileNotFoundException fnfe) { // FileNotFoundException�� IOException�� �������� �ۼ��ϴ� ���� ���� ����ó���̴�. 
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
