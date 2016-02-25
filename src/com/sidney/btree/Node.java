package com.sidney.btree;

import java.util.AbstractMap.SimpleEntry; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map.Entry; 
 
public class Node { 
     

    protected boolean isLeaf;    /** 是否为叶子节点 */    
    protected boolean isRoot;    /** 是否为根节点*/ 
    protected Node parent;       /** 父节点 */   
    protected Node previous;     /** 叶节点的前节点*/ 
    protected Node next;         /** 叶节点的后节点*/ 
    protected List<Entry<Comparable, Object>> entries;     /** 节点的关键字 */ 
    protected List<Node> children = null;    /** 子节点 */ 
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
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).getSerialNum(key, total); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
            	for(int i = 0 ;i < entries.size()-1;i++){
            		total += childrenCount.get(i);
            	}
                return children.get(children.size()-1).getSerialNum(key, total); 
            //否则沿比key大的前一个子节点继续搜索 
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
	 * 获取(L,K)Diversity Cloak下的对应的编号
	 * 返回第一个比key大的节点
	 * 存在entry中最后一个节点仍比key小，那么我们返回这个最后节点对应的编号
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
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
						
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
   
                return children.get(0).getSerialNumForLK(key, total); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
            	for(int i = 0 ;i < entries.size()-1;i++){
            		total += childrenCount.get(i);
            	}
                return children.get(children.size()-1).getSerialNumForLK(key, total); 
            //否则沿比key大的前一个子节点继续搜索 
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
         
        //如果是叶子节点 
        if (isLeaf) { 
            for (Entry<Comparable, Object> entry : entries) { 
                if (entry.getKey().compareTo(key) == 0) { 
                    //返回找到的对象 
                    return entry.getValue(); 
                } 
            } 
            //未找到所要查询的对象 
            return null; 
             
        //如果不是叶子节点 
        }else { 
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).get(key); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                return children.get(children.size()-1).get(key); 
            //否则沿比key大的前一个子节点继续搜索 
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
        //如果是叶子节点 
        if (isLeaf) { 
            for (Entry<Comparable, Object> entry : entries) { 
                if (entry.getKey().compareTo(key) == 0) { 
                    //返回找到的对象 
                    return this; 
                } 
            } 
            //未找到所要查询的对象 
            return null; 
             
        //如果不是叶子节点 
        }else { 
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                return children.get(0).getNode(key); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                return children.get(children.size()-1).getNode(key); 
            //否则沿比key大的前一个子节点继续搜索 
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
     * getNodeForLK提供给LKDiversityCloak获取节点的方法
     * 通过key返回最终的叶子节点
     * 当然这个key一般不会在这个节点上，可能也存在一致的情形
     */
    public Node getNodeForLK(Comparable key){
        //如果是叶子节点 
        if (isLeaf) { 
            return this; 
        //如果不是叶子节点 
        }else { 
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) < 0) { 
                return children.get(0).getNodeForLK(key); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) > 0) { 
                return children.get(children.size()-1).getNodeForLK(key); 
            //否则沿比key大的前一个子节点继续搜索 
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
        //如果是叶子节点 
        if (isLeaf){ 
            //不需要分裂，直接插入或更新 
            if (contains(key) || entries.size() < tree.getOrder()){ 
                
            	insertOrUpdate(key, obj); 
                if (parent != null) { 
                    //更新父节点 
                    parent.updateInsert(tree); 
                } 
                /**************************/
                /*
                 * 节点数处理代码
                 * 向上不需要分裂，则沿至根节点路径上对应的节点数都应进行+1操作
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
            //需要分裂   
            }else { 
                //分裂成左右两个节点 
                Node left = new Node(true); 
                Node right = new Node(true); 
                //设置链接 
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
                 
                //左右两个节点关键字长度 
                int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;  
                int rightSize = (tree.getOrder() + 1) / 2; 
                /** 设置节点内的数量 */
                
                
                
                
                insertOrUpdate(key, obj); 
              //复制原节点关键字到分裂出来的新节点 
                for (int i = 0; i < leftSize; i++){ 
                    left.getEntries().add(entries.get(i)); 
              
                } 
                for (int i = 0; i < rightSize; i++){ 
                    right.getEntries().add(entries.get(leftSize + i)); 
                } 

                //如果不是根节点 
                if (parent != null) { 
                    //调整父子节点关系 
                    int index = parent.getChildren().indexOf(this); 
                    parent.getChildren().remove(this); 
                    left.setParent(parent); 
                    right.setParent(parent); 
                    parent.getChildren().add(index,left); 
                    parent.getChildren().add(index + 1, right);
                    /*****************************/
                    /*
                     * 节点数处理代码
                     */
                    
                    if(isLeaf()&&(!parent.isRoot())){
                    	parent.getChildrenCount().set(index,(long)leftSize);
                    	parent.getChildrenCount().add(index+1,(long)rightSize);
                    }else{
                    	parent.getChildrenCount().set(index, (long)leftSize);
                    	parent.getChildrenCount().add(index+1, (long)rightSize);
                    }
                    /*
                     * 向上不需要再分裂，则沿至根节点路径上对应的节点数都应进行+1操作
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
                     
                    //父节点插入或更新关键字 
                    parent.updateInsert(tree); 
                    setParent(null); 
                //如果是根节点     
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
                     * 若是根节点，节点分裂后新的根节点上添加左右孩子的数量
                     */
                    parent.getChildrenCount().add((long)leftSize);
                	parent.getChildrenCount().add((long)rightSize); 
                	/***************************/
                    //更新根节点 
                    parent.updateInsert(tree); 
                } 
                 
 
            } 
             
        //如果不是叶子节点 
        }else { 
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 

                children.get(0).insertOrUpdate(key, obj, tree); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) {
     
            	
                children.get(children.size()-1).insertOrUpdate(key, obj, tree); 
            //否则沿比key大的前一个子节点继续搜索 
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
     
    /** 插入节点后中间节点的更新 */ 
    protected void updateInsert(BplusTree tree){ 
 
        validate(this, tree); 
         
        //如果子节点数超出阶数，则需要分裂该节点    
        if (children.size() > tree.getOrder()) { 
            //分裂成左右两个节点 
            Node left = new Node(false); 
            Node right = new Node(false); 
            //左右两个节点关键字长度    分裂为前后相等或前面比后面多一个
            int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2; 
            int rightSize = (tree.getOrder() + 1) / 2; 
            /*******************************/
            /*
             * 
             * 顺带更新count数据
             */
            //复制子节点到分裂出来的新节点，并更新关键字 
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
             
            //如果不是根节点 
            if (parent != null) { 
                //调整父子节点关系 
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
                //父节点更新关键字 
               
                parent.updateInsert(tree); 
                setParent(null); 
            //如果是根节点     
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
                //是根节点的情况下需要上升节点
                //更新根节点 
                parent.updateInsert(tree); 
            } 
        }
      
    } 
     
    /** 调整节点关键字*/ 
    protected static void validate(Node node, BplusTree tree) { 
         
        // 如果关键字个数与子节点个数相同 
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
            // 如果子节点数不等于关键字个数但仍大于M / 2并且小于M，并且大于2 
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
     
    /** 删除节点后中间节点的更新*/ 
    protected void updateRemove(BplusTree tree) { 
         
        validate(this, tree); 
 
        // 如果子节点数小于M / 2或者小于2，则需要合并节点 
        if (children.size() < tree.getOrder() / 2 || children.size() < 2) { 
            if (isRoot) { 
                // 如果是根节点并且子节点数大于等于2，OK 
                if (children.size() >= 2) { 
                    return; 
                // 否则与子节点合并 
                } else { 
                    Node root = children.get(0); 
                    tree.setRoot(root); 
                    root.setParent(null); 
                    root.setRoot(true); 
                    setEntries(null); 
                    setChildren(null); 
                } 
            } else { 
                //计算前后节点  
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
                 
                // 如果前节点子节点数大于M / 2并且大于2，则从其处借补 
                if (previous != null  
                        && previous.getChildren().size() > tree.getOrder() / 2 
                        && previous.getChildren().size() > 2) { 
                    //前叶子节点末尾节点添加到首位 
                    int idx = previous.getChildren().size() - 1; 
                    Node borrow = previous.getChildren().get(idx); 
                    previous.getChildren().remove(idx); 
                    borrow.setParent(this); 
                    children.add(0, borrow); 
                    validate(previous, tree); 
                    validate(this, tree); 
                    parent.updateRemove(tree); 
                     
                // 如果后节点子节点数大于M / 2并且大于2，则从其处借补 
                } else if (next != null  
                        && next.getChildren().size() > tree.getOrder() / 2 
                        && next.getChildren().size() > 2) { 
                    //后叶子节点首位添加到末尾 
                    Node borrow = next.getChildren().get(0); 
                    next.getChildren().remove(0); 
                    borrow.setParent(this); 
                    children.add(borrow); 
                    validate(next, tree); 
                    validate(this, tree); 
                    parent.updateRemove(tree); 
                     
                // 否则需要合并节点 
                } else { 
                    // 同前面节点合并 
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
                         
                    // 同后面节点合并 
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
        //如果是叶子节点 
        if (isLeaf){ 
             
            //如果不包含该关键字，则直接返回 
            if (!contains(key)){ 
                return false; 
            } 
             
            //如果既是叶子节点又是跟节点，直接删除 
            if (isRoot) { 
                remove(key); 
            }else { 
                //如果关键字数大于M / 2，直接删除 
                if (entries.size() > tree.getOrder() / 2 && entries.size() > 2) { 
                    remove(key); 
                }else { 
                    //如果自身关键字数小于M / 2，并且前节点关键字数大于M / 2，则从其处借补
                	//从其他节点借补
                    if (previous != null  
                            && previous.getEntries().size() > tree.getOrder() / 2 
                            && previous.getEntries().size() > 2 
                            && previous.getParent() == parent) { 
                        int size = previous.getEntries().size(); 
                        Entry<Comparable, Object> entry = previous.getEntries().get(size - 1); 
                        previous.getEntries().remove(entry); 
                        //添加到首位 
                        entries.add(0, entry); 
                        remove(key); 
                    //如果自身关键字数小于M / 2，并且后节点关键字数大于M / 2，则从其处借补    
                    }else if (next != null  
                            && next.getEntries().size() > tree.getOrder() / 2 
                            && next.getEntries().size() > 2 
                            && next.getParent() == parent) { 
                        Entry<Comparable, Object> entry = next.getEntries().get(0); 
                        next.getEntries().remove(entry); 
                        //添加到末尾 
                        entries.add(entry); 
                        remove(key); 
                    //否则需要合并叶子节点     
                    }else { 
                        //同前面节点合并 ，前面的节点关键字大于order/2  或者前面的关键字数小于等于2
                        if (previous != null  
                                && (previous.getEntries().size() <= tree.getOrder() / 2 || previous.getEntries().size() <= 2) 
                                && previous.getParent() == parent) { 
                            for (int i = previous.getEntries().size() - 1; i >=0; i--) { 
                                //从末尾开始添加到首位 
                                entries.add(0, previous.getEntries().get(i)); 
                            } 
                            remove(key); 
                            previous.setParent(null); 
                            previous.setEntries(null); 
                            parent.getChildren().remove(previous); 
                            //更新链表 
                            if (previous.getPrevious() != null) {    //前节点非空的情况下
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
                        //同后面节点合并    
                        }else if(next != null  
                                && (next.getEntries().size() <= tree.getOrder() / 2 || next.getEntries().size() <= 2) 
                                && next.getParent() == parent){ 
                            for (int i = 0; i < next.getEntries().size(); i++) { 
                                //从首位开始添加到末尾 
                                entries.add(next.getEntries().get(i)); 
                            } 
                            remove(key); 
                            next.setParent(null); 
                            next.setEntries(null); 
                            parent.getChildren().remove(next); 
                            //更新链表 
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
        //如果不是叶子节点   
        }else { 
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索 
            if (key.compareTo(entries.get(0).getKey()) <= 0) { 
                children.get(0).remove(key, tree); 
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索 
            }else if (key.compareTo(entries.get(entries.size()-1).getKey()) >= 0) { 
                children.get(children.size()-1).remove(key, tree); 
            //否则沿比key大的前一个子节点继续搜索 
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
     
    /** 判断当前节点是否包含该关键字*/ 
    protected boolean contains(Comparable key) { 
        for (Entry<Comparable, Object> entry : entries) { 
            if (entry.getKey().compareTo(key) == 0) { 
                return true; 
            } 
        } 
        return false; 
    } 
    /*
     * 插入到当前节点的关键字，true返回为插入成功，false为已存在此节点
     */
    /** 插入到当前节点的关键字中*/ 
    protected boolean insertOrUpdate(Comparable key, Object obj){ 
        Entry<Comparable, Object> entry = new SimpleEntry<Comparable, Object>(key, obj); 
        Long ltemp = new Long(0);
        //如果关键字列表长度为0，则直接插入 
        if (entries.size() == 0) { 
            entries.add(entry); 

            return true; 
        } 
        //否则遍历列表 
        for (int i = 0; i < entries.size(); i++) { 
            //如果该关键字键值已存在，则更新 
            if (entries.get(i).getKey().compareTo(key) == 0) { 
                entries.get(i).setValue(obj); 
                return false; 
                
            //否则插入   
            }else if (entries.get(i).getKey().compareTo(key) > 0){ 
                //插入到链首 
                if (i == 0) { 
                    entries.add(0, entry); 

                    return true; 
                //插入到中间 
                }else { 
                    entries.add(i, entry); 
                    return true; 
                } 
            } 
        } 
        //插入到末尾 
        
        entries.add(entries.size(), entry);
        return true;
    } 
     
    /** 删除节点*/ 
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