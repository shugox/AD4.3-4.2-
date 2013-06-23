import java.util.List;
import java.util.Map.Entry;


public interface DecisionTreeInterface {


	
	void setParameters(List<String> params);
	int decide(int decision, int verbose);

	
	
	int decide(int decision, String value);
	String conclude(List<String> params);
	String conclude_verbose(String[] params);
	String conclude(int decision);
	
	


	public List<String> getDecisions();
	public void setDecisions(List<String> decisions);
	public List<Entry<Integer, Integer>> getNext_decisions();
	public void setNext_decisions(List<Entry<Integer, Integer>> next_decisions);
	public List<String> getConclusions();
	public void setConclusions(List<String> conclusions);
	void setDecisionDescription(int index, String description);
	void setDecisionDescription(List<String> descriptions);
	String getDecisionDescription(int index);
	Entry<Integer, Integer> getFollowingDecisions(int index);
	String getParameterDescription(int index);
	
	void setParameterDescription(int index, String description);
	void setParameterDescriptions(List<String> descriptions);
	String getParameter(int index);
	String[] getParameters();
	/* parameter Setzten. */
	
	void setParameter(String param, int index);
	/* Anzahl der Parameter die für eine Decision benötigt werden */
	int getParameterCount();
	int getDecisionCount();

		
}
