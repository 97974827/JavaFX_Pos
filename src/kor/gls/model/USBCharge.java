package kor.gls.model;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;


public class USBCharge {
	
	
	
	public USBCharge() {
	
	}
	
	public static void main(String[] args) {
		
		try {
			String test = "���Ͽ� ���ڿ��� �����ϱ�";
			byte[] binary = test.getBytes();
			
			File file = new File("e:/test.txt");
			OutputStream out = new FileOutputStream(file);
			
			out.write(binary); // ������ ���̳ʸ��� ���� �Ǿ� �ֱ⶧���� r/w �� byte ��ȯ 
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
