/**
 * Created by zhanyang on 15/10/15.
 */
public class Greedy {

    boolean ifplayer1;
    int cutoff;

    public Greedy(boolean ifplayer1, int cutoff) {
        this.ifplayer1 = ifplayer1;
        this.cutoff = cutoff;
    }

    GameBoard decision(Action act, boolean ifplayer1){
        return new Minimax(ifplayer1, 1).minimaxDecision(act, ifplayer1);
    }
}
