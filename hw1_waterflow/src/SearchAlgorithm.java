import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

/**
 * Created by zhanyang on 15/9/14.
 */
public abstract class SearchAlgorithm {

    protected Node startnode = null;
    protected int starttime = 0;
    protected ArrayList<Node> explored = new ArrayList<>();
    protected Queue<Node> open = null;


    public Node search() {
        // initialize the open queue in instance
        open = makeQuene();
        open.add(startnode);
//        String queueorder = "";
        while (!open.isEmpty()) {
            Node node = open.poll();
//            queueorder += node.getName() + ",";
            if (node.isGoal()) {
//                System.out.println(queueorder);
                return node;
            }
            explored.add(node);

            // sort the path of node in different ways, UCS: cost, BFS: alphabet
            Comparator<NodePath> nodepathcomp = getComparator();
            ArrayList<NodePath> paths = expand(node, starttime);
            if (nodepathcomp != null) {
                Collections.sort(paths, nodepathcomp);
            }
            // add path to open queue
            for (NodePath path : paths) {
                open.add(path.endnode);
            }
        }
//        System.out.println(queueorder);
        return null;
    }

    public abstract Queue<Node> makeQuene();

    // expand the child of node, return the list of nodepath between node and children
    public abstract ArrayList<NodePath> expand(Node node, int starttime);

    public abstract Comparator<NodePath> getComparator();
}
