// coded by eliotjang
// 2019.09.29
package chapter4;
import java.io.*;
public class Assignment_PrintWriter {
	public static void main(String[] args) {
		String filename = "memo1.txt";
		String text;
		InputStreamReader isr;
		BufferedReader br_input;
		BufferedReader br_file;
		BufferedWriter bw;
		FileWriter fw;
		FileReader fr;
		try {
			fr = new FileReader(filename);
			fw = new FileWriter(filename);
			isr = new InputStreamReader(System.in);
			br_input = new BufferedReader(isr);
			br_file = new BufferedReader(fr);
			bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(System.out);
		
			System.out.println("�����Ḧ ���Ͻø� '���� Ű'�� ���� �ڿ� CTRL+Z�� �Է��Ͻÿ���");
			while((text=br_input.readLine()) != null) {
				bw.write(text+"\r\n"); //���Ͽ� ����
			}
			// ����� ������  ����ϴ� ���� �߿���.
			bw.flush();
			
			while((text=br_file.readLine()) != null ){
			    pw.println(text);
			}
			bw.close();
			br_input.close();
			br_file.close();
			pw.close();
		}catch (FileNotFoundException fnfe){ // FileNotFoundException�� IOException�� �������� �ۼ��ϴ� ���� ���� ����ó���̴�. 
			System.err.println(fnfe.toString());
		}catch (UnsupportedEncodingException uee) {
			System.err.println(uee.toString());
		}catch (IOException ioe){
			System.err.println(ioe.toString());
		}
	}
}