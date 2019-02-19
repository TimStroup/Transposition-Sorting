package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SearchDriver {

	public static void main(String[] args){
		String fileName = "input.txt";
		int arraySize = readInputSize(fileName);
		int[] sequence = new int[arraySize];
		readInputArray(sequence,fileName);
		System.out.println(Arrays.toString(sequence));
	}

	private static int readInputSize(String fileName){
		int arraySize = 0;
		try{
			Scanner scan = new Scanner(new File(fileName));
			while (scan.hasNextLine()){
				arraySize += 1;
				scan.nextLine();
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}

		return arraySize;
	}

	private static void readInputArray(int[] input,String fileName){
		try{
			Scanner scan = new Scanner(new File(fileName));
			for(int i =0;i < input.length;i++){
				input[i] = Integer.parseInt(scan.nextLine());
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

}
