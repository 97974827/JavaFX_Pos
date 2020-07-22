package kor.gls.model;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;


public class USBCharge {
	
	
	
	public USBCharge() {
	
	}
	
	public static void main(String[] args) {
		
		try {
			String test = "파일에 문자열을 저장하기";
			byte[] binary = test.getBytes();
			
			File file = new File("e:/test.txt");
			OutputStream out = new FileOutputStream(file);
			
			out.write(binary); // 파일은 바이너리로 구성 되어 있기때문에 r/w 시 byte 변환 
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
