package com.sidney.swing;

import java.awt.List;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class test6 extends JFrame implements FocusListener{
    List info = new List(10);
    JTextField tf = new JTextField("");
    JButton button = new JButton("ȷ��");
	public test6( String title) {
		// TODO Auto-generated constructor stub
		super(title);
		add(info,"North");
		add(tf,"Center");
		add(button,"South");
		tf.addFocusListener(this);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        test6 t = new test6("TEST");
        t.pack();
        t.setVisible(true);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		if(e.isTemporary())
			info.add("��ʱ�Ի��");
		else 
			info.add("�����Ի��");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		if(e.isTemporary())
			info.add("��ʱ��Lost");
		else 
			info.add("������Lost");
	}

}
