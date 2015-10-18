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


    public GameBoard decision(Action act) {
        int a = Integer.MIN_VALUE;
        int b = Integer.MAX_VALUE;
        return getMax(act, a, b).gb;
    }

    public Action getMax(Action act, int a, int b) {
        ArrayList<Action> actioncandidates = search.searchNextActions(act);

        if (act.gb.checkEmpty() || (act.depth >= cutoff && !act.freeturn)) {
            ev.evaluate(act);
            System.out.println(act.toAlphaBetaString(a, b));
            return act;
        }

        int v = Integer.MIN_VALUE;
        GameBoard gb = act.gb;
        act.value = v;
        System.out.println(act.toAlphaBetaString(a, b));

        for (Action possibleaction : actioncandidates) {
            int childvalue;
            Action ns;
            if (!possibleaction.freeturn) {
                ns = getMin(possibleaction, a, b);
            } else {
                ns = getMax(possibleaction, a, b);
            }
            childvalue = ns.value;
            if (childvalue > v) {
                v = childvalue;
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }

            act.value = v;

//            prune rest subtrees
            if (v >= b) {
                System.out.println(act.toAlphaBetaString(a, b));
                return new Action(act.gb, v);
            }

//            Update alpha
            a = Math.max(v, a);
            System.out.println(act.toAlphaBetaString(a, b));
        }
        return new Action(gb, v);
    }

    public Action getMin(Action act, int a, int b) {
        ArrayList<Action> actioncandidates = search.searchNextActions(act);

        if (act.gb.checkEmpty() || (act.depth >= cutoff && !act.freeturn)) {
            ev.evaluate(act);
            System.out.println(act.toAlphaBetaString(a, b));
            return act;
        }

        int v = Integer.MAX_VALUE;
        GameBoard gb = act.gb;
        act.value = v;
        System.out.println(act.toAlphaBetaString(a, b));

        for (Action possibleaction : actioncandidates) {
            int childvalue;
            Action ns;
            if (!possibleaction.freeturn) {
                ns = getMax(possibleaction, a, b);
            } else {
                ns = getMin(possibleaction, a, b);
            }
            childvalue = ns.value;
            if (childvalue < v) {
                v = childvalue;
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }

            act.value = v;

            if (v <= a) {
                System.out.println(act.toAlphaBetaString(a, b));
                return new Action(act.gb, v);
            }
            b = Math.min(v, b);
            System.out.println(act.toAlphaBetaString(a, b));
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
}
