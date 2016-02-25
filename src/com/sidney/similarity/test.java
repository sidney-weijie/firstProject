package com.sidney.similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double vec1[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0	};
				          
	
		double vec2[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		
		long start = 0,end = 0;
		 File file = new File("TestData\\record100.txt");
    	 try {
    		 
			 Scanner in = new Scanner(file);
			 start = System.currentTimeMillis();
			 
	
			 
		     for(int i = 0;i < 50 ;i++){
				 for(int j = 0;j<24;j++){
					 if(in.hasNext()){
					 vec1[j] = in.nextDouble();
					 }
				 }
				 
				 for(int j = 0;j<24;j++){
					 if(in.hasNext()){
					 vec2[j] = in.nextDouble();
					 }
				 }
				 
				System.out.println("cos= "+CosSimilarity.computeCosSim(vec1, vec2)
						            +"   pearson= "+PearsonSimilarity.compute(vec1, vec2));
				/*
				for(int k = 0;k<24;k++){
					System.out.println(vec1[k]+"  "+vec2[k]);
				}
				*/
			 }
			 
			 end = System.currentTimeMillis();
			 in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	 System.out.println("timeuserd "+(end - start));
	}

}
