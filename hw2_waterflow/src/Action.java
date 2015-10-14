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
    int value;

    public Action(String turn, GameBoard gb, boolean freeturn, boolean ifplayer1){
        this.turn = turn;
        this.gb = gb;
        this.freeturn = freeturn;
        this.ifplayer1 = ifplayer1;
    }

    public Action(String turn, GameBoard gb, boolean ifplayer1){
        this.turn = turn;
        this.gb = gb;
        this.freeturn = false;
        this.ifplayer1 = ifplayer1;
    }

    public Action(GameBoard gb, int value){
        this.gb = gb;
        this.value = value;
    }

}
