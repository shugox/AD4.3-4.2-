import java.util.ArrayList;
import java.util.HashMap;


public class DecisionTreeNode {
    private ArrayList<DecisionTreeNode> next;
    private ArrayList<String> decisions;
    private final String nodeName;
    
    private static HashMap<String,DecisionTreeNode> nodes = new HashMap<String, DecisionTreeNode>();
    
    private DecisionTreeNode(String s){
        nodeName = s;
    }
    
    /**
     * Erstellt einen Node wenn er nicht schon in nodes enthalten ist, sonst gibt er den Node in nodes zurueck
     * Fuer jeden Namen gibt es genau einen Node 
     * Wenn man mehrere Nodes hat mit gleichen Namen aber unterschiedlichen Resultaten bei gleichen nachfolgenden Entscheidungen,
     * kann man ein oder mehrere ' ' hinter den Namen setzen
     * @param s
     * @return
     */
    public static DecisionTreeNode createNode(String s){
        if(nodes.containsKey(s)){
            return nodes.get(s);
        }else{
            DecisionTreeNode node = new DecisionTreeNode(s);
            nodes.put(s, node);
            return node;
        }
    }
    
    /**
     * Erzeugt eine Transition zwischen this und nextNode mit decision als Name
     * @param decision
     * @param nextNode
     */
    public void createTransition(String decision, DecisionTreeNode nextNode){
        if(next == null){
            next = new ArrayList<DecisionTreeNode>();
            decisions = new ArrayList<String>();
        }
        next.add(nextNode);
        decisions.add(decision);
    }
    
    /**
     * Erzeugt eine Transition zwischen this und nodeName mit decision als Name
     * @param decision
     * @param nextNode
     */
    public DecisionTreeNode createTransition(String decision, String nodeName){
        createTransition(decision, createNode(nodeName));
        return createNode(nodeName);
    }
    
    public String getName(){
        return nodeName;
    }
    
    public ArrayList<DecisionTreeNode> getNextNodes(){
        if(next == null){
            return null;
        }
        return (ArrayList<DecisionTreeNode>) next.clone();
    }
    
    public ArrayList<String> getTransitionNames(){
        if(next == null){
            return null;
        }
        return (ArrayList<String>) decisions.clone();
    }
}