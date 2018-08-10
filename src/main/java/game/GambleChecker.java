package game;

import java.util.Arrays;

public class GambleChecker {
    //constant about gamble cards
    public static final int NOTHING = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int ROCK = 3;
    //constant about strategy decision
    public static final int MOVE = 4;
    public static final int FIRE = 5;
    public static final int SKILLS = 6;
    public static final int DEPOSIT = 7;

    // generate an ordered card heap
    static void cardHeapInit(int[] cardHeap, int playerNum) {
//        //count variable i
//        int i, j;
//        //the ratio of all kinds of cards is FIRE:MOVE:SKILLS:PAPER:SCISSORS:ROCK = 8:12:8:4:4:4
//        //the ratio of all kinds of cards is FIRE:MOVE:SKILLS:PAPER:SCISSORS:ROCK = 0:10:0:10:10:10
//        for (i = 0; i < 0 * playerNum; i++) {
//            cardHeap[i] = FIRE;
//        }
//        j = i;
//        for (; i < j + 10 * playerNum; i++) {
//            cardHeap[i] = MOVE;
//        }
//        j = i;
//        for (; i < j + 0 * playerNum; i++) {
//            cardHeap[i] = SKILLS;
//        }
//        j = i;
//        for (; i < j + 10 * playerNum; i++) {
//            cardHeap[i] = PAPER;
//        }
//        j = i;
//        for (; i < j + 10 * playerNum; i++) {
//            cardHeap[i] = SCISSORS;
//        }
//        j = i;
//        for (; i < j + 10 * playerNum; i++) {
//            cardHeap[i] = ROCK;
//        }
//        return cardHeap;
    }

    //mix the card heap up using random number
    static void cardHeapStir(int[] cardHeap) {
//        int len = cardHeap.length;
//        Double[] aid_arr = new Double[len];
//        for (int i = 0; i < len; i++) {
//            aid_arr[i] = Math.random();
//        }
//        for (int i = 0; i < len; i++) {
//            for (int j = i + 1; j < len; j++) {
//                if (aid_arr[i] > aid_arr[j]) {
//                    double minv = aid_arr[j];
//                    aid_arr[j] = aid_arr[i];
//                    aid_arr[i] = minv;
//
//                    int card = cardHeap[j];
//                    cardHeap[j] = cardHeap[i];
//                    cardHeap[i] = card;
//                }
//            }
//        }
    }

    //cards operation
    static void cardSort(int[] cardHeap) {
        for (int i = 0; i < cardHeap.length; i++) {
            for (int j = i + 1; j < cardHeap.length; j++) {
                if (cardHeap[i] < cardHeap[j]) {
                    int tmp = cardHeap[i];
                    cardHeap[i] = cardHeap[j];
                    cardHeap[j] = tmp;
                }
            }
        }
    }

    static void cardToPlayer(int card, Player playerMain) {
        for (int i = 0; i < playerMain.handCards.length; i++) {
            if (playerMain.handCards[i] == NOTHING) {
                playerMain.handCards[i] = card;
                playerMain.handCardsNum++;
                break;
            }
        }
    }

    static void cardToHeap(int[] cardHeap, int card) {
//
//        int i = (int) (Math.random() * cardHeap.length) % cardHeap.length;
//        while (cardHeap[i] != NOTHING) i = (i + 1) % cardHeap.length;
//
//        cardHeap[i] = card;
    }

    static void cardDistribute(int[] cardHeap, Player playerMain, int cardNum) {
        for(int i=0;i<cardNum;i++){
            int s = (int)(Math.random()*4)%4 + 1;
            cardToPlayer(s,playerMain);
        }
//        int distributedCardNum = 0;
//        for (int i = 0; i < cardHeap.length; i++) {
//            // dirty hack
//            if ((cardHeap[i] == 7) || (cardHeap[i] == -1))
//                cardHeap[i] = NOTHING;
//
//            if (cardHeap[i] != NOTHING) {
//                cardToPlayer(cardHeap[i], playerMain);
//                cardHeap[i] = NOTHING;
//                distributedCardNum++;
//            }
//            if (distributedCardNum == cardNum) {
//                cardSort(playerMain.handCards);
//                break;
//            }
//        }
    }

    static void cardDesert(Player playerMain, int[] cardHeap, int[] desertList) {
        for (int i = 0; i < desertList.length; i++) {
            if (desertList[i] == -1)
                break;
//            cardToHeap(cardHeap, playerMain.handCards[desertList[i]]);
            playerMain.handCards[desertList[i]] = NOTHING;
            playerMain.handCardsNum--;
        }
        cardSort(playerMain.handCards);
//        cardHeapStir(cardHeap);
    }

    /*
     * judge the result of one pair of gamble
     * if the return value is true, playerA wins
     */
    static boolean win(Player playerA, Player playerB) {
        switch (playerA.gamble - playerB.gamble) {
            case 1:
            case -2:
                return true;
            default:
                return false;
        }
    }

    /*
     * judge if a player is win this turn
     * if he wins then he can get one point of energy
     */
    static void winJudge(int playerNum, Player[] players) {
        for (int i = 0; i < playerNum; i++) {
            for (int j = 0; j < playerNum; j++) {
                if (i == j)
                    continue;
                if (players[i].team == players[j].team)
                    continue;
                players[i].isWin = win(players[i], players[j]);
                if (players[i].isWin)
                    break;
            }
        }
    }
}
