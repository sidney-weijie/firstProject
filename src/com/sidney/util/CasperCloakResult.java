package com.sidney.util;
import com.sidney.QuadTree.*;
public class CasperCloakResult {

	 private int k;              // �����������ʵ��������
     private MapRectangle rect;  //���ص�������
     
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
    public double CalArea(){             //��������������
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
