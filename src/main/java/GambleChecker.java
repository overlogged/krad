public class GambleChecker {
    //constant about gamble
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;

    /*
     judge the result of one pair of gamble
     if the return value is true, playerA wins
    */
    static boolean win(Player playerA, Player playerB){
        switch (playerA.gamble-playerB.gamble){
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
                if(players[i].team==players[j].team)
                    continue;
                players[i].isWin=win(players[i],players[j]);
                if(players[i].isWin) {
                    if(players[i].energy<players[i].energyLim)
                        players[i].energy += 1;
                    break;
                }
            }
        }
    }
}
