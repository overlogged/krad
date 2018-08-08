package game;

import java.util.TreeMap;

import game.GodHelper.*;
import scala.Option;

public class God {

    private int playerNum;          // how many people to play the game
    private Player[] allPlayers;    // preserve the state of players
    private boolean humanWin;       // whether human team wins
    private boolean zombieWin;      // whether zombie team wins
    private Map map;                // map of the game
    private String[] heroList = {"Calculus", "Linear Algebra", "PDE", "Mathematical Analysis"};
    private UserInfo[] allUserInfo;
    private int[] cardHeap;
    private int[] availableFireTarget;

    private String[] heroChoices;
    private int[] teamResult;
    private int[] decisionChoices;
    private int[] seenCardChoices;
    private int[] gambleChoices;
    private int[] cardNumList;
    private int[] playerWinList;
    private int[] energyList;
    private int[] healthPointList;
    private int[] locationList;
    private int[] elementList;
    private int[] teamList;
    private int[] scoreList;
    private int[] fireList;

    enum GameState {INIT, MAINGAME}

    private GameState gameState = GameState.INIT;
    private int[] playerState;

    enum PhaseState {PREPARE, GAMBLE, ACTION}

    private PhaseState phaseState = PhaseState.PREPARE;

    public String request(int sid, String msg) {
        int playerIndex = 0;
        for (int i = 0; i < playerNum; i++) {
            if (allPlayers[i].SID == sid) {
                playerIndex = i;
                break;
            }
        }
        String result = "{\"state\":\"Error\"}";
        switch (gameState) {
            case INIT:
                if (playerState[playerIndex] == 0) {
                    result = GodHelper.toInit(allUserInfo, "choose hero", playerIndex, heroList);
                    playerState[playerIndex] += 1;
                } else if (playerState[playerIndex] == 1) {
                    heroChoose(sid, msg);
                    gameState = GameState.MAINGAME;
                    playerState[playerIndex] = 0;
                    result = GodHelper.toChooseHero("start game", heroChoices, teamResult);
                }
                break;
            case MAINGAME:
                switch (phaseState) {
                    case PREPARE:
                        if (playerState[playerIndex] == 0) {
                            wait_count = 0;
                            if (allPlayers[playerIndex].handCardsNum + 4 <= allPlayers[playerIndex].healthPoint)
                                GambleChecker.cardDistribute(cardHeap, allPlayers[playerIndex], 4);
                            else
                                GambleChecker.cardDistribute(cardHeap, allPlayers[playerIndex], allPlayers[playerIndex].healthPoint - allPlayers[playerIndex].handCardsNum);
                            int[] playerHandCard = new int[allPlayers[playerIndex].handCardsNum];
                            for (int i = 0; i < allPlayers[playerIndex].handCardsNum; i++)
                                playerHandCard[i] = allPlayers[playerIndex].handCards[i];
                            seenCardJudge(playerIndex);
                            for (int i = 0; i < playerNum; i++) {
                                if (map.distance[allPlayers[playerIndex].preLoc][allPlayers[i].preLoc] < allPlayers[playerIndex].range)
                                    availableFireTarget[i] = 1;
                            }
                            int[] availableMoveDirection = map.units[allPlayers[playerIndex].preLoc].availableDir();
                            result = GodHelper.toCardDistribute("choose strategy decision", playerHandCard, availableFireTarget, availableMoveDirection);
                            playerState[playerIndex] += 1;
                        } else if (playerState[playerIndex] == 1) {
                            MsgChooseDecision dec = GodHelper.getChooseDecision(msg);

                            // operation to the player's properties
                            if (dec.decision() == -1)
                                allPlayers[playerIndex].stratDecision = GambleChecker.DEPOSIT;
                            else {
                                allPlayers[playerIndex].stratDecision = allPlayers[playerIndex].handCards[dec.decision()];
                                GambleChecker.cardToHeap(cardHeap, allPlayers[playerIndex].handCards[dec.decision()]);
                                allPlayers[playerIndex].handCards[dec.decision()] = GambleChecker.NOTHING;
                                GambleChecker.cardSort(allPlayers[playerIndex].handCards);
                                allPlayers[playerIndex].handCardsNum -= 1;
                            }
                            decisionChoices[playerIndex] = allPlayers[playerIndex].stratDecision;
                            featureChoose(msg, allPlayers[playerIndex], playerIndex);
                            int[] playerHandCard = new int[allPlayers[playerIndex].handCardsNum];
                            for (int i = 0; i < allPlayers[playerIndex].handCardsNum; i++)
                                playerHandCard[i] = allPlayers[playerIndex].handCards[i];
                            //ends

                            if (allPlayers[playerIndex].isSeenCard)
                                result = GodHelper.toChooseDecision("must choose seen card", playerHandCard);
                            else
                                result = GodHelper.toChooseDecision("choose seen card", playerHandCard);
                            playerState[playerIndex] += 1;
                        } else if (playerState[playerIndex] == 2) {
                            seenCard(playerIndex, msg, allPlayers[playerIndex]);
                            if (allPlayers[playerIndex].isSeenCard)
                                result = GodHelper.toSeenCard("GAMBLE: win judge", decisionChoices, seenCardChoices);
                            else
                                result = GodHelper.toSeenCard("GAMBLE: choose gamble", decisionChoices, seenCardChoices);
                            playerState[playerIndex] = 0;
                            phaseState = PhaseState.GAMBLE;
                        }
                        break;
                    case GAMBLE:
                        if (playerState[playerIndex] == 0) {
                            gambleChoose(msg, allPlayers[playerIndex], playerIndex);
                            playerState[playerIndex] += 1;
                            int[] playerHandCard = new int[allPlayers[playerIndex].handCardsNum];
                            for (int i = 0; i < allPlayers[playerIndex].handCardsNum; i++)
                                playerHandCard[i] = allPlayers[playerIndex].handCards[i];
                            result = GodHelper.toChooseGamble("win judge", playerHandCard);
                        } else if (playerState[playerIndex] == 1) {
                            winJudge();
                            for (int i = 0; i < playerNum; i++) {
                                if (allPlayers[i].isWin)
                                    playerWinList[i] = 1;
                            }
                            playerState[playerIndex] = 0;
                            phaseState = PhaseState.ACTION;
                            result = GodHelper.toWinJudge("ACTION: deposit account", gambleChoices, cardNumList, playerWinList, fireList);
                        }
                        break;
                    case ACTION:
                        if (playerState[playerIndex] == 0) {
                            String state;
                            if (humanWin || zombieWin) {
                                if (humanWin)
                                    state = "Game Over, human wins";
                                else
                                    state = "Game Over, zombie wins";
                                playerState[playerIndex] = 2;
                            } else {
                                state = "Account";
                                playerState[playerIndex] = 1;
                            }
                            result = GodHelper.toAccount(state, energyList, healthPointList, locationList, elementList, teamList);
                        } else if (playerState[playerIndex] == 1) {
                            desertAccount(playerIndex, msg);
                            int[] playerHandCard = new int[allPlayers[playerIndex].handCardsNum];
                            for (int i = 0; i < allPlayers[playerIndex].handCardsNum; i++)
                                playerHandCard[i] = allPlayers[playerIndex].handCards[i];
                            result = GodHelper.toDesertAccount("card distribute", allPlayers[playerIndex].energy, playerHandCard);
                            waitAllPlayers(playerIndex);
                            playerState[playerIndex] = 0;
                        } else if(playerState[playerIndex]==2){
                            // end
                            int[] playerSID = new int[playerNum];
                            for (int i = 0; i < playerNum; i++)
                                playerSID[i] = allPlayers[i].SID;
                            SessionController.endGame(playerSID, scoreList);
                            result = GodHelper.toGameOver("END", scoreList);
                        }
                        break;
                }
                break;
        }

        // for debug
        if (result.equals("{\"state\":\"Error\"}")) {
            System.out.printf("%d %s %s\n", sid, msg, gameState.toString());
        }

        return result;
    }

