package com.sidney.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HelloWorld extends JPanel {
    static final int WIDTH = 300;
    static final int HEIGHT = 150;
    JFrame loginframe;
    
    
    public void add(Component c,GridBagConstraints contrain,int x,int y, int w,int h){
    	//此方法用来添加控件到容器中
    	contrain.gridwidth = w;
    	contrain.gridheight = h;
    	contrain.gridx = x;
    	contrain.gridy = y;
    	add(c,contrain);
    }
    public HelloWorld(){
    	loginframe = new JFrame("Welcome to java");
    	loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	GridBagLayout lay = new GridBagLayout();
    	setLayout(lay);
    	loginframe.add(this,BorderLayout.WEST);
    	loginframe.setSize(WIDTH,HEIGHT);
    	Toolkit kit = Toolkit.getDefaultToolkit();
    	Dimension screenSize = kit.getScreenSize();
    	int width = screenSize.width;
    	int height = screenSize.height;
    	int x = (width-WIDTH)/2;
    	int y = (height - HEIGHT)/2;
    	loginframe.setLocation(x, y);
    	JButton ok = new JButton("登录");
    	JButton cancel = new JButton("放弃");
    	JLabel title = new JLabel("Welcome to JAVA");
    	JLabel name = new JLabel("用户名");
    	JLabel password = new JLabel("密码");
    	final JTextField nameinput = new JTextField(15);
    	final JTextField passwordinput = new JTextField(15);
    	GridBagConstraints contrains = new GridBagConstraints();
    	contrains.fill = GridBagConstraints.NONE;
    	contrains.anchor = GridBagConstraints.EAST;
    	contrains.weightx = 3;
    	contrains.weighty = 4;
    	add(title,contrains,0,0,4,1);
    	add(name, contrains,0,1,1,1);
    	add(password,contrains,0,2,1,1);
    	add(nameinput,contrains,2,1,1,1);
    	add(passwordinput,contrains,2,2,1,1);
    	add(ok,contrains,0,3,1,1);
    	add(cancel,contrains,2,3,1,1);
    	loginframe.setResizable(false);
    	loginframe.setVisible(true);
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloWorld hello = new HelloWorld();
	}

}
