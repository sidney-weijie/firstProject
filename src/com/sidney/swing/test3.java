package com.sidney.swing;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class test3 extends JFrame{

	public test3() {
		// TODO Auto-generated constructor stub
		super.setTitle("���Դ���");
		WindowListener wh = new windowhandler();
		addWindowListener(wh);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test3 me = new test3();
		me.setSize(400,300);
		me.setVisible(true);
	}
    
	class windowhandler extends WindowAdapter{



		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			JButton b1 = new JButton("ȷ��");
			JButton b2 = new JButton("ȡ��");
			JLabel jl = new JLabel("����ȷ���ر�ϵͳ����?");
			JDialog jd = new JDialog((JFrame)e.getSource(),"ϵͳ������!",true); //����һ���Ի���
			jd.setSize(200,100);
			jd.setLocation(0,0);
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(1,2));
			jd.add(p,"South");
			jd.add(jl,"Center");
			
			p.add(b1);
			p.add(b2);
			jd.setVisible(true);
			b1.setVisible(true);
			b2.setVisible(true);
			jl.setVisible(true);
		}

	}
}
