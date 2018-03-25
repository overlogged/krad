public class GambleChecker {
    //constant about judge
    private static final int DRAW=0;
    private static final int AWINS=1;
    private static final int BWINS=2;

    //judge the result of one pair of gamble
    int win(Player playerA,Player playerB) {
        switch (playerA.gamble - playerB.gamble) {
            case 0:
                return DRAW;
            case 1:
            case 2:
                return AWINS;
            default:
                return BWINS;
        }
    }
}
