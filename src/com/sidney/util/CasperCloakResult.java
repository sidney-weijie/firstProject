package com.sidney.util;
import com.sidney.QuadTree.*;
public class CasperCloakResult {

	 private int k;              // 匿名框的匿名实际匿名度
     private MapRectangle rect;  //返回的匿名框
     
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
	
	public CasperCloakResult(MapRectangle rect,int k){
		this.rect = rect;
		this.k = k;
	}
    public double CalArea(){             //返回匿名框的面积
    	return (rect.getRightUp().getX()-rect.getLeftDown().getX())
    			*(rect.getRightUp().getY()-rect.getLeftDown().getY());
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stubo
		
       	}

}
