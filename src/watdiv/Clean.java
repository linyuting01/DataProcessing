package watdiv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Set;

public class Clean {
	public static String cleanSecondEntry(String data){
		String[] tmp=data.split(" ");
		return tmp[0];
	}
	public static void main(String[] args) throws IOException {
		String oriPath = "D:/dataset/original/watdiv/watdiv.10M.nt";
		String cleanPath = "D:/dataset/clean/watdiv.10M.nt.clean";
		String entityMapPath="D:/dataset/clean/watdiv.entity";
		String predicateMapPath="D:/dataset/clean/watdiv.predicate";
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
		
		while ((line = bori.readLine()) != null) {
			String[] temp = line.split("\t");
			if (temp.length != 3)
				continue;
			temp[2]=cleanSecondEntry(temp[2]);
			
			if (!entity.containsKey(temp[0]))
				entity.put(temp[0], ecount++);
			if (!entity.containsKey(temp[2]))
				entity.put(temp[2], ecount++);
			if (!predicate.containsKey(temp[1]))
				predicate.put(temp[1], pcount++);
			clean.write(entity.get(temp[0])+"\t"+entity.get(temp[2])+"\n");
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
	}

}
