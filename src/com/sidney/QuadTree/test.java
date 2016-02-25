package com.sidney.QuadTree;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        QuadTree t = new QuadTree();
        MapRectangle m = new MapRectangle(new Point(0.0,0.0), new Point(1024.0,1024.0));
        QuadTreeNode qNode = t.createQuadTree(m, null, 3);
        t.setRoot(qNode);
        
        /*System.out.println(qNode.getDepth());
        System.out.println(qNode.getQuadChildernbyIndex(1).getRect().getLeftDown().getX());
        System.out.println(qNode.getQuadChildernbyIndex(0).getQuadChildernbyIndex(0).getDepth());
        System.out.println(qNode.getRect().getLeftDown().getX());
        */ QuadTree.travesal(t.getRoot());
	}

}
