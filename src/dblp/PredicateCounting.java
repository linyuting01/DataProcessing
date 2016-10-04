package dblp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class PredicateCounting {

	public static void main(String[] args) throws IOException {
		String path="D:/dataset/clean/dblp.triple";
		FileInputStream fori=new FileInputStream(path);
		BufferedReader bori=new BufferedReader(new InputStreamReader(fori));
		String line="";
		HashSet<String> predicate=new HashSet<String>();
		int count=0;
		while((line=bori.readLine())!=null){
			String[] lines=line.split("\t");
			predicate.add(lines[1]);
			count++;
			if(count%10000==0)
				System.out.println(count);
		}
		System.out.println("# of predicate:"+predicate.size());
	}

}
