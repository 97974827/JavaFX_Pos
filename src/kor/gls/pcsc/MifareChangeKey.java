package kor.gls.pcsc;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.annotation.processing.Messager;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import javax.swing.JScrollPane;

public class MifareChangeKey extends javax.swing.JFrame implements ReaderEvents.TransmitApduHandler, ActionListener, KeyListener, FocusListener {


	int retCode; 
	
	String tempString = "";
	private byte currentSector, currentSectorTrailer;
	
	PcscReader pcscReader;
	private Acr1281 acr1281;
	private MifareClassic mifareClassic;	
	
	private Acr1281.CHIP_TYPE currentChipType = Acr1281.CHIP_TYPE.UNKNOWN;
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JPanel contentPanel;
	private JTextField textFieldKeyStoreNumber;
	private JTextField textFieldKeyValueInput;
	private JTextField textFieldSectorNumber;
	private JTextField textFieldAuthenticationKeyStoreNumber;
	private JTextField textFieldCurrentSectorNumber;
	private JTextField textFieldSectorTrailerBlock;
	private JTextField textFieldKeyA;
	private JTextField textFieldAccessBits;
	private JTextField textFieldKeyB;
	
	private JRadioButton radioButtonKeyB;
	private JRadioButton radioButtonKeyA;
	
	private JButton buttonInitialize;
	private JButton buttonConnect;
	private JButton buttonLoadKeys;
	private JButton buttonAuthenticate;
	private JButton buttonRead;
	private JButton buttonUpdate;
	private JButton buttonClear;
	private JButton buttonReset;
	private JButton buttonQuit;
	
	private JComboBox comboBoxReader;

	private JPanel panel;
	private JPanel panel_2;
	
	private JLabel label;
	private JLabel label_1;
	private JTextArea textAreaMessage;
	
    
    /** Creates new form MifareChangeKey */
    public MifareChangeKey() {
    	setFont(new Font("Verdana", Font.PLAIN, 8));
    	this.setTitle("Mifare Change Key");
        //initComponents();        
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 829, 577);
                setResizable(false);
                
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
    	
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(10, 11, 396, 96);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblSelectReader = new JLabel("Select Reader");
		lblSelectReader.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblSelectReader.setBounds(10, 11, 85, 14);
		panel.add(lblSelectReader);
		
		comboBoxReader = new JComboBox();
		comboBoxReader.setFont(new Font("Verdana", Font.PLAIN, 10));
		comboBoxReader.setBounds(105, 7, 281, 22);
		panel.add(comboBoxReader);
		
