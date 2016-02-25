package com.sidney.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.sidney.QuadTree.Point;

public class Statistics {
	public static void StaAverArea(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println(filename + "不存在 ");
			return;
		}
		double total = 0.0;
		try {

			Scanner in = new Scanner(file);

			String[] arrs = null;

			while (in.hasNext()) {

				String s = in.nextLine();
				arrs = s.split("  ");
				double area = Double.parseDouble(arrs[4]);

				total += area;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(total / 10000);
	}
	
	public static void StaAverK(String filename,int num) {
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println(filename + "不存在 ");
			return;
		}
		double total = 0.0;
		try {

			Scanner in = new Scanner(file);

			String[] arrs = null;

			while (in.hasNext()) {

				String s = in.nextLine();
				arrs = s.split("  ");
				int reaK = Integer.parseInt(arrs[2]);

				total += reaK;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(total / num);
	}


	public static void main(String[] args) throws FileNotFoundException {
         
		/* String filename = "result\\CasperResult";
		for(int k = 3;k <= 10;k ++){
			StaAverK(filename+k,10000);
		}
		
		*/
		//statisticCasper("Interval");
		//staticArea("MinCover.out");
		//statisticHilbert();
		staticLKDiversity();
	}
	static void staticArea(String filename){
		
		File file = new File(filename);
		if (!file.exists()) {
			System.out.println(filename + "不存在 ");
			return;
		}
		double total = 0.0;
		int num = 0;
		double tw = 0.0;
		double tl = 0.0;
		try {

			Scanner in = new Scanner(file);

			String[] arrs = null;
			
			while (in.hasNext()) {

				String s = in.nextLine();
				arrs = s.split("  ");
				double width = Double.parseDouble(arrs[4]) - Double.parseDouble(arrs[2]);
				double length = Double.parseDouble(arrs[3]) - Double.parseDouble(arrs[1]); 
				tw += width;
				tl += length;
				total += width*length;
				num++;
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tw/num);
		System.out.println(tl/num);
		System.out.println(total / num);
	}
	
	
	static void statisticCasper(String path) throws FileNotFoundException{
		//String path = "Casper";
		File root = new File(path);
		File []fs = root.listFiles();
		File result = new File("StatisticResult\\"+path);
		PrintWriter out = new PrintWriter(result);
		
		for(int i = 0;i<fs.length;i++){
			String filename = path+"\\"+fs[i].getName();
			File file = new File(filename);
			Scanner in = new Scanner(file);
			double k = 0;
			int num = 0;
			double totalArea = 0.0;
			double tw = 0.0;
			double tl = 0.0;
			while(in.hasNext()){
				String []arrs;
				String s = in.nextLine();
				arrs = s.split("  ");
				totalArea+= Double.parseDouble(arrs[4]);
				k += Integer.parseInt(arrs[2]);
				num++;
			}
			
			System.out.println(fs[i].getName()+"  "+"arerageRectArea=  "+(totalArea/num)+"  averagek  "+(k/num));
			
			out.println(fs[i].getName()+"  "+"arerageRectArea=  "+(totalArea/num)+"  averagek  "+(k/num));
			in.close();
		}
		out.close();
	}
	
	static void statisticHilbert() throws FileNotFoundException{
		String path = "Hilbert";
		File root = new File(path);
		File []fs = root.listFiles();
		File result = new File("StatisticResult\\Hilbert");
		PrintWriter out = new PrintWriter(result);
		for(int i = 0;i<fs.length;i++){
			String filename = "Hilbert\\"+fs[i].getName();
			File file = new File(filename);
			Scanner in = new Scanner(file);
			double totalL = 0.0;
			double totalLK = 0.0;
			double k = 0;
			int num = 0;
			double total = 0.0;
			double tw = 0.0;
			double tl = 0.0;
			while(in.hasNext()){
				String []arrs;
				String s = in.nextLine();
				arrs = s.split("  ");
				double width = Double.parseDouble(arrs[3]) - Double.parseDouble(arrs[1]);
				double length = Double.parseDouble(arrs[2]) - Double.parseDouble(arrs[0]); 
				tw += width;
				tl += length;
				total += width*length;
				num++;
			}
			
			System.out.println(fs[i].getName()+"  "+"arerageRectArea=  "+(total/num)+"  averageLength  "+(tl/num)+"  averageWidth  "+(tw/num));
			
			out.println(fs[i].getName()+"  "+"arerageRectArea=  "+(total/num)+"  averageLength  "+(tl/num)+"  averageWidth  "+(tw/num));
			
			in.close();
		}
		out.close();
	}
	
	static void staticLKDiversity() throws FileNotFoundException{
		String path = "LKDiversity";
		File root = new File(path);
		File[]fs = root.listFiles();
		
		
		File resultFile = new File("StatisticResult\\LKDiversity");
		PrintWriter out = new PrintWriter(resultFile);
		for(int i = 0;i<fs.length;i++){
			String filename = "LKDiversity\\"+fs[i].getName();
			File file = new File(filename);
			Scanner in = new Scanner(file);
			double totalL = 0.0;
			double totalLK = 0.0;
			double k = 0;
			int num = 0;
			String deK = "k=";
			while(in.hasNext()){
				String[]arrs = null;
				String temp = in.nextLine();
				arrs = temp.split("  ");
				double minX,minY,maxX,maxY;
				double x1,x2,y1,y2;
				
				x1 = Double.parseDouble(arrs[2]);
				y1 = Double.parseDouble(arrs[3]);
				x2 = Double.parseDouble(arrs[4]);
				y2 = Double.parseDouble(arrs[5]);
				
				minX = Double.parseDouble(arrs[7]);
				minY = Double.parseDouble(arrs[8]);
				maxX = Double.parseDouble(arrs[9]);
				maxY = Double.parseDouble(arrs[10]);
				arrs[6] = arrs[6].replace(deK,"");
				k += Integer.parseInt(arrs[6]);
				
				totalL += (x2-x1)*(y2-y1);
				totalLK += (maxX-minX)*(maxY-minY);
				
				num++;
			}
			
			System.out.println(fs[i].getName()+"  "+"arerageLRect=  "+(totalL/num) +"  "+"averageLKRect=  "+ (totalLK/num)+ "  averK=  "+(k/num));
			out.println(fs[i].getName()+"  "+"arerageLRect=  "+(totalL/num) +"  "+"averageLKRect=  "+ (totalLK/num)+ "  averK=  "+(k/num));
			
			
			in.close();
			
			
		}
		out.close();
		
	}
	
	final static void showAllFiles(String str){
		File root = new File(str);
		File[]fs = root.listFiles();
		for(int i = 0;i<fs.length;i++){
			System.out.println(fs[i].getName());
		}
	}
	
}
