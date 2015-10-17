/**
 * Created by zhanyang on 15/10/11.
 */
public class EvaluationFunc {

    final static int MANCALADIFF = 0;

    boolean ifplayer1;
    int strategy = 0;

    public EvaluationFunc(boolean ifplayer1, int strategy) {
        this.ifplayer1 = ifplayer1;
        this.strategy = strategy;
    }

    public void evaluate(Action act){
        if(strategy == 0){
            act.value = getMancalaDiff(act.gb);
        }
    }

    public int getEvaluationValue(GameBoard gb){
        if(strategy == 0){
            return getMancalaDiff(gb);
        }
        return 0;
    }

    public int getMancalaDiff(GameBoard gb) {
        int sign = ifplayer1 ? 1 : -1;
        return sign * (gb.boards4B[gb.boards4B.length - 1] - gb.boards4A[gb.boards4A.length - 1]);
    }
}
