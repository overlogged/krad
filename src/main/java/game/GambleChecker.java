package game;

public class GambleChecker {
    //constant about gamble cards
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;
    //constant about strategy decision
    public static final int MOVE = 4;
    public static final int DEPOSIT = 5;
    public static final int FIRE = 6;
    public static final int SKILLS = 7;

    //TODO:Cards system{cards heap and cards distributing}
    // generate a random card heap
    int[] cardHeapGenerate(int[] cardHeap,int playerNum){
        int i;
        cardHeap = new int[37 * playerNum];
        for(i = 0;i < 8 * playerNum;i++){
            cardHeap[i] = FIRE;
        }
        for(;i < i + 12 * playerNum;i++){
            cardHeap[i] = MOVE;
        }
        for(;i < i + 8*playerNum;i++){
            cardHeap[i] = SKILLS;
        }
        return cardHeap;
    }


    /*
     judge the result of one pair of gamble
     if the return value is true, playerA wins
    */
    static boolean win(Player playerA, Player playerB){
        switch (playerA.gamble - playerB.gamble){
            case 1:
            case -2:
                return true;
            default:
                return false;
        }
    }

    /*
     judge if a player is win this turn
     if he wins then he can get one point of energy
    */
    static void winJudge(int playerNum, Player[] players){
        for(int i = 0; i < playerNum; i++){
            for(int j = 0; j < playerNum; j++){
                if(i == j)
                    continue;
                if(players[i].team == players[j].team)
                    continue;
                players[i].isWin = win(players[i],players[j]);
                if(players[i].isWin) {
                    if(players[i].energy < players[i].healthPoint)
                        players[i].energy += 1;
                    break;
                }
            }
        }
    }
}
