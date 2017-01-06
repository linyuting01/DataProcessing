package dbpedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BFS {
	public static String clean(String line){
		String[] temp= line.split("/");
		return temp[temp.length-1];
	}
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		String[] edges=new String[748]; 
		String[] src=new String[748];
		String[] dst=new String[748];
		
		for(int i=0;i<edges.length;i++){
			edges[i]=scan.nextLine();
		}
		
		for(int i=0;i<edges.length;i++){
			String[] data=edges[i].split("\t");
			src[i]=clean(data[1]);
			dst[i]=clean(data[0]);
		}
		
		HashSet<String> vertices=new HashSet<String>();
		
		for(int i=0;i<edges.length;i++){
			vertices.add(src[i]);
			vertices.add(dst[i]);
		}
		
		HashMap<String, HashSet<String>> adj=new HashMap<String, HashSet<String>>();
		
		for(int i=0;i<edges.length;i++){
			if(!adj.containsKey(src[i])){
				adj.put(src[i], new HashSet<String>());
			}
			if(!adj.containsKey(dst[i]))
				adj.put(dst[i], new HashSet<String>());
			adj.get(src[i]).add(dst[i]);
		}
		
		int total=0;
		Set<String> temp=adj.keySet();
		for(String t:temp){
			HashSet<String> tt=adj.get(t);
			total+=tt.size();
		}
		
		HashSet<String> visited=new HashSet<String>();
		Queue<String> queue=new LinkedList<String>();
		queue.add("owl:Thing");
//		visited.add("owl:Thing");
		
		while(!queue.isEmpty()){
			String cur=queue.poll();
			if(visited.contains(cur))
				continue ;
//			System.out.println(cur);
//			System.out.println(adj.containsKey(cur));
			HashSet<String> link=adj.get(cur);
//			System.out.println(link.size());
			int count=0;
			for(String e:link){
				if(visited.contains(e))
					continue;
				queue.add(e);
				count++;
				if(count>10)
					break;
			}
			visited.add(cur);	
		}
		for(int i=0;i<edges.length;i++){
			if(visited.contains(src[i])&&visited.contains(dst[i]))
				System.out.println("g.addEdge(\""+src[i]+"\",\""+dst[i]+"\");");
			
		}
		System.out.println("Vertices number is:"+ vertices.size());
		
		
		System.out.println(visited.size());
		vertices.removeAll(visited);
		System.out.println("Total is: "+total);
		System.out.println("All remain is: "+ vertices.size());
		
		
	}

}
