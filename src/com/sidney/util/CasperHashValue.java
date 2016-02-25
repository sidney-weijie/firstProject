package com.sidney.util;
import com.sidney.QuadTree.*;;
public class CasperHashValue {
    private QuadTreeNode qNode = null;
    private int needK;
    private int needArea;
    private Point poi = null;
    public CasperHashValue(QuadTreeNode qNode,int k,int area,Point p){
    	this.qNode = qNode;
    	this.needK = k;
    	this.needArea = area;
    	this.poi = p;
    }
	public QuadTreeNode getqNode() {
		return qNode;
	}
	public void setqNode(QuadTreeNode qNode) {
		this.qNode = qNode;
	}
	public int getNeedK() {
		return needK;
	}
	public void setNeedK(int needK) {
		this.needK = needK;
	}
	public int getNeedArea() {
		return needArea;
	}
	public void setNeedArea(int needArea) {
		this.needArea = needArea;
	}
	
	public Point getPoi() {
		return poi;
	}
	public void setPoi(Point poi) {
		this.poi = poi;
	}
	public void Print(){
		if(qNode!=null)
			qNode.getRect().Print();
		System.out.print("  ,"+needK+", "+needArea);
	}
}
