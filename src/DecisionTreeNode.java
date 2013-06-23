import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class DecisionTreeNode {
    private ArrayList<DecisionTreeNode> next;
    private ArrayList<String> decisions;
    private final String nodeName;
    public ArrayList<Path> path;
    public Rectangle rectangle;

    private DecisionTreeNode(String s) {
        nodeName = s;
    }

    /**
     * Erstellt einen Node wenn er nicht schon in nodes enthalten ist, sonst
     * gibt er den Node in nodes zurueck Fuer jeden Namen gibt es genau einen
     * Node Wenn man mehrere Nodes hat mit gleichen Namen aber unterschiedlichen
     * Resultaten bei gleichen nachfolgenden Entscheidungen, kann man ein oder
     * mehrere ' ' hinter den Namen setzen
     * 
     * @param s
     * @return
     */
    public static DecisionTreeNode createNode(String s) {
        DecisionTreeNode node = new DecisionTreeNode(s);
        return node;
    }

    /**
     * Erzeugt eine Transition zwischen this und nextNode mit decision als Name
     * 
     * @param decision
     * @param nextNode
     */
    public void createTransition(String decision, DecisionTreeNode nextNode) {
        // System.out.println(decision);
        if (next == null) {
            next = new ArrayList<DecisionTreeNode>();
            decisions = new ArrayList<String>();
        }
        next.add(nextNode);
        decisions.add(decision);
    }

    /**
     * Erzeugt eine Transition zwischen this und nodeName mit decision als Name
     * 
     * @param decision
     * @param nextNode
     */
    public DecisionTreeNode createTransition(String decision, String nodeName) {
        DecisionTreeNode n = createNode(nodeName);
        createTransition(decision, n);
        return n;
    }

    public boolean equals(DecisionTreeNode n) {
        return this == n;
    }

    public String getName() {
        return nodeName;
    }

    public ArrayList<DecisionTreeNode> getNextNodes() {
        if (next == null) {
            return null;
        }
        return (ArrayList<DecisionTreeNode>) next.clone();
    }

    public ArrayList<String> getTransitionNames() {
        if (next == null) {
            return null;
        }
        return (ArrayList<String>) decisions.clone();
    }

    public void changeColor(Color c) {
        if (rectangle != null) {
            rectangle.setStroke(c);
        }
        if (path != null) {
            for (int i = 0; i < path.size(); i++) {
                path.get(i).setStroke(c);
            }
        }
    }
}