import java.util.ArrayList;

/**
 * Created by zhanyang on 15/9/14.
 */
public class Node {

    private String name = null;
    private ArrayList<NodePath> paths = new ArrayList<>();
    private boolean isgoal = false;
    private int cost = 0;

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, boolean isgoal) {
        this.name = name;
        this.isgoal = isgoal;
    }

    public void addPath(NodePath path) {
        this.paths.add(path);
    }

    public boolean isGoal() {
        return this.isgoal;
    }

    public ArrayList<NodePath> getPaths() {
        return this.paths;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
