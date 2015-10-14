import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by zhanyang on 15/9/14.
 */
public class waterflow {

    public static void main(String[] args) throws IOException {

        if (args[0].equals("-i") && args.length > 1) {
            // clear the content of existing output.txt
            FileWriter clearfileobj = new FileWriter("output.txt");
            clearfileobj.close();

            // read the input file
            BufferedReader filein = new BufferedReader(new FileReader(args[1]));
            String line = filein.readLine();
            int casenum = Integer.parseInt(line);
            for (int i = 0; i < casenum; i++) {
                String alg = filein.readLine();

                // initialize starting node
                HashMap<String, Node> allnodes = new HashMap<>();
                String startnodename = filein.readLine();
                Node startnode = new Node(startnodename);
                allnodes.put(startnodename, startnode);

                //initialize goal node
                String[] goalnodenames = filein.readLine().split(" ");
                for (int j = 0; j < goalnodenames.length; j++) {
                    allnodes.put(goalnodenames[j], new Node(goalnodenames[j], true));
                }

                // initialize intermediate node
                String[] intermediatenodenames = filein.readLine().split(" ");
                for (int j = 0; j < intermediatenodenames.length; j++) {
                    allnodes.put(intermediatenodenames[j], new Node(intermediatenodenames[j]));
                }

                // initialize node path
                int pathnum = Integer.parseInt(filein.readLine());
                for (int j = 0; j < pathnum; j++) {
                    String[] words = filein.readLine().split(" ");
                    // Notice: copyOfRange can only be used in Java 1.6 or later!!
                    NodePath np = new NodePath(allnodes.get(words[0]), allnodes.get(words[1]),
                            Integer.parseInt(words[2]), (words[3].equals("0") ? null : Arrays.copyOfRange(words, 4, words.length)));
                    allnodes.get(words[0]).addPath(np);
                }

                int starttime = Integer.parseInt(filein.readLine());

                SearchAlgorithm alginstance = null;
                if (alg.equals("BFS")) {
                    alginstance = new BFS(startnode, starttime);
                } else if (alg.equals("DFS")) {
                    alginstance = new DFS(startnode, starttime);
                } else if (alg.equals("UCS")) {
                    alginstance = new UCS(startnode, starttime);
                }

                String message = "None";
                try {
                    Node goalnode = alginstance.search();
                    if (goalnode != null) {
                        message = goalnode.getName() + " " + ((starttime + goalnode.getCost()) % 24);
                    }
                    System.out.println(message);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    FileWriter fileout = new FileWriter("output.txt", true);
                    fileout.write(message);
                    if (i != casenum - 1) {
                        fileout.write("\n");
                    }
                    fileout.close();
                }


                // skip the space line between each case, remember to judge if the last case has this space line!!
                filein.readLine();
            }
        } else {
            System.out.println("Unacceptable parameter: you should execute by inputting 'java waterflow -i inputfile");
        }
    }
}
