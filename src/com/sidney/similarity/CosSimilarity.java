package com.sidney.similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CosSimilarity {
    
	public static double computeCosSimInt(ArrayList<Integer>a,ArrayList<Integer>b){
		
        double result = 0.0;
		
		double xi = 0.0;
		double yi = 0.0;
		
		for(int i = 0;i<a.size();i++){
			result += a.get(i)*b.get(i);
			xi += a.get(i)*a.get(i);
			yi += b.get(i)*b.get(i);
		}
		
		xi = Math.sqrt(xi);
		yi = Math.sqrt(yi);
		
		result = result/(xi*yi);
		
		return result;
		
	}
	public static double computeCosSim(ArrayList<Double>a,ArrayList<Double>b){
		
		double result = 0.0;
		
		double xi = 0.0;
		double yi = 0.0;
		
		for(int i = 0;i<a.size();i++){
			result += a.get(i)*b.get(i);
			xi += a.get(i)*a.get(i);
			yi += b.get(i)*b.get(i);
		}
		
		xi = Math.sqrt(xi);
		yi = Math.sqrt(yi);
		
		result = result/(xi*yi);
		
		return result;
	}
	
	public static double computeCosSim(double a[],double b[]){
		
		double result = 0.0;
		
		double xi = 0.0;
		double yi = 0.0;
		
		for(int i = 0;i<a.length;i++){
			result += a[i]*b[i];
			xi += a[i]*a[i];
			yi += b[i]*b[i];
		}
		
		xi = Math.sqrt(xi);
		yi = Math.sqrt(yi);
		
		result = result/(xi*yi);
		
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Double>a = new ArrayList<Double>();
		ArrayList<Double>b = new ArrayList<Double>();
		
		for(int i = 0;i<24;i++){
			a.add((double)i);
			//b.add(24.0-(double)i);
			b.add((double)i);
		}
		
		System.out.println(computeCosSim(a, b));
		
		
		ArrayList<Integer>c = new ArrayList<Integer>();
		ArrayList<Integer>d = new ArrayList<Integer>();
		File file = new File("similarity\\test.txt");
		try {
			Scanner in = new Scanner(file);
			String []arrs;
			String str = null;
			str = in.nextLine();
			arrs = str.split("  ");
			for(int i = 0;i<arrs.length-1;i++){
				c.add(Integer.parseInt(arrs[i]));
			}
			
			str = in.nextLine();
			arrs = str.split("  ");
			for(int i = 0;i<arrs.length;i++){
				d.add(Integer.parseInt(arrs[i]));
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		System.out.println(computeCosSimInt(c, d));
	}
	
	
}
