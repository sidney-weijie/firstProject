package com.sidney.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A program which demonstrates the basic paint mechanism for Swing components 
 * (JPanel for example).
 * @author han
 *
 */
public class SwingPaintDemo {
	public static void main(String[] args) {
		JFrame f = new JFrame("Aim For the Center");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JPanel panel = new BullsEyePanel();
		panel.add(new JLabel("BullsEye!", SwingConstants.CENTER), BorderLayout.CENTER);
		f.getContentPane().add(panel, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
}

/**
 * A Swing container that renders a bullseye background
 * where the area around the bullseye is transparent.
 */
@SuppressWarnings("serial")
class BullsEyePanel extends JPanel {

	public BullsEyePanel() {
		super();
		setOpaque(false); // we don't paint all our bits
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	@Override
	public Dimension getPreferredSize() {
		// Figure out what the layout manager needs and
		// then add 100 to the largest of the dimensions
		// in order to enforce a 'round' bullseye 
		/*    	the use of "super." is very important, 
    	  because otherwise the JRE will throw a StackOverflowError.
    	  And because of the JFrame.pack() used above, 
    	  the JFrame window will be resized to adapter the Container size.*/
		Dimension layoutSize = super.getPreferredSize();
		int max = Math.max(layoutSize.width,layoutSize.height);
		return new Dimension(max+100,max+100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension size = getSize();
		System.out.println(size.width);
		System.out.println(size.height);
		int x = 0;
		int y = 0;
		int i = 0;
		while(x < size.width && y < size.height) {
			g.setColor(i%2==0? Color.red : Color.white);
			g.fillOval(x,y,size.width-(2*x),size.height-(2*y));
			x+=10; y+=10; i++;
		}
	}
}