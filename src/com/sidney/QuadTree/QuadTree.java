package com.sidney.QuadTree;

public class QuadTree {
    private QuadTreeNode root;
    public QuadTree(){
    	root = null;
    }
    
    public QuadTreeNode createQuadTree(MapRectangle t,QuadTreeNode parent, int depth){
    	if(null != parent && depth == parent.getDepth()){
    		return null;
    	}
   
    	QuadTreeNode head = new QuadTreeNode();
    	head.initialChildren();
    	if(null == parent){
    		head.setDepth(1);
    	}else{
    		head.setDepth(parent.getDepth()+1);		
    	}
    	//����ֵ
    	head.setRect(t);
		head.setTotalUser(0);
		head.setParent(parent);
    	
    	//�ķ�����
    	Point temp = new Point();
    	MapRectangle p0 = new MapRectangle();
    	MapRectangle p1 = new MapRectangle();
    	MapRectangle p2 = new MapRectangle();
    	MapRectangle p3 = new MapRectangle();
    	MapRectangle[] p = new MapRectangle[4];
    	temp.setX((t.getRightUp().getX()+t.getLeftDown().getX())/2.0);
    	temp.setY((t.getRightUp().getY()+t.getLeftDown().getY())/2.0);
    	
    	
    	p0.setLeftDown(new Point(t.getLeftDown()));
    	p0.setRightUp(temp);
    	
    	p1.setLeftDown(new Point(((t.getRightUp().getX()+t.getLeftDown().getX())/2.0),
    			   t.getLeftDown().getY()) );
    	p1.setRightUp(new Point(t.getRightUp().getX(),((t.getRightUp().getY()+t.getLeftDown().getY())/2.0)));		        
    	
    	p2.setLeftDown(new Point(t.getLeftDown().getX(),((t.getLeftDown().getY()+t.getRightUp().getY())/2.0)));
    	p2.setRightUp(new Point(((t.getLeftDown().getX()+t.getRightUp().getX())/2.0),t.getRightUp().getY()));
    	
    	p3.setLeftDown(new Point(((t.getLeftDown().getX()+t.getRightUp().getX())/2.0),((t.getLeftDown().getY()+t.getRightUp().getY())/2.0)));
    	p3.setRightUp(new Point(t.getRightUp().getX(),t.getRightUp().getY()));
    	/*
    	*     ___________
    	*      P[0]|P[1]
    	*     _____|_____
    	*      P[2]|P[3]
    	*     _____|_____
    	*/
    	/*
    	*�ݹ鴴���Ĳ���
    	*/
    	
    	head.setQuadbyIndex(0, createQuadTree(p0,head,depth));
    	head.setQuadbyIndex(1, createQuadTree(p1,head,depth));
    	head.setQuadbyIndex(2, createQuadTree(p2,head,depth));
    	head.setQuadbyIndex(3, createQuadTree(p3,head,depth));
    	/*
    	for(int i = 0;i < 4; i++ ){
    		head.setQuadbyIndex(i, createQuadTree(p[i],head,depth));
    	}
    	*/
    	return head;
    }

    public void setRoot(QuadTreeNode t){
    	root = t;
    }
    public QuadTreeNode getRoot(){
    	return root;
    }
    
    public QuadTreeNode insertUser(Point userPoint){
    	if(null == root)
    		return null;
    	QuadTreeNode temp = root;
    	QuadTreeNode q = null;
    	
    	//�����ж��û����ڵĵ��Ƿ��ڱ�������
    	if(MapRectangle.CompareRect(root.getRect(), userPoint))
    	{   
    		root.UserAutoIncrement();
    	}else 
    		return null;
    
    	/* �ж������ĸ������䣬��������� */
    	while(null != temp.getQuadChildernbyIndex(0)){
    		for(int i = 0 ;i < 4; i++){
    			q = temp.getQuadChildernbyIndex(i);
    			if(MapRectangle.CompareRect(q.getRect(), userPoint))
    			{
    				/* 
    				 * �������
    				q.getRect().print();
    				userPoint.print();
    				*/
    				temp = q;
    				temp.UserAutoIncrement();
    				break;
    			}
    		}
    	}
    	return temp;
    }
    
    
    
    static public void travesal(QuadTreeNode head){
    	if(head == null)
    		return;
    	visit(head);
    	for(int i = 0;i < 4;i++){
    		travesal(head.getQuadChildernbyIndex(i));
    	}
    }
    public static void visit(QuadTreeNode head){
    	System.out.println("("+head.getRect().getLeftDown().getX()+","+head.getRect().getLeftDown().getY()+"), ("
    			            +head.getRect().getRightUp().getX()+","+head.getRect().getRightUp().getY()+")  height="+head.getDepth()+
    			            "  totalUser="+head.getTotalUser() 
    			        );
    }
}
