import java.util.ArrayList;


/**
 * Created by zhanyang on 15/10/11.
 */
public class Alpha_Beta {

    SearchStrategy search;
    EvaluationFunc ev;
    int cutoff;

    public Alpha_Beta(SearchStrategy search, EvaluationFunc ev, int cutoff) {
        this.search = search;
        this.ev = ev;
        this.cutoff = cutoff;
    }


    public GameBoard minimaxDecision(Action act, boolean ifplayer1) {
//        int maxvalue = Integer.MIN_VALUE;
        int a = Integer.MIN_VALUE;
        int b = Integer.MAX_VALUE;
//        printLog(act, 0, maxvalue, a, b);
//        GameBoard maxgb = null;
//        ArrayList<Action> actioncandidates = getAllAction(act);
//        if (actioncandidates.size() == 0) {
//            endGame(act.gb);
//            printLog(act, 0, new EvaluationFunc(ifplayer1).getSimpleEvaluation(act.gb) ,a, b);
//            return act.gb;
//        }
//        for (Action possibleaction : actioncandidates) {
//            Action childstate;
//            if (possibleaction.freeturn) {
//                childstate = getMax(possibleaction, 1, a, b);
//            } else {
//                childstate = getMin(possibleaction, 1, a, b);
//            }
//            if (childstate.value > maxvalue) {
//                maxvalue = childstate.value;
//                maxgb = childstate.gb;
//            }
//            a = maxvalue;
//            printLog(act, 0, maxvalue, a, b);
//        }
//        return maxgb;
        return getMax(act, 0, a, b).gb;
    }

    public Action getMax(Action act, int depth, int a, int b) {
        ArrayList<Action> actioncandidates = getAllAction(act);
        if (actioncandidates.size() == 0) {
            endGame(act.gb);
            int value = ev.getEvaluationValue(act.gb);
            printLog(act, depth, value, a, b);
            return new Action(act.gb, value);
        }

        int v = Integer.MIN_VALUE;
        GameBoard gb = act.gb;
        if (depth >= cutoff) {
//            int value = new EvaluationFunc(ifplayer1).getSimpleEvaluation(act.gb);
//            if (act.freeturn) {
//                v = value;
//            } else {
//                printLog(act, depth, value, a, b);
//                act.value = value;
//                return new Action(act.gb, value);
//            }
            if(!act.freeturn){
                int value = ev.getEvaluationValue(act.gb);
                printLog(act, depth, value, a, b);
                act.value = value;
                return new Action(act.gb, value);
            }
        }
        printLog(act, depth, v, a, b);

        for (Action possibleaction : actioncandidates) {
            int childvalue = 0;
            int childdepth = depth + 1;
            Action ns = null;
            if (act.freeturn) {
                childdepth = depth;
            }
            if (!possibleaction.freeturn) {
                ns = getMin(possibleaction, childdepth, a, b);
                childvalue = ns.value;
            } else {
                ns = getMax(possibleaction, childdepth, a, b);
                childvalue = ns.value;
            }

            if (childvalue > v) {
                v = childvalue;
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }
//            v = Math.max(v, childvalue);
            if(v>=b){
                printLog(act, depth, v, a, b);
                return new Action(act.gb, v);
            }
            a = Math.max(v, a);
            printLog(act, depth, v, a, b);
        }
        return new Action(gb, v);
    }

    public Action getMin(Action act, int depth, int a, int b) {
        int parenta = a;
        ArrayList<Action> actioncandidates = getAllAction(act);
        if (actioncandidates.size() == 0) {
            endGame(act.gb);
            int value = ev.getEvaluationValue(act.gb);
            printLog(act, depth, value, a, b);
            return new Action(act.gb, value);
        }

        int v = Integer.MAX_VALUE;
        GameBoard gb = act.gb;
        if (depth >= cutoff) {
//            int value = new EvaluationFunc(ifplayer1).getSimpleEvaluation(act.gb);
//            if (act.freeturn) {
//                v = value;
//            } else {
//                printLog(act, depth, value, a, b);
//                return new Action(act.gb, value);
//            }
            if(!act.freeturn){
                int value = ev.getEvaluationValue(act.gb);
                printLog(act, depth, value, a, b);
                act.value = value;
                return new Action(act.gb, value);
            }
        }
        printLog(act, depth, v, a, b);

        for (Action possibleaction : actioncandidates) {
            int childvalue = 0;
            int childdepth = depth + 1;
            Action ns = null;
            if (act.freeturn) {
                childdepth = depth;
            }
            if (!possibleaction.freeturn) {
                ns = getMax(possibleaction, childdepth, a, b);
                childvalue = ns.value;
            } else {
                ns = getMin(possibleaction, childdepth, a, b);
                childvalue = ns.value;
            }
            if (childvalue < v) {
                v = childvalue;
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }

//            v = Math.min(v, childvalue);
            if(v<=a){
                printLog(act, depth, v, a, b);
                return new Action(act.gb, v);
            }
            b = Math.min(v, b);
            printLog(act, depth, v, a, b);
        }
        return new Action(gb, v);
    }

    //    one player cannot go any step, put all opponent stone into his mancala
    public void endGame(GameBoard gb) {
        int[] nonemptyplayer = (gb.boards4B[0] == 0) ? gb.boards4A : gb.boards4B;
        for (int i = 0; i < nonemptyplayer.length - 1; i++) {
            nonemptyplayer[nonemptyplayer.length - 1] += nonemptyplayer[i];
            nonemptyplayer[i] = 0;
        }
    }

    public String printInfinity(int value){
        String valuestring;
        if (value == Integer.MAX_VALUE) {
            valuestring = "Infinity";
        } else if (value == Integer.MIN_VALUE) {
            valuestring = "-Infinity";
        } else {
            valuestring = Integer.toString(value);
        }
        return valuestring;
    }

    public void printLog(Action act, int depth, int value, int a, int b) {
        String msg = act.turn + "," + depth + "," + printInfinity(value) + "," + printInfinity(a) + "," + printInfinity(b);
        System.out.println(msg);
    }

    public ArrayList<Action> getAllAction(Action act) {
        int[] ownholes, opponentholes;
        int holenum = act.gb.holenum;

        ArrayList<Action> possibleactions = new ArrayList<>();

        for (int count = 0; count < holenum; count++) {
            int i;
            GameBoard gb = act.gb.clone();
            boolean freeturn = false;
            boolean nextplayer1 = !act.ifplayer1;
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
                    changeGameBoard(index, ownholes, opponentholes, holenum);
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

                String turn = generateActionName(i, act.ifplayer1, holenum);
                possibleactions.add(new Action(turn, gb, freeturn, nextplayer1, -1));
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

    public void changeGameBoard(int index, int[] ownholes, int[] opponentholes, int holenum) {
//        get into others owned hole
        if (index > holenum) {
            opponentholes[index - holenum - 1]++;
        }
//        get into your holes
        else {
            ownholes[index]++;
        }
    }
}
