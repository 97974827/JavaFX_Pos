package kor.gls.pcsc;


/*===========================================================================================
 * 
 *  Copyright (C)   : Advanced Card System Ltd
 * 
 *  File            : Acr89UA2.java
 * 
 *  Description     : 
 * 
 *  Author          : Charlotte Morfe
 *  
 *  Date            : 
 * 
 *  Revision Trail  : [Author] / [Date if modification] / [Details of Modifications done] 
 * 
 * =========================================================================================*/

// 리더기 인증 API
public class Acr1281
{		
	//private PcscReader pcscReader = new PcscReader();
	private PcscReader _pcscReader;
	
	public PcscReader getPcscConnection() {return this._pcscReader;}
	public void setPcscConnection(PcscReader pcscConnection) {this._pcscReader = pcscConnection;}
	
	public Acr1281(PcscReader pcscReader)
	{
		this._pcscReader = pcscReader;
	}
	
	public enum CHIP_TYPE
	{
		UNKNOWN (0), MIFARE_1K (1), MIFARE_4K (2);		
		private int value;
		
		private CHIP_TYPE(int value)
		{
			this.value = value;
		}		
	};	
	
	public enum KEYTYPES
	{
		ACR1281_KEYTYPE_A (96), ACR1281_KEYTYPE_B (97);
		private int value;
		
		private KEYTYPES(int value)
		{
			this.value = value;		
		}
	};
	
	public boolean loadAuthKey(byte keyNumber,  byte[] key) throws Exception
        {
            Apdu apdu;

            if(keyNumber > 0x01)
            {
                throw new Exception("Key number is invalid");  
            }

            if (key.length != 6)
                throw new Exception("Invalid key length");

            apdu = new Apdu();
            apdu.setCommand(new byte[] { (byte)0xFF, (byte)0x82, 0x00, (byte)keyNumber, (byte)0x06 });
            apdu.setSendData(key);
            apdu.setLengthExpected((byte) 0x02);

            getPcscConnection().sendApduCommand(apdu);
       
            if (apdu.getSw()[0] != (byte)0x90)
                return false;

            return true;
    }

    public boolean authenticate(byte blockNum, KEYTYPES keyType, byte KeyNum) throws Exception
    {
        Apdu apdu;

        if (KeyNum > 0x20)
            throw new Exception("Key number is invalid");

        apdu = new Apdu();
        apdu.setCommand(new byte[] { (byte)0xFF, (byte)0x86, (byte)0x00, (byte)0x00, (byte)0x05 });
        apdu.setSendData(new byte[] { (byte)0x01, (byte)0x00, (byte)blockNum, (byte)keyType.value, (byte)KeyNum });
        
        getPcscConnection().sendApduCommand(apdu);

        if (apdu.getSw()[0] != (byte)0x90)
            return false;

        return true;
    }
    
    public CHIP_TYPE getChipType() throws Exception
    {           
        CHIP_TYPE cardType = CHIP_TYPE.UNKNOWN;

        byte[] ATR = getPcscConnection().getAtr();
        
		if (ATR[13] == 0x00 && ATR[14] == 0x01)
			cardType = CHIP_TYPE.MIFARE_1K;
		else if (ATR[13] == 0x00 && ATR[14] == 0x02)
			cardType = CHIP_TYPE.MIFARE_4K;		
		else
			cardType = CHIP_TYPE.UNKNOWN;
		
		return cardType;
    }
	
}
