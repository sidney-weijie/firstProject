package com.sidney.similarity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PearsonSimilarity {
	
	public static double calculateDenominator(ArrayList<Double>x,ArrayList<Double>y){
		double standardDifference = 0.0;
		int size = x.size();
		double xAverage = 0.0;
		double yAverage = 0.0;
		double xException = 0.0;
		double yException = 0.0;
		double temp = 0.0;
		for(int i=0;i<size;i++){
			temp += x.get(i);
		}
		xAverage = temp/size;
		
		temp = 0.0;
		for(int i=0;i<size;i++){
			temp += y.get(i);
		}
		yAverage = temp/size;
		
		for(int i=0;i<size;i++){
			xException += Math.pow(x.get(i)-xAverage, 2);
			yException += Math.pow(y.get(i)-yAverage, 2);
		}
		//calculate denominator of 
		return standardDifference = Math.sqrt(xException*yException);
	}
	
	public static double calculateDenominator(double x[],double y[]){
		double standardDifference = 0.0;
		int size = x.length;
		double xAverage = 0.0;
		double yAverage = 0.0;
		double xException = 0.0;
		double yException = 0.0;
		double temp = 0.0;
		for(int i=0;i<size;i++){
			temp += x[i];
		}
		xAverage = temp/size;
		
		temp = 0.0;
		for(int i=0;i<size;i++){
			temp += y[i];
		}
		yAverage = temp/size;
		
		for(int i=0;i<size;i++){
			xException += Math.pow(x[i]-xAverage,2);
			yException += Math.pow(y[i]-yAverage, 2);
		}
		//calculate denominator of 
		return standardDifference = Math.sqrt(xException*yException);
	}
	
	public static double calcuteNumerator(ArrayList<Double>x,ArrayList<Double>y){
		double result =0.0;
		double xAverage = 0.0;
		double temp = 0.0;
		
		int xSize = x.size();
		for(int i=0;i<xSize;i++){
			temp += x.get(i);
		}
		xAverage = temp/xSize;
		
		double yAverage = 0.0;
		temp = 0.0;
		int ySize = y.size();
		for(int i=0;i<ySize;i++){
			temp += y.get(i);
		}
		yAverage = temp/ySize;
		
		//double sum = 0.0;
		for(int i=0;i<xSize;i++){
			result+= (x.get(i)-xAverage)*(y.get(i)-yAverage);
		}
		return result;
	}
	
	public static double calcuteNumerator(double x[],double y[]){
		double result =0.0;
		double xAverage = 0.0;
		double temp = 0.0;
		
		int xSize = x.length;
		for(int i=0;i<xSize;i++){
			temp += x[i];
		}
		xAverage = temp/xSize;
		
		double yAverage = 0.0;
		temp = 0.0;
		int ySize = y.length;
		for(int i=0;i<ySize;i++){
			temp += y[i];
		}
		yAverage = temp/ySize;
		
		//double sum = 0.0;
		for(int i=0;i<xSize;i++){
			result+= (x[i]-xAverage)*(y[i]-yAverage);
		}
		return result;
	}
	
	public static double compute(double a[],double b[]){
		return calcuteNumerator(a,b)/calculateDenominator(a,b);
	}
	
	public static double compute(ArrayList<Double>a,ArrayList<Double>b){
		return calcuteNumerator(a,b)/calculateDenominator(a,b);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double [] vec1 = {0.2,0.3,0.3,0.0,0.0};
		double [] vec2 = {0.0,0.0,0.0,0.3,0.3};
		
		double [] v1 = {0.1,0.2,0.3,0.4};
		double [] v2 = {0.2,0.4,0.6,0.8};
		
		ArrayList<Double> a = new ArrayList<Double>();
		ArrayList<Double> b = new ArrayList<Double>();
		a.add(0.3);
		a.add(0.3);
		a.add(0.3);
		a.add(0.0);
		a.add(0.0);
		
		b.add(0.0);
		b.add(0.0);
		b.add(0.0);
		b.add(0.3);
		b.add(0.3);
		System.out.println(compute(vec1,vec2));
		
	}
	
}
