/**
 * Created by zhanyang on 15/10/11.
 */
public class EvaluationFunc {

    boolean ifplayer1;

    public EvaluationFunc(boolean ifplayer1) {
        this.ifplayer1 = ifplayer1;
    }

    public int getSimpleEvaluation(GameBoard gb) {
        int sign = ifplayer1 ? 1 : -1;
        return sign * (gb.boards4B[gb.boards4B.length - 1] - gb.boards4A[gb.boards4A.length - 1]);
    }
}
