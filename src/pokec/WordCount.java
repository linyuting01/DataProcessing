package pokec;

import tools.IOPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by samjjx on 2017/1/10.
 */
public class WordCount {
    public static void main(String args[]) throws IOException {
        IOPack ioPack=new IOPack();
        String profilePath="/Users/samjjx/dev/dataset/pokec/soc-pokec-profiles.txt";
        BufferedReader br=ioPack.getBufferInstance(profilePath);
        BufferedWriter bw=ioPack.getWritter("/Users/samjjx/dev/dataset/pokec/soc-frequent200-size5-keywords");
//        String test="fdaga.fd$areq9fdagqrewq776dafafadsqeqw7daf6dad";
//        String[] testsplit=test.split("\\P{Alpha}+");
        String line="";
        HashSet<String> words=new HashSet<String>();
        HashMap<String, Integer> freq=new HashMap<String, Integer>();
        ArrayList<Integer> index=new ArrayList<>();
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
        int mark=0;
        while((line=br.readLine())!=null){
            String[] data=line.split("\t");
            for(int i=0;i<data.length;i++){
                if(index.contains(i)){
                    String[] temp=data[i].split("\\P{Alpha}+");
                    for(int j=0;j<temp.length;j++){
                        if(temp[j].length()<5)
                            continue;
                        if(!freq.containsKey(temp[j])){
                            freq.put(temp[j], 0);
                        }
                        freq.put(temp[j],freq.get(temp[j])+1);
                    }
                }
            }
            mark++;
            if(mark%1000==0){
                System.out.println("[Finish]"+mark);
            }
        }
        int threshold=200;
        int count=0;
        Set<String> keyset=freq.keySet();
        for(String key:keyset){
            int fword=freq.get(key);
            if(fword>threshold){
                words.add(key);
                bw.write(key+"\n");
                count++;
            }
        }
        bw.flush();
        bw.close();
        System.out.println("There are total "+count+" words satisfied the condition");
    }
}
