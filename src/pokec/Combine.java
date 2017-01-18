package pokec;

import tools.IOPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by samjjx on 2017/1/15.
 * Combine the graph with frequent key words
 */
public class Combine {
    public static void main(String[] args) throws IOException {
        String profilePath = "/Users/samjjx/dev/dataset/pokec/soc-pokec-profiles.txt";
        String relationPath = "/Users/samjjx/dev/dataset/pokec/soc-pokec-relationships.txt";
        String keywordPath = "/Users/samjjx/dev/dataset/pokec/soc-frequent200-size5-keywords";

        IOPack ioPack = new IOPack();
        BufferedReader probr = ioPack.getBufferInstance(profilePath);
        BufferedReader rebr = ioPack.getBufferInstance(relationPath);
        BufferedReader kwbr = ioPack.getBufferInstance(keywordPath);

        ArrayList<Integer> src = new ArrayList<>();
        ArrayList<Integer> dst = new ArrayList<>();

        ArrayList<Integer> index = new ArrayList<>();
        index.add(9);
        index.add(11);
        index.add(23);
        index.add(31);
        index.add(54);
        index.add(12);
        index.add(39);
        index.add(40);
        index.add(42);
        index.add(4);
        HashMap<String, Integer> labels =new HashMap<>();    // map to the new vertices
        HashMap<String, Integer> labelsMap =new HashMap<>(); // labels -> digital type
        HashMap<Integer, String> getLabelsMap= new HashMap<>(); // digital type -> labels

        ArrayList<String> vertices =new ArrayList<String>();
        for(int i=0;i<1632803;i++){
            vertices.add("People");
        }
        String line;
        int count = 1632803;
        int labelcount=1;
        labelsMap.put("People",0);
        getLabelsMap.put(0,"People");
        while ((line = kwbr.readLine()) != null) {
            labelsMap.put(line,labelcount);
            getLabelsMap.put(labelcount,line);
            labels.put(line,count);
            vertices.add(line);
            count++;
            labelcount++;
        }

        while ((line = rebr.readLine()) != null) {
            String[] edge=line.split("\t");
            src.add(Integer.parseInt(edge[0]));
            dst.add(Integer.parseInt(edge[1]));
        }

        Set<String> keyset = labels.keySet();
        int cal=0;
        while ((line = probr.readLine()) != null) {
            String[] data = line.split("\t");
            for (int i = 0; i < data.length; i++) {
                if (index.contains(i)) {
                    for(String key:keyset){
                        if(data[i].contains(key)){
                            src.add(Integer.parseInt(data[0]));
                            dst.add(labels.get(key));
                        }
                    }
                }
            }
            cal++;
            if(cal%1000==0){
                System.out.println("[Finish]"+cal);
            }
        }
        String basePath = "/Users/samjjx/dev/dataset/pokec-combine/pokec";
        String vPath=basePath+".v";
        String ePath=basePath+".e";
        String s2iPath=basePath+".mapi";
        String i2sPath=basePath+".maps";

        BufferedWriter vw=ioPack.getWritter(vPath);
        for(int i=0;i<vertices.size();i++){
            vw.write((i+1)+"\t"+labelsMap.get(vertices.get(i))+"\n");
        }
        vw.flush();
        vw.close();

        BufferedWriter ew=ioPack.getWritter(ePath);
        for(int i=0;i<src.size();i++){
            ew.write(src.get(i)+"\t"+dst.get(i)+"\t"+"c"+"\n");
        }
        ew.flush();
        ew.close();

        BufferedWriter s2iw=ioPack.getWritter(s2iPath);
        keyset=labelsMap.keySet();
        for(String key : keyset){
            s2iw.write(key+"\t"+labelsMap.get(key)+"\n");
        }
        s2iw.flush();
        s2iw.close();

        BufferedWriter i2sw=ioPack.getWritter(i2sPath);
        Set<Integer> tmp = getLabelsMap.keySet();
        for(Integer key:tmp){
            i2sw.write(key+"\t"+getLabelsMap.get(key)+"\n");
        }
        i2sw.flush();
        i2sw.close();
    }
}
