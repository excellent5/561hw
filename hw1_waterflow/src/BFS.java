import java.util.*;

/**
 * Created by zhanyang on 15/9/14.
 */
public class BFS extends SearchAlgorithm{

    public BFS(Node startnode, int starttime){
        this.startnode = startnode;
        this.starttime = starttime;
    }

    @Override
    public Queue<Node> makeQuene() {
        Queue<Node> open = new LinkedList<>();
        return open;
    }

    @Override
    public ArrayList<NodePath> expand(Node node, int starttime) {
        // get unexplored nodepaths
        ArrayList<NodePath> queuecandidates = new ArrayList<>();
        for (NodePath path : node.getPaths()) {
            if (!explored.contains(path.endnode) && !open.contains(path.endnode)) {
                path.endnode.setCost(node.getCost() + 1);
                queuecandidates.add(path);
            }
        }
        return queuecandidates;
    }

    @Override
    public Comparator<NodePath> getComparator() {
        return new Comparator<NodePath>() {
            @Override
            public int compare(NodePath o1, NodePath o2) {
                return o1.endnode.getName().compareTo(o2.endnode.getName());
            }
        };

    }
}
