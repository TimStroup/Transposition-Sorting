package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.PriorityQueue;

public class SearchDriver {

	public static void main(String[] args){
		String fileName = "input.txt";
		int arraySize = readInputSize(fileName);
		int[] sequence = new int[arraySize];
		int count = 1;
		readInputArray(sequence,fileName);
		//computeTransposes(sequence);

		Node firstNode = new Node(sequence,0,0);
		PriorityQueue<Node> frontier = new PriorityQueue<>();
		ArrayList<Node> explored = new ArrayList<>();

		frontier.add(firstNode);

		System.out.println("Number of transpositions: " + aStarSearch( frontier,explored));
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

	private static int[] transpose(int[] inputSequence, final int i, final int j,final int k){
		int lengthOfFirst = (j -i) +1;
		int lengthOfSecond =(k - (j+1) +1);
		int[] firstSection = new int[lengthOfFirst];
		int[] secondSection = new int[lengthOfSecond];
		System.arraycopy(inputSequence,i,firstSection,0,lengthOfFirst);
		System.arraycopy(inputSequence,j+1,secondSection,0,lengthOfSecond);
		System.arraycopy(secondSection,0,inputSequence,i,lengthOfSecond);
		System.arraycopy(firstSection,0,inputSequence,i+lengthOfSecond,lengthOfFirst);

		return inputSequence;
	}

	private static void computeTransposes(int[] sequence){
		int count = 1;
		for(int i =0; i <sequence.length-1;i++){
			for(int j = i; j <sequence.length-1;j++){
				for(int k = j+1; k <sequence.length;k++){
					System.out.print(count + ": " + Arrays.toString(transpose(sequence.clone(),i,j,k)));
					System.out.println(":  goal? " + detectGoalState(transpose(sequence.clone(),i,j,k)));
					count++;
				}
			}
		}
	}

	private static boolean detectGoalState(int[] sequence){
		int firstValue = sequence[0];
		boolean goalState = false;
		for(int i = 1; i < sequence.length;i++){
			if(firstValue < sequence[i]){
				goalState = true;
				firstValue = sequence[i];
			}
			else{
				goalState = false;
				break;
			}
		}
		return goalState;
	}

	//right now the heuristic is 0 but I need to update it with a good admissible one
	private static int getHeuristic(int[] sequence){
		return 0;
	}

	private static int aStarSearch(PriorityQueue<Node> frontier, ArrayList<Node> explored){
		while (!frontier.isEmpty()){
			//remove the first node in the priority queue
			Node currentNode = frontier.peek();
			frontier.remove(currentNode);
			//check if the current node is the goal
			// if so return the path cost or number of transpositions to get to that goal
			if( detectGoalState(currentNode.sequence)){
				return currentNode.pathCost;
			}
			//mark this state as explored and add it to the explored structure
			explored.add(currentNode);
			for(int i =0; i <currentNode.sequence.length-1;i++){
				for(int j = i; j <currentNode.sequence.length-1;j++){
					for(int k = j+1; k <currentNode.sequence.length;k++){
						int [] newSequence = transpose(currentNode.sequence.clone(),i,j,k);
						Node nextState = new Node(newSequence,currentNode.pathCost+1,getHeuristic(newSequence));
						if(!frontier.contains(nextState) && !explored.contains(nextState)){
							frontier.add(nextState);
						}
						else if(frontier.contains(nextState)){
							//System.out.println("Idk what to do here yet");
						}
					}
				}
			}

		}
		return -1;
	}
}
