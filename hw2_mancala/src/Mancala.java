import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhanyang on 15/10/11.
 */
public class Mancala {

    public static void main(String[] args) throws IOException {
        if (args[0].equals("-i") && args.length > 1) {
            // clear the content of existing output.txt
            FileWriter clearfileobj = new FileWriter("traverse_log.txt");
            clearfileobj.close();

            // read the input file
            BufferedReader filein = new BufferedReader(new FileReader(args[1]));

            // save the algorithm string, and initialize algorithm instance after getting all information
            String algorithm = filein.readLine();

            boolean ifplayer1 = true;
            if (filein.readLine().equals("2")) {
                ifplayer1 = false;
            }

            int depth = Integer.parseInt(filein.readLine());

            String board4player2 = filein.readLine();
            String board4player1 = filein.readLine();
            String mancala2 = filein.readLine();
            String mancala1 = filein.readLine();
            filein.close();
            GameBoard gbinstance = new GameBoard(board4player2, board4player1, mancala2, mancala1);

            Action rootact = new Action("root", gbinstance, false, ifplayer1, 0);
            EvaluationFunc ev = new EvaluationFunc(ifplayer1, EvaluationFunc.MANCALADIFF);
            SearchStrategy search = new SearchStrategy(SearchStrategy.TRAVERSE);
            FileWriter log = new FileWriter("traverse_log.txt", true);
            FileWriter nstate = new FileWriter("next_state.txt");

            GameBoard nextstate = null;
            if (algorithm.equals("1")) {
                nextstate = new Greedy(search, ev, depth, log).decision(rootact, ifplayer1);
            } else if (algorithm.equals("2")) {
                log.write("Node,Depth,Value" + "\n");
                nextstate = new Minimax(search, ev, depth, log).decision(rootact);
            } else if (algorithm.equals("3")) {
                log.write("Node,Depth,Value,Alpha,Beta" + "\n");
                nextstate = new Alpha_Beta(search, ev, depth, log).decision(rootact);
            } else if (algorithm.equals("4")) {
                // temporarily use minimax for competition
                log.write("Node,Depth,Value" + "\n");
                nextstate = new Minimax(search, ev, depth, log).decision(rootact);
            }
            log.close();
            nstate.write(nextstate.toString());
            nstate.close();
        } else {
            System.out.println("Unacceptable parameter: you should execute by inputting 'java waterflow -i inputfile");
        }

    }
}
