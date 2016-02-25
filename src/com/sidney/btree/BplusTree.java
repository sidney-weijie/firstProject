package com.sidney.btree;


import java.util.Random; 
import java.util.Vector;
 
public class BplusTree implements B { 
     
    /** 根节点 */ 
    protected Node root; 
     
    /** 阶数，M值 */ 
    protected int order; 
     
    /** 叶子节点的链表头*/ 
    protected Node head; 
     
    public Node getHead() { 
        return head; 
    } 
 
    public void setHead(Node head) { 
        this.head = head; 
    } 
 
    public Node getRoot() { 
        return root; 
    } 
 
    public void setRoot(Node root) { 
        this.root = root; 
    } 
 
    public int getOrder() { 
        return order; 
    } 
 
    public void setOrder(int order) { 
        this.order = order; 
    } 
 
    @Override 
    public Object get(Comparable key) { 
        return root.get(key); 
    } 
 
    @Override 
    public void remove(Comparable key) { 
        root.remove(key, this); 
 
    } 
 
    @Override 
    public void insertOrUpdate(Comparable key, Object obj) { 
        root.insertOrUpdate(key, obj, this); 
 
    } 
     
    public BplusTree(int order){ 
        if (order < 3) { 
            System.out.print("order must be greater than 2"); 
            System.exit(0); 
        } 
        this.order = order; 
        root = new Node(true, true); 
        head = root; 
    } 
    

    
     
    //测试 
    /*   public static void main(String[] args) { 
        BplusTree tree = new BplusTree(4); 
        Random random = new Random(); 
        long current = System.currentTimeMillis(); 
      Vector<Long> vec = new Vector<Long>();
        vec.add(new Long(5));
        vec.add(new Long(89));
        vec.add(new Long(34));
        vec.add(new Long(67));
        vec.add(new Long(23));
        vec.add(new Long(43));
        vec.add(new Long(2));
        vec.add(new Long(4));
        */
      /*  for (int j = 0; j < 1000; j++) { 
            for (int i = 0; i < 100; i++) { 
                int randomNumber = random.nextInt(1000); 
                tree.insertOrUpdate(randomNumber, randomNumber); 
            } 

        } 
    
        tree.insertOrUpdate(2, 2);
        tree.insertOrUpdate(3, 3);
        tree.insertOrUpdate(6, 6);
        tree.insertOrUpdate(23, 23);
       tree.insertOrUpdate(45, 45);
         tree.insertOrUpdate(78, 78);
         tree.insertOrUpdate(90, 90);
        tree.insertOrUpdate(91, 91);
        tree.insertOrUpdate(92, 92);
        tree.insertOrUpdate(93, 93);
        tree.insertOrUpdate(94, 94);
        tree.insertOrUpdate(95, 95);
        tree.insertOrUpdate(96, 96);
        tree.insertOrUpdate(97, 97);
        tree.insertOrUpdate(98, 98);
        tree.insertOrUpdate(99, 99);
        tree.insertOrUpdate(100, 100);
         tree.insertOrUpdate(101, 101);
        tree.insertOrUpdate(102, 102);
        tree.insertOrUpdate(25, 25);       
        tree.insertOrUpdate(103, 103);
        tree.insertOrUpdate(104,104);
        tree.insertOrUpdate(105, 105);
        tree.insertOrUpdate(106, 106);
        tree.insertOrUpdate(107,107);
        tree.insertOrUpdate(26, 26);
        tree.insertOrUpdate(27, 27);
        
        
        long duration = System.currentTimeMillis() - current; 
        System.out.println("time elpsed for duration: " + duration); 
        
        System.out.println("start");
        for(int i = 0; i < tree.getRoot().getChildren().size();i++){
        	System.out.println(tree.getRoot().getChildrenCount().get(i));
        }
        System.out.println("end");
        int total = 0;
        System.out.println(tree.getRoot().getSerialNum(107, total));
        
    }   */
 
} 