		buttonInitialize = new JButton("Initialize");
		buttonInitialize.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonInitialize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialize();
			}
		});
		buttonInitialize.setBounds(268, 35, 118, 23);
		panel.add(buttonInitialize);
		
		buttonConnect = new JButton("Connect");
		buttonConnect.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		buttonConnect.setBounds(268, 62, 118, 23);
		panel.add(buttonConnect);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Store Authentication Keys to Device", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 118, 396, 120);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblKeyStoreNumber = new JLabel("Key Store Number (dec)");
		lblKeyStoreNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblKeyStoreNumber.setBounds(16, 30, 143, 14);
		panel_1.add(lblKeyStoreNumber);
		
		textFieldKeyStoreNumber = new JTextField();
		textFieldKeyStoreNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldKeyStoreNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldKeyStoreNumber.setBounds(198, 27, 48, 20);
		panel_1.add(textFieldKeyStoreNumber);
		textFieldKeyStoreNumber.setColumns(10);
		
		JLabel lblKeyValueInput = new JLabel("Key Value Input");
		lblKeyValueInput.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblKeyValueInput.setBounds(16, 55, 123, 14);
		panel_1.add(lblKeyValueInput);
		
		textFieldKeyValueInput = new JTextField(16);		
		textFieldKeyValueInput.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldKeyValueInput.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				keyValueInputFocusLost();
			}
		});
		textFieldKeyValueInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldKeyValueInput.setBounds(198, 52, 161, 20);
		panel_1.add(textFieldKeyValueInput);
		textFieldKeyValueInput.setColumns(10);
		
		buttonLoadKeys = new JButton("Load Key");
		buttonLoadKeys.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonLoadKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadKeys();
			}
		});
		buttonLoadKeys.setBounds(268, 80, 118, 23);
		panel_1.add(buttonLoadKeys);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Authentication Function", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 245, 396, 133);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);		

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(16, 23, 90, 76);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Key Type:");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblNewLabel.setBounds(10, 11, 79, 14);
		panel_4.add(lblNewLabel);
		
		radioButtonKeyA = new JRadioButton("Key A");
		radioButtonKeyA.setFont(new Font("Verdana", Font.PLAIN, 10));
		radioButtonKeyA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButtonKeyA.setSelected(true);
	    		radioButtonKeyB.setSelected(false);
			}
		});
		radioButtonKeyA.setSelected(true);
		radioButtonKeyA.setBounds(20, 24, 60, 23);
		panel_4.add(radioButtonKeyA);
		
		radioButtonKeyB = new JRadioButton("Key B");
		radioButtonKeyB.setFont(new Font("Verdana", Font.PLAIN, 10));
		radioButtonKeyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButtonKeyA.setSelected(false);
	    		radioButtonKeyB.setSelected(true);
			}
		});
		radioButtonKeyB.setBounds(20, 46, 60, 23);
		panel_4.add(radioButtonKeyB);
		
		JLabel lblSectorNumberdec = new JLabel("Sector Number (dec)");
		lblSectorNumberdec.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblSectorNumberdec.setBounds(111, 51, 141, 14);
		panel_2.add(lblSectorNumberdec);
		
		textFieldSectorNumber = new JTextField();
		textFieldSectorNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldSectorNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldSectorNumber.setBounds(252, 48, 62, 20);
                textFieldSectorNumber.setDocument(Helper.intFilter());
		panel_2.add(textFieldSectorNumber);
		textFieldSectorNumber.setColumns(10);
		
		label = new JLabel("00 - 31");
		label.setFont(new Font("Verdana", Font.PLAIN, 10));
		label.setBounds(324, 51, 46, 14);
		panel_2.add(label);
		
		JLabel lblKeyStoreNumber_1 = new JLabel("Key Store Number (dec)");
		lblKeyStoreNumber_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblKeyStoreNumber_1.setBounds(111, 73, 151, 14); //73
		panel_2.add(lblKeyStoreNumber_1);
		
		textFieldAuthenticationKeyStoreNumber = new JTextField();
		textFieldAuthenticationKeyStoreNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldAuthenticationKeyStoreNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldAuthenticationKeyStoreNumber.setBounds(252, 70, 62, 20);
		panel_2.add(textFieldAuthenticationKeyStoreNumber);
		textFieldAuthenticationKeyStoreNumber.setColumns(10);
                textFieldAuthenticationKeyStoreNumber.setDocument(Helper.intFilter());
		
		label_1 = new JLabel("00 - 01");
		label_1.setFont(new Font("Verdana", Font.PLAIN, 10));
		label_1.setBounds(324, 73, 46, 14);
		panel_2.add(label_1);
		
		buttonAuthenticate = new JButton("Authenticate");
		buttonAuthenticate.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonAuthenticate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authenticate();
			}
		});
		buttonAuthenticate.setBounds(273, 100, 113, 23);
		panel_2.add(buttonAuthenticate);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(20, 23, 168, 76);
		panel_2.add(panel_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Change Sector Keys", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(10, panel_2.getY() + panel_2.getHeight() + 10, 396, 155);
		contentPanel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblCurrentSector = new JLabel("Current Sector");
		lblCurrentSector.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblCurrentSector.setBounds(20, 28, 98, 14);
		panel_5.add(lblCurrentSector);
		
		textFieldCurrentSectorNumber = new JTextField();
		textFieldCurrentSectorNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldCurrentSectorNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldCurrentSectorNumber.setBounds(128, 25, 47, 20);
		panel_5.add(textFieldCurrentSectorNumber);
		textFieldCurrentSectorNumber.setColumns(10);
		
		JLabel lblSectorTrainlerBlock = new JLabel("Sector Trailer Block");
		lblSectorTrainlerBlock.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblSectorTrainlerBlock.setBounds(185, 28, 121, 14);
		panel_5.add(lblSectorTrainlerBlock);
		
		textFieldSectorTrailerBlock = new JTextField();
		textFieldSectorTrailerBlock.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldSectorTrailerBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldSectorTrailerBlock.setBounds(310, 25, 55, 20);
		panel_5.add(textFieldSectorTrailerBlock);
		textFieldSectorTrailerBlock.setColumns(10);
		
		textFieldKeyA = new JTextField(16);
		textFieldKeyA.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldKeyA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				keyAFocusLost();
			}
		});
		textFieldKeyA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldKeyA.setBounds(10, 71, 121, 20);
		panel_5.add(textFieldKeyA);
		textFieldKeyA.setColumns(10);
		
		textFieldAccessBits = new JTextField(12);
		textFieldAccessBits.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldAccessBits.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				accessBitsFocusLost();
			}
		});
		textFieldAccessBits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldAccessBits.setBounds(141, 71, 110, 20);
		panel_5.add(textFieldAccessBits);
		textFieldAccessBits.setColumns(10);
		
		textFieldKeyB = new JTextField(16);
		textFieldKeyB.setFont(new Font("Verdana", Font.PLAIN, 10));
		textFieldKeyB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				keyBFocusLost();
			}
			
		});
		textFieldKeyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textFieldKeyB.setBounds(261, 71, 121, 20);
		panel_5.add(textFieldKeyB);
		textFieldKeyB.setColumns(10);
		
		JLabel lblKeyA = new JLabel("Key A");
		lblKeyA.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblKeyA.setBounds(53, 95, 46, 14);
		panel_5.add(lblKeyA);
		
		JLabel lblAccessBits = new JLabel("Access Bits");
		lblAccessBits.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblAccessBits.setBounds(171, 95, 80, 14);
		panel_5.add(lblAccessBits);
		
		JLabel lblKeyB = new JLabel("Key B");
		lblKeyB.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblKeyB.setBounds(310, 95, 46, 14);
		panel_5.add(lblKeyB);
		
		buttonRead = new JButton("Read");
		buttonRead.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				read();
			}
		});
		buttonRead.setBounds(63, 120, 128, 23);
		panel_5.add(buttonRead);
		
		buttonUpdate = new JButton("Update");
		buttonUpdate.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		buttonUpdate.setBounds(199, 120, 128, 23);
		panel_5.add(buttonUpdate);
		
		JLabel lblApduLogs = new JLabel("APDU Logs");
		lblApduLogs.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblApduLogs.setBounds(423, 11, 90, 14);
		contentPanel.add(lblApduLogs);
		
		buttonClear = new JButton("Clear");
		buttonClear.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		buttonClear.setBounds(426, 508, 117, 23);
		contentPanel.add(buttonClear);
		
		buttonReset = new JButton("Reset");
		buttonReset.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		buttonReset.setBounds(553, 508, 117, 23);
		contentPanel.add(buttonReset);
		
		buttonQuit = new JButton("Quit");
		buttonQuit.setFont(new Font("Verdana", Font.PLAIN, 10));
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		buttonQuit.setBounds(680, 508, 117, 23);
		contentPanel.add(buttonQuit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(416, 36, 384, 462);
		contentPanel.add(scrollPane);
		
		textAreaMessage = new JTextArea();
		textAreaMessage.setFont(new Font("Verdana", Font.PLAIN, 10));
		scrollPane.setViewportView(textAreaMessage);
		
        InitMenu();        

        pcscReader = new PcscReader();
		// Instantiate an event handler object 
        pcscReader.setEventHandler(new ReaderEvents());
		
		// Register the event handler implementation of this class
        pcscReader.getEventHandler().addEventListener(this);
        
        acr1281 = new Acr1281(pcscReader);
    }

    private void initialize()
    {
    	String[] readerList = null;
		
	    try
	    {
		    readerList = acr1281.getPcscConnection().listTerminals();					
			if (readerList.length == 0)
			{
				addMsgToLog("No PC/SC reader detected");
				return;
			}
			
			comboBoxReader.removeAllItems();
				
			for (int i = 0; i < readerList.length; i++)
			{
				if (!readerList.equals(""))	
					comboBoxReader.addItem(readerList[i]);
				else
					break;
			}
			
			buttonConnect.setEnabled(true);
			buttonInitialize.setEnabled(false);
			
			comboBoxReader.setEnabled(true);
		    
		    buttonReset.setEnabled(true);
	    }
	    catch (Exception ex)
	    {
	    	addMsgToLog(ex.getMessage().toString());
		JOptionPane.showMessageDialog(null,"No Reader Available");
	    }
    }
    
    private void connect()
    {
    	try
	{
            if(acr1281.getPcscConnection().isConnectionActive())	
                acr1281.getPcscConnection().disconnect();
			
            String rdrcon = (String)comboBoxReader.getSelectedItem();
			
            acr1281.getPcscConnection().connect(rdrcon, "*");
            mifareClassic = new MifareClassic(acr1281.getPcscConnection());
			
            addMsgToLog("Successful connection to " + rdrcon);
			
            textFieldKeyValueInput.setEnabled(true);		    
            textFieldKeyStoreNumber.setEnabled(true);		    
            textFieldAuthenticationKeyStoreNumber.setEnabled(true);	        
            textFieldSectorNumber.setEnabled(true);
	       	        
	    radioButtonKeyA.setEnabled(true);
	    radioButtonKeyB.setEnabled(true);	        
	        
	    buttonAuthenticate.setEnabled(true);
	    buttonLoadKeys.setEnabled(true);
	
            currentChipType = acr1281.getChipType();
			
            addMsgToLog("Chip Type: " + currentChipType.toString());
            addMsgToLog("");
			
            if(currentChipType != Acr1281.CHIP_TYPE.MIFARE_1K && currentChipType != Acr1281.CHIP_TYPE.MIFARE_4K)
            {
		JOptionPane.showMessageDialog(this, "Card is not supported.\r\nPlease present Mifare Classic card");
		return;
            }
			
            if(currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
		label.setText("00 - 15");
            else
		label = new JLabel("00 - 39");
	}
	catch (Exception ex)
	{
            addMsgToLog(ex.getMessage().toString());
	}
    }
    
    private void loadKeys()
    {
    	byte[] key = new byte[6];
 		byte keyNumber = 0x20;
		
		String[] strKeys;
		try
		{
                    if(textFieldKeyStoreNumber.getText().equals(""))    				
                    {
			JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 to 01");
			textFieldKeyStoreNumber.setFocusable(true);
			return;
                    }
                    else
                    {
			if(Integer.parseInt(textFieldKeyStoreNumber.getText()) > 1)
			{
                            JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 to 01");
                            textFieldKeyStoreNumber.setFocusable(true);
                            return;
			}
                    }
				
                    keyNumber = (byte) ((Integer)Integer.parseInt(textFieldKeyStoreNumber.getText(), 16)).byteValue();						
			
                    if(textFieldKeyStoreNumber.getText().equals(""))    				
                    {
			JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 to 31");
			textFieldKeyStoreNumber.setFocusable(true);
			return;
                    }
                    else
                    {
			if(Integer.parseInt(textFieldKeyStoreNumber.getText()) > 31)
			{
                            JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 to 31");
                            textFieldKeyStoreNumber.setFocusable(true);
                            return;
			}
                    }
				
                    keyNumber = (byte) ((Integer)Integer.parseInt(textFieldKeyStoreNumber.getText(), 16)).byteValue();
							
                    if(textFieldKeyValueInput.getText().trim().equals(""))
                    {	
                        JOptionPane.showMessageDialog(this, "Please key-in 6 bytes key");
			textFieldKeyValueInput.setFocusable(true);
			return;    			
                    }
			
                    if(textFieldKeyValueInput.getText().replaceAll(" ", "").length() != 12)
                    {
			JOptionPane.showMessageDialog(this, "Please key-in 6 bytes key");
			textFieldKeyValueInput.setFocusable(true);
			return;
                    }
			
                    // key should be 6 bytes long
                    strKeys = textFieldKeyValueInput.getText().trim().split(" ");
    		
                    for(int i = 0; i < strKeys.length; i++)
                    {
    			key[i] = (byte) ((Integer)Integer.parseInt(strKeys[i], 16)).byteValue();
                    }    
    		
                    if(acr1281.loadAuthKey(keyNumber, key) == false)    		
    			addMsgToLog("Load Key Failed!\r\n");
                    else
    			addMsgToLog("Load Key Success!\r\n");
                    }
		catch(Exception ex)
		{
			if(ex.getMessage() != null)
				addMsgToLog(ex.getMessage() + "\r\n");
		}    	
    }
    
    private void authenticate()
    {
		byte sectorNumber = 0x00;
		byte keyNumber = 0x00;
		Acr1281.KEYTYPES keyType = Acr1281.KEYTYPES.ACR1281_KEYTYPE_A;
				
		try
		{			
			if(textFieldSectorNumber.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please key-in valid sector number");
				textFieldKeyStoreNumber.setFocusable(true);
				return;
			}
			
			String temp = textFieldSectorNumber.getText();
			sectorNumber = Byte.valueOf(temp);			
			
			if(textFieldAuthenticationKeyStoreNumber.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 - 01");
				textFieldAuthenticationKeyStoreNumber.setFocusable(true);
				return;
			}
			else
			{
				if(Integer.parseInt(textFieldAuthenticationKeyStoreNumber.getText()) > 1)
				{
					JOptionPane.showMessageDialog(this, "Please key-in key store number from 00 - 01");
					textFieldAuthenticationKeyStoreNumber.setFocusable(true);
					return;
				}
			}
    			
			keyNumber = (byte)((Integer)Integer.parseInt(textFieldAuthenticationKeyStoreNumber.getText(), 16)).byteValue();
			
			if(radioButtonKeyB.isSelected())
				keyType = Acr1281.KEYTYPES.ACR1281_KEYTYPE_B;
			
			if(currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
			{
				if(sectorNumber > 39)
				{
					JOptionPane.showMessageDialog(this, "Card does not have sector " + sectorNumber);
					return;
				}
				
				currentSector = sectorNumber;
				
				// Mifare 4K is organized in 32 sectors with 4 blocks
				// and in 8 sectors with 16 blocks
				if(currentSector <= 31)
				{
					currentSectorTrailer = (byte)((currentSector * 4) + 3);    					
				}
				else
				{
					// 127 is the physical address of the last block (sector trailer) of the 32nd sector
					currentSectorTrailer = 127;
					
					// succeeding sector contains 16 blocks
					currentSectorTrailer += (byte)(((currentSector - 32) * 16) + 16);
				}
			}
			else
			{
				if(sectorNumber > 15)
				{
					JOptionPane.showMessageDialog(this, "Card does not have sector " + sectorNumber);
					return;
				}
				
				currentSector = sectorNumber;
				currentSectorTrailer = (byte) ((currentSector * 4) + 3);
			}
			
			if(acr1281.authenticate(currentSectorTrailer, keyType, keyNumber) == false)
				addMsgToLog("Authenticate Failed!\r\n");
			else
			{	
				addMsgToLog("Authenticate Success!\r\n");
				
				textFieldSectorTrailerBlock.setText(Byte.toString(currentSectorTrailer));
				textFieldCurrentSectorNumber.setText(Byte.toString(currentSector));
				
				textFieldAccessBits.setEnabled(true);
				textFieldKeyA.setEnabled(true);
				textFieldKeyB.setEnabled(true);

				buttonRead.setEnabled(true);
				buttonUpdate.setEnabled(true);
			}
		}
		catch (Exception ex)
        {
			if(ex.getMessage() != null)
				addMsgToLog(ex.getMessage() + "\r\n");
        }
    }
    
    private void read()
    {
    	byte[] sectorTrailer;
		
		try
		{
			sectorTrailer = mifareClassic.readBinaryBlock(currentSectorTrailer, (byte)0x10);
			
			if(sectorTrailer == null || sectorTrailer.length == 0)
				addMsgToLog("Read Failed!\r\n");
			else
			{	
				// Mifare does not allow you to read the actual key A.
				// We will leave this field as blank.
				textFieldKeyA.setText("");
				textFieldAccessBits.setText(byteArrayToString(sectorTrailer, 6, 4, true));
				textFieldKeyB.setText(byteArrayToString(sectorTrailer, 10, 6, true));				
				
				JOptionPane.showMessageDialog(this, "NOTE: Mifare does not allow user to read the actual key A. 'Key A' field will be set to empty.");
				addMsgToLog("Read Success!\r\n");				
			}
		}
		catch (Exception ex) 
		{
			if(ex.getMessage() != null)
				addMsgToLog(ex.getMessage() + "\r\n");
		}
    }
    
    private void update()
    {
    	byte[] keyA, accessBits, keyB;
    	byte[] newSectorTrailer;

    	try
    	{
    		keyA = Helper.stringToByteArray(textFieldKeyA.getText().trim().replaceAll(" ", ""));
    		accessBits = Helper.stringToByteArray(textFieldAccessBits.getText().replaceAll(" ", ""));
    		keyB = Helper.stringToByteArray(textFieldKeyB.getText().trim().replaceAll(" ", ""));
    		
    		if(keyA == null || keyA.length != 6)
    		{
    			JOptionPane.showMessageDialog(this, "Please key-in 6 bytes key A");
    			textFieldKeyA.setFocusable(true);
    			return;
    		}
			 
			 if(keyB == null || keyB.length != 6)
			 {
				 JOptionPane.showMessageDialog(this, "Please key-in 6 bytes key B");
				 textFieldKeyB.setFocusable(true);
				 return;
			 }
			 
			 if(accessBits == null || accessBits.length != 4)
			 {
				 JOptionPane.showMessageDialog(this, "Please key-in 4 bytes access bits");
				 textFieldAccessBits.setFocusable(true);
				 return;
			 }
			 
			 if(!Helper.byteArrayIsEqual(accessBits, new byte[] {(byte)0xFF, (byte)0x07, (byte)0x80, (byte)0x69}, 4))
			 {
				 if(JOptionPane.showConfirmDialog(this, "IMPORTANT: Check Access Bits!\r\n\r\n" +
                        "Please make sure that the access bits are valid you may refer to Mifare Classic manual for more information.\r\n" +
                        "If the access bits are invalid the sector will PERMANENTLY lock it self.\r\n\r\nContinue?") != 0)
				 {
					 return;
				 }

			 }
			 
			 JOptionPane.showMessageDialog(this, "Please take note of the following: \r\n\r\n" + 
					 							 "Sector: " + Byte.toString(currentSector) + 
					 							 "\r\nKey A : " + Helper.byteAsString(keyA, true) +
					 							 "\r\nKey B : " + Helper.byteAsString(keyB, true) +
					 							 "\r\nAccess Bits : " + Helper.byteAsString(accessBits, true));
			 
			 newSectorTrailer = new byte[16];
			 
			 int i;
			 
			 // Append Key A
			 for(i = 0; i < 6; i++)
				 newSectorTrailer[i] = keyA[i];
			 
			 // Append Access Bits
			 for(i = 0; i < 4; i++)
				 newSectorTrailer[i + 6] = accessBits[i];
			 
			 // Append key B
			 for(i = 0; i < 6; i++)
				 newSectorTrailer[i + 10] = keyB[i];
			 
			 mifareClassic.updateBinaryBlock(currentSectorTrailer, newSectorTrailer, (byte)0x10);
			 
			 addMsgToLog("Sector     : " + Byte.toString(currentSector));
			 addMsgToLog("New Key A  : " + Helper.byteAsString(keyA, true));
			 addMsgToLog("New Key B  : " + Helper.byteAsString(keyB, true));
			 addMsgToLog("Access Bits: " + Helper.byteAsString(accessBits, true));
			 addMsgToLog("\r\n");	            
			 addMsgToLog("Update Success!\r\n");		
			 
		 }
		 catch (Exception ex)
		 {
			 if(ex.getMessage() != null)
				 addMsgToLog(ex.getMessage() + "\r\n");
		 }	
    }
    
    private void reset()
    {    	
		try			
		{
			//disconnect
			if (acr1281.getPcscConnection().isConnectionActive())
				acr1281.getPcscConnection().disconnect();
			
			textAreaMessage.setText("");
			buttonInitialize.setEnabled(true);
			buttonConnect.setEnabled(false);
			InitMenu();
			comboBoxReader.removeAllItems();
			comboBoxReader.addItem("Please select reader                   ");
		}
		catch (Exception ex)
		{
			addMsgToLog(ex.getMessage().toString());
		}
    }
    
    private void quit()
    {    	
		this.dispose();
    }
    
    private void clear()
    {
    	textAreaMessage.setText("");
    }
    
    public void keyReleased(KeyEvent ke) {

	}
	
	public void keyPressed(KeyEvent ke) {
  		//restrict paste actions
		if (ke.getKeyCode() == KeyEvent.VK_V ) 
			ke.setKeyCode(KeyEvent.VK_UNDO );
		
  	}
	
	public void keyTyped(KeyEvent ke) 
	{  		

	}
	
	public void focusGained(FocusEvent e) 
	{
				
	}

	private void accessBitsFocusLost()
	{
		String tmpStr = "", tmpStr2 = "";
		
		tmpStr = "";
		tmpStr2 = "";
		
		tmpStr = textFieldAccessBits.getText().replaceAll(" ", "");
		
		for(int i = 0; i < tmpStr.length() / 2; i++)
		{
			tmpStr2 = tmpStr2 + " " + tmpStr.substring(i + i, i + i + 2);
		}
		
		textFieldAccessBits.setText(tmpStr2);
	}
	
	private void keyAFocusLost()
	{
		String tmpStr = "", tmpStr2 = "";
		
		tmpStr = "";
		tmpStr2 = "";
		
		tmpStr = textFieldKeyA.getText().replaceAll(" ", "");
		
		for(int i = 0; i < tmpStr.length() / 2; i++)
		{
			tmpStr2 = tmpStr2 + " " + tmpStr.substring(i + i, i + i + 2);
		}
		
		textFieldKeyA.setText(tmpStr2);
	}
	
	private void keyBFocusLost()
	{
		String tmpStr = "", tmpStr2 = "";
		
		tmpStr = "";
		tmpStr2 = "";
		
		tmpStr = textFieldKeyB.getText().replaceAll(" ", "");
		
		for(int i = 0; i < tmpStr.length() / 2; i++)
		{
			tmpStr2 = tmpStr2 + " " + tmpStr.substring(i + i, i + i + 2);
		}
		
		textFieldKeyB.setText(tmpStr2);
	}
	
	private void keyValueInputFocusLost()
	{
		String tmpStr = "", tmpStr2 = "";
		
		tmpStr = "";
		tmpStr2 = "";
		
		tmpStr = textFieldKeyValueInput.getText().replaceAll(" ", "");
		
		for(int i = 0; i < tmpStr.length() / 2; i++)
		{
			tmpStr2 = tmpStr2 + " " + tmpStr.substring(i + i, i + i + 2);
		}
		
		textFieldKeyValueInput.setText(tmpStr2);
	}
	
	String byteArrayToString(byte[] b, int startIndx, int len, boolean spaceInBetween)
	{
		byte[] newByte;
		
		if(b.length < startIndx + len)		
			b = new byte[startIndx + len];
		
		newByte = new byte[len];
		
		for(int i = 0; i < len; i++, startIndx++)
			newByte[i] = b[startIndx];
		
		return byteArrayToString(newByte, spaceInBetween);
	}
	
	String byteArrayToString(byte[] tmpBytes, boolean spaceInBetween)
	{
		String tmpStr = "", tmpStr2 = "";
		
		if(tmpBytes == null)
			return "";
		
		for(int i = 0; i < tmpBytes.length; i++)
		{
			tmpStr = Integer.toHexString(((Byte)tmpBytes[i]).intValue() & 0xFF).toUpperCase();
			
			//For single character hex
			if (tmpStr.length() == 1) 
				tmpStr = "0" + tmpStr;
			
			tmpStr2 += " " + tmpStr;  
		}
		
		return tmpStr2;
	}
    
    private void InitMenu()
    {        
        buttonReset.setEnabled(false);
        buttonLoadKeys.setEnabled(false);
        buttonAuthenticate.setEnabled(false);
        buttonRead.setEnabled(false);
        buttonUpdate.setEnabled(false);        
        
        textFieldAccessBits.setText("");
        textFieldAuthenticationKeyStoreNumber.setText("");
        textFieldCurrentSectorNumber.setText("");
        textFieldKeyA.setText("");
        textFieldKeyB.setText("");
        textFieldKeyStoreNumber.setText("");
        textFieldKeyValueInput.setText("");
        textFieldSectorNumber.setText("");
        textFieldSectorTrailerBlock.setText("");
        
        textFieldKeyValueInput.setEnabled(false);
        textFieldCurrentSectorNumber.setEnabled(false);
        textFieldSectorTrailerBlock.setEnabled(false);
        textFieldKeyA.setEnabled(false);
        textFieldAccessBits.setEnabled(false);
        textFieldKeyB.setEnabled(false);
        textFieldAuthenticationKeyStoreNumber.setEnabled(false);
        textFieldKeyStoreNumber.setEnabled(false);
        textFieldSectorNumber.setEnabled(false);
        
        radioButtonKeyA.setEnabled(false);
        radioButtonKeyB.setEnabled(false);

        addMsgToLog("Program Ready");
    }

	public void addMsgToLog(String prefixStr, byte[] buff, String postfixStr, int buffLen)
	{
		String tmpStr = "";

        if (buff.length < buffLen)
            return;

        tmpStr = null;

        //Convert each byte from buff to its string representation.
        for (int i = 0; i < buffLen; i++)
            tmpStr += String.format("{0:X2}", buff[i]) + " ";

        addMsgToLog(prefixStr + tmpStr + postfixStr);
	}
	
	void addTitleToLog(String title)
	{
		textAreaMessage.setSelectedTextColor(Color.black);
		textAreaMessage.append("\r\n" + title + "\r\n");
	}
	
	void addMsgToLog(String msg)
	{
		textAreaMessage.append(msg + "\r\n");		
	}	
	
	public void onSendCommand(ReaderEvents.TransmitApduEventArg event) 
	{
		addMsgToLog("<< " + event.getAsString(true));		
	}

	public void onReceiveCommand(ReaderEvents.TransmitApduEventArg event) 
	{
		addMsgToLog(">> " + event.getAsString(true));		
	}
	
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MifareChangeKey().setVisible(true);
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
