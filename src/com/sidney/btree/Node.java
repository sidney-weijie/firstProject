package com.sidney.btree;

import java.util.AbstractMap.SimpleEntry; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map.Entry; 
 
public class Node { 
     

    protected boolean isLeaf;    /** �Ƿ�ΪҶ�ӽڵ� */    
    protected boolean isRoot;    /** �Ƿ�Ϊ���ڵ�*/ 
    protected Node parent;       /** ���ڵ� */   
    protected Node previous;     /** Ҷ�ڵ��ǰ�ڵ�*/ 
    protected Node next;         /** Ҷ�ڵ�ĺ�ڵ�*/ 
    protected List<Entry<Comparable, Object>> entries;     /** �ڵ�Ĺؼ��� */ 
    protected List<Node> children = null;    /** �ӽڵ� */ 
    protected List<Long> childrenCount = null;
    
    public Node(boolean isLeaf) { 
        this.isLeaf = isLeaf; 
        entries = new ArrayList<Entry<Comparable, Object>>(); 
         
        if (!isLeaf) { 
            children = new ArrayList<Node>(); 
            childrenCount = new ArrayList<Long>();
        } 
        
    } 
 
    public boolean countPlus(int index){
    	if(childrenCount.size()>index){
    		childrenCount.set(index, childrenCount.get(index)+1);
    		return true;
    	}
    	return false;
    }


	public List<Long> getChildrenCount() {
		return childrenCount;
	}




	public void setChildrenCount(List<Long> childrenCount) {
		this.childrenCount = childrenCount;
	}




	public Node(boolean isLeaf, boolean isRoot) { 
        this(isLeaf); 
        this.isRoot = isRoot; 
        if(!isLeaf)
        {
        	childrenCount = new ArrayList<Long>();	
        }
        
    } 
    
