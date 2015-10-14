
/**
 * Created by zhanyang on 15/10/11.
 */
public class Mancala {

    public static void main(String[] args)  {
        boolean ifplayer1 = false;
        GameBoard gbinstance = new GameBoard("4 4 4 4 4 4", "4 4 4 4 4 4", "0", "0");
        Minimax mm = new Minimax(ifplayer1, gbinstance, 3);
        Action rootact = new Action("root", gbinstance, ifplayer1);
        GameBoard aaa = mm.minimaxDecision(rootact, ifplayer1);
        System.out.println("\n\n"+aaa);
    }
}
