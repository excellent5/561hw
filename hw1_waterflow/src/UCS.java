import java.util.*;

/**
 * Created by zhanyang on 15/9/14.
 */
public class UCS extends SearchAlgorithm {

    public UCS(Node startnode, int starttime) {
        this.startnode = startnode;
        this.starttime = starttime;
    }


    @Override
    public Queue<Node> makeQuene() {
        Queue<Node> open = new PriorityQueue<Node>(10, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.getCost() == o2.getCost()){
                    return o1.getName().compareTo(o2.getName());
                }
                return o1.getCost() - o2.getCost();
            }
        });
        return open;
    }


    @Override
    public ArrayList<NodePath> expand(Node node, int starttime) {
        // get unexplored nodepaths
        ArrayList<NodePath> queuecandidates = new ArrayList<>();
        for (NodePath path : node.getPaths()) {
            if (path.isavailable[(starttime + node.getCost()) % 24]) {
                if (!explored.contains(path.endnode) && !open.contains(path.endnode)) {
                    queuecandidates.add(path);
                    path.endnode.setCost(node.getCost() + path.length);
                } else if (open.contains(path.endnode)) {
                    if (node.getCost() + path.length < path.endnode.getCost()) {
                        open.remove(path.endnode);
                        path.endnode.setCost(node.getCost() + path.length);
                        open.add(path.endnode);
                    }
                } else if (explored.contains(path.endnode)) {
                    if (node.getCost() + path.length < path.endnode.getCost()) {
                        explored.remove(path.endnode);
                        path.endnode.setCost(node.getCost() + path.length);
                        open.add(path.endnode);
                    }
                }
            }
        }
        return queuecandidates;
    }

    @Override
    public Comparator<NodePath> getComparator() {
        return null;
    }
}
