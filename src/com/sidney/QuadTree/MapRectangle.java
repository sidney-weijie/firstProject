package com.sidney.QuadTree;

public class MapRectangle {
    private Point leftDown;
    private Point rightUp;
	public Point getLeftDown() {
		return leftDown;
	}
	public void setLeftDown(Point leftDown) {
		//System.out.println("set leftDown");
		this.leftDown = leftDown;
	   /*
		this.leftDown.setX(leftDown.getX());
		this.leftDown.setY(leftDown.getY());
	   */
	}
	public Point getRightUp() {
		return rightUp;
	}
	public void setRightUp(Point rightUp) {
		
		this.rightUp = rightUp;
		//this.rightUp = rightUp;
		/*this.rightUp.setX(rightUp.getX());
		this.rightUp.setY(rightUp.getY());
		*/
	}
	public MapRectangle(Point leftDown, Point rightUp) {
		this.leftDown = leftDown;
		this.rightUp = rightUp;
		/*this.leftDown = new Point(leftDown);
		this.rightUp = new Point(rightUp);
	   */
	}
    
	public MapRectangle() {
		// TODO Auto-generated constructor stub
		/*this.leftDown = new Point();
		this.rightUp = new Point();
	    */
	}
	public static MapRectangle combineTwoMapRect(MapRectangle r,MapRectangle s){
		
		Point left = new Point();
		Point right = new Point();
		left.setX(r.getLeftDown().getX()<s.getLeftDown().getX()?r.getLeftDown().getX():s.getLeftDown().getX());
		left.setY(r.getLeftDown().getY()<s.getLeftDown().getY()?r.getLeftDown().getY():s.getLeftDown().getY());
	    right.setX(r.getRightUp().getX()>s.getRightUp().getX()?r.getRightUp().getX():s.getRightUp().getX());
	    right.setY(r.getRightUp().getY()>s.getRightUp().getY()?r.getRightUp().getY():s.getRightUp().getY());
	    return new MapRectangle(left, right);
	}
    public static boolean CompareRect(MapRectangle rect,Point userPoint){
    	double x = userPoint.getX() + 0.001;
    	double y = userPoint.getY() + 0.001;
    	
    	if((x-rect.getLeftDown().getX())>0.000001 && (rect.getRightUp().getX()-x)>0.000001){
    		if((y-rect.getLeftDown().getY())>0.000001 && (rect.getRightUp().getY()-y)>0.000001)
    			return true;
    	}
    	
    	return false;
    }
    //用于判断匿名区大小是否每个符合要求
    public static boolean LargerThanAsked(MapRectangle rect,double length,double width){
    	if((rect.getRightUp().getX()-rect.getLeftDown().getX())>length &&
    			(rect.getRightUp().getY()-rect.getLeftDown().getY())>width)
    	         return true;
    	
    	return false;
    }
    
    public static boolean LargerThanAsked(MapRectangle rect,int area){
           
    	return rect.CalArea()>area;
    }
    
    public double CalArea(){
    	return (rightUp.getX()-leftDown.getX())*(rightUp.getY()-leftDown.getY());
    }
    //判断两个合并的区域大小要求是否符合
    public static boolean LargerThan2(MapRectangle rect,double length,double width){
    	if((length<2*(rect.getRightUp().getX()-rect.getLeftDown().getX()))&&
    			(width<2*(rect.getRightUp().getY()-rect.getLeftDown().getY()))||
    			     (length<(rect.getRightUp().getX()-rect.getLeftDown().getX()))&&
    			         (width<2*(rect.getRightUp().getY()-rect.getLeftDown().getY()))
    			)
    		return true;
    	
    	return false;
    }
    //判断合并之后的区域面积是否符合要求
    public static boolean LargerThan2(MapRectangle rect,int area){
    	
    	    if((2*rect.CalArea())>area)
    	    	return true;
    	    else 
    	    	return false;
    	    
    }
    
    public void Print(){
    	System.out.print("[("+leftDown.getX()+","+leftDown.getY()+"),("
    			            +rightUp.getX()+","+rightUp.getY()+")]"
    			);
    }
    
    /*
     * 判断a是否包含b，也就是说b是否是a的子区间
     */
    public static boolean includeRect(MapRectangle a,MapRectangle b){
    	double precision = -0.00001;
    	
    	
    	
    	if((b.getLeftDown().getX()-a.getLeftDown().getX()>precision)&&(b.getLeftDown().getY()-a.getLeftDown().getY())>precision){
    		if((a.getRightUp().getX()-b.getRightUp().getX()>precision)&&(a.getRightUp().getY()-b.getRightUp().getY())>precision){
    			return true;
    		}	
    	}
    	
    	return false;
    }
    /*
     * 判断参数里的矩形是否为真子集
     */
    public boolean isRealSubset(MapRectangle b){
    	double precision = 0.0;
    	if((b.getLeftDown().getX()-this.getLeftDown().getX()>precision)&&(b.getLeftDown().getY()-this.getLeftDown().getY())>precision){
    		if((this.getRightUp().getX()-b.getRightUp().getX()>precision)&&(this.getRightUp().getY()-b.getRightUp().getY())>precision){
    			return true;
    		}	
    	}
    	
    	return false;
    }
    
    public static MapRectangle getIntersection(MapRectangle a,MapRectangle b){
    	
    	if(a == null ||b == null){
    		return null;
    	}
    	
    	double minX = a.getLeftDown().getX() > b.getLeftDown().getX()?a.getLeftDown().getX():b.getLeftDown().getX();
    	double minY = a.getLeftDown().getY() > b.getLeftDown().getY()?a.getLeftDown().getY():b.getLeftDown().getY();
    	double maxX = a.getRightUp().getX() < b.getRightUp().getX()?a.getRightUp().getX():b.getRightUp().getX();
    	double maxY = a.getRightUp().getY() < b.getRightUp().getY()?a.getRightUp().getY():b.getRightUp().getY();
    	
    	
    	return new MapRectangle(new Point(minX,minY),new Point(maxX,maxY));
    	
    }
    
    
    public boolean isIntersects(MapRectangle a,MapRectangle b){
    	
    	if(a == null || b == null){
    		return false;
    	}
    	
    	return !(b.getLeftDown().getX()>a.getRightUp().getX()||
    			b.getRightUp().getX()<a.getLeftDown().getX()||
    			b.getLeftDown().getY()>a.getRightUp().getY()||
    			b.getRightUp().getY()<a.getLeftDown().getY()
    			);
    }
    
    public static void main(String[]args){
    	MapRectangle a = new MapRectangle(new Point(0.0,0.0),new Point(64.0,64.0));
    	MapRectangle b = new MapRectangle(new Point(16,16),new Point(72,32));
    	
    	MapRectangle temp = getIntersection(a,b);
    	if(temp != null){
    		temp.Print();
    	}
    	
    }
    
    public String toString(){
    	String temp = "("+leftDown.getX()+","+leftDown.getY()+"),("+rightUp.getX()+","+rightUp.getY()+")";
    	return temp;
    }
    public String toString2(){
    	String temp = "  "+leftDown.getX()+"  "+leftDown.getY()+"  "+rightUp.getX()+"  "+rightUp.getY();
    	return temp;
    }
    
}
