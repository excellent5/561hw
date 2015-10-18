import java.util.ArrayList;


/**
 * Created by zhanyang on 15/10/11.
 */
public class Minimax {

    SearchStrategy search;
    EvaluationFunc ev;
    int cutoff;

    public Minimax(SearchStrategy search, EvaluationFunc ev, int cutoff) {
        this.search = search;
        this.ev = ev;
        this.cutoff = cutoff;
    }


    public GameBoard decision(Action act) {
        return getMax(act).gb;
    }


    public Action getMax(Action act) {
        ArrayList<Action> actioncandidates = search.searchNextActions(act);

//        check if there exists player whose holes are all empty or the depth arrives cut-off value
        if (act.gb.checkEmpty() || (act.depth >= cutoff && !act.freeturn)) {
            ev.evaluate(act);
            System.out.println(act);
            return act;
        }

        int v = Integer.MIN_VALUE;
        GameBoard gb = act.gb;
        act.value = v;
        System.out.println(act);

        for (Action possibleaction : actioncandidates) {
            int childvalue;
            Action ns;
            if (!possibleaction.freeturn) {
                ns = getMin(possibleaction);
            } else {
                ns = getMax(possibleaction);
            }
            childvalue = ns.value;
            if (childvalue > v) {
                v = childvalue;
//                If current step is freeturn or root, store the gameboard of next step instead
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }
            act.value = v;
            System.out.println(act);
        }
        return new Action(gb, v);
    }

    public Action getMin(Action act) {
        ArrayList<Action> actioncandidates = search.searchNextActions(act);

        if (act.gb.checkEmpty() || (act.depth >= cutoff && !act.freeturn)) {
            ev.evaluate(act);
            System.out.println(act);
            return act;
        }

        int v = Integer.MAX_VALUE;
        GameBoard gb = act.gb;
        act.value = v;
        System.out.println(act);

        for (Action possibleaction : actioncandidates) {
            int childvalue;
            Action ns;
            if (!possibleaction.freeturn) {
                ns = getMax(possibleaction);

            } else {
                ns = getMin(possibleaction);
            }
            childvalue = ns.value;
            if (childvalue < v) {
                v = childvalue;
                if (act.freeturn || act.turn.equals("root")) {
                    gb = ns.gb;
                }
            }
            act.value = v;
            System.out.println(act);
        }
        return new Action(gb, v);
    }

}
