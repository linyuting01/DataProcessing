package pokec;

import tools.IOPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by samjjx on 2017/1/6.
 */
public class Generate {
    public static void main(String[] args) throws IOException {
        String profilePath="/Users/samjjx/dev/dataset/pokec/soc-pokec-profiles.txt";
        String relationPath="/Users/samjjx/dev/dataset/pokec/soc-pokec-relationships.txt";
        String basePath="/Users/samjjx/dev/dataset/pokec/pokec";
        String vPath=basePath+".v";
        String ePath=basePath+".e";
        String vmapPath=basePath+".vmap";

        IOPack ipack=new IOPack();
        BufferedReader pbr=ipack.getBufferInstance(profilePath);
        BufferedReader rbr=ipack.getBufferInstance(relationPath);

        String line="";
        HashMap<Integer,HashMap<String,Integer>> freq=new HashMap<Integer, HashMap<String, Integer>>();

        ArrayList<String> edges=new ArrayList<String>();
        int numVertices=1632803;
        HashMap<Integer,String> vertices=new HashMap<Integer,String>();
        for(int i=0;i<numVertices;i++)
            vertices.put(i+1,"People");
        while((line=rbr.readLine())!=null){
            edges.add(line);
        }

        System.out.println("===========Begin=============");
        HashMap<String, Integer> attrVertices=new HashMap<String, Integer>();
        //index 1 3 4 7 should be kept
        while((line=pbr.readLine())!=null){
            String[] data=line.split("\t");
            for(int i=0;i<data.length;i++){
                if(i!=1&&i!=3&&i!=4&&i!=7)
                    continue;;
                if(data[i].equals("null")) {
                    continue;
                }
                if(i==1){
                    if(data[i].equals("1"))
                        data[i]="public";
                    else if(data[i].equals("0"))
                        data[i]="private";
                }
                if(i==3){
                    if(data[i].equals("0"))
                        data[i]="female";
                    else if(data[i].equals("1"))
                        data[i]="male";
                }
                if(!attrVertices.containsKey(data[i])){
                    numVertices++;
                    attrVertices.put(data[i],numVertices);
                    vertices.put(numVertices,data[i]);
                }
                edges.add(data[0]+"\t"+attrVertices.get(data[i]));
//                if(!freq.containsKey(i)){
//                    freq.put(i,new HashMap<String, Integer>());
//                }
//                if(!freq.get(i).containsKey(data[i])){
//                    freq.get(i).put(data[i],0);
//                }
//                freq.get(i).put(data[i],freq.get(i).get(data[i])+1);
            }
        }
        int labelcount=1;

        System.out.println("===========Begin Writing Vertices=============");
        HashMap<String, Integer> labelMap=new HashMap<String, Integer>();
        BufferedWriter vw=ipack.getWritter(vPath);
        for(int i=1;i<=numVertices;i++){
            String label=vertices.get(i);
            if(!labelMap.containsKey(label)){
                labelMap.put(label,labelcount);
                labelcount++;
            }
            vw.write(i+"\t"+labelMap.get(label)+"\n");
        }
        vw.flush();
        vw.close();


        System.out.println("===========Begin Writing Vertices=============");
        BufferedWriter ew=ipack.getWritter(ePath);
        for(int i=0;i<edges.size();i++){
            ew.write(edges.get(i)+"\n");
        }
        ew.flush();
        ew.close();



        System.out.println("===========Begin Writing label map=============");
        BufferedWriter mapw=ipack.getWritter(vmapPath);
        Set<String> labels=labelMap.keySet();
        for(String label:labels){
            mapw.write(label+"\t"+labelMap.get(label)+"\n");
        }
        mapw.flush();
        mapw.close();
    }
}

