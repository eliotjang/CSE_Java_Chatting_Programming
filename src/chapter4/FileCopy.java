package chapter4;

import java.io.*;

public class FileCopy {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int data = 0;
		char[] buffer = new char[80];
		
		File file = new File("Exam_02.txt");
		FileReader fin = new FileReader(file);
		FileWriter fw = new FileWriter("Exam_02_1.txt");
		
		BufferedReader br = new BufferedReader(fin);
		//String line = Integer.toString(data);
		String line = String.valueOf(data);
		while((line=br.readLine()) != null) {
			System.out.println(line);
		}
		
		//data가 스트링을 바뀌었을 경우
		
		/*BufferedReader br = new BufferedReader(fin);
		while((data=br.readLine(buffer)) > -1) {
			System.out.println(data);
		}*/
		
		/*while((data = fin.read(buffer)) > -1) {
			fw.write(buffer,0,data);
		}*/
		fw.close();
		fin.close();
	}

}
