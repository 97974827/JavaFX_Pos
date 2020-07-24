/*
  Copyright(C):      Advanced Card Systems Ltd

  File:              mifareProg.java

  Description:       This sample program outlines the steps on how to
                     transact with Mifare 1K/4K cards using ACR128

  Author:            M.J.E.C. Castillo

  Date:              July 7, 2008

  Revision Trail:   (Date/Author/Description)

======================================================================*/
package kor.gls.pcsc;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MifareCardProgramming extends JFrame implements ReaderEvents.TransmitApduHandler,ActionListener, KeyListener{

	//JPCSC Variables
	int retCode;
	boolean connActive; 
	static String VALIDCHARS = "0123456789";
	static String VALIDCHARSHEX = "ABCDEFabcdef0123456789";
        private Acr1281.CHIP_TYPE currentChipType = Acr1281.CHIP_TYPE.UNKNOWN;
	
	//All variables that requires pass-by-reference calls to functions are
	//declared as 'Array of int' with length 1
	//Java does not process pass-by-ref to int-type variables, thus Array of int was used.
	int [] ATRLen = new int[1]; 
	int [] hContext = new int[1]; 
	int [] cchReaders = new int[1];
	int [] hCard = new int[1];
	int [] PrefProtocols = new int[1]; 		
	int [] RecvLen = new int[1];
	int SendLen = 0;
	int [] nBytesRet =new int[1];
	byte [] SendBuff = new byte[262];
	byte [] RecvBuff = new byte[262];
	byte [] szReaders = new byte[1024];
	
	//GUI Variables
    private JPanel authPanel, binBlkPanel, msgPanel, readerPanel, storeAuthPanel, valBlkPanel;
    private JButton buttonBinRead, buttonClear, buttonConnect, buttonInitialize, buttonLoadKey, buttonReset, buttonValDec, buttonValInc;
    private JButton buttonValRead, buttonValRes, buttonValStore, buttonAuthenticate, buttonBinUpdate, buttonQuit;
    private ButtonGroup bdKeyType, bgKeySource, bgStoreKeys;
    private JComboBox comboboxReader;
    private JLabel jLabel1, jLabel10, jLabel11, jLabel12, jLabel2, jLabel3, jLabel4;
    private JLabel jLabel6, jLabel7, jLabel8, jLabel9, lblReader;
    private JPanel keyTypePanel, keyValPanel;
    private JTextArea mMsg;
    private JRadioButton radiobuttonKeyA, radiobuttonKeyB;
    private JScrollPane scrlPaneMsg;
    private JTextField textboxBinBlk, textboxBinData, textboxBinLen, textboxBlockNumber, textboxKey1, textboxKey2, textboxKey3, textboxKey4, textboxKey5;
    private JTextField textboxKey6, textboxKeyAdd;
    private JTextField textboxMemAdd, textboxValAmount, textboxValBlk, textboxValSrc, textboxValTar;
	
 
    private JLabel lblApduLogs;
    private PcscReader pcscReader;
    private MifareClassic mifareClassic;
    private Acr1281 acr1281;
    

    Dimension dim =Toolkit.getDefaultToolkit().getScreenSize();
    
    public MifareCardProgramming() {
    	getContentPane().setFont(new Font("Verdana", Font.PLAIN, 10));
    	setFont(new Font("Verdana", Font.PLAIN, 8));
    	setResizable(false);
    	//this.setTitle("Mifare Card Programming");
        //initComponents();
        //initMenu();
    	
    	this.setTitle("Mifare Card Programming");
    	initComponents();
    	initMenu();
    	
    	pcscReader=new PcscReader(); //instantiate an event handler object
    	pcscReader.setEventHandler(new ReaderEvents());
    	pcscReader.getEventHandler().addEventListener(this);
    	acr1281= new Acr1281(pcscReader);
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

    	setSize(800,560);
        bgStoreKeys = new ButtonGroup();
        bgKeySource = new ButtonGroup();
        bdKeyType = new ButtonGroup();
        readerPanel = new JPanel();
        readerPanel.setBounds(10, 11, 356, 102);
        lblReader = new JLabel();
        lblReader.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonInitialize = new JButton();
        buttonInitialize.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonConnect = new JButton();
        buttonConnect.setFont(new Font("Verdana", Font.PLAIN, 10));
        storeAuthPanel = new JPanel();
        storeAuthPanel.setBounds(10, 113, 362, 120);
        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonLoadKey = new JButton();
        buttonLoadKey.setFont(new Font("Verdana", Font.PLAIN, 10));
        keyValPanel = new JPanel();
        textboxKey1 = new JTextField();
        textboxKey1.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKey2 = new JTextField();
        textboxKey2.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKey3 = new JTextField();
        textboxKey3.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKey4 = new JTextField();
        textboxKey4.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKey5 = new JTextField();
        textboxKey5.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKey6 = new JTextField();
        textboxKey6.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxMemAdd = new JTextField();
        textboxMemAdd.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxMemAdd.setEnabled(false);
        authPanel = new JPanel();
        authPanel.setBounds(10, storeAuthPanel.getY() + storeAuthPanel.
                getHeight() + 5, 362, 137);
        keyTypePanel = new JPanel();
        keyTypePanel.setBounds(16, 22, 92, 75);
        radiobuttonKeyA = new JRadioButton();
        radiobuttonKeyA.setFont(new Font("Verdana", Font.PLAIN, 10));
        radiobuttonKeyA.setSelected(true);
        radiobuttonKeyB = new JRadioButton();
        radiobuttonKeyB.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonAuthenticate = new JButton();
        buttonAuthenticate.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonAuthenticate.setBounds(232, 99, 114, 23);
        
        jLabel4 = new JLabel();
        jLabel4.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel4.setBounds(148, 70, 112, 14);
        textboxKeyAdd = new JTextField();
        textboxKeyAdd.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxKeyAdd.setEnabled(false);
        textboxKeyAdd.setBounds(253, 67, 29, 20);
        
        jLabel3 = new JLabel();
        jLabel3.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel3.setBounds(148, 45, 91, 14);        
        textboxBlockNumber = new JTextField();
        textboxBlockNumber.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxBlockNumber.setBounds(253, 42, 29, 20); //(138, 127, 29, 20);
        
        binBlkPanel = new JPanel();
        binBlkPanel.setBounds(10, authPanel.getY() + authPanel.getHeight() + 5, 362, 137);
        jLabel6 = new JLabel();
        jLabel6.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel7 = new JLabel();
        jLabel7.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel8 = new JLabel();
        jLabel8.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxBinBlk = new JTextField();
        textboxBinBlk.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxBinLen = new JTextField();
        textboxBinLen.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxBinData = new JTextField();
        textboxBinData.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonBinRead = new JButton();
        buttonBinRead.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonBinUpdate = new JButton();
        buttonBinUpdate.setFont(new Font("Verdana", Font.PLAIN, 10));
        valBlkPanel = new JPanel();
        valBlkPanel.setBounds(388, 11, 383, 187);
        jLabel9 = new JLabel();
        jLabel9.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel9.setBounds(16, 24, 83, 14);
        textboxValAmount = new JTextField();
        textboxValAmount.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxValAmount.setBounds(109, 21, 104, 20);
        jLabel10 = new JLabel();
        jLabel10.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel10.setBounds(16, 53, 67, 14);
        textboxValBlk = new JTextField();
        textboxValBlk.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxValBlk.setBounds(109, 50, 32, 20);
        jLabel11 = new JLabel();
        jLabel11.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel11.setBounds(16, 82, 83, 14);
        textboxValSrc = new JTextField();
        textboxValSrc.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxValSrc.setBounds(109, 79, 32, 20);
        jLabel12 = new JLabel();
        jLabel12.setFont(new Font("Verdana", Font.PLAIN, 10));
        jLabel12.setBounds(16, 111, 83, 14);
        textboxValTar = new JTextField();
        textboxValTar.setFont(new Font("Verdana", Font.PLAIN, 10));
        textboxValTar.setBounds(109, 108, 32, 20);
        buttonValStore = new JButton();
        buttonValStore.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonValStore.setBounds(248, 20, 120, 23);
        buttonValInc = new JButton();
        buttonValInc.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonValInc.setBounds(248, 49, 120, 23);
        buttonValDec = new JButton();
        buttonValDec.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonValDec.setBounds(248, 77, 120, 23);
        buttonValRead = new JButton();
        buttonValRead.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonValRead.setBounds(248, 107, 120, 23);
        buttonValRes = new JButton();
        buttonValRes.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonValRes.setBounds(248, 136, 120, 23);
        msgPanel = new JPanel();
        msgPanel.setBounds(376, 201, 406, 334);
        scrlPaneMsg = new JScrollPane();
        buttonClear = new JButton();
        buttonClear.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonReset = new JButton();
        buttonReset.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonQuit = new JButton();
        buttonQuit.setFont(new Font("Verdana", Font.PLAIN, 10));
        
        textboxBinBlk.setDocument(Helper.intFilter());
        textboxBinLen.setDocument(Helper.intFilter());
        textboxValAmount.setDocument(Helper.intFilter());
        textboxValBlk.setDocument(Helper.intFilter());
        textboxValSrc.setDocument(Helper.intFilter());
        textboxValTar.setDocument(Helper.intFilter());
        
        lblReader.setText("Select Reader");

		comboboxReader = new JComboBox();
		comboboxReader.setFont(new Font("Verdana", Font.PLAIN, 10));
		
        buttonInitialize.setText("Initialize");
        buttonConnect.setText("Connect");

        GroupLayout gl_readerPanel = new GroupLayout(readerPanel);
        gl_readerPanel.setHorizontalGroup(
        	gl_readerPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_readerPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_readerPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_readerPanel.createSequentialGroup()
        					.addComponent(lblReader)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(comboboxReader, 0, 207, Short.MAX_VALUE))
        				.addGroup(Alignment.TRAILING, gl_readerPanel.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(buttonInitialize, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(buttonConnect, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        gl_readerPanel.setVerticalGroup(
        	gl_readerPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_readerPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_readerPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblReader)
        				.addComponent(comboboxReader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(buttonInitialize)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(buttonConnect)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        readerPanel.setLayout(gl_readerPanel);

        storeAuthPanel.setBorder(BorderFactory.createTitledBorder("Store Authentication Keys to Device"));
        jLabel1.setText("key Store No.");
        jLabel2.setText("Key Value Input");
        buttonLoadKey.setText("Load Key");

        GroupLayout gl_keyValPanel = new GroupLayout(keyValPanel);
        gl_keyValPanel.setHorizontalGroup(
        	gl_keyValPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_keyValPanel.createSequentialGroup()
        			.addComponent(textboxKey1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(textboxKey2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(textboxKey3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(textboxKey4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(textboxKey5, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addGap(8)
        			.addComponent(textboxKey6, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        gl_keyValPanel.setVerticalGroup(
        	gl_keyValPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_keyValPanel.createParallelGroup(Alignment.BASELINE)
        			.addComponent(textboxKey1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addComponent(textboxKey2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addComponent(textboxKey3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addComponent(textboxKey4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addComponent(textboxKey5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addComponent(textboxKey6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
        );
        keyValPanel.setLayout(gl_keyValPanel);

        GroupLayout gl_storeAuthPanel = new GroupLayout(storeAuthPanel);
        gl_storeAuthPanel.setHorizontalGroup(
        	gl_storeAuthPanel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_storeAuthPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_storeAuthPanel.createParallelGroup(Alignment.LEADING)
        				.addComponent(buttonLoadKey, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_storeAuthPanel.createSequentialGroup()
        					.addGroup(gl_storeAuthPanel.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel2)
        						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_storeAuthPanel.createParallelGroup(Alignment.LEADING)
        						.addComponent(textboxMemAdd, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        						.addComponent(keyValPanel, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))))
        			.addContainerGap())
        );
        gl_storeAuthPanel.setVerticalGroup(
        	gl_storeAuthPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_storeAuthPanel.createSequentialGroup()
        			.addGroup(gl_storeAuthPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(textboxMemAdd, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1))
        			.addGroup(gl_storeAuthPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_storeAuthPanel.createSequentialGroup()
        					.addGap(12)
        					.addComponent(jLabel2))
        				.addGroup(gl_storeAuthPanel.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(keyValPanel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
        			.addComponent(buttonLoadKey)
        			.addContainerGap())
        );
        storeAuthPanel.setLayout(gl_storeAuthPanel);

        authPanel.setBorder(BorderFactory.createTitledBorder("Authentication Function"));

        jLabel3.setText("Block No. (Dec)");
        jLabel4.setText("Key Store Number");

        keyTypePanel.setBorder(BorderFactory.createTitledBorder("Key Type"));
        bdKeyType.add(radiobuttonKeyA);
        radiobuttonKeyA.setText("Key A");
        bdKeyType.add(radiobuttonKeyB);
        radiobuttonKeyB.setText("Key B");

        GroupLayout gl_keyTypePanel = new GroupLayout(keyTypePanel);
        gl_keyTypePanel.setHorizontalGroup(
        	gl_keyTypePanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_keyTypePanel.createSequentialGroup()
        			.addGap(10)
        			.addGroup(gl_keyTypePanel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(radiobuttonKeyB)
        				.addComponent(radiobuttonKeyA))
        			.addContainerGap(25, Short.MAX_VALUE))
        );
        gl_keyTypePanel.setVerticalGroup(
        	gl_keyTypePanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_keyTypePanel.createSequentialGroup()
        			.addComponent(radiobuttonKeyA)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(radiobuttonKeyB)
        			.addContainerGap(27, Short.MAX_VALUE))
        );
        keyTypePanel.setLayout(gl_keyTypePanel);

        buttonAuthenticate.setText("Authenticate");

        binBlkPanel.setBorder(BorderFactory.createTitledBorder("Binary Block Function"));

        jLabel6.setText("Start Block (Dec)");
        jLabel7.setText("Length");
        jLabel8.setText("Data (text)");
        buttonBinRead.setText("Read Block");
        buttonBinUpdate.setText("Update Block");

        GroupLayout gl_binBlkPanel = new GroupLayout(binBlkPanel);
        gl_binBlkPanel.setHorizontalGroup(
        	gl_binBlkPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_binBlkPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_binBlkPanel.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_binBlkPanel.createSequentialGroup()
        					.addComponent(buttonBinRead, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
        					.addGap(43)
        					.addComponent(buttonBinUpdate, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_binBlkPanel.createSequentialGroup()
        					.addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(textboxBinBlk, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jLabel7)
        					.addGap(18)
        					.addComponent(textboxBinLen, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        					.addGap(9))
        				.addComponent(jLabel8)
        				.addComponent(textboxBinData, 275, 275, 275))
        			.addContainerGap(69, Short.MAX_VALUE))
        );
        gl_binBlkPanel.setVerticalGroup(
        	gl_binBlkPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_binBlkPanel.createSequentialGroup()
        			.addGroup(gl_binBlkPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel6)
        				.addComponent(textboxBinLen, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel7)
        				.addComponent(textboxBinBlk, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jLabel8)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(textboxBinData, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_binBlkPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(buttonBinUpdate)
        				.addComponent(buttonBinRead))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        binBlkPanel.setLayout(gl_binBlkPanel);

        valBlkPanel.setBorder(BorderFactory.createTitledBorder("Value Block Function"));

        jLabel9.setText("Value Amount");
        jLabel10.setText("Block No.");
        jLabel11.setText("Source Block");
        jLabel12.setText("Target Block");
        buttonValStore.setText("Store Value");
        buttonValInc.setText("Increment");
        buttonValDec.setText("Decrement");
        buttonValRead.setText("Read Value");
        buttonValRes.setText("Restore Value");

        buttonClear.setText("Clear");
        buttonReset.setText("Reset");
        buttonQuit.setText("Quit");
        
        lblApduLogs = new JLabel("APDU Logs");
        lblApduLogs.setFont(new Font("Verdana", Font.PLAIN, 10));

        GroupLayout gl_msgPanel = new GroupLayout(msgPanel);
        gl_msgPanel.setHorizontalGroup(
        	gl_msgPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_msgPanel.createSequentialGroup()
        			.addGroup(gl_msgPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, gl_msgPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(buttonClear, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(buttonReset, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
        					.addGap(14)
        					.addComponent(buttonQuit, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_msgPanel.createSequentialGroup()
        					.addGap(14)
        					.addComponent(scrlPaneMsg, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
        				.addGroup(gl_msgPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblApduLogs)))
        			.addContainerGap())
        );
        gl_msgPanel.setVerticalGroup(
        	gl_msgPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_msgPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblApduLogs)
        			.addGap(12)
        			.addComponent(scrlPaneMsg, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_msgPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(buttonQuit)
        				.addComponent(buttonClear)
        				.addComponent(buttonReset))
        			.addContainerGap(16, Short.MAX_VALUE))
        );
        mMsg = new JTextArea();
        mMsg.setFont(new Font("Verdana", Font.PLAIN, 10));
        scrlPaneMsg.setViewportView(mMsg);
        
                mMsg.setColumns(20);
                mMsg.setRows(5);
        msgPanel.setLayout(gl_msgPanel);

        
        buttonInitialize.setMnemonic(KeyEvent.VK_I);
        buttonConnect.setMnemonic(KeyEvent.VK_C);
        buttonReset.setMnemonic(KeyEvent.VK_R);
        buttonQuit.setMnemonic(KeyEvent.VK_Q);
        buttonClear.setMnemonic(KeyEvent.VK_L);
        buttonLoadKey.setMnemonic(KeyEvent.VK_L);
        buttonAuthenticate.setMnemonic(KeyEvent.VK_A);
        buttonBinRead.setMnemonic(KeyEvent.VK_E);
        buttonBinUpdate.setMnemonic(KeyEvent.VK_U);
        buttonValStore.setMnemonic(KeyEvent.VK_S);
        buttonValInc.setMnemonic(KeyEvent.VK_I);
        buttonValDec.setMnemonic(KeyEvent.VK_D);
        buttonValRead.setMnemonic(KeyEvent.VK_E);
        buttonValRes.setMnemonic(KeyEvent.VK_T);
        valBlkPanel.setLayout(null);
        valBlkPanel.add(jLabel9);
        valBlkPanel.add(jLabel10);
        valBlkPanel.add(textboxValAmount);
        valBlkPanel.add(textboxValSrc);
        valBlkPanel.add(textboxValBlk);
        valBlkPanel.add(jLabel11);
        valBlkPanel.add(jLabel12);
        valBlkPanel.add(textboxValTar);
        valBlkPanel.add(buttonValRes);
        valBlkPanel.add(buttonValRead);
        valBlkPanel.add(buttonValDec);
        valBlkPanel.add(buttonValInc);
        valBlkPanel.add(buttonValStore);
        getContentPane().setLayout(null);
        getContentPane().add(authPanel);
        authPanel.setLayout(null);
        authPanel.add(keyTypePanel);
        authPanel.add(buttonAuthenticate);
        authPanel.add(jLabel3);
        authPanel.add(textboxBlockNumber);
        authPanel.add(jLabel4);
        authPanel.add(textboxKeyAdd);
        getContentPane().add(binBlkPanel);
        getContentPane().add(readerPanel);
        getContentPane().add(storeAuthPanel);
        getContentPane().add(valBlkPanel);
        getContentPane().add(msgPanel);

        buttonInitialize.addActionListener(this);
        buttonConnect.addActionListener(this);
        buttonReset.addActionListener(this);
        buttonQuit.addActionListener(this);
        buttonClear.addActionListener(this);
        buttonLoadKey.addActionListener(this);
        buttonAuthenticate.addActionListener(this);
        buttonBinRead.addActionListener(this);
        buttonBinUpdate.addActionListener(this);
        buttonValStore.addActionListener(this);
        buttonValInc.addActionListener(this);
        buttonValDec.addActionListener(this);
        buttonValRead.addActionListener(this);
        buttonValRes.addActionListener(this);

        textboxMemAdd.addKeyListener(this);
        textboxKey1.addKeyListener(this);
        textboxKey2.addKeyListener(this);
        textboxKey3.addKeyListener(this);
        textboxKey4.addKeyListener(this);
        textboxKey5.addKeyListener(this);
        textboxKey6.addKeyListener(this);
        textboxBlockNumber.addKeyListener(this);
        textboxBinData.addKeyListener(this);
        textboxKeyAdd.addKeyListener(this);
        textboxBinBlk.addKeyListener(this);
        textboxBinLen.addKeyListener(this);
        textboxValAmount.addKeyListener(this);
        textboxValBlk.addKeyListener(this);
        textboxValSrc.addKeyListener(this);
        textboxValTar.addKeyListener(this);
                
    }

	public void actionPerformed(ActionEvent e) 
	{
		
		if(buttonInitialize == e.getSource())
		{
			String[] readerList = null;
			
		    try
		    {
			    readerList = acr1281.getPcscConnection().listTerminals();					
				if (readerList.length == 0)
				{
					mMsg.append("No PC/SC reader detected");
					return;
				}
				
				comboboxReader.removeAllItems();
					
				for (int i = 0; i < readerList.length; i++)
				{
					if (!readerList.equals(""))	
						comboboxReader.addItem(readerList[i]);
					else
						break;
				}
				
				if(comboboxReader.getItemCount() > 0)
					comboboxReader.setSelectedIndex(0);
				
				buttonConnect.setEnabled(true);
				buttonInitialize.setEnabled(false);
				buttonReset.setEnabled(true);
		    }
		    catch (Exception ex)
		    {
		    	displayOut(0, 0, ex.getMessage().toString());
			JOptionPane.showMessageDialog(null,"No Reader Available");
		    }			
		} // Init
		
		
		if(buttonConnect == e.getSource())
		{
			try
			{
				if(acr1281.getPcscConnection().isConnectionActive())	
					acr1281.getPcscConnection().disconnect();
				
				String rdrcon = (String)comboboxReader.getSelectedItem();
				
				acr1281.getPcscConnection().connect(rdrcon, "*");
				mifareClassic = new MifareClassic(acr1281.getPcscConnection());
				
				displayOut(0, 0, "Successful connection to " + rdrcon);				
			    
                                currentChipType = acr1281.getChipType();
                                
				radiobuttonKeyA.setEnabled(true);
				radiobuttonKeyB.setEnabled(true);
				radiobuttonKeyA.setSelected(true);
				textboxKey1.setEnabled(true);
				textboxKey2.setEnabled(true);
				textboxKey3.setEnabled(true);
				textboxKey4.setEnabled(true);
				textboxKey5.setEnabled(true);
				textboxKey6.setEnabled(true);
				textboxMemAdd.setEnabled(true);
				textboxBlockNumber.setEnabled(true);
				textboxKeyAdd.setEnabled(true);
				textboxBinBlk.setEnabled(true);
				textboxBinLen.setEnabled(true);
				textboxBinData.setEnabled(true);
				textboxValAmount.setEnabled(true);
				textboxValBlk.setEnabled(true);
				textboxValSrc.setEnabled(true);
				textboxValTar.setEnabled(true);
				buttonLoadKey.setEnabled(true);
				buttonAuthenticate.setEnabled(true);
				buttonBinRead.setEnabled(true);
				buttonBinUpdate.setEnabled(true);
				buttonValStore.setEnabled(true);
				buttonValInc.setEnabled(true);
				buttonValDec.setEnabled(true);
				buttonValRead.setEnabled(true);
				buttonValRes.setEnabled(true);
				textboxMemAdd.setEnabled(true);
				textboxKeyAdd.setEnabled(true);
			}
			catch (Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}
		} // Connect
		
		
		if(buttonClear == e.getSource())
		{			
			mMsg.setText("");			
		} // Clear
		
		if(buttonReset == e.getSource())
		{
			try			
			{
				//disconnect
				if (acr1281.getPcscConnection().isConnectionActive())
					acr1281.getPcscConnection().disconnect();
				
				initMenu();
				mMsg.setText("");
				comboboxReader.removeAllItems();
				comboboxReader.addItem("Please select reader                   ");
			}
			catch (Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}			
		} // Reset
		
		if(buttonQuit == e.getSource())
		{			
			this.dispose();			
		} // Quit
		
		if(buttonLoadKey == e.getSource())
		{
			
			byte[] key = new byte[6];
			byte keyNumber;
			
			try
			{
				
				
                            if(textboxMemAdd.getText().equals(""))
                            {
                                textboxMemAdd.requestFocus();
                                return;
                            }
					
                            keyNumber = (byte)((Integer)Integer.parseInt(textboxMemAdd.getText(), 16)).byteValue();
                            if(keyNumber > (byte) 0x01)
                            {
                                textboxMemAdd.setText("01");
                                return;
                            }
						
                            if(textboxKey1.getText().equals(""))
                            {
                                textboxKey1.requestFocus();
                                return;				
                            }
				
                            if(textboxKey2.getText().equals(""))
                            {	
                                textboxKey2.requestFocus();
                                return;				
                            }
					
                            if(textboxKey3.getText().equals(""))
                            {	
                                textboxKey3.requestFocus();
                                return;				
                            }
	
                            if(textboxKey4.getText().equals(""))
                            {
                                textboxKey4.requestFocus();
                                return;
                            }
	
                            if(textboxKey5.getText().equals(""))
                            {
                                textboxKey5.requestFocus();
                                return;
                            }
				
                            if(textboxKey6.getText().equals(""))
                            {
                                textboxKey6.requestFocus();
                                return;				
                            }	
				
                            key[0] = (byte)((Integer)Integer.parseInt(textboxKey1.getText(), 16)).byteValue();
                            key[1] = (byte)((Integer)Integer.parseInt(textboxKey2.getText(), 16)).byteValue();
                            key[2] = (byte)((Integer)Integer.parseInt(textboxKey3.getText(), 16)).byteValue();
                            key[3] = (byte)((Integer)Integer.parseInt(textboxKey4.getText(), 16)).byteValue();
                            key[4] = (byte)((Integer)Integer.parseInt(textboxKey5.getText(), 16)).byteValue();
                            key[5] = (byte)((Integer)Integer.parseInt(textboxKey6.getText(), 16)).byteValue();				

                            if(acr1281.loadAuthKey(keyNumber, key) == false)  
                                displayOut(0, 0, "Load Key Failed!");
			}
			catch(Exception ex)
			{
                            displayOut(0, 0, ex.getMessage().toString());
			}		
		} // Load Key
		
		
		if(buttonAuthenticate == e.getSource())
		{	
			byte blockNumber;
			byte keyNumber = 0x20;
			Acr1281.KEYTYPES keyType = Acr1281.KEYTYPES.ACR1281_KEYTYPE_A;
			
			try
			{
				//validate input
				if(textboxBlockNumber.getText().equals(""))
				{				
					textboxBlockNumber.selectAll();
					textboxBlockNumber.requestFocus();
					return;				
				}
				
                                if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                }                       
					
                                if(textboxKeyAdd.getText().equals(""))
                                {
                                    textboxKeyAdd.requestFocus();
                                    return;
                                }

                                keyNumber = (byte)((Integer)Integer.parseInt(textboxKeyAdd.getText(), 16)).byteValue();
                                if(keyNumber > (byte) 0x01)
                                {
                                    textboxKeyAdd.setText("01");
                                    return;
                                }
				
				if(radiobuttonKeyB.isSelected())
                                    keyType = Acr1281.KEYTYPES.ACR1281_KEYTYPE_B;
				
				//blockNumber = (byte)(Integer)Integer.parseInt(textboxBlockNumber.getText(), 16)).byteValue(); 
				
                                blockNumber = Byte.parseByte(textboxBlockNumber.getText());
                                
				if(acr1281.authenticate(blockNumber, keyType, keyNumber) == false)
                                    displayOut(0, 0, "Authenticate Failed!");
				
				displayOut(0,0,"Succesful Authentication");
				
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
				
			}
		}  // Authenticate
		
		if(buttonBinRead == e.getSource())
		{		
			int blockNumber;
			int length;
			byte[] tempStr;
			
			try
			{	
				//validate input
				textboxBinData.setText("");
				
				if(textboxBinBlk.getText().equals(""))
				{				
					textboxBinBlk.requestFocus();
					return;
				}
	
				if(textboxBinLen.getText().equals(""))
				{			
					textboxBinLen.requestFocus();
					return;
				}
	
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				
				blockNumber = Integer.parseInt(textboxBinBlk.getText());
				length = Integer.parseInt(textboxBinLen.getText());
											
				tempStr = mifareClassic.readBinaryBlock((byte)blockNumber, (byte)length);				
				textboxBinData.setText(Helper.byteArrayToString(tempStr));
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}			
		}  // Read
		
		if(buttonBinUpdate == e.getSource())
		{		
			int blockNumber;
			int length;
			
			try
			{
				String tmpStr="";
				
				//validate input
				if (textboxBinData.getText().equals(""))
				{
					textboxBinData.requestFocus();
					return;
				}
				
				if(textboxBinBlk.getText().equals(""))
				{	
					textboxBinBlk.requestFocus();
					return;				
				}
				
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				if(textboxBinLen.getText().equals(""))
				{
					textboxBinLen.requestFocus();
					return;				
				}
				
				if((Integer.parseInt(textboxBinLen.getText()) % 16) != 0)
				{
					textboxBinLen.requestFocus();
					JOptionPane.showMessageDialog(this, "Data length must be multiple of 16");
				}
		
				blockNumber = Integer.parseInt(textboxBinBlk.getText());
				length = Integer.parseInt(textboxBinLen.getText());                           
                                
                                if (blockNumber < 127) {
                                    if (((blockNumber + 1) % 4 == 0) || ((blockNumber + 1) % 16 == 0)){
                                        int confirmationRes;
                                        confirmationRes = JOptionPane.showConfirmDialog(this,"Block Number is a sector trailer!","Sector Trailer Notification",YES_NO_OPTION);
                                        if (confirmationRes == JOptionPane.NO_OPTION) {
                                            return;
                                        }                                        
                                    } 
                                }
                                
				byte[] buff = new byte[length];
				
				tmpStr = textboxBinData.getText();
				
				for (int i = 0; i < tmpStr.length(); i++)
					buff[i] = (byte)tmpStr.charAt(i);	
				
				mifareClassic.updateBinaryBlock((byte)blockNumber, buff, (byte)length);
				
				textboxBinData.setText("");
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}			
		}  // Update
		
		if(buttonValStore==e.getSource())
		{	
			int amount;
			int blockNumber;
			
			try
			{			
				//validate input
				if (textboxValAmount.getText().equals(""))
				{
					textboxValAmount.requestFocus();
					return;
				}
			
				if(Integer.parseInt(textboxValAmount.getText()) > 2147483647)
				{				
					textboxValAmount.setText("2147483647");
					textboxValAmount.requestFocus();
					return;				
				}
				
				if(textboxValBlk.getText().equals(""))
				{
					textboxValBlk.requestFocus();
					return;				
				}
			
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				textboxValSrc.setText("");
				textboxValTar.setText("");
				
				blockNumber = Integer.parseInt(textboxValBlk.getText());
				amount = Integer.parseInt(textboxValAmount.getText());	
				
				mifareClassic.store((byte)blockNumber, amount);	
				displayOut(0, 0, "Store Value Success!");
				textboxValAmount.setText("");
				
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}
		} // Store Value
		
		if (buttonValInc == e.getSource())
		{	
			try
			{
				int amount;
				
				//validate input
				if (textboxValAmount.getText().equals(""))
				{
					textboxValAmount.requestFocus();
					return;
				}
		
				if(Integer.parseInt(textboxValAmount.getText()) > 2147483647)
				{	
					textboxValAmount.setText("2147483647");
					textboxValAmount.requestFocus();
					return;
				}
				
				if(textboxValBlk.getText().equals(""))
				{
					textboxValBlk.requestFocus();
					return;
				}
				
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				textboxValSrc.setText("");
				textboxValTar.setText("");
				
				amount = Integer.parseInt(textboxValAmount.getText());
				
				mifareClassic.increment((byte)Integer.parseInt(textboxValBlk.getText()), amount);				
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}	
		}  // Increment Value
		
		if(buttonValDec == e.getSource())
		{	
			try
			{
				int amount;
				
				//validate input
				if (textboxValAmount.getText().equals(""))
				{
					textboxValAmount.requestFocus();
					return;
				}
	
				if(Integer.parseInt(textboxValAmount.getText()) > 2147483647)
				{				
					textboxValAmount.setText("2147483647");
					textboxValAmount.requestFocus();
					return;				
				}
				
				if(textboxValBlk.getText().equals(""))
				{
					textboxValBlk.requestFocus();
					return;
				}
		
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				textboxValSrc.setText("");
				textboxValTar.setText("");
				
				amount = Integer.parseInt(textboxValAmount.getText());
				
				mifareClassic.decrement((byte)Integer.parseInt(textboxValBlk.getText()), amount);				
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}			
		}  // Decrement Value
		
		if(buttonValRead == e.getSource())
		{	
			try
			{
				int amount = 0;
				
				//validate input
				if(textboxValBlk.getText().equals(""))
				{	
					textboxValBlk.requestFocus();
					return;
				}
				
				if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 63)
                                        textboxBlockNumber.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxBlockNumber.getText()) > 255)
                                        textboxBlockNumber.setText("255");
                                } 
				
				textboxValAmount.setText("");
				textboxValSrc.setText("");
				textboxValTar.setText("");
				
				amount = mifareClassic.inquireAmount((byte)Integer.parseInt(textboxValBlk.getText()));				
				textboxValAmount.setText("" + amount);
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}
			
		} // Read Value
		
		if(buttonValRes == e.getSource())
		{	
			try
			{
				//validate input
				if(textboxValSrc.getText().equals(""))
				{				
					textboxValSrc.requestFocus();
					return;				
				}
				
				if(textboxValTar.getText().equals(""))
				{				
					textboxValTar.requestFocus();
					return;				
				}
		
                                if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxValSrc.getText()) > 63)
                                        textboxValSrc.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxValSrc.getText()) > 255)
                                        textboxValSrc.setText("255");
                                } 
                                
                                if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_1K)
                                {
                                    if (Integer.parseInt(textboxValTar.getText()) > 63)
                                        textboxValTar.setText("63");
                                }
                                else if (currentChipType == Acr1281.CHIP_TYPE.MIFARE_4K)
                                {
                                    if (Integer.parseInt(textboxValTar.getText()) > 255)
                                        textboxValTar.setText("255");
                                }                             
				
				textboxValAmount.setText("");
				textboxValBlk.setText("");
				
				mifareClassic.restoreAmount((byte) Integer.parseInt(textboxValSrc.getText()), (byte) Integer.parseInt(textboxValTar.getText()));
			}
			catch(Exception ex)
			{
				displayOut(0, 0, ex.getMessage().toString());
			}
		}  // Restore Value
			
	}
	
	
	public void displayOut(int mType, int msgCode, String printText)
	{
		switch(mType)
		{			
			case 2: mMsg.append("< " + printText + "\n"); break;
			case 3: mMsg.append("> " + printText + "\n"); break;
			default: mMsg.append(printText + "\n");
		}		
	}
	

	
	public void initMenu()
	{
		buttonConnect.setEnabled(false);
		buttonInitialize.setEnabled(true);
		buttonReset.setEnabled(false);
		mMsg.setText("");
		textboxMemAdd.setText("");
		textboxKey1.setText("");
		textboxKey2.setText("");
		textboxKey3.setText("");
		textboxKey4.setText("");
		textboxKey5.setText("");
		textboxKey6.setText("");
		textboxBlockNumber.setText("");
		textboxKeyAdd.setText("");		
		radiobuttonKeyA.setEnabled(false);
		radiobuttonKeyB.setEnabled(false);
		textboxBinBlk.setText("");
		textboxBinLen.setText("");
		textboxBinData.setText("");
		textboxValAmount.setText("");
		textboxValBlk.setText("");
		textboxValSrc.setText("");
		textboxValTar.setText("");
		textboxKey1.setEnabled(false);
		textboxKey2.setEnabled(false);
		textboxKey3.setEnabled(false);
		textboxKey4.setEnabled(false);
		textboxKey5.setEnabled(false);
		textboxKey6.setEnabled(false);		
		textboxBlockNumber.setEnabled(false);
		textboxBinBlk.setEnabled(false);
		textboxBinLen.setEnabled(false);
		textboxBinData.setEnabled(false);
		textboxValAmount.setEnabled(false);
		textboxValBlk.setEnabled(false);
		textboxValSrc.setEnabled(false);
		textboxValTar.setEnabled(false);
		buttonLoadKey.setEnabled(false);
		buttonAuthenticate.setEnabled(false);
		buttonBinRead.setEnabled(false);
		buttonBinUpdate.setEnabled(false);
		buttonValStore.setEnabled(false);
		buttonValInc.setEnabled(false);
		buttonValDec.setEnabled(false);
		buttonValRead.setEnabled(false);
		buttonValRes.setEnabled(false);
		displayOut(0, 0, "Program Ready");
	}
	
	public void keyReleased(KeyEvent ke) { }
	
	public void keyPressed(KeyEvent ke) 
	{
  		//restrict paste actions
		if (ke.getKeyCode() == KeyEvent.VK_V ) 
			ke.setKeyCode(KeyEvent.VK_UNDO );						
  	}
	
	public void keyTyped(KeyEvent ke) 
	{  		
  		Character x = (Character)ke.getKeyChar();
  		char empty = '\r';
  		

  		//Check valid characters
  		if(textboxBlockNumber.isFocusOwner() || textboxBinBlk.isFocusOwner() || textboxBinLen.isFocusOwner() || textboxValAmount.isFocusOwner() || textboxValSrc.isFocusOwner() || textboxValBlk.isFocusOwner() || textboxValTar.isFocusOwner())
  		{	
  			if (VALIDCHARS.indexOf(x) == -1 ) 
  				ke.setKeyChar(empty);
  		}
  		else
  		{	
  			if (VALIDCHARSHEX.indexOf(x) == -1 ) 
  				ke.setKeyChar(empty);  			
  		}
  					  
		//Limit character length
  		if(textboxBlockNumber.isFocusOwner() || textboxBinBlk.
                        isFocusOwner() || textboxValBlk.isFocusOwner() || 
                        textboxValSrc.isFocusOwner() || textboxValTar.
                                isFocusOwner())
  		{	
  			if (((JTextField)ke.getSource()).getText().length() >= 3 ) 
  			{
  				ke.setKeyChar(empty);	
  				return;
  			}  			
  		}
  		else if(textboxValAmount.isFocusOwner())
  		{	
  			if (((JTextField)ke.getSource()).getText().length() >= 10 ) 
  			{		
  				ke.setKeyChar(empty);	
  				return;  				
  			}  			
  		}
                else if (textboxBinData.isFocusOwner())
                {
                    if (((JTextField)ke.getSource()).getText().length() >= 16)
                    {
                        ke.setKeyChar(empty);
                        return;
                    }
                }
  		else
  		{  			
  			if (((JTextField)ke.getSource()).getText().length() >= 2 ) 
  			{		
  				ke.setKeyChar(empty);	
  				return;  				
  			}  			
  		}		
	}
	
	
	public void onSendCommand(ReaderEvents.TransmitApduEventArg event) 
	{
		displayOut(2, 0, event.getAsString(true));
	}

	public void onReceiveCommand(ReaderEvents.TransmitApduEventArg event) 
	{
		displayOut(3, 0, event.getAsString(true) + "\r\n");
	}
	
    
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MifareCardProgramming().setVisible(true);
            }
        });
    }



}
