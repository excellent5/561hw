/**
 * Created by zhanyang on 15/10/11.
 */
public class GameBoard {

    // the last element in boards is Mancala, board elements are indexed in counter-clockwise order.
    int[] boards4A, boards4B;
    int holenum;

    public GameBoard(String player2, String player1, String mancala4A, String mancala4B) {
        boards4A = string2intarray(player2, mancala4A, false);
        boards4B = string2intarray(player1, mancala4B, true);
        holenum = boards4A.length - 1;
    }

    public GameBoard(int[] boards4A, int[] boards4B, int holenum) {
        this.boards4A = boards4A;
        this.boards4B = boards4B;
        this.holenum = holenum;
    }

    public int[] string2intarray(String boardstates, String mancala, boolean ifplayer1) {
        String[] boardstate = boardstates.split(" ");
        int[] boardstateinteger = new int[boardstate.length + 1];
        for (int i = 0; i < boardstate.length; i++) {
            int index = i;
            if (!ifplayer1) {
                index = boardstate.length - i - 1;
            }
            boardstateinteger[index] = Integer.parseInt(boardstate[i]);
        }
        boardstateinteger[boardstateinteger.length - 1] = Integer.parseInt(mancala);
        return boardstateinteger;
    }

    public boolean ifempty(int[] board) {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != 0) {
                return false;
            }
        }
        return true;
    }

    //    one player cannot go any step, put all opponent stone into his mancala
    public void emptyBoard(int[] board) {
        for (int i = 0; i < board.length - 1; i++) {
            board[board.length - 1] += board[i];
            board[i] = 0;
        }
    }

    public boolean checkEmpty(){
        if(ifempty(boards4A)){
            emptyBoard(boards4B);
            return true;
        }
        else if(ifempty(boards4B)){
            emptyBoard(boards4A);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String msg = "";
        String boardsa = "";
        String boardsb = "";
        for (int i = 0; i < holenum; i++) {
            boardsa += Integer.toString(boards4A[holenum - i - 1]) + ",";
            boardsb += Integer.toString(boards4B[i]) + ",";
        }
        msg += boardsa.substring(0, boardsa.length() - 1) + "\n" + boardsb.substring(0, boardsb.length() - 1) +
                "\n" + boards4A[boards4A.length - 1] + "\n" + boards4B[boards4B.length - 1];
        return msg;
    }

    @Override
    public GameBoard clone() {
        return new GameBoard(boards4A.clone(), boards4B.clone(), holenum);
    }
}
