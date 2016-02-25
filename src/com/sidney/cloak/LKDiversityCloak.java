package com.sidney.cloak;
import com.sidney.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;

import com.sidney.btree.*;
import com.sidney.Hilbert.Hilbert;
import com.sidney.QuadTree.*;

import com.sidney.util.MinBoundRect;

public class LKDiversityCloak {
	private BplusTree btree = null;
	private int k;                 // �����kֵ
	private int totalUser;         // �������������û�
	private int hCodeOrder;        // Hilbert ����
	private int L;                 // necessary diversity
	private QuadTree qtree = null; // �Ĳ����ռ仮��
	private int totalPS;           // �����������ܹ�����
	private static double totalArea = 0.0;
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

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public QuadTree getQtree() {
		return qtree;
	}

	public void setQtree(QuadTree qtree) {
		this.qtree = qtree;
	}

	public int getTotalPS() {
		return totalPS;
	}

	public void setTotalPS(int totalPS) {
		this.totalPS = totalPS;
	}

	// ��ȡ����
	public boolean ReadUsedLocData(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("�ļ�:" + filename + "������!");
			return false;
		}
		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int userId, x, y, needk, area;
			int i = 0;

			while (in.hasNext()&&i++<50000) {
				String s = in.nextLine();
				arrs = s.split("  ");
				userId = Integer.parseInt(arrs[0]); // �û�ID
				x = Integer.parseInt(arrs[1]); // x����
				y = Integer.parseInt(arrs[2]); // Y����
				// needk = Integer.parseInt(arrs[3]); // �û�����������k
				// area = Integer.parseInt(arrs[4]); // �û�������С���������

				qtree.insertUser(new Point(x, y));

				totalUser++;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * ��ȡ�ļ��е�PSλ����Ϣ
	 */
	public boolean readPSInfo(String filename) {
		File file = new File(filename);

		if (!file.exists()) {
			System.out.println(filename + "�����ڣ���ȡʧ��!");
			return false;
		}

		try {
			Scanner in = new Scanner(file);
			String[] arrs = null;
			int x;
			int y;
			int halfHeight;
			int halfLength;
			while (in.hasNext()) {
				String temp = in.nextLine();
				arrs = temp.split("  ");
				x = Integer.parseInt(arrs[1]);
				y = Integer.parseInt(arrs[2]);

				halfHeight = Integer.parseInt(arrs[3]);
				halfLength = Integer.parseInt(arrs[4]);
				long hValue = Hilbert.xy2d(x, y, this.hCodeOrder);
				btree.insertOrUpdate(hValue, new MinBoundRect(x, y, halfHeight,
						halfLength));
				totalPS++;
			}

			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return true;
	}

	public LKDiversityCloak(int k, int L, int hCodeOrder, int btreeOrder,
			int qTreeOrder, MapRectangle initialArea) {
		btree = new BplusTree(btreeOrder);
		this.totalUser = 0;
		this.hCodeOrder = hCodeOrder;
		this.L = L;
		this.totalPS = 0;
		/*
		 * Ӧ��д��ȱʡ������
		 */
		qtree = new QuadTree();
		QuadTreeNode qNode = qtree
				.createQuadTree(initialArea, null, qTreeOrder);
		qtree.setRoot(qNode);
	}
    

	
	@SuppressWarnings("unchecked")
	public MapRectangle getLDiversityArea(int x, int y) {

		if (totalPS < L) {
			System.out.println("TotalPS less than L needed!");
			return null;
		}
		long hValue = Hilbert.xy2d(x, y, hCodeOrder);
		int serialNum = 0;
		serialNum = btree.getRoot().getSerialNumForLK(hValue, serialNum);
		Node treeNode = btree.getRoot().getNodeForLK(hValue);

		int start = serialNum - (serialNum % L);
		int end;
		if (serialNum > (totalPS - 2 * L + 1)) {
			end = totalPS - 1;
		} else {
			end = start + L - 1;
		}

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = 0;
		double maxY = 0;
		// ��ǰɨ
		Node q = treeNode;
		int i = serialNum;
		int index = 0;
		int flag = 0;

		int j = 0;
		boolean find = false; // �����ж��Ƿ���forѭ�����ҵ���ֵ���������һ����Ϊ���ĵ�
		if(q == null) {
			System.out.println("q is null");
		}
		for (j = 0; j < q.getEntries().size(); j++) {
			if (q.getEntries().get(j).getKey().compareTo(hValue) >= 0) {
				index = j;
				flag = j;
				find = true;
				break;
			}
		}

		if (!find) {
			index = q.getEntries().size() - 1;
			flag = index;
		}

		while (i >= start && q != null) {

			MinBoundRect temp = (MinBoundRect) q.getEntries().get(index)
					.getValue();
			
			if (minX > (temp.getX() - temp.getHalfLength())) {
				minX = temp.getX() - temp.getHalfLength();
			}
			if (minY > (temp.getY() - temp.getHalfHeight())) {
				minY = temp.getY() - temp.getHalfHeight();
			}
			if (maxX < (temp.getX() + temp.getHalfLength())) {
				maxX = temp.getX() + temp.getHalfLength();
			}
			if (maxY < (temp.getY() + temp.getHalfHeight())) {
				maxY = temp.getY() + temp.getHalfHeight();
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
		// ���ɨ
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

			MinBoundRect temp = (MinBoundRect) p.getEntries().get(index)
					.getValue();
			
			if (minX > (temp.getX() - temp.getHalfLength())) {
				minX = temp.getX() - temp.getHalfLength();
			}
			if (minY > (temp.getY() - temp.getHalfHeight())) {
				minY = temp.getY() - temp.getHalfHeight();
			}
			if (maxX < (temp.getX() + temp.getHalfLength())) {
				maxX = temp.getX() + temp.getHalfLength();
			}
			if (maxY < (temp.getY() + temp.getHalfHeight())) {
				maxY = temp.getY() + temp.getHalfHeight();
			}

			if (index == p.getEntries().size() - 1) {
				index = 0;
			} else {
				index++;
			}
			i++;
		}
		p = null;

		/*
		 * ��Ӷ�x,y����ıȽϣ�x,y�п������ڱ�Ե�ϵĵ�
		 */
		if (minX > x) {
			minX = x;
		}
		if (minY > y) {
			minY = y;
		}
		if (maxX < x) {
			maxX = x;
		}
		if (maxY < y) {
			maxY = y;
		}
		
		//�п��ܳ��ָ�ֵ�����
		
		if(minX < 0){
			minX = 0;
		}
		if(minY < 0){
			minY = 0;
		}
		if(maxX < 0){
			maxX = 0;
		}
		if(maxY < 0){
			maxY = 0;
		}

		return new MapRectangle(new Point(minX, minY), new Point(maxX, maxY));

	}

	public QuadTreeNode getMinCover(MapRectangle rect, QuadTreeNode q) {

		QuadTreeNode temp = q;
		if (!MapRectangle.includeRect(q.getRect(), rect)) {
			return null;
		}

		while (temp.getQuadChildernbyIndex(0) != null) {
			boolean flag = false;
			for (int i = 0; i < 4; i++) {
				if (MapRectangle.includeRect(temp.getQuadChildernbyIndex(i)
						.getRect(), rect)) {
					temp = temp.getQuadChildernbyIndex(i);
					flag = true;

					break;
				}
			}

			if (!flag) {
				break;
			}

		}
		return temp;

	}
	
	public MapRectangle MinCoverRecord(MapRectangle rectLdiversity, int askedK)
   {
		QuadTreeNode root = qtree.getRoot();
		QuadTreeNode q = root;

		if (!MapRectangle.includeRect(q.getRect(), rectLdiversity)) {
			// ������ڵ㶼�������������
		}
		


		q = getMinCover(rectLdiversity, q);
		MapRectangle MinCover = q.getRect();
		return MinCover;
	}
	
	/*
	 * ���ݻ��L diversity��MapRectangle�����K����ʵ��
	 */
	public LKClockResult genASR(MapRectangle rectLdiversity, int askedK)
			throws InterruptedException {
		QuadTreeNode root = qtree.getRoot();
		QuadTreeNode q = root;

		if (!MapRectangle.includeRect(q.getRect(), rectLdiversity)) {
			// ������ڵ㶼�������������
		}
		


		q = getMinCover(rectLdiversity, q);
		MapRectangle MinCover = q.getRect();
		
        /* 
         * minCover�е����û���С��K�����
         */
		if (q.getTotalUser() < askedK) {

			while (q.getTotalUser() < askedK && q.getParent() != null) {
				q = q.getParent();
			}
			/*if (q != null) {
				System.out.print("result is=");
				q.getRect().Print();
			}
			*/
			return new LKClockResult(q.getTotalUser(),q.getRect());
		}
		double middleX = (MinCover.getRightUp().getX() + MinCover.getLeftDown()
				.getX()) / 2.0;
		double middleY = (MinCover.getRightUp().getY() + MinCover.getLeftDown()
				.getY()) / 2.0;


		// ����MinCover��������ָ����
		ArrayList<MapRectangle> iniR = new ArrayList<MapRectangle>(); // ��¼����������ĸ�������
		ArrayList<QuadTreeNode> minCR = new ArrayList<QuadTreeNode>(); // ��¼������������ε���С�Ĳ����ڵ�
		ArrayList<QuadTreeNode> qChildren = new ArrayList<QuadTreeNode>(); // ��¼��MinCover��Ӧ�ڵ�ĸ����ӽڵ㣬�����ж�
		
		double rightUpYSubMiddleY = rectLdiversity.getRightUp().getY()- middleY;       //�����ж�����ľ���ռMinCover����״
		double leftDownYSubMiddleY = rectLdiversity.getLeftDown().getY()-middleY;
		double rightUpXSubMiddleX = rectLdiversity.getRightUp().getX() - middleX;
		double leftDownXSubMiddleX = rectLdiversity.getLeftDown().getX() - middleX;
		
		if (rightUpYSubMiddleY < 0.00001
				|| leftDownYSubMiddleY > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, rectLdiversity.getRightUp().getY()));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), rectLdiversity
							.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);
		} else if (rightUpXSubMiddleX < 0.00001
				|| leftDownXSubMiddleX > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t1 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(rectLdiversity
					.getRightUp().getX(), rectLdiversity.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);

		}