	public int getSerialNum(Comparable key,int total){
		if(isLeaf){
			for(Entry<Comparable,Object>entry : entries){
				if(entry.getKey().compareTo(key) == 0){
					return total;
				}
				total++;
			}
			return -1;
		}else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).getSerialNum(key, total); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
            	for(int i = 0 ;i < entries.size()-1;i++){
            		total += childrenCount.get(i);
            	}
                return children.get(children.size()-1).getSerialNum(key, total); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                	
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                        return children.get(i).getSerialNum(key, total); 
                    } 
                    total += childrenCount.get(i);
                }    
            } 
        } 
		return -1;
	}
	/*
	 * ��ȡ(L,K)Diversity Cloak�µĶ�Ӧ�ı��
	 * ���ص�һ����key��Ľڵ�
	 * ����entry�����һ���ڵ��Ա�keyС����ô���Ƿ���������ڵ��Ӧ�ı��
	 */
	public int getSerialNumForLK(Comparable key,int total){
		if(isLeaf){
			
			for(Entry<Comparable,Object>entry : entries){
				if(entry.getKey().compareTo(key) >= 0){
					return total;
				}
				total++;
			}
			return --total;
		}else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
						
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
   
                return children.get(0).getSerialNumForLK(key, total); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
            	for(int i = 0 ;i < entries.size()-1;i++){
            		total += childrenCount.get(i);
            	}
                return children.get(children.size()-1).getSerialNumForLK(key, total); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                	
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                        return children.get(i).getSerialNumForLK(key, total); 
                    } 
                    total += childrenCount.get(i);
                	
                }    
                
            } 
            
            
        } 
		return -1;
	}
	
	
    public Object get(Comparable key) { 
         
        //�����Ҷ�ӽڵ� 
        if (isLeaf) { 
            for (Entry<Comparable, Object> entry : entries) { 
                if (entry.getKey().compareTo(key) == 0) { 
                    //�����ҵ��Ķ��� 
                    return entry.getValue(); 
                } 
            } 
            //δ�ҵ���Ҫ��ѯ�Ķ��� 
            return null; 
             
        //�������Ҷ�ӽڵ� 
        }else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).get(key); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                return children.get(children.size()-1).get(key); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                        return children.get(i).get(key); 
                    } 
                }    
            } 
        } 
         
        return null; 
    } 
     
    public Node getNode(Comparable key){
        //�����Ҷ�ӽڵ� 
        if (isLeaf) { 
            for (Entry<Comparable, Object> entry : entries) { 
                if (entry.getKey().compareTo(key) == 0) { 
                    //�����ҵ��Ķ��� 
                    return this; 
                } 
            } 
            //δ�ҵ���Ҫ��ѯ�Ķ��� 
            return null; 
             
        //�������Ҷ�ӽڵ� 
        }else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).getNode(key); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                return children.get(children.size()-1).getNode(key); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                        return children.get(i).getNode(key); 
                    } 
                }    
            } 
        } 
         
        return null; 
    }
    /*
     * getNodeForLK�ṩ��LKDiversityCloak��ȡ�ڵ�ķ���
     * ͨ��key�������յ�Ҷ�ӽڵ�
     * ��Ȼ���keyһ�㲻��������ڵ��ϣ�����Ҳ����һ�µ�����
     */
    public Node getNodeForLK(Comparable key){
        //�����Ҷ�ӽڵ� 
        if (isLeaf) { 
            return this; 
        //�������Ҷ�ӽڵ� 
        }else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) < 0) { 
                return children.get(0).getNodeForLK(key); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) > 0) { 
                return children.get(children.size()-1).getNodeForLK(key); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) >= 0) { 
                        return children.get(i).getNodeForLK(key); 
                    } 
                }    
            } 
        } 
         
        return null; 
    }
    
    
    
        
    public void insertOrUpdate(Comparable key, Object obj, BplusTree tree){ 
        //�����Ҷ�ӽڵ� 
        if (isLeaf){ 
            //����Ҫ���ѣ�ֱ�Ӳ������� 
            if (contains(key) || entries.size() < tree.getOrder()){ 
                
            	insertOrUpdate(key, obj); 
                if (parent != null) { 
                    //���¸��ڵ� 
                    parent.updateInsert(tree); 
                } 
                /**************************/
                /*
                 * �ڵ����������
                 * ���ϲ���Ҫ���ѣ����������ڵ�·���϶�Ӧ�Ľڵ�����Ӧ����+1����
                 */
                if(!isRoot()){
	                Node qPar = parent;
	                Node p = this; 
	                int index;
	                while(qPar!=null){	                
	                	index = qPar.getChildren().indexOf(p);	                	
	                	qPar.countPlus(index);	                	
	                	qPar = qPar.getParent();
	                	p = p.getParent();
	                }
                }
                
                /***************************/
            //��Ҫ����   
            }else { 
                //���ѳ����������ڵ� 
                Node left = new Node(true); 
                Node right = new Node(true); 
                //�������� 
                if (previous != null){ 
                    previous.setNext(left); 
                    left.setPrevious(previous); 
                } 
                if (next != null) { 
                    next.setPrevious(right); 
                    right.setNext(next); 
                } 
                if (previous == null){ 
                    tree.setHead(left); 
                } 
                
                left.setNext(right); 
                right.setPrevious(left); 
                previous = null; 
                next = null; 
                 
                //���������ڵ�ؼ��ֳ��� 
                int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;  
                int rightSize = (tree.getOrder() + 1) / 2; 
                /** ���ýڵ��ڵ����� */
                
                
                
                
                insertOrUpdate(key, obj); 
              //����ԭ�ڵ�ؼ��ֵ����ѳ������½ڵ� 
                for (int i = 0; i < leftSize; i++){ 
                    left.getEntries().add(entries.get(i)); 
              
                } 
                for (int i = 0; i < rightSize; i++){ 
                    right.getEntries().add(entries.get(leftSize + i)); 
                } 

                //������Ǹ��ڵ� 
                if (parent != null) { 
                    //�������ӽڵ��ϵ 
                    int index = parent.getChildren().indexOf(this); 
                    parent.getChildren().remove(this); 
                    left.setParent(parent); 
                    right.setParent(parent); 
                    parent.getChildren().add(index,left); 
                    parent.getChildren().add(index + 1, right);
                    /*****************************/
                    /*
                     * �ڵ����������
                     */
                    
                    if(isLeaf()&&(!parent.isRoot())){
                    	parent.getChildrenCount().set(index,(long)leftSize);
                    	parent.getChildrenCount().add(index+1,(long)rightSize);
                    }else{
                    	parent.getChildrenCount().set(index, (long)leftSize);
                    	parent.getChildrenCount().add(index+1, (long)rightSize);
                    }
                    /*
                     * ���ϲ���Ҫ�ٷ��ѣ����������ڵ�·���϶�Ӧ�Ľڵ�����Ӧ����+1����
                     */
                    if(!parent.isRoot()){
                        if(parent.getEntries().size() <= tree.order){
    	                   Node qPar = parent.getParent();
    	                   Node p = parent; 
    	                   int index1;
    	                   while(qPar!=null){
    	                
    	                	index1 = qPar.getChildren().indexOf(p);
    	                	
    	                	qPar.countPlus(index1);   	                	   	           	                	
    	                    qPar = qPar.getParent();
    	                    p = p.getParent();
    	                  }
                    }
                    }
                    /*****************************/
                    setEntries(null); 
                    setChildren(null); 
                     
                    //���ڵ�������¹ؼ��� 
                    parent.updateInsert(tree); 
                    setParent(null); 
                //����Ǹ��ڵ�     
                }else { 
                    isRoot = false; 
                    Node parent = new Node(false, true); 
                    tree.setRoot(parent); 
                    left.setParent(parent); 
                    right.setParent(parent); 
                    parent.getChildren().add(left); 
                    parent.getChildren().add(right); 
                    setEntries(null); 
                    setChildren(null); 
                    /***************************/
                    /*
                     * ���Ǹ��ڵ㣬�ڵ���Ѻ��µĸ��ڵ���������Һ��ӵ�����
                     */
                    parent.getChildrenCount().add((long)leftSize);
                	parent.getChildrenCount().add((long)rightSize); 
                	/***************************/
                    //���¸��ڵ� 
                    parent.updateInsert(tree); 
                } 
                 
 
            } 
             
        //�������Ҷ�ӽڵ� 
        }else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 

                children.get(0).insertOrUpdate(key, obj, tree); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
     
            	
                children.get(children.size()-1).insertOrUpdate(key, obj, tree); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                    	
                    	children.get(i).insertOrUpdate(key, obj, tree); 
                        break; 
                    } 
                }    
            } 
        } 
    } 
     
    /** ����ڵ���м�ڵ�ĸ��� */ 
    protected void updateInsert(BplusTree tree){ 
 
        validate(this, tree); 
         
        //����ӽڵ�����������������Ҫ���Ѹýڵ�    
        if (children.size() > tree.getOrder()) { 
            //���ѳ����������ڵ� 
            Node left = new Node(false); 
            Node right = new Node(false); 
            //���������ڵ�ؼ��ֳ���    ����Ϊǰ����Ȼ�ǰ��Ⱥ����һ��
            int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2; 
            int rightSize = (tree.getOrder() + 1) / 2; 
            /*******************************/
            /*
             * 
             * ˳������count����
             */
            //�����ӽڵ㵽���ѳ������½ڵ㣬�����¹ؼ��� 
            for (int i = 0; i < leftSize; i++){ 
                left.getChildren().add(children.get(i)); 
                left.getEntries().add(new SimpleEntry(children.get(i).getEntries().get(0).getKey(), null)); 
                /*****************************/
                if(children.get(i).isLeaf()){
                	left.getChildrenCount().add(new Long(children.get(i).entries.size()));
                	
                } else {

                	long temp = 0;
                	for(int j = 0; j < children.get(i).children.size();j++){
                		temp += children.get(i).getChildrenCount().get(j);
                		
                     }
                	left.getChildrenCount().add(new Long(temp));                	
                	
                }
                /*****************************/
                children.get(i).setParent(left); 
            } 
            for (int i = 0; i < rightSize; i++){ 
                right.getChildren().add(children.get(leftSize + i)); 
                right.getEntries().add(new SimpleEntry(children.get(leftSize + i).getEntries().get(0).getKey(), null)); 
                /*****************************/
                if(children.get(0).isLeaf()){
                	
                	right.getChildrenCount().add((long)(children.get(leftSize+i).entries.size()));
                }  else{
                   	long temp = 0;
                   
                   	for(int j = 0; j < children.get(leftSize+i).children.size();j++){
                		
                		temp += children.get(leftSize+i).getChildrenCount().get(j);               		
                     }                 
                	right.getChildrenCount().add((long)temp);
                                	
                }
                /*****************************/
                children.get(leftSize + i).setParent(right); 
            } 
             
            //������Ǹ��ڵ� 
            if (parent != null) { 
                //�������ӽڵ��ϵ 
                int index = parent.getChildren().indexOf(this); 
                parent.getChildren().remove(this); 
                left.setParent(parent); 
                right.setParent(parent); 
                parent.getChildren().add(index,left); 
                parent.getChildren().add(index + 1, right); 
                /****************************/
                int leftCount = 0;
                for(int i = 0;i < left.getEntries().size();i++){
                	leftCount += left.getChildrenCount().get(i);
                }
                int rightCount = 0;
                for(int i = 0;i < right.getEntries().size();i++){
                	rightCount += right.getChildrenCount().get(i);
                }
                parent.getChildrenCount().set(index,(long)leftCount);
                parent.getChildrenCount().add(index+1,(long)rightCount);                              
                /***************************/
                setEntries(null); 
                setChildren(null); 
                //���ڵ���¹ؼ��� 
               
                parent.updateInsert(tree); 
                setParent(null); 
            //����Ǹ��ڵ�     
            }else { 
                isRoot = false; 
                Node parent = new Node(false, true); 
                tree.setRoot(parent); 
                left.setParent(parent); 
                right.setParent(parent); 
                parent.getChildren().add(left); 
                parent.getChildren().add(right); 
                /********************/
                int leftCount = 0;
                for(int i = 0;i < left.getEntries().size();i++){
                	leftCount += left.getChildrenCount().get(i);
                }
                int rightCount = 0;
                for(int i = 0;i < right.getEntries().size();i++){
                	rightCount += right.getChildrenCount().get(i);
                }
                parent.getChildrenCount().add((long)(leftCount));
                parent.getChildrenCount().add((long)(rightCount));

                /********************/
                setEntries(null); 
                setChildren(null); 
                setChildrenCount(null);
                //�Ǹ��ڵ���������Ҫ�����ڵ�
                //���¸��ڵ� 
                parent.updateInsert(tree); 
            } 
        }
      
    } 
     
    /** �����ڵ�ؼ���*/ 
    protected static void validate(Node node, BplusTree tree) { 
         
        // ����ؼ��ָ������ӽڵ������ͬ 
        if (node.getEntries().size() == node.getChildren().size()) { 
            for (int i = 0; i < node.getEntries().size(); i++) { 
                Comparable key = node.getChildren().get(i).getEntries().get(0).getKey(); 
                if (node.getEntries().get(i).getKey().compareTo(key) != 0) { 
                    node.getEntries().remove(i); 
                    node.getEntries().add(i, new SimpleEntry(key, null)); 
                    if(!node.isRoot()){ 
                        validate(node.getParent(), tree); 
                    } 
                } 
            } 
            // ����ӽڵ��������ڹؼ��ָ������Դ���M / 2����С��M�����Ҵ���2 
        } else if (node.isRoot() && node.getChildren().size() >= 2  
                ||node.getChildren().size() >= tree.getOrder() / 2  
                && node.getChildren().size() <= tree.getOrder() 
                && node.getChildren().size() >= 2) { 
            node.getEntries().clear(); 
            for (int i = 0; i < node.getChildren().size(); i++) { 
                Comparable key = node.getChildren().get(i).getEntries().get(0).getKey(); 
                node.getEntries().add(new SimpleEntry(key, null)); 
                if (!node.isRoot()) { 
                    validate(node.getParent(), tree); 
                } 
            } 
        } 
    } 
     
    /** ɾ���ڵ���м�ڵ�ĸ���*/ 
    protected void updateRemove(BplusTree tree) { 
         
        validate(this, tree); 
 
        // ����ӽڵ���С��M / 2����С��2������Ҫ�ϲ��ڵ� 
        if (children.size() < tree.getOrder() / 2 || children.size() < 2) { 
            if (isRoot) { 
                // ����Ǹ��ڵ㲢���ӽڵ������ڵ���2��OK 
                if (children.size() >= 2) { 
                    return; 
                // �������ӽڵ�ϲ� 
                } else { 
                    Node root = children.get(0); 
                    tree.setRoot(root); 
                    root.setParent(null); 
                    root.setRoot(true); 
                    setEntries(null); 
                    setChildren(null); 
                } 
            } else { 
                //����ǰ��ڵ�  
                int currIdx = parent.getChildren().indexOf(this); 
                int prevIdx = currIdx - 1; 
                int nextIdx = currIdx + 1; 
                Node previous = null, next = null; 
                if (prevIdx >= 0) { 
                    previous = parent.getChildren().get(prevIdx); 
                } 
                if (nextIdx < parent.getChildren().size()) { 
                    next = parent.getChildren().get(nextIdx); 
                } 
                 
                // ���ǰ�ڵ��ӽڵ�������M / 2���Ҵ���2������䴦�貹 
                if (previous != null  
                        && previous.getChildren().size() > tree.getOrder() / 2 
                        && previous.getChildren().size() > 2) { 
                    //ǰҶ�ӽڵ�ĩβ�ڵ���ӵ���λ 
                    int idx = previous.getChildren().size() - 1; 
                    Node borrow = previous.getChildren().get(idx); 
                    previous.getChildren().remove(idx); 
                    borrow.setParent(this); 
                    children.add(0, borrow); 
                    validate(previous, tree); 
                    validate(this, tree); 
                    parent.updateRemove(tree); 
                     
                // �����ڵ��ӽڵ�������M / 2���Ҵ���2������䴦�貹 
                } else if (next != null  
                        && next.getChildren().size() > tree.getOrder() / 2 
                        && next.getChildren().size() > 2) { 
                    //��Ҷ�ӽڵ���λ��ӵ�ĩβ 
                    Node borrow = next.getChildren().get(0); 
                    next.getChildren().remove(0); 
                    borrow.setParent(this); 
                    children.add(borrow); 
                    validate(next, tree); 
                    validate(this, tree); 
                    parent.updateRemove(tree); 
                     
                // ������Ҫ�ϲ��ڵ� 
                } else { 
                    // ͬǰ��ڵ�ϲ� 
                    if (previous != null  
                            && (previous.getChildren().size() <= tree.getOrder() / 2 || previous.getChildren().size() <= 2)) { 
                         
                        for (int i = previous.getChildren().size() - 1; i >= 0; i--) { 
                            Node child = previous.getChildren().get(i); 
                            children.add(0, child); 
                            child.setParent(this); 
                        } 
                        previous.setChildren(null); 
                        previous.setEntries(null); 
                        previous.setParent(null); 
                        parent.getChildren().remove(previous); 
                        validate(this, tree); 
                        parent.updateRemove(tree); 
                         
                    // ͬ����ڵ�ϲ� 
                    } else if (next != null  
                            && (next.getChildren().size() <= tree.getOrder() / 2 || next.getChildren().size() <= 2)) { 
 
                        for (int i = 0; i < next.getChildren().size(); i++) { 
                            Node child = next.getChildren().get(i); 
                            children.add(child); 
                            child.setParent(this); 
                        } 
                        next.setChildren(null); 
                        next.setEntries(null); 
                        next.setParent(null); 
                        parent.getChildren().remove(next); 
                        validate(this, tree); 
                        parent.updateRemove(tree); 
                    } 
                } 
            } 
        } 
    } 
     
    public boolean remove(Comparable key, BplusTree tree){ 
        //�����Ҷ�ӽڵ� 
        if (isLeaf){ 
             
            //����������ùؼ��֣���ֱ�ӷ��� 
            if (!contains(key)){ 
                return false; 
            } 
             
            //�������Ҷ�ӽڵ����Ǹ��ڵ㣬ֱ��ɾ�� 
            if (isRoot) { 
                remove(key); 
            }else { 
                //����ؼ���������M / 2��ֱ��ɾ�� 
                if (entries.size() > tree.getOrder() / 2 && entries.size() > 2) { 
                    remove(key); 
                }else { 
                    //�������ؼ�����С��M / 2������ǰ�ڵ�ؼ���������M / 2������䴦�貹
                	//�������ڵ�貹
                    if (previous != null  
                            && previous.getEntries().size() > tree.getOrder() / 2 
                            && previous.getEntries().size() > 2 
                            && previous.getParent() == parent) { 
                        int size = previous.getEntries().size(); 
                        Entry<Comparable, Object> entry = previous.getEntries().get(size - 1); 
                        previous.getEntries().remove(entry); 
                        //��ӵ���λ 
                        entries.add(0, entry); 
                        remove(key); 
                    //�������ؼ�����С��M / 2�����Һ�ڵ�ؼ���������M / 2������䴦�貹    
                    }else if (next != null  
                            && next.getEntries().size() > tree.getOrder() / 2 
                            && next.getEntries().size() > 2 
                            && next.getParent() == parent) { 
                        Entry<Comparable, Object> entry = next.getEntries().get(0); 
                        next.getEntries().remove(entry); 
                        //��ӵ�ĩβ 
                        entries.add(entry); 
                        remove(key); 
                    //������Ҫ�ϲ�Ҷ�ӽڵ�     
                    }else { 
                        //ͬǰ��ڵ�ϲ� ��ǰ��Ľڵ�ؼ��ִ���order/2  ����ǰ��Ĺؼ�����С�ڵ���2
                        if (previous != null  
                                && (previous.getEntries().size() <= tree.getOrder() / 2 || previous.getEntries().size() <= 2) 
                                && previous.getParent() == parent) { 
                            for (int i = previous.getEntries().size() - 1; i >=0; i--) { 
                                //��ĩβ��ʼ��ӵ���λ 
                                entries.add(0, previous.getEntries().get(i)); 
                            } 
                            remove(key); 
                            previous.setParent(null); 
                            previous.setEntries(null); 
                            parent.getChildren().remove(previous); 
                            //�������� 
                            if (previous.getPrevious() != null) {    //ǰ�ڵ�ǿյ������
                                Node temp = previous; 
                                temp.getPrevious().setNext(this); 
                                previous = temp.getPrevious(); 
                                temp.setPrevious(null); 
                                temp.setNext(null);                          
                            }else { 
                                tree.setHead(this); 
                                previous.setNext(null); 
                                previous = null; 
                            } 
                        //ͬ����ڵ�ϲ�    
                        }else if(next != null  
                                && (next.getEntries().size() <= tree.getOrder() / 2 || next.getEntries().size() <= 2) 
                                && next.getParent() == parent){ 
                            for (int i = 0; i < next.getEntries().size(); i++) { 
                                //����λ��ʼ��ӵ�ĩβ 
                                entries.add(next.getEntries().get(i)); 
                            } 
                            remove(key); 
                            next.setParent(null); 
                            next.setEntries(null); 
                            parent.getChildren().remove(next); 
                            //�������� 
                            if (next.getNext() != null) { 
                                Node temp = next; 
                                temp.getNext().setPrevious(this); 
                                next = temp.getNext(); 
                                temp.setPrevious(null); 
                                temp.setNext(null); 
                            }else { 
                                next.setPrevious(null); 
                                next = null; 
                            } 
                        } 
                    } 
                } 
                parent.updateRemove(tree); 
            } 
        //�������Ҷ�ӽڵ�   
        }else { 
            //���keyС�ڵ��ڽڵ�����ߵ�key���ص�һ���ӽڵ�������� 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                children.get(0).remove(key, tree); 
            //���key���ڽڵ����ұߵ�key�������һ���ӽڵ�������� 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                children.get(children.size()-1).remove(key, tree); 
            //�����ر�key���ǰһ���ӽڵ�������� 
            }else { 
                for (int i = 0; i < entries.size(); i++) { 
                    if (entries.get(i).getKey().compareTo(key) <= 0 && entries.get(i+1).getKey().compareTo(key) > 0) { 
                        children.get(i).remove(key, tree); 
                        break; 
                    } 
                }    
            } 
        } 
        return true;
    } 
     
    /** �жϵ�ǰ�ڵ��Ƿ�����ùؼ���*/ 
    protected boolean contains(Comparable key) { 
        for (Entry<Comparable, Object> entry : entries) { 
            if (entry.getKey().compareTo(key) == 0) { 
                return true; 
            } 
        } 
        return false; 
    } 
    /*
     * ���뵽��ǰ�ڵ�Ĺؼ��֣�true����Ϊ����ɹ���falseΪ�Ѵ��ڴ˽ڵ�
     */
    /** ���뵽��ǰ�ڵ�Ĺؼ�����*/ 
    protected boolean insertOrUpdate(Comparable key, Object obj){ 
        Entry<Comparable, Object> entry = new SimpleEntry<Comparable, Object>(key, obj); 
        Long ltemp = new Long(0);
        //����ؼ����б���Ϊ0����ֱ�Ӳ��� 
        if (entries.size() == 0) { 
            entries.add(entry); 

            return true; 
        } 
        //��������б� 
        for (int i = 0; i < entries.size(); i++) { 
            //����ùؼ��ּ�ֵ�Ѵ��ڣ������ 
            if (entries.get(i).getKey().compareTo(key) == 0) { 
                entries.get(i).setValue(obj); 
                return false; 
                
            //�������   
            }else if (entries.get(i).getKey().compareTo(key) > 0){ 
                //���뵽���� 
                if (i == 0) { 
                    entries.add(0, entry); 

                    return true; 
                //���뵽�м� 
                }else { 
                    entries.add(i, entry); 
                    return true; 
                } 
            } 
        } 
        //���뵽ĩβ 
        
        entries.add(entries.size(), entry);
        return true;
    } 
     
    /** ɾ���ڵ�*/ 
    protected void remove(Comparable key){ 
        int index = -1; 
        for (int i = 0; i < entries.size(); i++) { 
            if (entries.get(i).getKey().compareTo(key) == 0) { 
                index = i; 
                break; 
            } 
        } 
        if (index != -1) { 
            entries.remove(index); 
        } 
    } 
     
    public Node getPrevious() { 
        return previous; 
    } 
 
    public void setPrevious(Node previous) { 
        this.previous = previous; 
    } 
 
    public Node getNext() { 
        return next; 
    } 
 
    public void setNext(Node next) { 
        this.next = next; 
    } 
 
    public boolean isLeaf() { 
        return isLeaf; 
    } 
 
    public void setLeaf(boolean isLeaf) { 
        this.isLeaf = isLeaf; 
    } 
 
    public Node getParent() { 
        return parent; 
    } 
 
    public void setParent(Node parent) { 
        this.parent = parent; 
    } 
 
    public List<Entry<Comparable, Object>> getEntries() { 
        return entries; 
    } 
 
    public void setEntries(List<Entry<Comparable, Object>> entries) { 
        this.entries = entries; 
    } 
 
    public List<Node> getChildren() { 
        return children; 
    } 
 
    public void setChildren(List<Node> children) { 
        this.children = children; 
    } 
     
    public boolean isRoot() { 
        return isRoot; 
    } 
 
    public void setRoot(boolean isRoot) { 
        this.isRoot = isRoot; 
    } 
     
    public String toString(){ 
        StringBuilder sb = new StringBuilder(); 
        sb.append("isRoot: "); 
        sb.append(isRoot); 
        sb.append(", "); 
        sb.append("isLeaf: "); 
        sb.append(isLeaf); 
        sb.append(", "); 
        sb.append("keys: "); 
        for (Entry entry : entries){ 
            sb.append(entry.getKey()); 
            sb.append(", "); 
        } 
        sb.append(", "); 
        return sb.toString(); 
         
    } 
 
}