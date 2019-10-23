package chapter7;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class MydownloadServer2 {
    
    static Socket client = new Socket();
    
    public static void main(String[] args) throws Exception{
        ServerSocket soc = new ServerSocket(11111); 
        System.out.println("서버 시작");
        client = soc.accept();                       
        System.out.println("클라이언트 대기");
        
        InputStream in = null;                       
        FileOutputStream out = null;
        
        in = client.getInputStream();               
        DataInputStream din = new DataInputStream(in);  
        
        while(true){
            int data = din.readInt();          
            String filename = din.readUTF();         
            File file = new File(filename);            
            out = new FileOutputStream(file);         
 
            int datas = data;                            
            byte[] buffer = new byte[1024];     
            int len;                               
        
        
            for(;data>0;data--){                 
            	len = in.read(buffer);
            	out.write(buffer,0,len);
            }
        
            System.out.println("서버에서 클라이언트로 송신 완료");
            out.flush();
            out.close();
        }
    }
 
}

