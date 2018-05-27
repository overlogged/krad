package game;

public class GambleChecker {
    //constant about gamble cards
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;
    //constant about strategy decision
    public static final int MOVE = 4;
    public static final int FIRE = 5;
    public static final int SKILLS = 6;
    public static final int DEPOSIT = 7;

    //TODO:Cards system{cards heap and cards distributing}
    // generate an ordered card heap
    int[] cardHeapInit(int[] cardHeap,int playerNum){
        //count variable i
        int i;
        // the number of cards equals to 37 * number of players
        cardHeap = new int[37 * playerNum];

        //the ratio of all kinds of cards is 8:12:8:4:4:4
        for(i = 0;i < 8 * playerNum;i++){
            cardHeap[i] = FIRE;
        }
        for(;i < i + 12 * playerNum;i++){
            cardHeap[i] = MOVE;
        }
        for(;i < i + 8*playerNum;i++){
            cardHeap[i] = SKILLS;
        }
        for(;i < i + 4*playerNum;i++){
            cardHeap[i] = PAPER;
        }
        for(;i < i + 4*playerNum;i++){
            cardHeap[i] = SCISSORS;
        }
        for(;i < i + 4*playerNum;i++){
            cardHeap[i] = STONE;
        }
        return cardHeap;
    }
    //mix the card heap up using random number
    int[] cardHeapStir(int[] cardHeap){
        for(int i = 0;i < cardHeap.length;i++){
            int tmp = (int)( Math.random() * (cardHeap.length) );
            int tmpCard = cardHeap[i];
            cardHeap[i] = cardHeap[tmp];
            cardHeap[tmp] = tmpCard;
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
