package synthetic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Grape {

	public static void main(String[] args) throws IOException {
		FileInputStream fori = new FileInputStream(args[0]);
		BufferedReader bori = new BufferedReader(new InputStreamReader(fori));
		File eFile=new File(args[1]+".e");
		BufferedWriter ebf=new BufferedWriter(new FileWriter(eFile));
		
		String temp;
		while((temp=bori.readLine())!=null){
			String[] data=temp.split("\t");
			if(data[0]!="a")
				continue ;
			ebf.write(data[1]+"\t"+data[2]+"\t"+data[3]+"\n");
		}
		ebf.flush();
		ebf.close();
		
		File vFile=new File(args[1]+".v");
		BufferedWriter vbf=new BufferedWriter(new FileWriter(vFile));
		int number=Integer.parseInt(args[2]);
		Random rand=new Random();
		for(int i=0;i<number;i++){
			vbf.write((i+1)+"\t"+rand.nextInt(10)+"\n");
		}
		vbf.flush();
		vbf.close();
	}
}
