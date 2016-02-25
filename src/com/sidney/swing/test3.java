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
		super.setTitle("测试窗口");
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
			JButton b1 = new JButton("确定");
			JButton b2 = new JButton("取消");
			JLabel jl = new JLabel("你能确定关闭系统了吗?");
			JDialog jd = new JDialog((JFrame)e.getSource(),"系统出错了!",true); //创建一个对话框
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
