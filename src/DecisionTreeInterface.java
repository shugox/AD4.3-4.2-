import java.util.List;
import java.util.Map.Entry;


public interface DecisionTreeInterface {

	int getParameterCount();
	void setParameters(List<String> params);
	void setParameter(String param, int index);
	
	String getParameter(int index);
	List<String> getParameters();
	
	void setParameterDescription(int index, String description);
	void setParameterDescriptions(List<String> descriptions);
	String getParameterDescription(int index);	

	
	int getDecisionCount();
	Entry<Integer, Integer> getFollowingDecisions(int index);
	int decide(int decision, int verbose);
	
	void setDecisionDescription(int index, String description);
	void setDecisionDescription(List<String> descriptions);
	String getDecisionDescription(int index);

	String conclude(List<String> params);
	String conclude_verbose(List<String> params);
	String conclude(int decision);
	
	


	public List<String> getDecisions();
	public void setDecisions(List<String> decisions);
	public List<Entry<Integer, Integer>> getNext_decisions();
	public void setNext_decisions(List<Entry<Integer, Integer>> next_decisions);
	public List<String> getConclusions();
	public void setConclusions(List<String> conclusions);
	
	
	
		
}
