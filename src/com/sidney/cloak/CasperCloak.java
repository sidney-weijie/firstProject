package com.sidney.cloak;

import com.sidney.QuadTree.*;
import com.sidney.util.CasperCloakResult;
import com.sidney.util.CasperHashValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import com.sidney.util.*;

/*
 * map中的
 * key    为用户ID
 * value  为 <QuadTreeNode,int k,int area> 这里的k为用户的需求k,area为用户的需求面积
 */
public class CasperCloak {
	private Map map = null;
	private QuadTree qTree = null;

	public CasperCloak() {
		this.map = new HashMap<String, CasperHashValue>();
		qTree = new QuadTree();
		MapRectangle m = new MapRectangle(new Point(0.0, 0.0), new Point(
				24000.0, 30000.0));
		QuadTreeNode qNode = qTree.createQuadTree(m, null, 11);
		qTree.setRoot(qNode);
	}

	// 获取合适的匿名区域，若找不到则返回null值
	public CasperCloakResult FindASR(String userId, int k, int area) {

		CasperHashValue cValue = (CasperHashValue) map.get(userId);
		if (cValue == null) {
			System.out.println("未找到用户编号为" + userId + "的哈希值!");
			return null;
		}

		QuadTreeNode qNode = cValue.getqNode();
		if (null != qNode) {
			QuadTreeNode p = qNode;
			QuadTreeNode q = qNode;
			int ASRUser;
			while (null != p.getParent()) {
                
				boolean flag = MapRectangle.LargerThanAsked(p.getRect(), area);
				if (flag && p.getTotalUser() >= k) {
					MapRectangle tempRect = new MapRectangle(p.getRect()
							.getLeftDown(), p.getRect().getRightUp());
					return new CasperCloakResult(tempRect, p.getTotalUser());
				}
				int i;
				q = p.getParent();
				for (i = 0; i < 4; i++) {
					if (q.getQuadChildernbyIndex(i) == p)
						break;
				}
				// 判断与邻居节点的用户数和是否大于等于K
				/* 通过判断合并邻居看是否符合要求 */
				/*
				 * 采用异或与邻居节点进行合并判断
				 */
				MapRectangle tempRect = null;

				if ((q.getQuadChildernbyIndex(i).getTotalUser() + q
						.getQuadChildernbyIndex(i ^ 1).getTotalUser()) >= k) {
					if (MapRectangle.LargerThan2(q.getQuadChildernbyIndex(i)
							.getRect(), area)) {
						ASRUser = q.getQuadChildernbyIndex(i).getTotalUser()
								+ q.getQuadChildernbyIndex(i ^ 1)
										.getTotalUser();
						tempRect = MapRectangle.combineTwoMapRect(q
								.getQuadChildernbyIndex(i).getRect(), q
								.getQuadChildernbyIndex(i ^ 1).getRect());
						return new CasperCloakResult(tempRect, ASRUser);
					}
				} else if ((q.getQuadChildernbyIndex(i).getTotalUser() + q
						.getQuadChildernbyIndex(i ^ 2).getTotalUser()) >= k) {
					if (MapRectangle.LargerThan2(q.getQuadChildernbyIndex(i)
							.getRect(), area)) {
						ASRUser = q.getQuadChildernbyIndex(i).getTotalUser()
								+ q.getQuadChildernbyIndex(i ^ 2)
										.getTotalUser();
						tempRect = MapRectangle.combineTwoMapRect(q
								.getQuadChildernbyIndex(i).getRect(), q
								.getQuadChildernbyIndex(i ^ 1).getRect());
						return new CasperCloakResult(tempRect, ASRUser);
					}
				}
				p = p.getParent();
			}
		}
		return null;
	}

