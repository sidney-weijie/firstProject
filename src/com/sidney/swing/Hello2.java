package com.sidney.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

public class Hello2 {
    private static final long serialVersionID = 1L;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private JPopupMenu pop;
    private JMenuItem item2;
    private JFrame f;
    private JMenuItem item1;
    private JPanel p;
    private JToolBar bar;
    
	public Hello2() {
		// TODO Auto-generated constructor stub
		f = new JFrame("我的测试界面");
		JMenuBar menubar1 = new JMenuBar();
		p = new JPanel();
		f.setContentPane(p);
		f.setJMenuBar(menubar1);
		//菜单栏
		JMenu menu1 = new JMenu("菜单1");
		JMenu menu2 = new JMenu("菜单2");
		JMenu menu3 = new JMenu("菜单3");
		JMenu menu4 = new JMenu("菜单4");
		JMenu menu5 = new JMenu("菜单5");
	    
		menubar1.add(menu1);
		menubar1.add(menu2);
		menubar1.add(menu3);
		menubar1.add(menu4);
		menubar1.add(menu5);
		
		item1 = new JMenuItem("子菜单1");
		item2 = new JMenuItem("子菜单2");
		
		JMenuItem item3 = new JMenuItem("子菜单3");
		JMenuItem item4 = new JMenuItem("子菜单4");
		JMenuItem item5 = new JMenuItem("子菜单5");
		JMenuItem item6 = new JMenuItem("子菜单6");
		JMenuItem item7 = new JMenuItem("子菜单7");
		JMenuItem item8 = new JMenuItem("子菜单8");
		JMenuItem item9 = new JMenuItem("子菜单9");
		JMenuItem item10 = new JMenuItem("子菜单10");
		JMenuItem item11 = new JMenuItem("子菜单11");
		JMenuItem item12 = new JMenuItem("子菜单12");
		
		menu1.add(item1);
		menu1.addSeparator();
		menu1.add(item2);
		menu1.addSeparator();
		
		menu1.add(item3);
		menu2.add(item4);
		menu2.addSeparator();
		
		menu2.add(item5);
		menu3.add(item6);
		menu3.addSeparator();
		menu3.add(item7);
		
		menu4.add(item8);
		menu4.addSeparator();
		menu4.add(item9);
		menu4.addSeparator();
		menu4.add(item10);
		
		menu5.add(item11);
		menu5.addSeparator();
		menu5.add(item12);
		
		JButton button1 = new JButton("工具1");
		JButton button2 = new JButton("工具2");
		JButton button3 = new JButton("工具3");
		//bar是工具栏，添加到工具栏上
		bar = new JToolBar();
		bar.add(button1);
		bar.add(button2);
		bar.add(button3);
		
		BorderLayout bord = new BorderLayout();
		p.setLayout(bord);
		p.add("North",bar); //North表示设置bar到Panel的顶部
		f.setVisible(true);
		f.setSize(WIDTH,HEIGHT);
		//获取显示器分辨率
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		System.out.println("width="+width +"  height="+height);
		int x = (width-WIDTH)/2;
		int y = (height - HEIGHT)/2;
		//设置初始化对话框位置    这里是显示器居中
		f.setLocation(x,y);
		
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         new Hello2();
	}

}
