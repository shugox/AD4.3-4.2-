import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DecisionTree implements DecisionTreeInterface{
	
	
	List<String> decisions;
	List<Entry<Integer,Integer>> next_decisions;
	String[] decisionDescriptions;	
	List<String> conclusions;	
	List<String> param_values;
 	String[] paramDescriptions;
 	int param_count;
 	DecisionTree(int parameter_count, int decision_count) {
 		param_count = parameter_count;
 		paramDescriptions = new String[param_count];
 		decisionDescriptions = new String[decision_count];
 		decisions = new LinkedList<String>();
		next_decisions = new LinkedList<Entry<Integer,Integer>>();
		conclusions = new LinkedList<String>();
		
 	}
	DecisionTree(int parameter_count, List<String> decisions, List<String> conclusions, List<Entry<Integer,Integer>> next) {
		this.decisions = decisions;
		this.conclusions = conclusions;
		this.next_decisions = next;
		param_count = parameter_count;
		decisionDescriptions = new String[decisions.size()];
		paramDescriptions = new String[param_count];
	}
	@Override
	public String conclude(List<String> params) {
		this.param_values = params;
		return conclude(0);
	}
	@Override
	public String conclude(int decision) {
		int next = decision;
		while(!(next < 0)) {
			next = decide(next, 0);
		}
		//System.out.println(next);
		return this.conclusions.get((next*-1)-2);
	}
	public String conclude_verbose(int decision) {
		int next = decision;
		int before;
		while(!(next < 0)) {
			System.out.println(decisionDescriptions[next]);
			before = next;
			next = decide(next, 1);
			if(next_decisions.get(before).getKey()-1 == next) System.out.println("Falsch");
			else System.out.println("Wahr");
		}
		System.out.println("Conclusion: " + this.conclusions.get((next*-1)-2));
		return this.conclusions.get((next*-1)-2);
	}
	
	public int decide(int decision, int verbose) {
		Boolean result;
		int next;
		List<String> formattedDecition = formatDecision(decisions.get(decision));
		result = evaluateDecision(formattedDecition, verbose);
		if(result) next =  next_decisions.get(decision).getValue()-1;
		else next = next_decisions.get(decision).getKey()-1;
		return next;
	}
	private Boolean evaluateDecision(List<String> formattedDecision, int verbose)
	{
		Boolean result;
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			if(verbose != 0 )System.out.println(formattedDecision.get(0)+ " " + formattedDecision.get(1) + " " + formattedDecision.get(2));
			result = (Boolean) engine.eval(formattedDecision.get(0)+ " " + formattedDecision.get(1) + " " + formattedDecision.get(2));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	private List<String> formatDecision(String args) {
		List<String> formattedStrings = new LinkedList<String>();
		Pattern pattern = Pattern.compile("(\\$?[0-9a-zA-Z]+|[><=|&]*)");
		Matcher matcher = pattern.matcher(args);
		while(matcher.find()) {
			if(matcher.start() - matcher.end() != 0){
				String temp;
				if(matcher.group().contains("$")) {
					 temp = param_values.get(Integer.parseInt(matcher.group().replace("$", ""))-1);
				
					
				} else {
					temp = matcher.group();
				
				}
				if(temp.matches("[a-zA-Z]+")){
					temp = "\""+temp+ "\"";
				}
				
				formattedStrings.add(temp);
			}	   
			     
			      
		}
		return formattedStrings;
	}

	@Override
	public int getParameterCount() {
		return param_count;
	}


	@Override
	public String getParameterDescription(int index) {
		return paramDescriptions[index-1];
	}


	@Override
	public void setParameters(List<String> params) {
		param_values = params;
		
	}


	@Override
	public void setParameter(String param, int index) {
		param_values.set(index-1,  param);
		
	}


	@Override
	public int getDecisionCount() {
		return decisions.size();
	}





	@Override
	public String getDecisionDescription(int index) {
		return decisionDescriptions[index-1];
	}
	
	

	@Override
	public void setParameterDescription(int index, String description) {
		
		paramDescriptions[index-1] = description;
		
	}


	@Override
	public void setParameterDescriptions(List<String> descriptions) {
		this.paramDescriptions = (String[]) descriptions.toArray();
		
	}


	@Override
	public void setDecisionDescription(int index, String description) {
		decisionDescriptions[index-1] = description;
		
	}


	@Override
	public void setDecisionDescription(List<String> descriptions) {
		this.decisionDescriptions = (String[]) descriptions.toArray();
		
	}




	public List<String> getDecisions() {
		return decisions;
	}
	public void setDecisions(List<String> decisions) {
		this.decisions = decisions;
	}
	public List<Entry<Integer, Integer>> getNext_decisions() {
		return next_decisions;
	}
	public void setNext_decisions(List<Entry<Integer, Integer>> next_decisions) {
		this.next_decisions = next_decisions;
	}
	public List<String> getConclusions() {
		return conclusions;
	}
	public void setConclusions(List<String> conclusions) {
		this.conclusions = conclusions;
	}


	@Override
	public String getParameter(int index) {
		return param_values.get(index-1);
		
	}



	@Override
	public List<String> getParameters() {
		return param_values;
		
	}
	@Override
	public Entry<Integer, Integer> getFollowingDecisions(int index) {
		return next_decisions.get(index);		
	}
	@Override
	public String conclude_verbose(List<String> params) {
		this.param_values = params;
		return conclude_verbose(0);
		
	}
}