package chapter8;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
 
public class MydownloadClient{
	
    public static void main(String[] args){
        OutputStream out;
        FileInputStream fin;
        
        try{
            Socket soc = new Socket("127.0.0.1",11111); 
            System.out.println("클라이언트 시작");       
            out =soc.getOutputStream();                 
            DataOutputStream dout = new DataOutputStream(out); 
            
            
            Scanner s = new Scanner(System.in);  
            
            
            while(true){
                String filename = s.next();    
                fin = new FileInputStream(new File(filename));
            
                byte[] buffer = new byte[1024];       
                int len;                               
                int data=0;                            
        
                while((len = fin.read(buffer))>0){    
               		data++;                        
               	}
        
                int datas = data;                     
                fin.close();
                fin = new FileInputStream(filename);   
                dout.writeInt(data);                  
                dout.writeUTF("2"+filename);     
        
                len = 0;
        
                for(;data>0;data--){                  
                	len = fin.read(buffer);      
                	out.write(buffer,0,len);  
                }
        
                System.out.println(filename.toString());
                System.out.println("저장완료");
            }
            
        }catch(Exception e){
        	System.out.println(e.toString());
        }
        
    }
}
