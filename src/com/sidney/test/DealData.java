package com.sidney.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DealData {

	static void dealNGData(){
		File file = new File("G:\\NetworkGenerator\\oldenburg.dat");
		File outFile = new File("G:\\NetworkGenerator\\out.dat");
		try {
			Scanner in = new Scanner(file);
			PrintWriter out = new PrintWriter(outFile);
			String []arrs = null;
			String str = "";
			int i = 0 ;
			while(in.hasNext()&&i<10){
				str = in.nextLine();
				arrs = str.split("	");
				if(arrs[0].equalsIgnoreCase("newpoint")){
				out.println(arrs[1]+"  "+(int)Double.parseDouble(arrs[5])+"  "+(int)Double.parseDouble(arrs[6]));
				System.out.println(arrs[0]+"  "+ arrs[1]+"  "+arrs[2]+"  "+arrs[3]+"  "+arrs[4]+"  "+arrs[5]);
				}
				//i++;
			}
			out.flush();
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static void genPS(){
		File fileIN = new File("TestData\\out50100.dat");
		File fileOUT = new File("TestData\\test.dat");
		try {
			Scanner in = new Scanner(fileIN);
			PrintWriter out = new PrintWriter(fileOUT);
			String str = null;
			int i = 0;
			while(in.hasNext()&&i<10){
				str = in.nextLine();
				str = str+"  2  2000";
				
				out.println(str);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File file = new File("TestData\\oldenburg.dat");
		genPS();
	}

}
