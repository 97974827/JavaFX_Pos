package kor.gls.pcsc;

import java.security.NoSuchAlgorithmException;

import javax.naming.CommunicationException;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import org.apache.commons.codec.binary.StringUtils;

public class ReaderUtil {

		
	public static void main(String[] args) throws CardException, CommunicationException, NullPointerException, NoSuchAlgorithmException {
		Scard sCard = new Scard();
		 
		// ������ ��� ��������
		String[] readers = sCard.getReaderList();

		// �����⿡ �����ϱ�
		sCard.connect(readers[0]);
		System.out.println(readers[0]);
//		// ��� APDU �����ϱ�
//		CommandAPDU commandApdu = new CommandAPDU((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, StringUtils.unHex("A0000000030000"), 0, (byte)0x07);
//
//		// APDU �����ϱ�
//		ResponseAPDU responseApdu = sCard.apdu(commandApdu);



	}
}
