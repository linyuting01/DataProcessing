package tools;

import java.io.*;

/**
 * Created by samjjx on 2017/1/6.
 */
public class IOPack {
    public IOPack(){

    }
    public BufferedReader getBufferInstance(String path) throws FileNotFoundException {
        FileInputStream fr = new FileInputStream(path);
        BufferedReader br=new BufferedReader(new InputStreamReader(fr));
        return br;
    }
    public BufferedWriter getWritter(String path) throws IOException {
        File file=new File(path);
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        return bw;
    }
}
