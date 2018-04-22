import scala.concurrent.java8.FuturesConvertersImpl;

public class PlayerCheckerTest {
    public final static int INF = 2147483647;
    static PlayerCheckerTest test = new PlayerCheckerTest();
    static PlayerChecker pc = new PlayerChecker();

    void playerInit(Player playerA) {
        playerA.team = Player.ZOMBIE;
        playerA.range = 0;
        playerA.firePow = 0;
        playerA.hasElem = false;
        playerA.energy = INF;
        playerA.energyLim = INF;
        playerA.mot = 10000;
    }

    void printPlayerStatus(Player playerMain) {
        System.out.println("player's status");
        System.out.println("name: " + playerMain.name);
        System.out.println("team: " + playerMain.team);
        System.out.println("present location: " + playerMain.preLoc);
        System.out.println("gamble this turn: " + playerMain.gamble);
        System.out.println("win or not this turn: " + playerMain.isWin);
        System.out.println("decision this turn: " + playerMain.decision);
        System.out.println("present energy: " + playerMain.energy);
        System.out.println("has elem or not: " + playerMain.hasElem);
        System.out.println("energy limit: " + playerMain.energyLim);
        System.out.println("fire power: " + playerMain.firePow);
        System.out.println("motility: " + playerMain.mot);
        System.out.println("range: " + playerMain.range);
    }

    void infectionTest(Player playerA, Player playerB) {
        playerA.team = Player.ZOMBIE;
        playerB.team = Player.HUMAN;
        pc.elemAcq(playerB);
        pc.infection(playerA, playerB);
        assert playerB.team==Player.ZOMBIE : "infection failed! ";
        assert playerB.energy==0 : "energy not lost";
        assert playerB.hasElem==false : "elem not lost";
    }

    void energyConsumeTest(Player playerMain){
        playerMain.energy=1000;
        pc.energyConsume(playerMain,10000);
        assert playerMain.energy == 0:"consume failed";
    }

    public static void main(String argv[]) {
        Player playerA = new Player();
        Player playerB = new Player();
        test.playerInit(playerA);
        test.playerInit(playerB);
        test.infectionTest(playerA, playerB);
        playerA.team = Player.HUMAN;
        playerB.team = Player.HUMAN;
        test.energyConsumeTest(playerA);
    }
}
