package main.java;

import java.util.Arrays;

public class Node implements Comparable{

	public int[] sequence;
	public int pathCost;
	public int heuristic;

	public Node(int[] sequence,int pathCost, int heuristic){
		this.sequence = sequence;
		this.pathCost = pathCost;
		this.heuristic = heuristic;
	}


	@Override
	public int compareTo(Object o) {
		if(o instanceof Node){
			Node passedIn = (Node) o;
			if((this.heuristic + this.pathCost) > (passedIn.heuristic + passedIn.pathCost)){
				return 1;
			}
			else if((this.heuristic + this.pathCost) < (passedIn.heuristic + passedIn.pathCost)){
				return -1;
			}
			else return 0;
		}
		return -1;
	}

	@Override
	public int hashCode() {
		return java.util.Arrays.hashCode(sequence);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(obj instanceof Node){
			Node passedIn = (Node) obj;
			result = Arrays.equals(this.sequence,passedIn.sequence);
			return result;
		}
		return result;
	}
}
