package com.sidney.QuadTree;
public class Point {
     private double x;
     private double y;
	public Point(double x, double y) {
	
		this.x = x;
		this.y = y;
	}
    public Point(){
    	
    }
	public Point(Point a) {
		// TODO Auto-generated constructor stub
		this.x = a.getX();
		this.y = a.getY();
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void print(){
		System.out.println("("+x+","+y+") ");
	}
    
}
