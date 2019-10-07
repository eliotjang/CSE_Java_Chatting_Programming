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
		
			System.out.println("§종료를 원하시면 '엔터 키'를 누른 뒤에 CTRL+Z를 입력하시오§");
			while((text=br_input.readLine()) != null) {
				bw.write(text+"\r\n"); //파일에 저장
			}
			// 저장된 파일을  출력하는 것이 중요함.
			bw.flush();
			
			while((text=br_file.readLine()) != null ){
			    pw.println(text);
			}
			bw.close();
			br_input.close();
			br_file.close();
			pw.close();
		}catch (FileNotFoundException fnfe){ // FileNotFoundException은 IOException에 속하지만 작성하는 것이 좋은 예외처리이다. 
			System.err.println(fnfe.toString());
		}catch (UnsupportedEncodingException uee) {
			System.err.println(uee.toString());
		}catch (IOException ioe){
			System.err.println(ioe.toString());
		}
	}
}