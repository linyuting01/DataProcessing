package dbpedia;

import java.util.Scanner;

public class Drawing {
	public static String clean(String line){
		String[] temp= line.split("/");
		return temp[temp.length-1];
	}
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		while(scan.hasNext()){
			String temp=scan.nextLine();
			String[] data=temp.split("\t");
			System.out.println("g.addEdge(\""+clean(data[0])+"\",\""+clean(data[1])+"\");");
		}

	}

}
