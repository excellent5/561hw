
/**
 * Created by zhanyang on 15/10/11.
 */
public class Mancala {

    public static void main(String[] args)  {
        boolean ifplayer1 = true;
        GameBoard gbinstance = new GameBoard("3 3 3 3 3", "3 3 3 3 3", "0", "0");
//        GameBoard gbinstance = new GameBoard("1", "2", "0", "0");
//        Minimax mm = new Minimax(ifplayer1, gbinstance, 2);
        Alpha_Beta mm = new Alpha_Beta(ifplayer1, gbinstance, 2);
        Action rootact = new Action("root", gbinstance, ifplayer1);
        GameBoard aaa = mm.minimaxDecision(rootact, ifplayer1);
        System.out.println("\n\n"+aaa);
    }
}
