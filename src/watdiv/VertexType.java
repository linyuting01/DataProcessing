package watdiv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class VertexType {
	/**
	 * Input watdiv, Output the vertices types
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		String path="D:/dataset/clean/watdiv.entity";
		String outPath="D:/dataset/clean/watdiv.entity.type";
		FileInputStream fin=new FileInputStream(path);
		BufferedReader br=new BufferedReader(new InputStreamReader(fin));
		String entity="";
		File outFile=new File(outPath);
		BufferedWriter bw=new BufferedWriter(new FileWriter(outFile));
		
		String[] types={"AgeGroup","City","Country","Gender","Genre","Language","Offer","Product","ProductCategory","Purchase","Retailer","Review","Role","SubGenre","Topic","User","Website"};
		System.out.println("Number of types:\t"+types.length);
		HashMap<String, Integer> entityType=new HashMap<String, Integer>();
		l:while((entity=br.readLine())!=null){
			String[] line=entity.split("\t");
			if(line[0].contains("Genre")&&!line[0].contains("SubGenre")){
				entityType.put(line[1], 4);
				bw.write(line[1]+"\t"+4+"\n");
				continue l;
			}
			if(line[0].contains("Product")&&line[0].contains("ProductCategory")){
				entityType.put(line[1], 7);
				bw.write(line[1]+"\t"+7+"\n");
				continue l;
			}
			for(int i=0;i<types.length;i++){
				if(line[0].contains(types[i])){
					entityType.put(line[1], i);
					bw.write(line[1]+"\t"+i+"\n");
					continue l;
				}
			}

			bw.write(line[1]+"\t"+17+"\n");
		}
		bw.flush();
		bw.close();
	}
}
