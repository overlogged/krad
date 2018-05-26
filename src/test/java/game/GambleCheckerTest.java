package game;

public class GambleCheckerTest {
    static GambleCheckerTest test = new GambleCheckerTest();
    static GambleChecker gc = new GambleChecker();

    void setFrontEndData(FrontEndData frontEndData){
        frontEndData.playerNum=4;
        frontEndData.players = new Player[frontEndData.playerNum];
        for(int i = 0; i<frontEndData.playerNum; i++)
            frontEndData.players[i] = new Player();

        for(int i=0;i<4;i++)
            frontEndData.players[i].energyLim = 10;

        frontEndData.players[0].energy = 10;
        frontEndData.players[1].energy = 9;
        frontEndData.players[2].energy = 0;
        frontEndData.players[3].energy = 3;

        frontEndData.players[0].team = Player.ZOMBIE;
        frontEndData.players[1].team = Player.HUMAN;
        frontEndData.players[2].team = Player.HUMAN;
        frontEndData.players[3].team = Player.HUMAN;

        frontEndData.players[0].gamble = Player.PAPER;
        frontEndData.players[1].gamble = Player.SCISSORS;
        frontEndData.players[2].gamble = Player.STONE;
        frontEndData.players[3].gamble = Player.SCISSORS;
    }

    void winJudgeTest(FrontEndData frontEndData){
        gc.winJudge(frontEndData.playerNum,frontEndData.players);
        assert frontEndData.players[0].isWin==true: "player0 false";
        assert frontEndData.players[1].isWin==true: "player1 false";
        assert frontEndData.players[2].isWin==false: "player2 false";
        assert frontEndData.players[3].isWin==true: "player3 false";
        assert frontEndData.players[0].energy == 10;
        assert frontEndData.players[1].energy == 10;
        assert frontEndData.players[2].energy == 0;
        assert frontEndData.players[3].energy == 4;
    }

    public static void main(String argv[]){
        FrontEndData frontEndData = new FrontEndData();
        test.setFrontEndData(frontEndData);
        test.winJudgeTest(frontEndData);
    }
}
