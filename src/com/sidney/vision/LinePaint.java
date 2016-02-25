package com.sidney.vision;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Line2D.Double;
import java.util.Vector;
public class LinePaint extends JFrame
{	
    public LinePaint()
    {
        super("设置线条粗细");
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args)
    {
        new LinePaint();
    }
    public void paint(Graphics g)
    {
        Graphics2D g_2d=(Graphics2D)g;
        Line2D line_1=new Line2D.Double(50,50,120,80);
        Line2D line_2=new Line2D.Double(50,80,120,80);
        Line2D line_3=new Line2D.Double(50,110,120,110);
        
        Vector<Point> vp = new Vector<Point>();
        
        vp.add(new Point(50,50));
        vp.add(new Point(200, 50));
        vp.add(new Point(200,200));
        vp.add(new Point(50,200));
        
        vp.add(new Point(vp.get(0).getX(), vp.get(0).getY()));
        
        Vector<Line2D> vL = new Vector<Line2D>();
        int i = 0;
        for(i = 0;i<vp.size()-1;i++){
        	vL.add(new Line2D.Double(vp.get(i).getX(),vp.get(i).getY(),vp.get(i+1).getX(),vp.get(i+1).getY()));
        }
        vL.add(new Line2D.Double(100,100,100,100));
        vL.add(new Line2D.Double(300,300,300,300));
        vL.add(new Line2D.Double(320,320,320,320));
        Color co1=new Color(250,250,250);
        
       
        g_2d.setColor(co1);              //设置颜色
        g_2d.fillRect(0, 0, 500, 500);   //填充纯白背景
        
         co1=new Color(0,0,250);
        
        g_2d.setColor(co1);   //设置绘置对象的颜色
        for(i = 0;i<vL.size();i++){
        	BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        	g_2d.setStroke(bs);
        	g_2d.draw(vL.get(i));
        	
        }
        
        //CAP_BUTT  _ROUND _SOUARE表示三种不同的线端
   /*     BasicStroke bs_1=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
        BasicStroke bs_2=new BasicStroke(16f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
        BasicStroke bs_3=new BasicStroke(16f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND);
        g_2d.setStroke(bs_1);
                g_2d.draw(line_1);
        g_2d.setStroke(bs_2);
                g_2d.draw(line_2);
        g_2d.setStroke(bs_3);
        g_2d.draw(line_3);
     */
    }
}

