package com.sidney.cloak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.sidney.Hilbert.Hilbert;
import com.sidney.QuadTree.*;

public class IntervalCloak {
	private QuadTree qTree = null;
	private Vector<Point> vec = null;

	public IntervalCloak() {
		// TODO Auto-generated constructor stub
		vec = new Vector<Point>();
		qTree = new QuadTree();
		MapRectangle m = new MapRectangle(new Point(0.0, 0.0), new Point(
				24000.0, 32000.0));
		QuadTreeNode qNode = qTree.createQuadTree(m, null, 11);
		qTree.setRoot(qNode);
	}
	//x,y是以0,0坐标为开始的最大坐标
	public IntervalCloak(int x,int y,int level){
		vec = new Vector<Point>();
		qTree = new QuadTree();
		MapRectangle m = new MapRectangle(new Point(0.0, 0.0), new Point(
				x, y));
		QuadTreeNode qNode = qTree.createQuadTree(m, null, level);
		qTree.setRoot(qNode);
	}
	public void insertUser(int x,int y){
		Point temp = new Point(x,y);
		vec.add( temp);
		qTree.insertUser(temp);
		
	}
	// 读取文件数据
	public void ReadData(String filename) {
		File file = new File(filename);
		if (!file.exists())
			return;

		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int userId, x, y;

			while (in.hasNext()) {

				String s = in.nextLine();
				arrs = s.split("  ");
				userId = Integer.parseInt(arrs[0]);
				x = Integer.parseInt(arrs[1]);
				y = Integer.parseInt(arrs[2]);
				/* 测试语句 */
				// System.out.println(x+","+y);
				Point temp = new Point(x, y);
				vec.add(temp);
				qTree.insertUser(temp);
				// System.out.println(arrs[0]+","+arrs[1]);
				// 测试语句

			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 寻找匿名区ASR */
	public IntervalCloakResult FindASR(Point userPoint, int k) {

		// 区域内未初始化
		if (qTree.getRoot() == null)
			return null;
		// 坐标不在根节点的范围内
		if (!MapRectangle.CompareRect(qTree.getRoot().getRect(), userPoint))
		{
			//System.out.println(userPoint.getX()+"  "+userPoint.getY());
			return null;
		}
		QuadTreeNode temp = null;
		QuadTreeNode q = qTree.getRoot();
		while (q != null && q.getTotalUser() >= k) {

			if (q.getQuadChildernbyIndex(0) == null) {
				temp = q;
				break;
			}
			for (int i = 0; i < 4; i++) {

				if (MapRectangle.CompareRect(q.getQuadChildernbyIndex(i)
						.getRect(), userPoint)) {

					temp = q;
					q = q.getQuadChildernbyIndex(i);
					break;
				}
			}
		}

		IntervalCloakResult rs = new IntervalCloakResult(new MapRectangle(temp
				.getRect().getLeftDown(), temp.getRect().getRightUp()),
				temp.getTotalUser());
		return rs;

	}

	public static void main(String[] args) throws FileNotFoundException {
		// File file = new File("data.txt");
		// IntervalCloak.genData("test.dat");

		

		ArrayList list = new ArrayList();
		for (int j = 0; j < 10000; j++) {
			list.add(j);
		}
        
		ArrayList<Integer> lk = new ArrayList<Integer>();
		ArrayList<Integer> lcase = new ArrayList<Integer>();
		
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
		
		for(int o = 0 ;o<lk.size();o++){
			for(int p = 0;p<lcase.size();p++){
				IntervalCloak inc = new IntervalCloak();
				//inc.ReadData("CaseperK2",100000);
				inc.ReadData("TestData\\out50100.dat",50000);
				PrintWriter out = new PrintWriter("Interval\\IntercalCLoak" + lk.get(o)+"k"+lcase.get(p)+"case");
			int i = 0;
			IntervalCloakResult rs = null;
			Point value = null;
			int notGenASR = 0;
			long startTime = System.currentTimeMillis();
			while (i < lcase.get(p)) {
				value = inc.getVec().elementAt(i);
				if (value == null) {
					i++;
					notGenASR++;
					continue;
				}
				rs = inc.FindASR(value, lk.get(o));
				out.println(list.get(i) + "  " + lk.get(o) + "  " + rs.getK() + "  "
						+ 0 + "  " + rs.getRect().CalArea() + "  "+value.getX()+"  "+value.getY()+ 
						"  "+rs.getRect().getLeftDown().getX()+"  "+rs.getRect().getLeftDown().getY()+
						"  "+rs.getRect().getRightUp().getX()+"  "+rs.getRect().getRightUp().getY()
						);
				i++;
			}
			long endTime = System.currentTimeMillis();
			System.out.println("k=  " + lk.get(o) +"  case=  "+lcase.get(p)+ "  timeUsed:" + (endTime - startTime)
					+ "  notGenASR=" + notGenASR);
			out.close();
			}
		}

	}

	public QuadTree getqTree() {
		return qTree;
	}

	public void setqTree(QuadTree qTree) {
		this.qTree = qTree;
	}

	public Vector<Point> getVec() {
		return vec;
	}

	/*
	 * 随机生成数据 这种随机生成的数据分布还算均匀，但与现实的数据差别很大
	 */
	public static void genData(String filename, int num)
			throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		Random rand = new Random();

		for (int i = 0; i < num; i++) {
			String str = i + "  " + rand.nextInt(65535) + "  "
					+ rand.nextInt(65535);
			out.println(str);
		}
		out.close();
	}
	
	public void ReadData(String filename,int n) {
		File file = new File(filename);
		if (!file.exists())
			return;

		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int userId, x, y;
            int flag = 0;
			while (in.hasNext()&&flag < n) {

				String s = in.nextLine();
				arrs = s.split("  ");
				userId = Integer.parseInt(arrs[0]);
				x = Integer.parseInt(arrs[1]);
				y = Integer.parseInt(arrs[2]);
				/* 测试语句 */
				// System.out.println(x+","+y);
				Point temp = new Point(x, y);
				vec.add(temp);
				qTree.insertUser(temp);
				// System.out.println(arrs[0]+","+arrs[1]);
				// 测试语句
                flag++;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
