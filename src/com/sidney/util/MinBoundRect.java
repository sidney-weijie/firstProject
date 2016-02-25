package com.sidney.util;
import com.sidney.QuadTree.*;
public class MinBoundRect {
	private int x;    //���ĵ�����x
	private int y;    //���ĵ�����y
	private int halfHeight;    //��С������θ߶ȵ�һ��
	private int halfLength;    //��С������γ��ȵ�һ��
	
	public MinBoundRect(){
		
	}
	public MinBoundRect(int x,int y,int halfHeight,int halfLength){
		this.x = x;
		this.y = y;
		this.halfHeight = halfHeight;
		this.halfLength = halfLength;
	}
	public MinBoundRect(Point leftDown,Point rightUp){
		this.x = (int) ((leftDown.getX()+rightUp.getX())/2);
		this.y = (int) ((leftDown.getY()+rightUp.getY())/2);
		this.halfHeight = (int)((rightUp.getY()-leftDown.getY())/2);
		this.halfLength = (int)((rightUp.getX()-leftDown.getX())/2);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHalfHeight() {
		return halfHeight;
	}
	public void setHalfHeight(int halfHeight) {
		this.halfHeight = halfHeight;
	}
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	
	public String toString(){
		
		String str = x+"  "+y+"  "+halfHeight+"  "+halfLength;
		return str;
		
	}
	
}
