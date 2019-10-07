// coded by eliotjang
// 2019.09.29
package chapter4;
import java.io.*;
// commit something
public class Assignment_AppendNewFile {
	public static void main(String[] args) {
		String text;
		if(args.length != 2) {
			System.err.println("usage: �Է�����1.txt �Է�����2.txt");
			System.exit(1);
		}
		try {
		FileReader f1 = new FileReader(args[0]);
		FileReader f2 = new FileReader(args[1]);
		FileWriter f3 = new FileWriter("thirdfile.txt");
		
		BufferedReader br1 = new BufferedReader(f1);
		BufferedReader br2 = new BufferedReader(f2);
		BufferedWriter bw = new BufferedWriter(f3);
		
		while((text=br1.readLine()) != null) {
			bw.write(text + "\r\n");
		}
		while((text=br2.readLine()) != null) {
			bw.write(text + "\r\n");
		}
		
		bw.flush();
		bw.close();
		br1.close();
		br2.close();
		
		}catch(FileNotFoundException fnfe) { // FileNotFoundException�� IOException�� �������� �ۼ��ϴ� ���� ���� ����ó���̴�.
			System.err.println(fnfe.toString());
		}catch(IOException ioe) {
			System.err.println(ioe.toString());
		}
	}
}
