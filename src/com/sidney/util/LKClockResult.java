package com.sidney.util;
import com.sidney.QuadTree.*;
public class LKClockResult {
	private int k;
	private MapRectangle rect;
	
	
	
	public LKClockResult(int k, MapRectangle rect) {
		this.k = k;
		this.rect = rect;
	}
	public LKClockResult(){
		rect = null;
		k = 0;
	}


	public int getK() {
		return k;
	}



	public void setK(int k) {
		this.k = k;
	}



	public MapRectangle getRect() {
		return rect;
	}



	public void setRect(MapRectangle rect) {
		this.rect = rect;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String toString(){
		String temp = "k="+k + rect.toString2();
		return temp;
	}

}
