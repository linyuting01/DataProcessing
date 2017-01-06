package dbpedia;

public class Test {
	public static String extract(String line){
		line=line.replace("<", "");
		line=line.replace(">", "");
		line=line.replace("\"","");
		line=line.split("@")[0];
		String[] temp=line.split("/");
		return temp[temp.length-1];
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
	public static void main(String[] args) {
		String line1=new String("owl:Thing)");
		System.out.println(line1.split(")"));
		String line2=new String("ssss");
		System.out.println(line1==line2);
		String line="<http://dbpedia.org/resource/Computer_accessibility> <http://dbpedia.org/ontology/wikiPageWikiLinkText> \"Computer accessibility\"@en .";
		String[] temp=cleanInput(line).split("\t");
		
		System.out.println(extract(temp[0]));
		System.out.println(extract(temp[1]));
		System.out.println(extract(temp[2]));
	}

}
