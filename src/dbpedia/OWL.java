package dbpedia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OWL {
	public static String clean(String temp) {
		if(temp.equals("owl:Thing)"))
			return "owl:Thing";
		return temp.split("<")[1].split(">")[0];
	}
	
	public static void main(String[] args) throws IOException {
		Scanner scan=new Scanner(System.in);
		File cleanFile=new File("D://dataset/clean/owl2015.dat");
		BufferedWriter clean=new BufferedWriter(new FileWriter(cleanFile));
		int i=0;
		while(scan.hasNext()){
			String temp=scan.nextLine();
			String[] data=temp.split(" ");
			clean.write(clean(data[1])+"\t"+clean(data[2])+"\n");
			i++;
			if(i>=748)
				break;
		}
		clean.flush();
		clean.close();
	}

}
