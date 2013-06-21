import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

	/**
	 * @param args
	 */
	static DecisionTree parseTreeCsv(String path) {
		List<String> decisions = new LinkedList<String>();
		List<Entry<Integer,Integer>> next_decisions = new LinkedList<Entry<Integer,Integer>>();
		List<String> conclusions = new LinkedList<String>();
		BufferedReader br = null;
		Pattern pattern;
		Matcher matcher;
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
			while(((line = br.readLine()) != null)) {
				if(!line.startsWith("//")) {
					
				
				pattern = Pattern.compile("(\\$?[0-9a-zA-Z]+[><=|&]+\\$?[0-9a-zA-Z]+)");
				matcher = pattern.matcher(line);
				while(matcher.find()) {
					decisions.add(matcher.group());
					//System.out.println("decision: " + matcher.group());
				}
				pattern = Pattern.compile("-?[1-9]+,-?[1-9]+");
				matcher = pattern.matcher(line);
				while(matcher.find()) {
					next_decisions.add(new AbstractMap.SimpleEntry<Integer,Integer>(Integer.parseInt(matcher.group().split(",")[0]), Integer.parseInt(matcher.group().split(",")[1])));
					//System.out.println("following: " + Integer.parseInt(matcher.group().split(",")[0]) + " "+ Integer.parseInt(matcher.group().split(",")[1]));
				}
				pattern = Pattern.compile("\"[A-Za-z ]+\"");
				matcher = pattern.matcher(line);
				while(matcher.find()) {
					conclusions.add(matcher.group().replace("\"", ""));
					//System.out.println(matcher.group().replace("\"", ""));
				}
				//System.out.println(line);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
	   } finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
		return new DecisionTree(decisions.size(), decisions, conclusions, next_decisions);
		
	}
	
	static List<List<String>> parseParameters(String path) {
		List<List<String>> parameters = new LinkedList<List<String>>();
		BufferedReader br = null;
		Pattern pattern;
		Matcher matcher;
		
		try {
			String line;
			
			br = new BufferedReader(new FileReader(path));
			while(((line = br.readLine()) != null)) {
				if(!line.startsWith("//")) {
				
				List<String> params = new LinkedList<String>();
				pattern = Pattern.compile("\"\\S+\"");
				matcher = pattern.matcher(line);
				while(matcher.find()) {
					params.add(matcher.group().replace("\"", ""));
					//System.out.println(matcher.group().replace("\"", ""));
				}
				parameters.add(params);
				//System.out.println(line);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
	   } finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
		return parameters;
	}
}
