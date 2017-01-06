package watdiv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import datasetreader.Edge;
import datasetreader.Reader;

public class Clean {
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
	public static String extract(String line){
		line=line.replace("<", "");
		line=line.replace(">", "");
		line=line.replace("\"","");
		line=line.split("@")[0];
		String[] temp=line.split("/");
		return temp[temp.length-1];
	}
	public static void main(String[] args) throws IOException {
		if(args.length!=2){
			System.out.println("Parameters wrong");
			return;
		}
		
		String oriPath = args[0];
		String cleanPath = args[0]+".clean";
		String entityMapPath=args[0]+".entity";
		String predicateMapPath=args[0]+".predicate";
		String vFile=args[0]+".v";
		
		FileInputStream fori = new FileInputStream(oriPath);
		BufferedReader bori = new BufferedReader(new InputStreamReader(fori));
		String line;
		HashMap<String, Integer> entity = new HashMap<String, Integer>();
		HashMap<String, Integer> predicate = new HashMap<String, Integer>();
		int ecount = 0;
		int pcount = 0;
		int cleanIndicator=0;
		File cleanFile=new File(cleanPath);
		BufferedWriter clean=new BufferedWriter(new FileWriter(cleanFile));
		
		Reader r=new Reader(oriPath);
		HashMap<String, String> instance=r.readInstance(args[1]);
		while ((line = bori.readLine()) != null) {
			line=cleanInput(line);
			String[] temp = line.split("\t");
			if (temp.length != 3)
				continue;
			temp[2]=cleanSecondEntry(temp[2]);
			temp[0]=extract(temp[0]);
			temp[2]=extract(temp[2]);
			if(temp[0].equals(temp[2]))
				continue;
			if(!instance.containsKey(temp[0])||!instance.containsKey(temp[2]))
				continue;
			
			if (!entity.containsKey(temp[0]))
				entity.put(temp[0], ecount++);
			if (!entity.containsKey(temp[2]))
				entity.put(temp[2], ecount++);
			if (!predicate.containsKey(temp[1]))
				predicate.put(temp[1], pcount++);
			clean.write(entity.get(temp[0])+"\t"+entity.get(temp[2])+"\t"+1+"\n");
			cleanIndicator++;
			if(cleanIndicator%10000==0){
				System.out.println(cleanIndicator);
			}
		}
		clean.flush();
		clean.close();
		File entityFile=new File(entityMapPath);
		BufferedWriter entitybf=new BufferedWriter(new FileWriter(entityFile));
		Set<String> keySet=entity.keySet();
		for(String e:keySet){
			entitybf.write(e+"\t"+entity.get(e)+"\n");
		}
		entitybf.flush();
		entitybf.close();
		
		File predicateFile=new File(predicateMapPath);
		BufferedWriter predicatebf=new BufferedWriter(new FileWriter(predicateFile));
		keySet=predicate.keySet();
		for(String e:keySet){
			predicatebf.write(e+"\t"+predicate.get(e)+"\n");
		}
		
		predicatebf.flush();
		predicatebf.close();

		File vertexFile=new File(vFile);
		BufferedWriter vbf=new BufferedWriter(new FileWriter(vertexFile));
		String[] entities=new String[entity.size()];
		
		for(String e:keySet){
			entities[entity.get(e)]=e;
		}
		for(int i=0;i<entities.length;i++){
			vbf.write(i+"\t"+instance.get(entities[i])+"\t"+entities[i]+"\n");
		}
		vbf.flush();
		vbf.close();
	}
}
