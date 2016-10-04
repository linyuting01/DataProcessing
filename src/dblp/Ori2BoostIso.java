package dblp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Ori2BoostIso {
	public static void main(String[] args) throws IOException{
		
		String oriPath="D:/dataset/original/com-dblp.ungraph.txt";
		String boostPath="D:/dataset/boostiso/com-dblp.graph";
		int labelSet=1;
		FileInputStream fori=new FileInputStream(oriPath);
		BufferedReader bori=new BufferedReader(new InputStreamReader(fori));
		String line="";
		int vertex=0;
		HashSet<Integer> vSet=new HashSet<Integer>();
		HashMap<Integer, Integer> vMap=new HashMap<Integer, Integer>();
		ArrayList<Integer> src=new ArrayList<Integer>();
		ArrayList<Integer> dst=new ArrayList<Integer>();
		while((line=bori.readLine())!=null){
			if(line.charAt(0)=='#')
				continue;
			String[] temp=line.split("\t");
			int source=Integer.parseInt(temp[0]);
			int target=Integer.parseInt(temp[1]);
			if(!vSet.contains(source)){
				vSet.add(source);
				vMap.put(source, vertex++);
			}
			if(!vSet.contains(target)){
				vSet.add(target);
				vMap.put(target, vertex++);
			}
			src.add(vMap.get(source));
			dst.add(vMap.get(target));
		}
		
		File boostFile=new File(boostPath);
		BufferedWriter bwboost=new BufferedWriter(new FileWriter(boostFile));
		bwboost.write("t # 0\n");
		Random random=new Random();
		for(Integer v:vSet){
			bwboost.write("v "+vMap.get(v)+" "+random.nextInt(labelSet)+"\n");
		}
		for(int i=0;i<src.size();i++){
			bwboost.write("e "+src.get(i)+" "+dst.get(i)+" 0 \n");
		}
		bwboost.flush();
		bwboost.close();
	}
}
