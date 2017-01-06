package datasetreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Reader {
	String basePath;
	public Reader(String basePath){
		this.basePath=basePath;
	}
	public static String extract(String line){
		line=line.replace("<", "");
		line=line.replace(">", "");
		line=line.replace("\"","");
		line=line.split("@")[0];
		String[] temp=line.split("/");
		return temp[temp.length-1];
	}
	public HashMap<String, Integer> readEntity() throws IOException{
		FileInputStream fr = new FileInputStream(basePath+".entity");
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
		String line;
		HashMap<String, Integer> result=new HashMap<String, Integer>();
		System.out.println("======Begin reading entity=====");
		int count=0;
		while((line=br.readLine())!=null){
			String[] data=line.split("\t");
			result.put(data[0], Integer.parseInt(data[1]));
			count++;
			if(count%100000==0)
				System.out.println("[Finish]"+count);
		}
		System.out.println("======Finish reading entity=====");
		
		return result;
	}
	public HashMap<String, String> readInstance(String path) throws IOException{
		FileInputStream instanceFile=new FileInputStream(new File(path));
		BufferedReader instancebr=new BufferedReader(new InputStreamReader(instanceFile));
		String temp;
		HashMap<String, String> instanceType=new HashMap<String, String>();
		
		int i=0;
		System.out.println("========Begin reading instance======");
		while((temp=instancebr.readLine())!=null){
			String[] data=temp.split(" ");
			instanceType.put(extract(data[0]), extract(data[2]));
			i++;
			if(i%100000==0)
				System.out.println("[Finish]"+i);
		}
		System.out.println("========End of reading instance======");
		return instanceType;
	}
	
	public ArrayList<Edge> readCleanEdge() throws NumberFormatException, IOException{
		ArrayList<Edge> edges=new ArrayList<Edge>();
		FileInputStream instanceFile=new FileInputStream(new File(basePath+".clean"));
		BufferedReader instancebr=new BufferedReader(new InputStreamReader(instanceFile));
		String temp;
		int i=0;
		System.out.println("========Begin reading edges======");
		while((temp=instancebr.readLine())!=null){
			String[] data=temp.split("\t");
			edges.add(new Edge(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
			i++;
			if(i%100000==0)
				System.out.println("[Finish]"+i);
		}
		System.out.println("========End of reading edges======");
		
		return edges;
	}
}