    // functions for INIT stage
    private Integer choice_count = 0;

    private void heroChoose(int sid, String msg) {
        int playerIndex;
        String heroChoice;
        MsgChooseHero choose = GodHelper.getChooseHero(msg);
        synchronized (this) {
            choice_count += 1;
            for (playerIndex = 0; playerIndex < playerNum; playerIndex++) {
                if (allPlayers[playerIndex].SID == sid) {
                    allPlayers[playerIndex].chara = choose.hero();
                    heroChoices[playerIndex] = choose.hero();
                }
            }
            if (choice_count < playerNum) {
                while (choice_count < playerNum) {
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                teamDivide();
                mapInit();
                GambleChecker.cardHeapInit(cardHeap, playerNum);
                GambleChecker.cardHeapStir(cardHeap);
                this.notifyAll();
            }
        }
    }

    private void teamDivide() {
        int zombie = (int) (Math.random() * playerNum);
        allPlayers[zombie].team = Player.ZOMBIE;
        allPlayers[zombie].healthPoint = 12;
        allPlayers[zombie].mot = 10;
        allPlayers[zombie].hasElem = false;
        allPlayers[zombie].firePow = 0;
        allPlayers[zombie].range = 0;
        teamResult[zombie] = Player.ZOMBIE;
        for (int i = 0; i < allPlayers.length; i++) {
            if (i != zombie) {
                allPlayers[i].team = Player.HUMAN;
                teamResult[i] = Player.HUMAN;
                allPlayers[i].range = 3;
                allPlayers[i].firePow = 3;
            }
        }
    }

    private void mapInit() {
        for (int i = 0; i < playerNum; i++) {
            if (allPlayers[i].team == Player.HUMAN)
                allPlayers[i].preLoc = map.fighter_init;
            else if (allPlayers[i].team == Player.ZOMBIE)
                allPlayers[i].preLoc = map.poisoner_init;
            locationList[i] = allPlayers[i].preLoc;
        }
    }

    // functions for GAMBLE stage
    private void featureChoose(String msg, Player playerMain, int playerIndex) {
        MsgChooseDecision decisionFeature = GodHelper.getChooseDecision(msg);
        int decision = playerMain.stratDecision;
        int direction = -1;
        if (decisionFeature.moveDirection() != -1)
            direction = map.units[allPlayers[playerIndex].preLoc].neighbors[decisionFeature.moveDirection()];

        if ((decision < 4) || (decision > 7))
            decision = GambleChecker.DEPOSIT;

        if (decision == GambleChecker.MOVE) {
            playerMain.moveDirection = direction;
            playerMain.energyConsume = Math.min(playerMain.energy, playerMain.mot);
        } else if (decision == GambleChecker.FIRE) {
            playerMain.fireTarget = decisionFeature.fireTarget();
            playerMain.energyConsume = Math.min(playerMain.energy, playerMain.firePow);
        } else if (decision == GambleChecker.DEPOSIT) {
            playerMain.energyConsume = 0;
        }
    }

    private Integer seen_card_count = 0;

    private void seenCard(int playerIndex, String msg, Player playerMain) {
        MsgSeenCard msgSeenCard = GodHelper.getSeenCard(msg);
        synchronized (this) {
            seen_card_count += 1;
            if ((playerMain.isSeenCard) || (msgSeenCard.seenCard() != 0)) {
                playerMain.gamble = msgSeenCard.seenCard();
                playerMain.gambleNum = 1;
                playerMain.isSeenCard = true;
                seenCardChoices[playerIndex] = msgSeenCard.seenCard();
            }
            if (seen_card_count < playerNum) {
                while (seen_card_count < playerNum) {
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                this.notifyAll();
                for (int i = 0; i < playerNum; i++)
                    fireList[i] = allPlayers[i].fireTarget;
            }
        }
    }

    private void gambleChoose(String msg, Player playerMain, int playerIndex) {
        MsgChooseGamble msgChooseGamble = GodHelper.getChooseGamble(msg);
        int[] gambleChoose = new int[msgChooseGamble.gambleCard().length];
        for (int i = 0; i < msgChooseGamble.gambleCard().length; i++)
            gambleChoose[i] = msgChooseGamble.gambleCard()[i];
        if (!playerMain.isSeenCard) {
            if ((msgChooseGamble.gambleCard() == null) || (msgChooseGamble.gambleCard()[0] == -1))
                playerMain.gamble = 1;
            playerMain.gamble = playerMain.handCards[gambleChoose[0]];
            playerMain.gambleNum = gambleChoose.length;
            for (int i = 0; i < playerMain.gambleNum; i++) {
                GambleChecker.cardToHeap(cardHeap, playerMain.handCards[playerMain.handCards[gambleChoose[i]]]);
                playerMain.handCards[gambleChoose[i]] = GambleChecker.NOTHING;
                GambleChecker.cardSort(playerMain.handCards);
                playerMain.handCardsNum -= 1;
            }
        }
        gambleChoices[playerIndex] = playerMain.gamble;
        cardNumList[playerIndex] = playerMain.gambleNum;
    }


    private Integer gamble_count = 0;

    private void winJudge() {
        synchronized (this) {
            gamble_count += 1;
            if (gamble_count < playerNum) {
                while (gamble_count < playerNum) {
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                GambleChecker.winJudge(playerNum, allPlayers);
                // trigger
                doAction();
                this.notifyAll();
            }
        }
    }

    // functions for ACTION stage
    void doAction() {
        depositAccount();
        skillsAccount();
        fireAccount();
        moveAccount();
        elemAccount();
        humanVictory();
        infectionAccount();
    }

    private void depositAccount() {
        for (int i = 0; i < playerNum; i++) {
            PlayerChecker.energyConsume(allPlayers[i], allPlayers[i].energyConsume);
            if (allPlayers[i].isWin)
                PlayerChecker.energyAcq(allPlayers[i], allPlayers[i].gambleNum);
            energyList[i] = allPlayers[i].energy;
        }
    }

    private void skillsAccount() {
    }     //TODO:skills

    private void fireAccount() {
        for (int i = 0; i < playerNum; i++) {
            if (allPlayers[i].fireTarget == -1)
                continue;
            if (allPlayers[i].stratDecision == GambleChecker.FIRE) {
                PlayerChecker.fire(map, allPlayers[i], allPlayers[allPlayers[i].fireTarget]);
            }
        }
        for (int i = 0; i < playerNum; i++)
            healthPointList[i] = allPlayers[i].healthPoint;
    }

    private void moveAccount() {
        for (int i = 0; i < playerNum; i++) {
            if (allPlayers[i].moveDirection == -1)
                continue;
            if (decisionChoices[i] == GambleChecker.MOVE) {
                allPlayers[i].preLoc = MapChecker.tryMove(map, allPlayers[i].preLoc, allPlayers[i].moveDirection, allPlayers[i].energyConsume);
            }
        }
        for (int i = 0; i < playerNum; i++)
            locationList[i] = allPlayers[i].preLoc;
    }

    private void elemAccount() {
        int elemNum = 0;
        for (int i = 0; i < playerNum; i++) {
            if (map.units[allPlayers[i].preLoc].status == 2) {
                allPlayers[i].hasElem = true;
                elementList[i] = 1;
                for (int j = 0; j < playerNum; j++) {
                    if (elementList[j] == 1)
                        elemNum += 1;
                }
            }
        }
    }

    private void humanVictory() {
        for (int i = 0; i < playerNum; i++) {
            if ((allPlayers[i].preLoc == map.fighter_evacuate) && (allPlayers[i].hasElem)) {
                humanWin = true;
            }
        }
    }

    private void infectionAccount() {
        for (int i = 0; i < playerNum; i++) {
            for (int j = 0; j < playerNum; j++) {
                PlayerChecker.infection(map, allPlayers[i], allPlayers[j]);
            }
        }
        for (int i = 0; i < playerNum; i++)
            teamList[i] = allPlayers[i].team;
        Boolean flag = true;
        for (int i = 0; i < playerNum; i++) {
            if (allPlayers[i].team == Player.HUMAN)
                flag = false;
        }
        if (flag)
            zombieWin = true;
    }


    private int desert_count = 0;

    private void desertAccount(int playerIndex, String msg) {
        MsgDesertAccount msgDesertAccount = GodHelper.getDesertAccount(msg);
        int[] cardList = new int[msgDesertAccount.desertCardList().length];
        for (int i = 0; i < cardList.length; i++)
            cardList[i] = msgDesertAccount.desertCardList()[i];
        synchronized (this) {
            desert_count += 1;
            if ((cardList[0] != -1) && (msgDesertAccount.desertCardList() != null))
                GambleChecker.cardDesert(allPlayers[playerIndex], cardHeap, cardList);
            if (allPlayers[playerIndex].energy > allPlayers[playerIndex].healthPoint)
                allPlayers[playerIndex].energy = allPlayers[playerIndex].healthPoint;
            if (desert_count < playerNum) {
                while (desert_count < playerNum) {
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                this.notifyAll();
            }
        }
    }


    private int wait_count = 0;

    private void waitAllPlayers(int playerIndex) {
        synchronized (this) {
            wait_count += 1;
            if (wait_count < playerNum) {
                while (wait_count < playerNum) {
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                phaseState = PhaseState.PREPARE;
                seen_card_count = 0;
                gamble_count = 0;
                desert_count = 0;
                // reset all temp variables
                for (int i = 0; i < playerNum; i++) {
                    allPlayers[i].stratDecision = GambleChecker.DEPOSIT;
                    allPlayers[i].fireTarget = -1;
                    allPlayers[i].moveDirection = -1;
                    availableFireTarget[i] = -1;
                    decisionChoices[i] = GambleChecker.DEPOSIT;
                    seenCardChoices[i] = 0;
                    gambleChoices[i] = GambleChecker.PAPER;
                    cardNumList[i] = 0;
                    playerWinList[i] = 0;
                    fireList[i] = -1;
                }
                this.notifyAll();
            }
        }
    }

    //other auxiliary functions
    public void initialPlayer(int[] playerSID) {
        playerNum = playerSID.length;
        allPlayers = new Player[playerNum];
        allUserInfo = new UserInfo[playerNum];
        playerState = new int[playerNum];
        map = new Map("map/1.map");

        heroChoices = new String[playerNum];
        teamResult = new int[playerNum];
        decisionChoices = new int[playerNum];
        seenCardChoices = new int[playerNum];
        cardHeap = new int[40 * playerNum];
        gambleChoices = new int[playerNum];
        cardNumList = new int[playerNum];
        playerWinList = new int[playerNum];
        energyList = new int[playerNum];
        healthPointList = new int[playerNum];
        locationList = new int[playerNum];
        elementList = new int[playerNum];
        teamList = new int[playerNum];
        availableFireTarget = new int[playerNum];
        scoreList = new int[playerNum];
        fireList = new int[playerNum];

        for (int i = 0; i < playerNum; i++) {
            allPlayers[i] = new Player();
            allPlayers[i].SID = playerSID[i];
            allPlayers[i].handCardsNum = 0;
            allPlayers[i].healthPoint = 6;
            allPlayers[i].mot = 4;
            allPlayers[i].energy = 0;
            allPlayers[i].handCards = new int[allPlayers[i].healthPoint + 4];
            allPlayers[i].fireTarget = -1;
            allPlayers[i].moveDirection = -1;
            playerState[i] = 0;
            scoreList[i] = 100;
            fireList[i] = -1;

            // ghost sid
            if (allPlayers[i].SID < 12 && allPlayers[i].SID>=0) {
                allPlayers[i].user_info = GodHelper.ghostUser();
            } else {
                Option<UserModel.User> user = UserController.getProfile(playerSID[i]);
                if (user.isEmpty()) allPlayers[i].user_info = GodHelper.ghostUser();
                else allPlayers[i].user_info = user.get();
            }
            allUserInfo[i] = new UserInfo(i, allPlayers[i].user_info.nickname());
        }
    }

    public void seenCardJudge(int playerIndex) {
        boolean isSeenCard = true;
        for (int i = 0; i < allPlayers[playerIndex].handCardsNum; i++) {
            if ((allPlayers[playerIndex].handCards[i] == 1)
                    || (allPlayers[playerIndex].handCards[i] == 2)
                    || (allPlayers[playerIndex].handCards[i] == 3))
                isSeenCard = false;
        }
        if (isSeenCard)
            allPlayers[playerIndex].isSeenCard = true;
        else
            allPlayers[playerIndex].isSeenCard = false;
    }

}