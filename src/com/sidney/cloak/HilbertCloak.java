package com.sidney.cloak;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

import com.sidney.Hilbert.*;
import com.sidney.QuadTree.*;
import com.sidney.btree.*;

public class HilbertCloak {
	private BplusTree btree;
	private int k;
	private int totalUser;
	private int hCodeOrder;

	public HilbertCloak(int k, int hCodeOrder, int btreeOrder) {
		this.totalUser = 0;
		this.k = k;
		this.btree = new BplusTree(btreeOrder);
		this.hCodeOrder = hCodeOrder;
	}

	public BplusTree getBtree() {
		return btree;
	}

	public void setBtree(BplusTree btree) {
		this.btree = btree;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}

	public int gethCodeOrder() {
		return hCodeOrder;
	}

	public void sethCodeOrder(int hCodeOrder) {
		this.hCodeOrder = hCodeOrder;
	}


	public void readData(String filename) {

		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("文件" + filename + "打开异常");

			return;
		}
		
		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int x, y;
			int i = 0;
			while (in.hasNext() && i < 100000) {
				String s = in.nextLine();
				arrs = s.split("  ");
				x = Integer.parseInt(arrs[1]);
				y = Integer.parseInt(arrs[2]);

				long temp = Hilbert.xy2d(x, y, hCodeOrder);
				btree.insertOrUpdate(temp, new Point(x, y));
				// System.out.println(temp);
				totalUser++;
				i++;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MapRectangle getArea(int x, int y) {

		if (totalUser < k) {
			System.out.println("TotalUser less than K needed!");
			return null;
		}
		long hValue = Hilbert.xy2d(x, y, hCodeOrder);

		int serialNum = 0;
		serialNum = btree.getRoot().getSerialNum(hValue, serialNum);

		// 数据中不存在，则需要插入
		if (serialNum == -1) {
			btree.getRoot().insertOrUpdate(hValue, new Point(x, y), btree);
			totalUser++;
			serialNum = btree.getRoot().getSerialNum(hValue, serialNum);
		}
		
		Node treeNode = btree.getRoot().getNode(hValue);

		int start = serialNum - (serialNum % k);
		int end;
		if (serialNum > (totalUser - 2 * k + 1)) {
			end = totalUser - 1;
		} else {
			end = start + k - 1;
		}
		/*
		 * System.out.println("serialNum = "+ serialNum);
		 * System.out.println("start = "+start);
		 * System.out.println("end   = "+end );
		 */
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = 0;
		double maxY = 0;
		// 向前扫
		Node q = treeNode;
		int i = serialNum;
		int index = 0;
		int flag = 0;

		for (int j = 0; j < q.getEntries().size(); j++) {
			if (q.getEntries().get(j).getKey().compareTo(hValue) == 0) {
				index = j;
				flag = j;
			}
		}

		while (i >= start && q != null) {

			Point temp = (Point) q.getEntries().get(index).getValue();
			// System.out.println("q.entries"+q.getEntries().get(index).getKey());
			if (minX > temp.getX()) {
				minX = temp.getX();
			}
			if (minY > temp.getY()) {
				minY = temp.getY();
			}
			if (maxX < temp.getX()) {
				maxX = temp.getX();
			}
			if (maxY < temp.getY()) {
				maxY = temp.getY();
			}

			if (index == 0) {
				if (q != btree.getHead()) {

					q = q.getPrevious();
					index = q.getEntries().size() - 1;
				} else {

					break;
				}

			} else {
				index--;
			}
			i--;
		}
		q = null;
		// 向后扫
		Node p = treeNode;
		i = serialNum + 1;
		if (flag == p.getEntries().size() - 1) {
			index = 0;
		} else {
			index = flag + 1;
		}
		while (i <= end && p != null) {

			if (index == 0) {
				p = p.getNext();
				if (p == null) {
					break;
				}
			}

			Point temp = (Point) p.getEntries().get(index).getValue();
			// System.out.println("p.entries"+p.getEntries().get(index).getKey());
			if (minX > temp.getX()) {
				minX = temp.getX();
			}
			if (minY > temp.getY()) {
				minY = temp.getY();
			}
			if (maxX < temp.getX()) {
				maxX = temp.getX();
			}
			if (maxY < temp.getY()) {
				maxY = temp.getY();
			}

			if (index == p.getEntries().size() - 1) {
				index = 0;
			} else {
				index++;
			}
			i++;
		}
		p = null;

		return new MapRectangle(new Point(minX, minY), new Point(maxX, maxY));

	}

	public boolean insertUser(int x, int y) {
		long key = Hilbert.xy2d(x, y, this.hCodeOrder);
		int total = 0;
		if (btree.getRoot().getSerialNum(key, total) != -1) {
			btree.insertOrUpdate(key, new Point(x, y));
			totalUser++;
			return true;
		}
		return false;
	}

	public boolean deleteUser(int x,int y) {
		long key = Hilbert.xy2d(x, y, hCodeOrder);
		int total = 0;
		
		if(btree.getRoot().getSerialNum(key, total)==-1){
			return false;
		}
		
		btree.remove(key);
		totalUser--;
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		List<Integer> lk = new ArrayList<Integer>();
		List<Integer> lOrder = new ArrayList<Integer>();
		
		lk.add(2);
		lk.add(3);
		lk.add(4);
		lk.add(5);
		lk.add(6);
		lk.add(7);
		lk.add(8);
		lk.add(9);
		lk.add(10);
		lk.add(15);
		lk.add(20);
		lk.add(30);
		lk.add(50);
		lk.add(80);
		lk.add(100);
		
		lOrder.add(3);
		lOrder.add(4);
		lOrder.add(5);
		/*lOrder.add(6);
		lOrder.add(8);
		lOrder.add(10);
		lOrder.add(12);
		lOrder.add(15);
		lOrder.add(20);
		lOrder.add(25);
		lOrder.add(30);
		*/
		for(int o=0;o<lk.size();o++){
			
			for(int p=0;p<lOrder.size();p++){
				HilbertCloak hc = new HilbertCloak(lk.get(o), 16, lOrder.get(p));

				
				hc.readData("TestData\\out50100.dat");


				//System.out.println(hc.getTotalUser() + "  timeUsed:" + (end - start));
				/*
				for (int i = 0; i < hc.getBtree().getRoot().getChildren().size(); i++) {
					System.out.println(hc.getBtree().getRoot().getChildrenCount()
							.get(i));
				}
			    */
				BplusTree btree = hc.getBtree();
				Node head = btree.getHead();

				List<Long> list = new ArrayList<Long>();
				list.add((long) 100);
				list.add((long) 200);
				list.add((long) 300);
				list.add((long) 400);
				
				list.add((long) 500);
				list.add((long) 600);
				list.add((long) 700);
				list.add((long) 800);
				list.add((long) 900);
				list.add((long) 1000);
				list.add((long) 2000);
				list.add((long) 5000);
				list.add((long) 8000);
				list.add((long) 10000);
				Random random = new Random();

				for (int j = 0; j < list.size(); j++) {
					long start = System.currentTimeMillis();
					File file = new File("Hilbert\\hilbert.out" +lk.get(o)+"k"+lOrder.get(p)+"hOrder"+ list.get(j).toString());
					PrintWriter pr = new PrintWriter(file);
					File userFile = new File("TestData\\out50100.dat");
					Scanner in = new Scanner(userFile);
					String str = null;
					String arrs[] = null;
					for (int i = 0; i < list.get(j); i++) {
						str = in.nextLine();
						arrs = str.split("  ");
						MapRectangle rect = hc.getArea(Integer.parseInt(arrs[1]),
								Integer.parseInt(arrs[2]));
						pr.println(rect.getLeftDown().getX() + "  "
								+ rect.getLeftDown().getY() + "  "
								+ rect.getRightUp().getX() + "  "
								+ rect.getRightUp().getY());
					}
					pr.flush();
					pr.close();
					in.close();
					long end = System.currentTimeMillis();
					System.out.println("btreeOrder=" + hc.getBtree().getOrder()
							+ "  K=  " + hc.getK() + "  data=  " + list.get(j)
							+ "  timeused  " + (end - start) + "  ms");

				}
			}
			
			
		}
		

	}
}
