package com.sidney.vision;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.w3c.dom.css.Rect;

import com.sidney.QuadTree.MapRectangle;
import com.sidney.cloak.IntervalCloak;

/*
class rectangle{
	public int x;
	public int y;
	public int width;
	public int height;
	public rectangle(int x,int y,int width,int heigth){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
*/
public class CloakTool	extends JFrame implements ActionListener,
MouseListener, ItemListener {
	
	Vector paintInfo = null;
	int con = 1;                  //画笔大小
	int paintInfoLength;
	Vector paintRect = null;
	Color c = new Color(255, 255, 255); // 画笔颜色
	Panel toolPanel;
	Button addPoint;
	Button openFile;
	Button ICloak;           //
	Label rK;                //实际的k值标示
	Label aK;                //需求的k值
	JTextField xcoordinate;
	JTextField ycoordinate;
	JTextField needk;       //输入需求的k
	FileDialog openSource;
	
	IntervalCloak IC = null;
	
	
	public CloakTool(){
		super("匿名图形化工具");
		
		
		paintInfo = new Vector();
		paintRect = new Vector();
		paintInfoLength = 0;
		addPoint = new Button("添加点");
		openFile = new Button("打开文件");
		ICloak = new Button("IntervalCloak");
		xcoordinate = new JTextField(8);
		ycoordinate = new JTextField(8);	
		needk = new JTextField(4);
		aK = new Label("需求k");
		//xcoordinate.setBounds(0, 0, 50, 10);
		//ycoordinate.setBounds(100, 0, 50, 10);
		//addPoint.setBounds(150, 0, 30, 10);
		
		addPoint.addActionListener(this);
		openFile.addActionListener(this);
		ICloak.addActionListener(this);
		
		toolPanel = new Panel();
		
		toolPanel.add(xcoordinate);
		toolPanel.add(ycoordinate);
		toolPanel.add(addPoint);
		toolPanel.add(openFile);
		toolPanel.add(aK);
		toolPanel.add(needk);
		toolPanel.add(ICloak);
		add(toolPanel,BorderLayout.SOUTH);
		IC = new IntervalCloak(800,560,5);
		//setBounds(230, 50, 900, 650);
		
		setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        openSource = new FileDialog(this, "打开数据文件", FileDialog.LOAD);
		openSource.setVisible(false);
		
		openSource.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				openSource.setVisible(false);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics g){
		System.out.println("paints");
		Graphics2D g2d = (Graphics2D) g;
		int n = paintInfo.size();
		/*if(n == paintInfoLength){
			return;
		}*/
		Point p = null;
		Color co = new Color(255,255,255);
		g2d.setColor(co);
		g2d.draw(new Rectangle(0, 0, 800, 560));
		co = new Color(0,0,0);
		g2d.setColor(co);
		for(int i = 0;i<n;i++){
			p = (Point) paintInfo.elementAt(i);
			BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        	g2d.setStroke(bs);
        	
        	g2d.draw((new Line2D.Double(p.getX(),p.getY(),p.getX(),p.getY())));
		}
		
		Rectangle2D rect = null;
		co = new Color(0,255,0);
		for(int i = 0 ;i < paintRect.size();i++){
			
			rect = (Rectangle2D)paintRect.elementAt(i);
			BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        	g2d.setStroke(bs);
        	
        	System.out.println(rect.getHeight());
        	System.out.println(rect.getWidth());
        	g2d.draw(rect);
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CloakTool();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("actionPerformed");
	
		if(e.getSource() == addPoint){
			System.out.println("addPoint");
			String xStr = xcoordinate.getText();
			String yStr = ycoordinate.getText();
			
			int x = -1,y = -1;
			
			if(xStr.length() < 1){
				JOptionPane.showMessageDialog(null, "x坐标为空", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(yStr.length() < 1){
				JOptionPane.showMessageDialog(null, "y坐标为空", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				x = Integer.parseInt(xStr);
				y = Integer.parseInt(yStr);
			} catch (Throwable e2) {
				// TODO: handle exception
				e2.printStackTrace();
				
			}
			
			
			if(x <= 0 || y <= 0 || x>800 || y>560){
				JOptionPane.showMessageDialog(null, "坐标范围错误", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Point  p = new Point(x,y);
			paintInfo.add(p);
			
			repaint();
		}
		
		
		if(e.getSource() == ICloak){
			
			
			int k = 0;
			int x = -1,y = -1;
			String xStr = xcoordinate.getText();
			String yStr = ycoordinate.getText();
			
			String kStr = needk.getText();
			if(kStr.length()<1){
				JOptionPane.showMessageDialog(null, "未输入k值", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				k = Integer.parseInt(kStr);
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("k值输入错误");
				if(k<=0||k>IC.getqTree().getRoot().getTotalUser()){
					JOptionPane.showMessageDialog(null, "k值输入范围错误", "提示", JOptionPane.ERROR_MESSAGE);
				
				}else{
					JOptionPane.showMessageDialog(null, "k值错误", "提示", JOptionPane.ERROR_MESSAGE);
					
				}
				
				return;
			}
			
			
			
			
			
			if(xStr.length() < 1){
				JOptionPane.showMessageDialog(null, "x坐标为空", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(yStr.length() < 1){
				JOptionPane.showMessageDialog(null, "y坐标为空", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				x = Integer.parseInt(xStr);
				y = Integer.parseInt(yStr);
			} catch (Throwable e2) {
				// TODO: handle exception
				e2.printStackTrace();
				
			}
			
			
			MapRectangle temp = IC.FindASR(new com.sidney.QuadTree.Point(x,y), k).getRect();
			
			Rectangle2D r = new Rectangle2D.Double(temp.getLeftDown().getX(),temp.getLeftDown().getY(),
								(temp.getRightUp().getX()-temp.getLeftDown().getX()), 
								(temp.getRightUp().getY()-temp.getLeftDown().getY()));
			////////////////////
			System.out.println((int)temp.getLeftDown().getX());
			System.out.println((int)temp.getLeftDown().getY());
			System.out.println((int)(temp.getRightUp().getX()-temp.getLeftDown().getX()));
			System.out.println((int)(temp.getRightUp().getY()-temp.getLeftDown().getY()));
			///////////////////
			if(paintRect == null){
				paintRect = new Vector();
			}
			
			paintRect.add(r);
			repaint();
		}
		
		if(e.getSource() == openFile){
			openSource.setVisible(true);
			
			if(openSource.getFile() != null){
				
				try {
					paintInfo.removeAllElements();
					File in = new File(openSource.getDirectory(),openSource.getFile());
					Scanner scan = new Scanner(in);
					int userno,x,y;
					if(IC.getqTree().getRoot().getTotalUser()>0){
						IC = null;
						IC = new IntervalCloak(800, 560, 5);
					}
					while(scan.hasNext()){
						userno = scan.nextInt();
						x = scan.nextInt();
						y = scan.nextInt();
						paintInfo.add(new Point(x,y));
						IC.getqTree().insertUser(new com.sidney.QuadTree.Point(x,y));
					}
					
					scan.close();
					repaint();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
