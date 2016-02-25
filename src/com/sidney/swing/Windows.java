package com.sidney.swing;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
public class Windows extends JFrame {
	public Windows(String title,int width,int height){
		super(title);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	private void init(){
		JButton btnOK = new JButton("OK");
		btnOK.setBackground(Color.BLUE);
		btnOK.setBorder(BorderFactory.createLineBorder(Color.RED));
		btnOK.setToolTipText("Hi,This is a Button");
		this.add(btnOK);
	}
	public static void main(String[] argc){
        Windows win = new Windows("This is my Window", 200, 200);
        win.setVisible(true);
	}
}
