import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
    Stage stage;
    Scene scene;
    String csv_path;
    final int sizex = 1500;
    final int sizey = 1000;
    final int stepx = 600;
    final int virtualSizeX = 3000;
    DecisionTreeNode firstNode;
    int index = 0;
    DecisionTree TestTree;


    /**
     * Erstellt ein Text in einem Rechteck und zieht einen Rahmen darum
     * 
     * @param s
     * @param r
     * @return
     */
    private Node createText(String s, Rectangleing r, DecisionTreeNode n ){
        return createText(s, r, Color.BLACK, n);
    }

    private Node createText(String s, Rectangleing r, Color c, DecisionTreeNode n) {
        Group g = new Group();
        // System.out.println(s);
        Text t = new Text(((r.minx + (stepx / 2) / 2) - (s.length() * 3) + 15), (r.miny + r.maxy) / 2 + 7, s);
        if (r.maxy - r.miny > 10) {
            Rectangle borders = new Rectangle();
            borders.setX(r.minx + 5);
            borders.setY(r.miny + 5);
            borders.setWidth((stepx / 2) - 5);
            borders.setHeight(r.maxy - r.miny - 5);
            borders.setFill(Color.WHITE);
            borders.setStrokeLineCap(StrokeLineCap.ROUND);
            borders.setStroke(c);
            n.rectangle = borders;
            g.getChildren().add(borders);
        }
        g.getChildren().add(t);
        return g;
    }

    /**
     * Erstellt einen Pfad
     * 
     * @param sx
     * @param sy
     * @param dx
     * @param dy
     * @return
     */
    private Path createPath(int sx, int sy, int dx, int dy, Color c) {
        Path p = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(sx);
        moveTo.setY(sy);

        LineTo lineTo = new LineTo();
        lineTo.setX(dx);
        lineTo.setY(dy);
        p.getElements().add(moveTo);
        p.getElements().add(lineTo);

        p.setStrokeWidth(2);
        p.setStroke(c);
        return p;
    }

    /**
     * Erstellt eine Transition
     * 
     * @param s
     * @param r
     * @return
     */
    private Node createTransition(String s, Rectangleing r, DecisionTreeNode n) {
        return createTransition(s, r, Color.BLACK, n);
    }

    private Node createTransition(String s, Rectangleing r, Color c, DecisionTreeNode n) {
        Group g = new Group();
        Path p = createPath(r.minx - (stepx / 2), ((r.maxy - r.miny) / 2) + r.miny, r.minx, ((r.maxy - r.miny) / 2) + r.miny, c);
        g.getChildren().add(p);
        n.path = new ArrayList<Path>();
        n.path.add(p);
        p = createPath(r.minx - 5, ((r.maxy - r.miny) / 2) + r.miny - 3, r.minx, ((r.maxy - r.miny) / 2) + r.miny, c);
        g.getChildren().add(p);
        n.path.add(p);
        p = createPath(r.minx - 5, (((r.maxy - r.miny) / 2) + r.miny) + 3, r.minx, ((r.maxy - r.miny) / 2) + r.miny, c);
        g.getChildren().add(p);
        n.path.add(p);
        g.getChildren().add(new Text(r.minx - (stepx / 4) + 5 - (s.length() * 3), ((r.maxy - r.miny) / 2) + r.miny - 2, s));
        return g;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(GUI.class, args);

    }

    /**
     * Bitte Ueberschreiben
     * 
     * @return
     */
    private DecisionTreeNode doMagic() {
        int transition;

        TestTree = Parser.parseTreeCsv(csv_path);
        List<String> decisions = TestTree.getDecisions();
        DecisionTreeNode Nodes[] = new DecisionTreeNode[decisions.size()];
        DecisionTreeNode Conclusions[] = new DecisionTreeNode[TestTree.getConclusions().size()];
        for (int i = 0; i < decisions.size(); i++) {
            Nodes[i] = DecisionTreeNode.createNode(TestTree.getDecisionDescription(i + 1));
        }
        for (int i = 0; i < TestTree.getConclusions().size(); i++) {
            Conclusions[i] = DecisionTreeNode.createNode(TestTree.getConclusions().get(i));
        }
        for (int i = 0; i < TestTree.getNext_decisions().size(); i++) {
            // System.out.println(i);
            transition = TestTree.getNext_decisions().get(i).getKey();
            String[] transition_text = TestTree.getformatedDecision(i, "x").toArray(new String[3]);

            if (transition > 0) {
                // System.out.println("Nein d");
                Nodes[i].createTransition(transition_text[0].replace("\"", "") + " !" + transition_text[1] + " " + transition_text[2],
                        Nodes[transition - 1]);
            } else {
                // System.out.println("Nein c");
                Nodes[i].createTransition(transition_text[0].replace("\"", "") + " !" + transition_text[1] + " " + transition_text[2],
                        Conclusions[(transition * -1) - 1]);
            }
            transition = TestTree.getNext_decisions().get(i).getValue();
            transition_text = TestTree.getformatedDecision(i, "x").toArray(new String[3]);
            if (transition > 0) {
                // System.out.println("Ja d ");
                Nodes[i].createTransition(transition_text[0].replace("\"", "") + " " + transition_text[1] + " " + transition_text[2],
                        Nodes[transition - 1]);
            } else {
                // System.out.println("Ja c");
                Nodes[i].createTransition(transition_text[0].replace("\"", "") + " " + transition_text[1] + " " + transition_text[2],
                        Conclusions[(transition * -1) - 1]);
            }
        }
        /*
         * + DecisionTreeNode n = DecisionTreeNode.createNode("Start");
         * DecisionTreeNode bla = n; n.createTransition("Test1", "Ende1"); n =
         * n.createTransition("Test2", "Bla2"); n.createTransition("Test4",
         * "Tsdas"); n.createTransition("Test5", "Tsdaasds");
         * n.createTransition("Test6", "Tsasds");
         */
        return Nodes[0];
    }

    /**
     * Erstellt die Gui
     */
    @Override
    public void start(Stage arg0) throws Exception {
        stage = new Stage();
        stage.show();
        List<String> args = getParameters().getUnnamed();
        if (args.size() != 1)
            throw new IllegalArgumentException();
        csv_path = args.get(0);
        // csv_path = "Bild.csv";
        firstNode = doMagic();
        index = 0;
        scene = createView(0, firstNode);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
       // startNode;
        
        firstNode.changeColor(Color.RED);
        DecisionTreeNode node = firstNode.getNextNodes().get(0);
        node.changeColor(Color.RED);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    survey();
                }
            }
        });

    }

    private void survey() {
        int next = 0;
        while (next >= 0) {
            String inputValue = "";
            while (inputValue.equals("")) {
                inputValue = JOptionPane.showInputDialog(this.TestTree.getDecisionDescription(next + 1) + "\n"
                        + this.TestTree.getParameterDescription(TestTree.getParamFroDecision(next)));
                if (inputValue == null)
                    return;
            }

            next = TestTree.decide(next, inputValue);
        }

        JOptionPane.showMessageDialog(null, this.TestTree.getConclusions().get((next * -1) - 2));
    }

    /**
     * Erstellt die scene des Trees
     * 
     * @param i
     * @param startNode
     * @return
     */
    private Scene createView(int i, DecisionTreeNode startNode) {
        final Scene tree = new Scene(new Group(), sizex, sizey);

        final ObservableList<Node> content = ((Group) tree.getRoot()).getChildren();
        content.addAll(createStep(startNode, new Rectangleing(0, stepx, 0, sizey), i));
        
        final ScrollBar sc = new ScrollBar();
        sc.setLayoutY(sizey - 10);
        sc.setLayoutX(0);
        sc.setMin(0);
        sc.setOrientation(Orientation.HORIZONTAL);
        sc.setPrefHeight(10);
        sc.setPrefWidth(sizex);
        sc.setMax(virtualSizeX);
        content.add(sc);
        
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                tree.getRoot().setLayoutX(-new_val.doubleValue());
                sc.setLayoutX(0 + new_val.doubleValue());
            }
        });
        
        return tree;
    }

    /**
     * Erstellt einen Zustand und ruft die Methoden fuer die Zustaende auf in
     * die sie Transistieren
     * 
     * @param startNode
     * @param r
     * @param index
     * @return
     */
    private ArrayList<Node> createStep(DecisionTreeNode startNode, Rectangleing r, int index) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        if (index <= 0) {
            nodes.add(createText(startNode.getName(), r, startNode));
        }

        if (startNode.getNextNodes() != null) {
            Rectangleing rec = new Rectangleing(r.minx, r.maxx, r.miny, r.miny + ((r.maxy - r.miny) / startNode.getNextNodes().size()));
            if (index <= 0) {
                rec.minx = r.maxx;
                rec.maxx += stepx;
            }
            for (int i = 0; i < startNode.getNextNodes().size(); i++) {
                if (index <= 0) {
                        nodes.add(createTransition(startNode.getTransitionNames().get(i), rec, startNode.getNextNodes().get(i)));
                }
                nodes.addAll(createStep(startNode.getNextNodes().get(i), rec, index - 1));
                rec.miny += ((r.maxy - r.miny) / startNode.getNextNodes().size());
                rec.maxy += ((r.maxy - r.miny) / startNode.getNextNodes().size());
            }
        }
        return nodes;
    }

    /**
     * Der Name wurde nur gewaehlt weil Rectangle schon belegt war
     * 
     */
    private class Rectangleing {
        int minx;
        int maxx;
        int miny;
        int maxy;

        Rectangleing(int x1, int x2, int y1, int y2) {
            minx = x1;
            miny = y1;
            maxx = x2;
            maxy = y2;
        }

    }
}
