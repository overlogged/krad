package game;

import scala.concurrent.java8.FuturesConvertersImpl;

public class GambleCheckerTest {


    static void setFrontEndData(FrontEndData frontEndData){
        frontEndData.playerNum = 4;
        frontEndData.players = new Player[frontEndData.playerNum];
        for(int i = 0; i<frontEndData.playerNum; i++)
            frontEndData.players[i] = new Player();

        for(int i=0;i < 4;i++)
            frontEndData.players[i].healthPoint = 10;

        frontEndData.players[0].healthPoint = 10;
        frontEndData.players[0].handCards = new int[40 * frontEndData.playerNum];
//        frontEndData.players[0].cardsDesertList = new int[40 * frontEndData.playerNum];
//        for(int i = 0;i < frontEndData.players[0].cardsDesertList.length;i++)
//            frontEndData.players[0].cardsDesertList[i] = -1;

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
        frontEndData.players[2].gamble = Player.ROCK;
        frontEndData.players[3].gamble = Player.SCISSORS;
    }

    static void winJudgeTest(FrontEndData frontEndData){
        GambleChecker.winJudge(frontEndData.playerNum,frontEndData.players);
        assert frontEndData.players[0].isWin==true: "player0 false";
        assert frontEndData.players[1].isWin==true: "player1 false";
        assert frontEndData.players[2].isWin==false: "player2 false";
        assert frontEndData.players[3].isWin==true: "player3 false";
        assert frontEndData.players[0].energy == 10;
        assert frontEndData.players[1].energy == 10;
        assert frontEndData.players[2].energy == 0;
        assert frontEndData.players[3].energy == 4;
    }

    static int[] cardHeapInitTest(int[] cardHeap,int playerNum){
        int i,j;
        int[] cardType = new int[7];
        for(i = 0;i < 7;i++)
            cardType[i] = 0;
        cardHeap = GambleChecker.cardHeapInit(cardHeap,playerNum);
        for(j = 0;j < 7;j++) {
            for (i = 0; i < cardHeap.length; i++) {
                if (cardHeap[i] == j)
                    cardType[j] ++;
            }
        }
        assert cardType[0] == 0 : "NOTHING CARD ERROR";
        assert cardType[1] == 4 * playerNum : "PAPER CARD ERROR";
        assert cardType[2] == 4 * playerNum : "SCISSORS CARD ERROR";
        assert cardType[3] == 4 * playerNum : "ROCK CARD ERROR";
        assert cardType[4] == 12 * playerNum : "MOVE CARD ERROR";
        assert cardType[5] == 8 * playerNum : "FIRE CARD ERROR";
        assert cardType[6] == 8 * playerNum : "SKILLS CARD ERROR";
        return cardHeap;
    }

    static void cardHeapStirTest(int[] cardHeap,int playerNum){
        int i,j;
        int[] cardType = new int[7];
        for(i = 0;i < 7;i++)
            cardType[i] = 0;
        GambleChecker.cardHeapStir(cardHeap);
        for(j = 0;j < 7;j++) {
            for (i = 0; i < cardHeap.length; i++) {
                if (cardHeap[i] == j)
                    cardType[j] ++;
            }
        }
        assert cardType[0] == 0 : "NOTHING CARD ERROR";
        assert cardType[1] == 4 * playerNum : "PAPER CARD ERROR";
        assert cardType[2] == 4 * playerNum : "SCISSORS CARD ERROR";
        assert cardType[3] == 4 * playerNum : "ROCK CARD ERROR";
        assert cardType[4] == 12 * playerNum : "MOVE CARD ERROR";
        assert cardType[5] == 8 * playerNum : "FIRE CARD ERROR";
        assert cardType[6] == 8 * playerNum : "SKILLS CARD ERROR";
    }

    static void cardDistributeTest(int[] cardHeap, Player playerMain, int cardNum){
        int i,j;
//        for(i = 0;i < 8;i++)
//            System.out.println(cardHeap[i]);
//        System.out.println();
        GambleChecker.cardDistribute(cardHeap,playerMain,cardNum);
        assert playerMain.handCardsNum == 4 :"HANDCARD NUMBER ERROR";
//        for(i = 0;i < playerMain.handCards.length;i++) {
//            if(playerMain.handCards[i] != 0)
//                System.out.println(playerMain.handCards[i]);
//        }
//        System.out.println();
        GambleChecker.cardDistribute(cardHeap,playerMain,cardNum);
        assert playerMain.handCardsNum == 8 :"HANDCARD NUMBER ERROR";
//        System.out.println(playerMain.handCardsNum);
//        System.out.println();
//        for(i = 0;i < playerMain.handCards.length;i++) {
//            if(playerMain.handCards[i] != 0)
//                System.out.println(playerMain.handCards[i]);
//        }
    }

   static void cardDesertTest(Player playerMain, int[] cardHeap){
        int i,j;
        int cardNum = 12;
        int totalCardHeap = cardHeap.length;

        playerMain.handCardsNum = 0;
        for(i = 0;i < playerMain.handCards.length;i++)
            playerMain.handCards[i] = 0;
        GambleChecker.cardDistribute(cardHeap,playerMain,cardNum);
        for(i = 0;i < playerMain.handCardsNum;i++) {
            if(playerMain.handCards[i] != 0)
                System.out.println(playerMain.handCards[i]);
        }
        for(i = 0,j = 0;i < cardHeap.length;i++){
            if(cardHeap[i] != 0)
                j++;
        }
        assert j == totalCardHeap - cardNum : "HEAP NUMBER ERROR";
        assert playerMain.handCardsNum == cardNum : "HAND CARD NUMBER ERROR";
        System.out.println();
//        playerMain.cardsDesertNum = playerMain.handCardsNum - playerMain.healthPoint;
//        System.out.println(playerMain.handCards[10]);
//        System.out.println(playerMain.handCards[11]);
//        System.out.println();
//        playerMain.cardsDesertList[0] = 10;
//        playerMain.cardsDesertList[1] = 11;
//        GambleChecker.cardDesert(playerMain,cardHeap);
//        assert playerMain.handCardsNum == cardNum - playerMain.cardsDesertNum : "HAND CARD AFTER DESERT ERROR";
        for(i = 0;i < playerMain.handCardsNum;i++)
            System.out.println(playerMain.handCards[i]);
        for(i = 0,j = 0;i < cardHeap.length;i++){
            if(cardHeap[i] != 0)
                j++;
        }
//        assert j == totalCardHeap - cardNum + playerMain.cardsDesertNum : "HEAP CARD NUMBER AFTER DESERT ERROR";
    }

    public static void main(String argv[]){
        FrontEndData frontEndData = new FrontEndData();
        int playerNum = 5;
        int[] cardHeap = new int[40 * playerNum];
        GambleCheckerTest.setFrontEndData(frontEndData);
        cardHeap = GambleCheckerTest.cardHeapInitTest(cardHeap,playerNum);
        GambleCheckerTest.cardHeapStirTest(cardHeap, playerNum);
        GambleCheckerTest.cardDistributeTest(cardHeap,frontEndData.players[0],4);
//        test.cardHeapStirTest(cardHeap,playerNum);
//        test.cardDistributeTest(cardHeap,frontEndData.players[0],4);
//        cardHeap = test.cardHeapInitTest(cardHeap,playerNum);
//        test.cardHeapStirTest(cardHeap,playerNum);
//        test.cardDesertTest(frontEndData.players[0],cardHeap);
//        test.winJudgeTest(frontEndData);
    }
}
