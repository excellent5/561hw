import java.io.FileWriter;

/**
 * Created by zhanyang on 15/10/15.
 */
public class Greedy {

    SearchStrategy search;
    EvaluationFunc ev;
    int cutoff;
    FileWriter fw;

    public Greedy(SearchStrategy search, EvaluationFunc ev, int cutoff, FileWriter fw) {
        this.search = search;
        this.ev = ev;
        this.cutoff = cutoff;
        this.fw = fw;
    }

    public GameBoard decision(Action act, boolean ifplayer1) {
        return new Minimax(search, ev, cutoff, fw).decision(act);
    }
}
