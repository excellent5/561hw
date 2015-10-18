/**
 * Created by zhanyang on 15/10/11.
 */
public class Action {
    // the pit name of action
    String turn;
    // the gameboard state after taking this action
    GameBoard gb;
    // if after taking this action will cause an extra freeturn
    boolean freeturn;
    // who take turn after this action
    boolean ifplayer1;

    int depth;
    int value;

    public Action(String turn, GameBoard gb, boolean freeturn, boolean ifplayer1, int depth) {
        this.turn = turn;
        this.gb = gb;
        this.freeturn = freeturn;
        this.ifplayer1 = ifplayer1;
        this.depth = depth;
    }


    public Action(GameBoard gb, int value) {
        this.gb = gb;
        this.value = value;
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

    @Override
    public String toString() {
        String msg = turn + "," + depth + "," + printInfinity(value);
        return msg;
    }

    public String toAlphaBetaString(int a, int b) {
        return toString() + "," + printInfinity(a) + "," + printInfinity(b);
    }
}
