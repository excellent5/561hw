import java.util.Arrays;

/**
 * Created by zhanyang on 15/9/14.
 */
public class NodePath {

    Node startnode = null;
    Node endnode = null;
    int length = 1;
    boolean[] isavailable = new boolean[24];

    public NodePath(Node startnode, Node endnode, int length, String[] stoptime) {
        this.startnode = startnode;
        this.endnode = endnode;
        this.length = length;
        Arrays.fill(isavailable, Boolean.TRUE);
        if (stoptime != null) {
            for (String time : stoptime) {
                int start = Integer.parseInt(time.split("-")[0]);
                int stop = Integer.parseInt(time.split("-")[1]);
                Arrays.fill(isavailable, start, stop + 1, Boolean.FALSE);
            }
        }
    }

}
