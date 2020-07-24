package kor.gls.pcsc;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class Scard {
	
	 private TerminalFactory terminalFactory;
	 private Card card = null;
	 private CardChannel cardChannel = null;
	 
	 public Scard() throws NullPointerException, NoSuchAlgorithmException {
		 terminalFactory = TerminalFactory.getInstance("PC/SC", null);
	 }
	 
	 public String[] getReaderList() throws CardException {
	 
		 List<CardTerminal> list = terminalFactory.terminals().list();
		 String[] readerList    = new String[list.size()];
		 
		 for(int i = 0;i<list.size();i++) {
			 readerList[i] = ((CardTerminal)list.get(i)).getName();
		 }
	
	 	return readerList;
	 }
	 
	 public boolean connect(String readerName) {
	 
		 try {
			 CardTerminal cardTerminal = terminalFactory.terminals().getTerminal(readerName);
			 card = cardTerminal.connect("*");
			 cardChannel = card.getBasicChannel();
			  
			 return true;
		 } catch(Exception e) {
			 e.printStackTrace();
			 return false;
		 }
	 }
	 
	 public ATR getATR() {
		 return card.getATR();
	 }
	 
	 public void disConnect() {
		 try { 
			 card.disconnect(true);
			  
			 cardChannel = null;
			 card   = null;
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public ResponseAPDU apdu(CommandAPDU cmd) {
		 
		 try {
			 return cardChannel.transmit(cmd);
		 } catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
	 }

	
}
