package dbpedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import datasetreader.Edge;
import datasetreader.Reader;

public class GrapeFile {
	public static String cleanSecondEntry(String data){
		String[] tmp=data.split(" ");
		return tmp[0];
	}
	public static String cleanInput(String line){
		String[] data=line.split(" ");
		String result=data[0];
		result+="\t"+data[1]+"\t";
		for(int i=2;i<data.length-1;i++){
			result+=data[i]+" ";
		}
		result+=data[data.length-1];
		return result;
	}
	
	/**
	 * The Input are the .clean file, instance type file. The output is .v
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if(args.length<2){
			System.out.println("parameters missed");
			return;
		}
		String entityPath=args[0];
		String instancetypePath=args[1];
		String oriPath=args[2];
		
		FileInputStream entityFile=new FileInputStream(new File(entityPath));
		BufferedReader entitybr = new BufferedReader(new InputStreamReader(entityFile));
		String temp;
		HashMap<Integer, String> entity=new HashMap<Integer, String>();
		HashMap<String, String> instanceType=new HashMap<String, String>();
		HashSet<String> matchedEntity=new HashSet<String>();
		System.out.println("========Begin reading entity======");
		int i=0;
		while((temp=entitybr.readLine())!=null){
			String[] data=temp.split("t");
			entity.put(Integer.parseInt(data[1]), data[0]);
			i++;
			if(i%100000==0)
				System.out.println("[Finish]"+i);
		}
		System.out.println("========End of reading entity======");
		
		Reader r=new Reader(args[0]);
		instanceType=r.readInstance(instancetypePath);
		
		System.out.println("There are "+entity.size()+" entities");
		int count=0;
		for(i=0;i<entity.size();i++){
			if(instanceType.containsKey(entity.get(i))){
				count++;
				if(count<20){
					System.out.println("[Top match]"+ entity.get(i));
				}
				matchedEntity.add(entity.get(i));
			}
		}
		System.out.println("There are "+ count+" matched");
		ArrayList<Edge> edges=r.readCleanEdge();
		
		String line;
		i=0;
		FileInputStream fori = new FileInputStream(oriPath);
		BufferedReader bori = new BufferedReader(new InputStreamReader(fori));
		int ecount=0;
		while ((line = bori.readLine()) != null) {
			line=cleanInput(line);
			String[] data = line.split("\t");
			if (data.length != 3)
				continue;
			data[2]=cleanSecondEntry(data[2]);
			if(!matchedEntity.contains(data[0])||!matchedEntity.contains(data[2]))
				continue;
			
			
			i++;
			if(i%10000==0){
				System.out.println("[Finish]"+i);
			}
		}
	}
}
