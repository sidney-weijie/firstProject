package com.sidney.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class genData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//genTestData(10000,"10000.txt");
		genData1(100, "record100.txt");
	}

	static void genTestData(int length,String outfile){
		
		File fileOUT = new File("TestData\\"+outfile);
		try {
			Random rand = new Random(System.currentTimeMillis());
			PrintWriter out = new PrintWriter(fileOUT);
			int x,y;
			for(int i = 1; i <= length;i++){
				x = rand.nextInt(800);
				y = rand.nextInt(560);
				out.println(i+"  "+x+"  "+y);
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//生成比例的数据
	static void genData1(int length, String outfile){
		File fileOUT = new File("TestData\\"+outfile);
		double [] temp = {0.0, 0.0, 0.0, 0.0, 0.0,
				          0.0, 0.0, 0.0, 0.0, 0.0,
				          0.0, 0.0, 0.0, 0.0, 0.0,
				          0.0, 0.0, 0.0, 0.0, 0.0,
				          0.0, 0.0, 0.0, 0.0, 0.0,
				          0.0, 0.0, 0.0, 0.0};
		
		try {
			Random rand = new Random(System.currentTimeMillis());
			PrintWriter out = new PrintWriter(fileOUT);
			
			for(int i = 1; i <= length;i++){
				double total = 0;
				for(int j = 0; j < temp.length; j++){
					temp[j] = rand.nextInt(100);
					total += temp[j];
				}
				
				for(int j = 0;j < temp.length; j++){
					if(total < 0.00001){
						break;
					}
					if(j < (temp.length-1) ) 
					   out.print(temp[j]/total+"  ");
				}
				out.println();
				
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
