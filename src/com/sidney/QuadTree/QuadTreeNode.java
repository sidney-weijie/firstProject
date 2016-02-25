/**
 * 
 */
package com.sidney.QuadTree;

/**
 * @author Sidney
 *
 */
public class QuadTreeNode {
    private int depth;
    private MapRectangle rect;
    private QuadTreeNode parent;
    private int totalUser;
    private QuadTreeNode children[];
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public MapRectangle getRect() {
		return rect;
	}
	public void setRect(MapRectangle rect) {
		this.rect = rect;
	}
	public QuadTreeNode getParent() {
		return parent;
	}
	public void setParent(QuadTreeNode parent) {
		this.parent = parent;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
    
	public void setQuadbyIndex(int i,QuadTreeNode t){
		this.children[i] = t;
	}
    public QuadTreeNode getQuadChildernbyIndex(int i){
    	return this.children[i];
    }
    public QuadTreeNode(){
    	rect = new MapRectangle();
    	totalUser = 0;
    }
    public void UserAutoIncrement(){
    	totalUser++;
    }
    public void initialChildren(){
    	children = new QuadTreeNode[4];
    }
}
