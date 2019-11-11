// coded by eliotjang
// 2019.09.29
package chapter4;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Assignment_CompareFile {
	public static void main(String[] args) {
		boolean tf = true;
		String text1, text2;
		if(args.length != 2) {
			System.err.println("usage: 입력파일1.txt 입력파일2.txt");
		}
		try{
			File filename1 = new File(args[0]);
			File filename2 = new File(args[1]);
			
			FileReader fr1 = new FileReader(filename1);
			FileReader fr2 = new FileReader(filename2);
			
			BufferedReader br1 = new BufferedReader(fr1);
			BufferedReader br2 = new BufferedReader(fr2);
			
			long lastModified1 = filename1.lastModified();
			long lastModified2 = filename2.lastModified();
			
			Date lastModifiedDate1 = new Date(lastModified1);
			Date lastModifiedDate2 = new Date(lastModified2);
			SimpleDateFormat formatType = new SimpleDateFormat("yyyy년 MM월 dd일(E요일) kk시 mm분");
			
			while( ((text1=br1.readLine()) != null) && ((text2=br2.readLine()) != null) ) {
				if ( (text1 == null) || (text2 == null) ) {
					tf = false;
					break;
				}
				if ( !(text1.equals(text2)) ) {
					tf = false;
					break;
				}else {
					tf = true;
				}
			}
			if(tf) {
				System.out.println(filename1 + " last modified = " + formatType.format(lastModifiedDate1));
				System.out.println(filename2 + " last modified = " + formatType.format(lastModifiedDate2));
			}else {
				System.out.println(filename1 + " length = " + filename1.length());
				System.out.println(filename2 + " length = " + filename2.length());
			}
			br2.close();
		}catch(FileNotFoundException fnfe) { // FileNotFoundException은 IOException에 속하지만 작성하는 것이 좋은 예외처리이다.
			System.err.println(fnfe.toString());
		}catch(IOException ioe) {
			System.err.println(ioe.toString());
		}
	}
}
