package com.sidney.QuadTree;

public class IntervalCloakResult {
    private MapRectangle rect = null;
    private int k;
    public IntervalCloakResult(MapRectangle rect,int k){
    	this.rect = rect;
    	this.k = k;
    }
	public MapRectangle getRect() {
		return rect;
	}
	public void setRect(MapRectangle rect) {
		this.rect = rect;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	
    public double CalArea(){             //返回匿名框的面积
    	return (rect.getRightUp().getX()-rect.getLeftDown().getX())
    			*(rect.getRightUp().getY()-rect.getLeftDown().getY());
    }
    
}
