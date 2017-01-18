package pokec;

import tools.IOPack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by samjjx on 2017/1/16.
 * Translate the map file from sla to english
 */
public class Translator {
    public static void main(String[] args) throws IOException {
        IOPack ioPack =new IOPack();

        String basePath = "/Users/samjjx/dev/dataset/pokec-combine/pokec";
        String s2iPath=basePath+".mapi";
        String i2sPath=basePath+".maps";
        String sla = basePath+".total";
        String en = basePath+".total-en";

        BufferedReader bri = ioPack.getBufferInstance(s2iPath);
        BufferedReader brs = ioPack.getBufferInstance(i2sPath);
        BufferedReader brsla = ioPack.getBufferInstance(sla);
        BufferedReader bren = ioPack.getBufferInstance(en);

        String line;

        HashMap<String, Integer> mapInt=new HashMap<>();
        HashMap<Integer, String> mapS=new HashMap<>();
        HashMap<String, Integer> mapInten=new HashMap<>();
        HashMap<Integer, String> mapSen=new HashMap<>();
        HashMap<String, String> sla2en=new HashMap<>();

        while((line=bri.readLine())!=null){
            String[] data = line.split("\t");
            mapInt.put(data[0],Integer.parseInt(data[1]));
        }

        while((line=brs.readLine())!=null){
            String[] data = line.split("\t");
            mapS.put(Integer.parseInt(data[0]), data[1]);
        }

        while((line=brsla.readLine())!=null){
            sla2en.put(line,bren.readLine());
        }

        Set<String> keyset = mapInt.keySet();
        for(String key : keyset){
            mapInten.put(sla2en.get(key),mapInt.get(key));
        }

        Set<Integer> keysetS = mapS.keySet();
        for(Integer key: keysetS){
            mapSen.put(key, sla2en.get(mapS.get(key)));
        }

        String s2iPathen = basePath+".mapien";
        String i2sPathen = basePath+".mapsen";

        BufferedWriter bwien = ioPack.getWritter(s2iPathen);
        BufferedWriter bwsen = ioPack.getWritter(i2sPathen);

        keyset = mapInten.keySet();
        for(String key : keyset){
            bwien.write(key+"\t"+mapInten.get(key)+"\n");
        }
        bwien.flush();
        bwien.close();

        keysetS = mapSen.keySet();
        for(Integer key : keysetS){
            bwsen.write(key+"\t"+mapSen.get(key)+"\n");
        }
        bwsen.flush();
        bwsen.close();
    }
}
