package com.sidney.swing;

import java.awt.BorderLayout;

import javax.swing.*;

import java.awt.*;
public class Hello3 {
	private static final long serialVerionID=1L;
	static final int WIDTH = 600;
	static final int HEIGHT = 400;
	public Hello3(){
		JFrame f = new JFrame("���Դ���");
		f.setSize(WIDTH,HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		JPanel contentpan = new JPanel();
		f.setContentPane(contentpan);
		JButton b1 = new JButton("ȷ��");
		JButton b2 = new JButton("ȡ��");
		contentpan.add(b1);
		contentpan.add(b2);
		
	}
	
	public static void main(String[]args){
		new Hello3();
	}
}
