import java.util.ArrayList;

/**
 * Created by zhanyang on 15/10/17.
 */
public class SearchStrategy {

    final static int TRAVERSE = 0;
    final static int HEURISTIC = 1;

    int strategy = 0;

    public SearchStrategy(int strategy) {
        this.strategy = strategy;
    }

    public ArrayList<Action> searchNextActions(Action currentact) {
        if (strategy == 0) {
            return getAllAction(currentact);
        }
        return null;
    }

    public ArrayList<Action> getAllAction(Action act) {
        int[] ownholes, opponentholes;
        int holenum = act.gb.holenum;

        ArrayList<Action> possibleactions = new ArrayList<>();

        for (int count = 0; count < holenum; count++) {
            // the next hole index to choose, when it's player2's turn, i doesn't equal to count
            int i;
            String turn;
            GameBoard gb = act.gb.clone();
            boolean freeturn = false;
            boolean nextplayer1 = !act.ifplayer1;
            int depth = (act.freeturn) ? act.depth : act.depth + 1;

            if (act.ifplayer1) {
                ownholes = gb.boards4B;
                opponentholes = gb.boards4A;
                i = count;
            } else {
                ownholes = gb.boards4A;
                opponentholes = gb.boards4B;
                i = holenum - 1 - count;
            }

            if (ownholes[i] != 0) {
                // sprinkle stones to other holes
                int stonenum = ownholes[i];
                ownholes[i] = 0;
                int index = 0;
                for (int j = 1; j <= stonenum; j++) {
                    index = (i + j) % (2 * holenum + 1);
//                  get into others owned hole
                    if (index > holenum) {
                        opponentholes[index - holenum - 1]++;
                    }
                    else {
                        ownholes[index]++;
                    }
                }

                // if it comes to own Mancala
                if (index == holenum) {
                    freeturn = true;
                    nextplayer1 = act.ifplayer1;
                }

                // if it comes to an empty hole in your side, do not care whether opposite is empty or not
                else if (index < holenum && ownholes[index] == 1) {
                    ownholes[holenum] += opponentholes[holenum - index - 1] + 1;
                    opponentholes[holenum - index - 1] = 0;
                    ownholes[index] = 0;
                }

                turn = generateActionName(i, act.ifplayer1, holenum);
                possibleactions.add(new Action(turn, gb, freeturn, nextplayer1, depth));
            }
        }

        return possibleactions;
    }


    public String generateActionName(int index, boolean ifplayer1, int holenum) {
        String name = "";
        if (ifplayer1) {
            name += "B" + (index + 2);
        } else {
            name += "A" + (holenum + 1 - index);
        }
        return name;
    }

}