	// 读取数据
	public void ReadData(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("文件:" + filename + "不存在!");
			return;
		}
		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int userId, x, y, k, area;
			int i = 0;
			QuadTreeNode qNode = null;
			CasperHashValue cHashValue = null;
			while (in.hasNext()) {
				String s = in.nextLine();
				arrs = s.split("  ");
				userId = Integer.parseInt(arrs[0]); // 用户ID
				x = Integer.parseInt(arrs[1]); // x坐标
				y = Integer.parseInt(arrs[2]); // Y坐标
				k = Integer.parseInt(arrs[3]); // 用户需求匿名度k
				area = Integer.parseInt(arrs[4]); // 用户需求最小匿名框面积

				qNode = qTree.insertUser(new Point(x, y));
				cHashValue = new CasperHashValue(qNode, k, area,new Point(x,y));
				map.put(arrs[0].trim(), cHashValue);
				// System.out.println(arrs[0] + "," + arrs[1]);
				// 测试语句
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Throwable {

		/*
		 * 
		 * 随机生成匿名度为2~10之间的数据值
		 */
		/*
		 * for(int k = 2;k<10;k++){ String filename = "CaseperK";
		 * CasperCloak.genData(filename+k, k,100000,2000); }
		 */
		
		/* Iterator entries = mapTemp.entrySet().iterator(); */

		int i = 0;
		CasperCloakResult rs = null;
		ArrayList list = new ArrayList();

		for (int j = 0; j < 10000; j++) {
			list.add(String.valueOf(j));
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
		
		for (int o = 0;o<lk.size();o++) {
			for(int p = 0;p<lcase.size();p++){
				
				CasperCloak cac = new CasperCloak();

				//cac.ReadData("CaseperK2",100000);
				cac.ReadData("TestData\\out50100.dat",50000);
				/* cac.showMapContent(); */
				Map mapTemp = cac.getMap();
				
				PrintWriter out = new PrintWriter("Casper\\CasperResult"+lk.get(o)+"k"+lcase.get(p)+"case"+".dat");
				int notGenASR = 0;
				long startTime = System.currentTimeMillis();
				i = 0;
				while (i < lcase.get(p)) {
					
					CasperHashValue value = (CasperHashValue) mapTemp.get(list.get(i));
					if(value == null){
						notGenASR++;
						i++;
						System.out.println("找不到匿名对应的键值！");
						continue;
					}
					rs = cac.FindASR(list.get(i).toString(),lk.get(o) , value.getNeedArea());
					if (rs == null) {
						notGenASR++;
						i++;
						continue;
					}
					out.print(list.get(i)+"  "+lk.get(o)+"  "+rs.getK()+"  "+value.getNeedArea()+"  "+rs.getRect().CalArea()+
							"  "+value.getPoi().getX()+"  "+value.getPoi().getY()+
							"  "+rs.getRect().getLeftDown().getX()+"  "+rs.getRect().getLeftDown().getY()+
							"  "+rs.getRect().getRightUp().getX()+"  "+rs.getRect().getRightUp().getY()
							);
					out.println();
					i++;
				}
	            
				long endTime = System.currentTimeMillis();
				out.close();
				System.out.println("K=  "+lk.get(o)+"  case=  "+lcase.get(p)+"  timeused:  " + (endTime - startTime) + "  Can'tGenASRCase="+notGenASR);
				cac.finalize();
			}
			
		}
	}

	/*
	 * 随机生成数据
	 */
	public static void genData(String filename, int k, int num, int area)
			throws FileNotFoundException {
		PrintWriter out = new PrintWriter(filename);
		Random rand = new Random();

		for (int i = 0; i < num; i++) {
			String str = i + "  " + rand.nextInt(65535) + "  "
					+ rand.nextInt(65535) + "  " + k + "  " + area;
			// System.out.println(str);
			out.println(str);
		}
		out.close();
	}

	public void showMapContent() {

		if (map == null)
			return;
		Iterator entries = map.entrySet().iterator();

		while (entries.hasNext()) {

			Map.Entry entry = (Map.Entry) entries.next();
			Object key = entry.getKey();
			CasperHashValue value = (CasperHashValue) entry.getValue();

			System.out.print("   userId:" + key.toString());
			value.Print();
			System.out.println("");

		}
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public QuadTree getqTree() {
		return qTree;
	}

	public void setqTree(QuadTree qTree) {
		this.qTree = qTree;
	}
	
	public void ReadData(String filename,int n) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println("文件:" + filename + "不存在!");
			return;
		}
		try {

			Scanner in = new Scanner(file);
			String[] arrs = null;
			int userId, x, y, k, area;
			int i = 0;
			QuadTreeNode qNode = null;
			CasperHashValue cHashValue = null;
			int flag = 0;
			while (in.hasNext()&&flag<n) {
				String s = in.nextLine();
				arrs = s.split("  ");
				userId = Integer.parseInt(arrs[0]); // 用户ID
				x = Integer.parseInt(arrs[1]); // x坐标
				y = Integer.parseInt(arrs[2]); // Y坐标
				k = 5;
				area = 2000;
				//k = Integer.parseInt(arrs[3]); // 用户需求匿名度k
				//area = Integer.parseInt(arrs[4]); // 用户需求最小匿名框面积

				qNode = qTree.insertUser(new Point(x, y));
				cHashValue = new CasperHashValue(qNode, k, area,new Point(x,y));
				map.put(arrs[0].trim(), cHashValue);
				// System.out.println(arrs[0] + "," + arrs[1]);
				// 测试语句
				flag++;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
