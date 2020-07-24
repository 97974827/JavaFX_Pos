/*
  Copyright(C):      Advanced Card Systems Ltd

  File:              mainApplet.java

  Description:       This program enables the user to browse the sample codes for ACR122

  Author:            M.J.E.C. Castillo

  Date:              August 4, 2008

  Revision Trail:   (Date/Author/Description)

======================================================================*/
package kor.gls.pcsc;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.*;
import java.applet.Applet;
import javax.swing.JApplet;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class mainApplet extends JApplet implements ActionListener
{
	public mainApplet() {
	}
	
	//Variables
	boolean openMifare = false, openchangeKey = false, openPicc = false;

	//GUI Variables
    private JButton bMifare, bChangeKey;
	
	static MifareCardProgramming mifare;
	static MifareChangeKey changeKey;

	
	public void init() 
   	{
		setSize(220,150);
	    bMifare = new JButton();
	    bMifare.setFont(new Font("Verdana", Font.PLAIN, 8));
	    bMifare.setBounds(10, 44, 210, 23);
	    bChangeKey = new JButton();
	    bChangeKey.setFont(new Font("Verdana", Font.PLAIN, 8));
	    bChangeKey.setBounds(10, 73, 210, 23);

        bMifare.setText("Mifare Card Programming");
        bChangeKey.setText("Mifare Change Key");
        
        JLabel lblMifareCardProgramming = new JLabel("Mifare Card Programming");
        lblMifareCardProgramming.setFont(new Font("Verdana", Font.PLAIN, 8));
        lblMifareCardProgramming.setBounds(57, 19, 115, 14);
        
        getContentPane().setLayout(null);
        getContentPane().add(lblMifareCardProgramming);
        getContentPane().add(bMifare);
        getContentPane().add(bChangeKey);
        
        bMifare.addActionListener(this);
        bChangeKey.addActionListener(this);
   		
   	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
		if(bMifare == e.getSource())
		{
			closeFrames();
			
			if(openMifare == false)
			{
				mifare = new MifareCardProgramming();
				mifare.setVisible(true);
				openMifare = true;
			}
			else
			{
			
				mifare.dispose();
				mifare = new MifareCardProgramming();
				mifare.setVisible(true);
				openMifare = true;
			}
			
		}
		
		if(bChangeKey == e.getSource())
		{
			
			closeFrames();
			
			if(openchangeKey == false)
			{
				changeKey = new MifareChangeKey();
				changeKey.setVisible(true);
				openchangeKey = true;
			}
			else
			{
				changeKey.dispose();
				changeKey = new MifareChangeKey();
				changeKey.setVisible(true);
				openchangeKey = true;
			}
		}
		
	}
	
	public void closeFrames()
	{
		
		if(openMifare == true)
		{
			mifare.dispose();
			openMifare = false;
		}
		
		if(openchangeKey == true)
		{
			changeKey.dispose();
			openchangeKey = false;
		}
		
	}
	
	public void start()
	{
	
	}
}