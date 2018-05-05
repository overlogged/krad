public class GambleChecker {
    //constant about gamble
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;

    /*
     judge the result of one pair of gamble
     if the return value is true, playerA wins
    */
    boolean win(Player playerA, Player playerB){
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
    void winJudge(FrontEndData gambleData){
        for(int i = 0; i < gambleData.playerNum; i++){
            for(int j = 0; j < gambleData.playerNum; j++){
                if(i == j)
                    continue;
                if(gambleData.players[i].team==gambleData.players[j].team)
                    continue;
                gambleData.players[i].isWin=win(gambleData.players[i],gambleData.players[j]);
                if(gambleData.players[i].isWin) {
                    gambleData.players[i].energy += 1;
                    break;
                }
            }
        }
    }
}