		else {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, middleY));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t2 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(middleX,
					rectLdiversity.getRightUp().getY()));
			MapRectangle t3 = new MapRectangle(new Point(middleX, middleY),
					new Point(rectLdiversity.getRightUp().getX(),
							rectLdiversity.getRightUp().getY()));

			iniR.add(t0);
			iniR.add(t1);
			iniR.add(t2);
			iniR.add(t3);

		}

		boolean special = true;
		int type = 0;

		if (rightUpYSubMiddleY < 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���°벿�� 0 1
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp1 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp1 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(1));
			minCR.add(qtemp0);
			minCR.add(qtemp1);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(1));
			type = 1;
	

		} else if (leftDownYSubMiddleY > 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���ϰ벿��

			QuadTreeNode qtemp2 = null;
			QuadTreeNode qtemp3 = null;
			qtemp2 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(2));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));

			minCR.add(qtemp2);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(2));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 2;

		} else if (rightUpXSubMiddleX < 0.00001) {
			// �������
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp2 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp2 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(2));

			minCR.add(qtemp0);
			minCR.add(qtemp2);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(2));
			type = 3;
		} else if (leftDownXSubMiddleX > 0.00001) {
			// �Ҳ�����
			QuadTreeNode qtemp1 = null;
			QuadTreeNode qtemp3 = null;
			qtemp1 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(1));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));
			minCR.add(qtemp1);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(1));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 4;

		} else {
			// ռ���ĸ�����
			
			for (int i = 0; i < 4; i++) {
				QuadTreeNode temp = null;
				temp = getMinCover(iniR.get(i), q.getQuadChildernbyIndex(i));
				if (temp != null){
					minCR.add(getMinCover(iniR.get(i),
							q.getQuadChildernbyIndex(i)));
					
					}
			}
			for(int i = 0;i<4;i++){
				qChildren.add(q.getQuadChildernbyIndex(i));
			}
			
			special = false;
		}
		

		int userNum = 0;

		
		for (int i = 0; i < minCR.size(); i++) {
			userNum += minCR.get(i).getTotalUser();
		}
		while (userNum < askedK) {
			userNum = 0;
			boolean flag = false;
			for (int i = 0; i < iniR.size(); i++) {
				//�ضԽ�������
				if (MapRectangle.includeRect(qChildren.get(i).getRect(), minCR
						.get(i).getRect())) {
					QuadTreeNode qnode = minCR.get(i).getParent();
					minCR.set(i, qnode);
					flag = true;
				}
				userNum += minCR.get(i).getTotalUser();
			}
			if (!flag) {
				break;
			}

		}

		if (userNum < askedK && (!special)) {
			//ֱ�ӷ���MinCover
			return new LKClockResult(q.getTotalUser(),MinCover);
			
		} else {


			MapRectangle rs = null;
			//rs = new MapRectangle(new Point(0, 0), new Point(0, 0));
			rs = minCR.get(0).getRect();
			for (int i = 0; i < minCR.size(); i++) {
				rs = MapRectangle.combineTwoMapRect(minCR.get(i).getRect(), rs);
			}
		
			return new LKClockResult(userNum,rs);
		}
		/*
		 * ��Ҫ��������Ĳ�������Ϊ��С�ķָ���ο�
		 */

		/*
		 * | 2 | 3 | 
		 * | 0 | 1 |
		 */


	}
	
	public LKClockResult genASR1(MapRectangle rectLdiversity, int askedK)
			throws InterruptedException {
		QuadTreeNode root = qtree.getRoot();
		QuadTreeNode q = root;

		if (!MapRectangle.includeRect(q.getRect(), rectLdiversity)) {
			// ������ڵ㶼�������������
		}
		


		q = getMinCover(rectLdiversity, q);
		MapRectangle MinCover = q.getRect();
		
        /* 
         * minCover�е����û���С��K�����
         */
		if (q.getTotalUser() < askedK) {
			int realUserNum = q.getTotalUser();
			QuadTreeNode p = null;
			/*
			 * Casper��
			 */
			while ( realUserNum <askedK&&q.getParent() != null) {
				
				p = q.getParent();
				int flag;
				for(flag = 0;flag < 4;flag++){
					
					if(p.getQuadChildernbyIndex(flag) == q)
					{
						break;
					}
				}
				
				/*
				 * �����ֵܽڵ���бȽ�
				 */
				
				if((p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^1).getTotalUser())>=askedK){
					realUserNum = p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^1).getTotalUser();
					return new LKClockResult(realUserNum,
							MapRectangle.combineTwoMapRect(p.getQuadChildernbyIndex(flag).getRect(), p.getQuadChildernbyIndex(flag^1).getRect()));
				
				}else if((p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^2).getTotalUser())>=askedK){
					realUserNum = p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^2).getTotalUser();
					
					return new LKClockResult(realUserNum,
							MapRectangle.combineTwoMapRect(p.getQuadChildernbyIndex(flag).getRect(), p.getQuadChildernbyIndex(flag^2).getRect()));
				}
				
				q = q.getParent();
				realUserNum = q.getTotalUser();
			}
			/*if (q != null) {
				System.out.print("result is=");
				q.getRect().Print();
			}
			*/
			return new LKClockResult(q.getTotalUser(),q.getRect());
		}
		double middleX = (MinCover.getRightUp().getX() + MinCover.getLeftDown()
				.getX()) / 2.0;
		double middleY = (MinCover.getRightUp().getY() + MinCover.getLeftDown()
				.getY()) / 2.0;


		// ����MinCover��������ָ����
		ArrayList<MapRectangle> iniR = new ArrayList<MapRectangle>(); // ��¼����������ĸ�������
		ArrayList<QuadTreeNode> minCR = new ArrayList<QuadTreeNode>(); // ��¼������������ε���С�Ĳ����ڵ�
		ArrayList<QuadTreeNode> qChildren = new ArrayList<QuadTreeNode>(); // ��¼��MinCover��Ӧ�ڵ�ĸ����ӽڵ㣬�����ж�
		
		double rightUpYSubMiddleY = rectLdiversity.getRightUp().getY()- middleY;       //�����ж�����ľ���ռMinCover����״
		double leftDownYSubMiddleY = rectLdiversity.getLeftDown().getY()-middleY;
		double rightUpXSubMiddleX = rectLdiversity.getRightUp().getX() - middleX;
		double leftDownXSubMiddleX = rectLdiversity.getLeftDown().getX() - middleX;
		
		if (rightUpYSubMiddleY < 0.00001
				|| leftDownYSubMiddleY > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, rectLdiversity.getRightUp().getY()));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), rectLdiversity
							.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);
		} else if (rightUpXSubMiddleX < 0.00001
				|| leftDownXSubMiddleX > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t1 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(rectLdiversity
					.getRightUp().getX(), rectLdiversity.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);

		}

		else {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, middleY));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t2 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(middleX,
					rectLdiversity.getRightUp().getY()));
			MapRectangle t3 = new MapRectangle(new Point(middleX, middleY),
					new Point(rectLdiversity.getRightUp().getX(),
							rectLdiversity.getRightUp().getY()));

			iniR.add(t0);
			iniR.add(t1);
			iniR.add(t2);
			iniR.add(t3);

		}

		boolean special = true;
		int type = 0;

		if (rightUpYSubMiddleY < 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���°벿�� 0 1
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp1 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp1 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(1));
			minCR.add(qtemp0);
			minCR.add(qtemp1);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(1));
			type = 1;
	

		} else if (leftDownYSubMiddleY > 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���ϰ벿��

			QuadTreeNode qtemp2 = null;
			QuadTreeNode qtemp3 = null;
			qtemp2 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(2));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));

			minCR.add(qtemp2);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(2));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 2;

		} else if (rightUpXSubMiddleX < 0.00001) {
			// �������
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp2 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp2 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(2));

			minCR.add(qtemp0);
			minCR.add(qtemp2);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(2));
			type = 3;
		} else if (leftDownXSubMiddleX > 0.00001) {
			// �Ҳ�����
			QuadTreeNode qtemp1 = null;
			QuadTreeNode qtemp3 = null;
			qtemp1 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(1));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));
			minCR.add(qtemp1);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(1));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 4;

		} else {
			// ռ���ĸ�����
			
			for (int i = 0; i < 4; i++) {
				QuadTreeNode temp = null;
				temp = getMinCover(iniR.get(i), q.getQuadChildernbyIndex(i));
				if (temp != null){
					minCR.add(getMinCover(iniR.get(i),
							q.getQuadChildernbyIndex(i)));
					
					}
			}
			for(int i = 0;i<4;i++){
				qChildren.add(q.getQuadChildernbyIndex(i));
			}
			
			special = false;
		}
		

		int userNum = 0;

		
		for (int i = 0; i < minCR.size(); i++) {
			userNum += minCR.get(i).getTotalUser();
		}
		while (userNum < askedK) {
			userNum = 0;
			boolean flag = false;
			for (int i = 0; i < iniR.size(); i++) {
				//�ضԽ�������
				if (MapRectangle.includeRect(qChildren.get(i).getRect(), minCR
						.get(i).getRect())) {
					QuadTreeNode qnode = minCR.get(i).getParent();
					minCR.set(i, qnode);
					flag = true;
				}
				userNum += minCR.get(i).getTotalUser();
			}
			if (!flag) {
				break;
			}

		}

		if (userNum < askedK && (!special)) {
			//ֱ�ӷ���MinCover
			return new LKClockResult(q.getTotalUser(),MinCover);
			
		} else {


			MapRectangle rs = null;
			//rs = new MapRectangle(new Point(0, 0), new Point(0, 0));
			rs = minCR.get(0).getRect();
			for (int i = 0; i < minCR.size(); i++) {
				rs = MapRectangle.combineTwoMapRect(minCR.get(i).getRect(), rs);
			}
		
			return new LKClockResult(userNum,rs);
		}
		/*
		 * ��Ҫ��������Ĳ�������Ϊ��С�ķָ���ο�
		 */

		/*
		 * | 2 | 3 | 
		 * | 0 | 1 |
		 */


	}
	
	public LKClockResult genASR2(MapRectangle rectLdiversity, int askedK)
			throws InterruptedException {
		QuadTreeNode root = qtree.getRoot();
		QuadTreeNode q = root;

		if (!MapRectangle.includeRect(q.getRect(), rectLdiversity)) {
			// ������ڵ㶼�������������
		}
		//System.out.println(rectLdiversity.toString());
		//System.out.println(q.getRect().toString());

		q = getMinCover(rectLdiversity, q);
		MapRectangle MinCover = q.getRect();
		//System.out.println(MinCover.toString2() +"  " + MinCover.CalArea());
        /* 
         * minCover�е����û���С��K�����
         */
		if (q.getTotalUser() < askedK) {
			int realUserNum = q.getTotalUser();
			QuadTreeNode p = null;
			/*
			 * Casper��
			 */
			while ( realUserNum <askedK&&q.getParent() != null) {
				
				p = q.getParent();
				int flag;
				for(flag = 0;flag < 4;flag++){
					
					if(p.getQuadChildernbyIndex(flag) == q)
					{
						break;
					}
				}
				
				/*
				 * �����ֵܽڵ���бȽ�
				 */
				
				if((p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^1).getTotalUser())>=askedK){
					realUserNum = p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^1).getTotalUser();
					return new LKClockResult(realUserNum,
							MapRectangle.combineTwoMapRect(p.getQuadChildernbyIndex(flag).getRect(), p.getQuadChildernbyIndex(flag^1).getRect()));
				
				}else if((p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^2).getTotalUser())>=askedK){
					realUserNum = p.getQuadChildernbyIndex(flag).getTotalUser()+p.getQuadChildernbyIndex(flag^2).getTotalUser();
					
					return new LKClockResult(realUserNum,
							MapRectangle.combineTwoMapRect(p.getQuadChildernbyIndex(flag).getRect(), p.getQuadChildernbyIndex(flag^2).getRect()));
				}
				
				q = q.getParent();
				realUserNum = q.getTotalUser();
			}
			/*if (q != null) {
				System.out.print("result is=");
				q.getRect().Print();
			}
			*/
			return new LKClockResult(q.getTotalUser(),q.getRect());
		}
		
		double middleX = (MinCover.getRightUp().getX() + MinCover.getLeftDown()
				.getX()) / 2.0;
		double middleY = (MinCover.getRightUp().getY() + MinCover.getLeftDown()
				.getY()) / 2.0;


		// ����MinCover��������ָ����
		ArrayList<MapRectangle> iniR = new ArrayList<MapRectangle>(); // ��¼����������ĸ�������
		ArrayList<QuadTreeNode> minCR = new ArrayList<QuadTreeNode>(); // ��¼������������ε���С�Ĳ����ڵ�
		ArrayList<QuadTreeNode> qChildren = new ArrayList<QuadTreeNode>(); // ��¼��MinCover��Ӧ�ڵ�ĸ����ӽڵ㣬�����ж�
		
		double rightUpYSubMiddleY = rectLdiversity.getRightUp().getY()- middleY;       //�����ж�����ľ���ռMinCover����״
		double leftDownYSubMiddleY = rectLdiversity.getLeftDown().getY()-middleY;
		double rightUpXSubMiddleX = rectLdiversity.getRightUp().getX() - middleX;
		double leftDownXSubMiddleX = rectLdiversity.getLeftDown().getX() - middleX;
		
		if (rightUpYSubMiddleY < 0.00001
				|| leftDownYSubMiddleY > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, rectLdiversity.getRightUp().getY()));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), rectLdiversity
							.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);
		} else if (rightUpXSubMiddleX < 0.00001
				|| leftDownXSubMiddleX > 0.00001) {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t1 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(rectLdiversity
					.getRightUp().getX(), rectLdiversity.getRightUp().getY()));
			iniR.add(t0);
			iniR.add(t1);

		}

		else {
			MapRectangle t0 = new MapRectangle(
					new Point(rectLdiversity.getLeftDown().getX(),
							rectLdiversity.getLeftDown().getY()), new Point(
							middleX, middleY));
			MapRectangle t1 = new MapRectangle(new Point(middleX,
					rectLdiversity.getLeftDown().getY()), new Point(
					rectLdiversity.getRightUp().getX(), middleY));
			MapRectangle t2 = new MapRectangle(new Point(rectLdiversity
					.getLeftDown().getX(), middleY), new Point(middleX,
					rectLdiversity.getRightUp().getY()));
			MapRectangle t3 = new MapRectangle(new Point(middleX, middleY),
					new Point(rectLdiversity.getRightUp().getX(),
							rectLdiversity.getRightUp().getY()));

			iniR.add(t0);
			iniR.add(t1);
			iniR.add(t2);
			iniR.add(t3);

		}

		boolean special = true;
		int type = 0;

		if (rightUpYSubMiddleY < 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���°벿�� 0 1
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp1 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp1 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(1));
			minCR.add(qtemp0);
			minCR.add(qtemp1);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(1));
			type = 1;
	

		} else if (leftDownYSubMiddleY > 0.00001) {
			// Ldiversity����ľ���ֻռMinCover���ϰ벿��

			QuadTreeNode qtemp2 = null;
			QuadTreeNode qtemp3 = null;
			qtemp2 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(2));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));

			minCR.add(qtemp2);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(2));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 2;

		} else if (rightUpXSubMiddleX < 0.00001) {
			// �������
			QuadTreeNode qtemp0 = null;
			QuadTreeNode qtemp2 = null;
			qtemp0 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(0));
			qtemp2 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(2));

			minCR.add(qtemp0);
			minCR.add(qtemp2);
			qChildren.add(q.getQuadChildernbyIndex(0));
			qChildren.add(q.getQuadChildernbyIndex(2));
			type = 3;
		} else if (leftDownXSubMiddleX > 0.00001) {
			// �Ҳ�����
			QuadTreeNode qtemp1 = null;
			QuadTreeNode qtemp3 = null;
			qtemp1 = getMinCover(iniR.get(0), q.getQuadChildernbyIndex(1));
			qtemp3 = getMinCover(iniR.get(1), q.getQuadChildernbyIndex(3));
			minCR.add(qtemp1);
			minCR.add(qtemp3);
			qChildren.add(q.getQuadChildernbyIndex(1));
			qChildren.add(q.getQuadChildernbyIndex(3));
			type = 4;

		} else {
			// ռ���ĸ�����
			
			for (int i = 0; i < 4; i++) {
				QuadTreeNode temp = null;
				temp = getMinCover(iniR.get(i), q.getQuadChildernbyIndex(i));
				if (temp != null){
					minCR.add(getMinCover(iniR.get(i),
							q.getQuadChildernbyIndex(i)));
					
					}
			}
			for(int i = 0;i<4;i++){
				qChildren.add(q.getQuadChildernbyIndex(i));
			}
			
			special = false;
		}
		

		int userNum = 0;
		double largestArea = 0.0;
		
		
		for (int i = 0; i < minCR.size(); i++) {
			userNum += minCR.get(i).getTotalUser();
			/*
			 * Ѱ������������
			 */
			if(minCR.get(i).getRect().CalArea()>largestArea){
				largestArea = minCR.get(i).getRect().CalArea();
			}
		}
		int j = 0;
		int count = 0;
		while (userNum < askedK) {
			userNum = 0;
			//boolean flag = false;
			
			
			//����ضԽ�������
			if (minCR.get(j).getRect().CalArea() < largestArea) {
				QuadTreeNode qnode = minCR.get(j).getParent();
				minCR.set(j, qnode);
				count++;
			//	flag = true;
			}
			
			for (int i = 0; i < iniR.size(); i++) {
				userNum += minCR.get(i).getTotalUser();
			}
			
			if(userNum>=askedK){
				break;
			}
			
			
			j++;
			if(j == iniR.size()){
				//ǰ��ļ��ξ������� 
				if(count == 0){
					largestArea = minCR.get(0).getParent().getRect().CalArea();
				}
				
				j = j%iniR.size();
				count = 0;
			}
		}

		if (userNum < askedK && (!special)) {
			//ֱ�ӷ���MinCover
			return new LKClockResult(q.getTotalUser(),MinCover);
			
		} else {


			MapRectangle rs = null;
			//rs = new MapRectangle(new Point(0, 0), new Point(0, 0));
			rs = minCR.get(0).getRect();
			for (int i = 0; i < minCR.size(); i++) {
				rs = MapRectangle.combineTwoMapRect(minCR.get(i).getRect(), rs);
			}
		
			return new LKClockResult(userNum,rs);
		}
		/*
		 * ��Ҫ��������Ĳ�������Ϊ��С�ķָ���ο�
		 */

		/*
		 * | 2 | 3 | 
		 * | 0 | 1 |
		 */


	}
	

	
	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {

		//showPS();
		test1();
	}
	public static void showPS(){
		int kValue = 5;
		int lValue = 3;
		int hilbertOrder = 15;
		int quadTreeOrder = 11;
		int bplustreeOrder = 4;
		
		
		
		MapRectangle initArea = new MapRectangle(new Point(0.0, 0.0),
				new Point(32768.0, 32768.0));
		//ʵ������
		LKDiversityCloak lk = new LKDiversityCloak(kValue, lValue,
				hilbertOrder, bplustreeOrder, quadTreeOrder, initArea);
		//����PS����
		lk.readPSInfo("TestData\\ps.dat");
		//�����û�����
		lk.ReadUsedLocData("TestData\\test.dat");
		Node head = lk.getBtree().getHead();
		while(head!=null){
			List<Entry<Comparable, Object>> entries;
			entries=head.getEntries();
			for(int i = 0;i<entries.size();i++){
				
				MinBoundRect mRect = (MinBoundRect)entries.get(i).getValue();
				Long key = (Long)entries.get(i).getKey();
				System.out.println(key+"  " + mRect.toString());
			}
			
			head = head.getNext();
		}
	}
	public static void test1() throws Throwable{
		
				int kValue = 3;
				int lValue = 3;
				int hilbertOrder = 15;
				int quadTreeOrder = 12;
				int bplustreeOrder = 4;
				int lcase = 10000;
				
				
				MapRectangle initArea = new MapRectangle(new Point(0.0, 0.0),
						new Point(24000.0, 32768.0));
				//ʵ������
				LKDiversityCloak lk = new LKDiversityCloak(kValue, lValue,
						hilbertOrder, bplustreeOrder, quadTreeOrder, initArea);
				//����PS����
				lk.readPSInfo("TestData\\ps.dat");
				//�����û�����
				lk.ReadUsedLocData("TestData\\test.dat");
				
				
				
				long start = System.currentTimeMillis();
				
				
				File file = new File("TestData\\test.dat");                  //�û���������ļ�
				File outPutFile = new File("LKDiversity\\"+kValue+"k"+lValue+"L"+bplustreeOrder+"border"+lcase+"case"+".out");       //��������¼�ļ�
				Scanner in = new Scanner(file);                    
				PrintWriter out = new PrintWriter(outPutFile);
				File outMinCover = new File("MinCover.out");
				PrintWriter oMinCover = new PrintWriter(outMinCover);
				int i = 0;
				Random rand = new Random(System.currentTimeMillis());
				while(in.hasNext()&&i++<lcase){                     //i��ʾ���Ե�������
				   String [] arrs = null;
				   String str = in.nextLine();
				   arrs = str.split("  ");
				   int x = Integer.parseInt(arrs[1]);
				   int y = Integer.parseInt(arrs[2]);
				   
				  //  int x = rand.nextInt(23999)+1;
				  //  int y = rand.nextInt(27998)+1;
				  // System.out.println("x="+x+"  y="+y);
				   MapRectangle tRect = lk.getLDiversityArea(x, y);
				  
				   LKClockResult lrs =  lk.genASR2(tRect, kValue);
				   oMinCover.println(lk.MinCoverRecord(tRect, 10).toString2());
				   out.println("x="+x+"  y="+y +tRect.toString2()+"  "+lrs.toString()+"  "+i);
					
				}
				oMinCover.close();
				out.close();
				in.close();
				long end = System.currentTimeMillis();
				System.out.println("k=  "+kValue+ "  lvalue=  "+lValue+"  btreeorder=  "+bplustreeOrder+"  case=  "+lcase+"  timeUsed=  "+(end - start));
				lk.finalize();
				
	}
	public static void test2() throws Throwable{
		// genData("LKTest.txt",100);
				ArrayList<Integer> kvalue = new ArrayList<Integer>();
				ArrayList<Integer> lcase = new ArrayList<Integer>();
				ArrayList<Integer> lvalue = new ArrayList<Integer>();
				ArrayList<Integer> btreeorder = new ArrayList<Integer>();
				
				kvalue.add(5);
				/*kvalue.add(4);
				kvalue.add(5);
				kvalue.add(6);
				kvalue.add(8);
				kvalue.add(10);
				kvalue.add(20);
				kvalue.add(30);
				kvalue.add(40);
				kvalue.add(50);
				*/
				
				lvalue.add(3);
				/*lvalue.add(3);
				lvalue.add(4);
				lvalue.add(5);
				lvalue.add(6);
				lvalue.add(8);
				lvalue.add(10);
				*/
				lcase.add(100);
				lcase.add(200);
				
				lcase.add(300);
				lcase.add(400);
				lcase.add(500);
				lcase.add(600);
				lcase.add(700);
				lcase.add(800);
				lcase.add(900);
				
				lcase.add(1000);
				lcase.add(2000);
				lcase.add(3000);
				lcase.add(5000);
				lcase.add(8000);
				lcase.add(10000);
				
				btreeorder.add(5);
				/*btreeorder.add(4);
				btreeorder.add(5);
				btreeorder.add(6);
				btreeorder.add(8);
				btreeorder.add(10);
				btreeorder.add(12);
				btreeorder.add(15);
				btreeorder.add(20);
				btreeorder.add(25);
				btreeorder.add(30);
				*/
				int kValue = 5;
				int lValue = 3;
				int hilbertOrder = 14;
				int quadTreeOrder = 11;
				int bplustreeOrder = 4;
				//��ʼ��������
				//genPSData("PS.txt",20000);
				
				for(int o= 0;o<kvalue.size();o++)
					for(int p = 0;p<lvalue.size();p++)
						for(int r=0;r<btreeorder.size();r++)
							for(int s = 0;s < lcase.size();s++)
				{
				
				MapRectangle initArea = new MapRectangle(new Point(0.0, 0.0),
						new Point(32000.0, 32000.0));
				//ʵ������
				LKDiversityCloak lk = new LKDiversityCloak(kvalue.get(o), lvalue.get(p),
						hilbertOrder, btreeorder.get(r), quadTreeOrder, initArea);
				//����PS����
				lk.readPSInfo("TestData\\ps.dat");
				//�����û�����
				lk.ReadUsedLocData("TestData\\test.dat");
				
				
				
				long start = System.currentTimeMillis();
				
				
				File file = new File("TestData\\test.dat");                  //�û���������ļ�
				File outPutFile = new File("LKDiversity\\"+kvalue.get(o)+"k"+lvalue.get(p)+"L"+btreeorder.get(r)+"border"+lcase.get(s)+"case"+".out");       //��������¼�ļ�
				Scanner in = new Scanner(file);                    
				PrintWriter out = new PrintWriter(outPutFile);
				File outMinCover = new File("MinCover.out");
				PrintWriter oMinCover = new PrintWriter(outMinCover);
				int i = 0;
				while(in.hasNext()&&i++<lcase.get(s)){                     //i��ʾ���Ե�������
				   String [] arrs = null;
				   String str = in.nextLine();
				   arrs = str.split("  ");
				   int x = Integer.parseInt(arrs[1]);
				   int y = Integer.parseInt(arrs[2]);
				   
				   // int x = 868;
				   // int y = 42218;
				   
				   MapRectangle tRect = lk.getLDiversityArea(x, y);
				  
				   LKClockResult lrs =  lk.genASR2(tRect, kvalue.get(o));
				   oMinCover.println(lk.MinCoverRecord(tRect, 10).toString2());
				   out.println("x="+x+"  y="+y +tRect.toString2()+"  "+lrs.toString()+"  "+i);
					
				}
				oMinCover.close();
				out.close();
				in.close();
				long end = System.currentTimeMillis();
				System.out.println("k=  "+kvalue.get(o)+ "  lvalue=  "+lvalue.get(p)+"  btreeorder=  "+btreeorder.get(r)+"  case=  "+lcase.get(s)+"  timeUsed=  "+(end - start));
				lk.finalize();
				}
	}

	public static void genData(String filename, int num)
			throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		Random rand = new Random();

		for (int i = 0; i < num; i++) {
			String str = i + "  " + (rand.nextInt(126) + 1) + "  "
					+ (rand.nextInt(126) + 1);
			out.println(str);
		}
		out.close();
	}
	
	public static void genPSData(String filename,int num) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(filename);
		Random rand = new Random();
			
		for (int i = 0; i < num; i++) {
			String str =  (rand.nextInt(64000) + 1) + "  "
					+ (rand.nextInt(64000) + 1)+"  "+(rand.nextInt(80)+1)+"  "+(rand.nextInt(80)+1);
			out.println(str);
		}
		out.close();
	}
    //ʹ��similarity����Ĺ���
	public MapRectangle getLDiversityAreaBySim(int x, int y,float threshold) {

		if (totalPS < L) {
			System.out.println("TotalPS less than L needed!");
			return null;
		}
		long hValue = Hilbert.xy2d(x, y, hCodeOrder);
		int serialNum = 0;
		serialNum = btree.getRoot().getSerialNumForLK(hValue, serialNum);
		Node treeNode = btree.getRoot().getNodeForLK(hValue);

		int start = serialNum - (serialNum % L);
		int end;
		if (serialNum > (totalPS - 2 * L + 1)) {
			end = totalPS - 1;
		} else {
			end = start + L - 1;
		}

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = 0;
		double maxY = 0;
		// ��ǰɨ
		Node q = treeNode;
		int i = serialNum;
		int index = 0;
		int flag = 0;

		int j = 0;
		boolean find = false; // �����ж��Ƿ���forѭ�����ҵ���ֵ���������һ����Ϊ���ĵ�
		if(q == null) {
			System.out.println("q is null");
		}
		for (j = 0; j < q.getEntries().size(); j++) {
			if (q.getEntries().get(j).getKey().compareTo(hValue) >= 0) {
				index = j;
				flag = j;
				find = true;
				break;
			}
		}

		if (!find) {
			index = q.getEntries().size() - 1;
			flag = index;
		}

		while (i >= start && q != null) {

			MinBoundRect temp = (MinBoundRect) q.getEntries().get(index)
					.getValue();
			
			if (minX > (temp.getX() - temp.getHalfLength())) {
				minX = temp.getX() - temp.getHalfLength();
			}
			if (minY > (temp.getY() - temp.getHalfHeight())) {
				minY = temp.getY() - temp.getHalfHeight();
			}
			if (maxX < (temp.getX() + temp.getHalfLength())) {
				maxX = temp.getX() + temp.getHalfLength();
			}
			if (maxY < (temp.getY() + temp.getHalfHeight())) {
				maxY = temp.getY() + temp.getHalfHeight();
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
		// ���ɨ
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

			MinBoundRect temp = (MinBoundRect) p.getEntries().get(index)
					.getValue();
			
			if (minX > (temp.getX() - temp.getHalfLength())) {
				minX = temp.getX() - temp.getHalfLength();
			}
			if (minY > (temp.getY() - temp.getHalfHeight())) {
				minY = temp.getY() - temp.getHalfHeight();
			}
			if (maxX < (temp.getX() + temp.getHalfLength())) {
				maxX = temp.getX() + temp.getHalfLength();
			}
			if (maxY < (temp.getY() + temp.getHalfHeight())) {
				maxY = temp.getY() + temp.getHalfHeight();
			}

			if (index == p.getEntries().size() - 1) {
				index = 0;
			} else {
				index++;
			}
			i++;
		}
		p = null;

		/*
		 * ��Ӷ�x,y����ıȽϣ�x,y�п������ڱ�Ե�ϵĵ�
		 */
		if (minX > x) {
			minX = x;
		}
		if (minY > y) {
			minY = y;
		}
		if (maxX < x) {
			maxX = x;
		}
		if (maxY < y) {
			maxY = y;
		}
		
		//�п��ܳ��ָ�ֵ�����
		
		if(minX < 0){
			minX = 0;
		}
		if(minY < 0){
			minY = 0;
		}
		if(maxX < 0){
			maxX = 0;
		}
		if(maxY < 0){
			maxY = 0;
		}

		return new MapRectangle(new Point(minX, minY), new Point(maxX, maxY));

	}
}
