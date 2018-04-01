public class GambleChecker {
    //judge the result of one pair of gamble
    void win(Player playerA, Player playerB) {
        switch (playerA.gamble - playerB.gamble) {
            case 0:
                break;
            case 1:
            case 2:
                playerA.isWin=true;
            default:
                playerB.isWin=true;
        }
    }
}
