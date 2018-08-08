package game;

public class CardHeapTest {
    public void cardDis(Player playerMain, int[] cardHeap){
        if(playerMain.handCardsNum + 4 <= playerMain.healthPoint)
            GambleChecker.cardDistribute(cardHeap,playerMain,4);
        else
            GambleChecker.cardDistribute(cardHeap,playerMain, playerMain.healthPoint - playerMain.handCardsNum);
    }

    public void cycleDis(Player playerMain, int[] cardHeap, CardHeapTest ct, int[] cardDesertList){
        ct.cardDis(playerMain,cardHeap);
        for(int i = 0; i < playerMain.handCardsNum; i++)
            System.out.println(playerMain.handCards[i]);
        GambleChecker.cardDesert(playerMain,cardHeap,cardDesertList);
        System.out.println();
    }
    public void cyclePrint(int[] cardHeap){
        for(int i = 0; i < cardHeap.length; i++)
            System.out.println(cardHeap[i]);
        System.out.println();
        System.out.println();
    }
    public void cardTypeDetec(int[] cardHeap){
        int[] cardType= new int[5];
        for(int i = 0; i < 5; i++)
            cardType[i] = 0;
        for(int i = 0;i < cardHeap.length; i++){
            if(cardHeap[i] == 0)
                cardHeap[0] += 1;
            else if(cardHeap[i] == 1)
                cardType[1] += 1;
            else if(cardHeap[i] == 2)
                cardType[2] += 1;
            else if(cardHeap[i] == 3)
                cardType[3] += 1;
            else if(cardHeap[i] == 4)
                cardType[4] += 1;
        }
        System.out.println("NOTHING:"+ cardType[0]);
        System.out.println("PAPER:"+ cardType[1]);
        System.out.println("SCISSORS:"+ cardType[2]);
        System.out.println("ROCK:"+ cardType[3]);
        System.out.println("MOVE:"+ cardType[4]);
        System.out.println();
        System.out.println();
    }
    public static void main(String[] args){
        CardHeapTest ct  = new CardHeapTest();
        int[] cardHeap;
        int playerNum = 4;
        Player playerMain = new Player();
        playerMain.healthPoint = 12;
        playerMain.handCards = new int[4 + playerMain.healthPoint];
        cardHeap = new int[40 * playerNum];
        int[] cardDesertList = new int[40 * playerNum];

        for(int i = 0; i < cardDesertList.length; i++)
            cardDesertList[i] = -1;
        cardDesertList[0] = 0;
        cardDesertList[1] = 1;
        cardDesertList[2] = 2;
        GambleChecker.cardHeapInit(cardHeap,playerNum);
        GambleChecker.cardHeapStir(cardHeap);
        for(int i = 0; i < 100; i++) {
            ct.cycleDis(playerMain, cardHeap, ct, cardDesertList);
        }
        ct.cardDis(playerMain,cardHeap);
        System.out.println(playerMain.handCardsNum);
        ct.cyclePrint(playerMain.handCards);
        ct.cyclePrint(cardHeap);
    }
}
