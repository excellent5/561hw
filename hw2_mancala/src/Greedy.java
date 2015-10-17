/**
 * Created by zhanyang on 15/10/15.
 */
public class Greedy {

    SearchStrategy search;
    EvaluationFunc ev;
    int cutoff;

    public Greedy(SearchStrategy search, EvaluationFunc ev, int cutoff) {
        this.search = search;
        this.ev = ev;
        this.cutoff = cutoff;
    }

    public GameBoard decision(Action act, boolean ifplayer1){
        return new Minimax(search, ev, cutoff).minimaxDecision(act);
    }
}
