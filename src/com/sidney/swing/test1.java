package com.sidney.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class test1 {
	static final int WIDTH = 300;
	static final int HEIGHT = 200;
	static JTextField field = new JTextField(20);
	public test1() {
		// TODO Auto-generated constructor stub
		
	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("test program");
		f.setSize(WIDTH,HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		
		f.setContentPane(contentPanel);
		JButton b = new JButton("Çå¿ÕÎÄ±¾¿ò");
		contentPanel.add(field,"North");
        contentPanel.add(b,"South");
        
        
        b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				field.setText("AAAAAAAAAAAAA");
			}
		});
	}

}
/*
class ActionHandler implements ActionListener{
	public void actionPerformed(ActionEvent e){
		new test1().field.setText("");
	}
}
*/