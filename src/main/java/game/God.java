package game;

import java.util.TreeMap;
import game.GodHelper.*;
import scala.Option;

public class God {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // class: store all Player
    // function initialPlayerCharacter: 1. get the choice of players from initialPlayer
    //                                  2. set the value of character value
    // function initialPlayerCharacter: 1. get the default birth unit from map
    //                                  2. set the value of position
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    GambleChecker gc;
    PlayerChecker pc;

    private int playerNum;          // how many people to play the game
    private Player[] allPlayers;    // preserve the state of players
    private boolean humanWin;      // whether someone win
    private MapUnit[] gameMap;      // map of the game
    private String[] heroList = {"0"};
    private UserInfo[] allUserInfo;
    private int[] cardHeap;
    private int[] playerHandCard;

    private String[] heroChoices;
    private int[] teamResult;


    enum GameState{ INIT, MAPINIT, MAINGAME }
    private GameState gameState = GameState.INIT;
    private int[] playerState;
    enum PhaseState{ PREPARE, GAMBLE, ACTION}
    private PhaseState phaseState = PhaseState.PREPARE;

    // Step two: gaming
    // Stage one: ready
    //2
    //申明要使用的主动技               get the skillChoice
    //send the skillChoice

    //3
    //申明要使用的行动                 get the moveChoice
    //send the moveChoice
    // Stage two: gamble
    //4
    // 猜拳                          get the gambleChoice
    //send the gambleChoice
    // 充能调整
    // Stage three: 行动
    //5
    //主动技能的释放                   get None
    //send the skillResult，具体内容暂定
    //6
    //开火结果                        get None
    //send the fireResult，moveChoice不是fire就0，具体内容暂定
    //7
    //位置变更                        get None
    //send the positionResult
    // Stage four: 结算
    //8
    //要素确认                        get None
    //send the personFactorResult
    //9
    //获胜判定                        get None
    //send the winResult
    //10
    //感染判定                        get None
    //send the infectionResult
    //11
    //能量有无溢出                    get None
    //send the overflowResult

    //12
    //get哪边赢了                     get None
    //send the gameOver

    public String request(int sid,String msg) {
        int playerIndex = 0;
        for(int i = 0;i < playerNum; i++){
            if(allPlayers[i].SID == sid) {
                playerIndex = i;
                break;
            }
        }
        String result = "{\"state\":\"Error\"}";
        switch(gameState) {
            case INIT:
                if(playerState[playerIndex] == 0) {
                    result = GodHelper.toInit(allUserInfo, "Choose hero", heroList);
                    playerState[playerIndex] += 1;
                } else if(playerState[playerIndex] == 1) {
                    heroChoose(sid, msg);
                    gameState = GameState.MAPINIT;
                    playerState[playerIndex] = 0;
                    result = GodHelper.toChooseHero("Start game", heroChoices,teamResult);
                }
                break;
            case MAPINIT:
                break;
            case MAINGAME:
                switch(phaseState){
                    case PREPARE:
                        if(playerState[playerIndex] == 0){
                            gc.cardDistribute(cardHeap,allPlayers[playerIndex],4);
                            playerHandCard = new int[allPlayers[playerIndex].handCardsNum];
                            result = GodHelper.toCardDistribute("choose strategy decision",playerHandCard);
                            playerState[playerIndex] += 1;
                        }
                        else if(playerState[playerIndex] == 1){
                            allPlayers[playerIndex].gamble = GodHelper.getChooseDecision(msg).decision();
                            allPlayers[playerIndex].gambleNum = GodHelper.getChooseDecision(msg).cardNum();
                            result = GodHelper.toChooseDecision("choose the feature of the decision");
                            playerState[playerIndex] += 1;
                        }
                        else if(playerState[playerIndex] == 2){
                            //TODO: return msg to the frontend
                            phaseState = PhaseState.GAMBLE;
                        }
                        break;
                    case GAMBLE:
                        break;
                    case ACTION:
                        break;
                }
                break;
        }

        // for debug
        if(result.equals("{\"state\":\"Error\"}")){
            System.out.printf("%d %s %s\n",sid,msg,gameState.toString());
        }

        return result;
    }

    /**
     * sample code
     * wait all players to send message
     */
    private final TreeMap<GameState,Integer> wait_map = new TreeMap<GameState, Integer>();
    private void waitAllPlayers(){
        synchronized (wait_map){
            Integer counter = wait_map.get(gameState);
            if(counter==null) counter = 0;
            counter++;
            wait_map.put(gameState,counter);
            if(counter < playerNum){
                do{
                    try{
                        wait_map.wait();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    counter = wait_map.get(gameState);
                }while (counter < playerNum);
            } else {
                wait_map.notifyAll();
            }
        }
    }

    private Integer choice_count = 0;
    private void heroChoose(int sid,String msg){
        int playerIndex;
        String heroChoice;
        MsgChooseHero choose = GodHelper.getChooseHero(msg);
        synchronized (this){
            choice_count += 1;
            for( playerIndex = 0;playerIndex < playerNum;playerIndex++){
                if(allPlayers[playerIndex].SID == sid) {
                    allPlayers[playerIndex].chara = choose.hero();
                    heroChoices[playerIndex] = choose.hero();
                }
            }
            if(choice_count < playerNum){
                while (choice_count < playerNum){
                    try {
                        this.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                teamDivide();
                gc.cardHeapInit(cardHeap,playerNum);
                gc.cardHeapStir(cardHeap);
                this.notifyAll();
            }
        }
    }
    private void teamDivide(){
        int zombie = (int)( Math.random() * playerNum);
        allPlayers[zombie].team = Player.ZOMBIE;
        teamResult[zombie] = Player.ZOMBIE;
        for(int i = 0;i < allPlayers.length;i++){
            if(i != zombie) {
                allPlayers[i].team = Player.HUMAN;
                teamResult[i] = Player.HUMAN;
            }
        }
    }
    private Integer feat_choice_count = 0;
    private void featureChoose(){
        //TODO:every player choose the feature of his strategy
        synchronized (this){
            feat_choice_count += 1;
            if(feat_choice_count < playerNum){
                while(feat_choice_count < playerNum){
                    try{
                        this.wait();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }else {
                this.notifyAll();
            }
        }
    }

    public void initialPlayer(int[] playerSID)  {
        playerNum = playerSID.length;
        allPlayers = new Player[playerNum];
        allUserInfo = new UserInfo[playerNum];
        playerState = new int[playerNum];

        heroChoices = new String[playerNum];
        teamResult = new int[playerNum];
        for(int i = 0;i < playerNum;i++) {
            allPlayers[i] = new Player();
            allPlayers[i].SID = playerSID[i];
            allPlayers[i].handCardsNum = 0;
            allPlayers[i].handCards = new int[40 * playerNum];
            allPlayers[i].cardsDesertNum = 0;
            allPlayers[i].cardsDesertList = new int[40 * playerNum];
            playerState[i] = 0;
            Option<UserModel.User> user = UserController.getProfile(playerSID[i]);
            if(user.isEmpty()) allPlayers[i].user_info = GodHelper.ghostUser();
            else allPlayers[i].user_info = user.get();
            allUserInfo[i] = new UserInfo(i,allPlayers[i].user_info.nickname());
        }
        /*
        // TODO: get the playersCharacterChoice from 前端
        int[] playersCharacterChoice=new int[playerNum];
        // begin
        // 这段要写怎么从前端搞过来，我下面随便写的
        for (int i = 0; i < playerNum; i++){
            playersCharacterChoice[i] = System.in.read();
        }
        // end

        this.initialPlayerCharacter(playerNum, allPlayers, playersCharacterChoice);
        this.initialPlayerPos(playerNum, allPlayers, );
        */
        /*
        private void initialPlayerCharacter(int playerNum, Player[] allPlayers, int[] playersCharacterChoice) {

            for(int i = 0; i < playerNum; i++){
                // TODO: 从player里面例化出来character，然后赋值
                //begin
                allPlayers[i].healthPoint = Player.character[playersCharacterChoice].healthPoint;
                allPlayers[i].mot = Player.character[playersCharacterChoice].mot;
                allPlayers[i].firePow = Player.character[playersCharacterChoice].firePow;
                allPlayers[i].range = Player.character[playersCharacterChoice].range;
                //end
            }
        }
        */

        /*
        private void initialPlayerPos(int playerNum, Player[] allPlayers, MapUnit[] defaultMap) {
            for(int i = 0; i < playerNum; i++){
                //TODO: 地图能不能告诉我出生地址
                //begin
                allPlayers[i].preLoc = defaultMap.birthLoc;
                //end
            }
        }
        */
    }

    /*
    private String initiatePlayer(int[] playerSID)
     */

/*
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: start the game
    // set map
    // ask everyone to choose character
    // set everyone at its initial point
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void initialGame(int[] SID) throws IOException {
        AllPlayer defaultNewPlayer = new AllPlayer();
        defaultNewPlayer.allPlayers = new Player[];
        //TODO: setmap
        //begin
        //我这里用的是特殊案例
        gameMap = Create();
        //end
        //choose character
        defaultNewPlayer.initialPlayer(SID);
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: at the beginning of each term, player tell if he uses skill
    // all players choose whether use skill
    //     default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void tellSkill(AllPlayer defaultAllPlayer) throws IOException {
        // TODO: 1.问前端这人要不要搞事
        // TODO: 2.从前端知道这人搞不搞事
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            System.out.print("你想用技能吗？");
            //TODO: Player::skillDecisions
            //begin
            defaultAllPlayer.allPlayers[i].skillsDecision = System.in.read();
            //end
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: at the beginning of each term, player tell the action for this term
    // all players choose which action to use
    //     default: getEnergy
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void tellAction(AllPlayer defaultAllPlayer) throws IOException{
        // TODO: 1.问前端这人想搞什么事
        // TODO: 2.从前端知道这人搞不搞事
        for(int i = 0; i < defaultAllPlayer.playerNum; i++) {
            System.out.print("你想做什么行动？");
            //TODO: Player::moveDecision
            //begin
            defaultAllPlayer.allPlayers[i].stratDecision = System.in.read();
            //end
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 要素确认
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void checkFactor(AllPlayer defaultAllPlayer){
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            if(!defaultAllPlayer.allPlayers[i].hasElem && defaultAllPlayer.allPlayers[i].preLoc.status==1
                    && defaultAllPlayer.allPlayers[i].team == Player.HUMAN)
                defaultAllPlayer.allPlayers[i].hasElem = true;
            else
                ;
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 按某个顺序使用技能
    // 根据技能调整人物
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void useSkill(AllPlayer defaultAllPlayer) throws IOException{
        // TODO 安排顺序，体现在下面是怎么for的
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            // TODO: SkillsChecker
            // begin
            if(defaultAllPlayer.allPlayers[i].skillsDecision != 0)
                ;
                // end
            else
                ;
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 开火
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void fire(AllPlayer defaultAllPlayer) throws IOException{
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            // TODO: 前端交互：这是给谁fire的
            System.out.print("你想对几号玩家fire？");
            //begin
            int fired = System.in.read();
            //end
            // TODO: 前端交互：这是给哪个方向fire的
            System.out.print("你想对几号玩家fire？");
            //begin
            int fireDirection = System.in.read();
            //end
            // TODO: fire好像那边还在修改
            // TODO: 把fire改成静态
            // begin
            if(defaultAllPlayer.allPlayers[i].stratDecision == Player.FIRE) {
                PlayerChecker.fire(defaultAllPlayer.allPlayers[i].preLoc, defaultAllPlayer.allPlayers[fired].preLoc, defaultAllPlayer.allPlayers[i].preLoc.edge[fireDirection]);
            }
            // end
            else
                continue;
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 石头剪刀布
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void gamble(AllPlayer defaultAllPlayer) throws IOException{

        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            System.out.print("你猜拳想出啥？");
            // TODO: 前端交互
            // begin
            defaultAllPlayer.allPlayers[i].gamble = System.in.read();
            // end
            // 通过调用gamblechecker来进行充能调整
            GambleChecker.winJudge(defaultAllPlayer.playerNum, defaultAllPlayer.allPlayers);
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 判断移动距离
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void movePlayers(AllPlayer defaultAllPlayer) throws IOException{
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            System.out.print("你想走哪个方向？");
            // TODO: 前端交互
            // begin
            defaultAllPlayer.allPlayers[i].gamble = System.in.read();
            // end
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:游戏结束
    // 感染者赢：所有人都是zombie
    // 生存者赢：拿到要素
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    void gameOver(boolean humanwin){
        if (humanwin){
            //TODO: if (humanWin), tell 前端
            System.out.print("Human win!");
        }
        else{

            //TODO: if (zombieWin), tell 前端
            System.out.print("Zombie win!");
        }
        //TODO:前端可能需要展示数据？
        //TODO:清理本局游戏残留数据
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:判断胜负
    // 感染者赢：所有人都是zombie
    // 生存者赢：拿到要素
    //    return: 有没有结束游戏
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    boolean checkWhetherWin(AllPlayer defaultAllPlayer){
        boolean zombieLocalWin = checkWhetherZombieWin(defaultAllPlayer);
        boolean humanLocalWin = checkWhetherHumanWin(defaultAllPlayer);
        if(humanLocalWin) {
            humanWin = true;
            return true;
        }
        else if(zombieLocalWin) {
            humanWin = false;
            gameOver(true);
            return true;
        }
        else
            return false;
    }

    boolean checkWhetherZombieWin(AllPlayer defaultAllPlayer){
        boolean win = true;
        for (int i = 0; i < defaultAllPlayer.playerNum; i++)
            if(defaultAllPlayer.allPlayers[i].team == Player.HUMAN){
                win = false;
                break;
            }
        return win;
    }
    boolean checkWhetherHumanWin(AllPlayer defaultAllPlayer){
        boolean win = false;
        for (int i = 0; i < defaultAllPlayer.playerNum; i++)
            //TODO: status加一个离开点leave
            //begin
            if(defaultAllPlayer.allPlayers[i].team == Player.HUMAN &&
                    defaultAllPlayer.allPlayers[i].preLoc.status == MapUnit.LEAVE &&
                    defaultAllPlayer.allPlayers[i].hasElem == true){
                win = true;
                break;
            }
        //end

        return win;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: 大家感染状态
    // 感染：human和zombie在同一格子
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void checkWhetherInfect(AllPlayer defaultAllplayer){
        for (int i = 0; i < defaultAllplayer.playerNum; i++){
            for (int j = i+1; j < defaultAllplayer.playerNum; j++){
                // TODO: 让PlayerChecker.infection静态
                PlayerChecker.infection(defaultAllPlayer.allPlayers[i], defaultAllPlayer.allPlayers[j]);
            }
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: 有没有人能量溢出
    // 溢出：变成最大值
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void checkWhetherEnergyOverflow(AllPlayer defaultAllplayer){
        for (int i = 0; i < defaultAllplayer.playerNum; i++){
            if (defaultAllplayer.allPlayers[i].energy > defaultAllplayer.allPlayers[i].healthPoint){
                defaultAllplayer.allPlayers[i].energy = defaultAllplayer.allPlayers[i].healthPoint;
            }
        }
    }
    void game() throws  IOException{
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Step one: init
        // func: 获取人的数目然后进行初始化
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // TODO: 跟前端交互来完成获得SID[]
        //begin
        initialGame(int[] SID);
        //end


        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Step two: gaming
        // func: 在每个周期进行操作
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        while(true){
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Stage one: ready
            // func: 申明要使用的主动技
            //      申明要使用的行动
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //1 申明要使用的主动技
            tellSkill(defaultAllPlayer);
            //2 申明要使用的行动
            tellAction(defaultAllPlayer);

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Stage two: gamble
            // func: 猜拳并记录结果，充能调整
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            gamble(defaultAllPlayer);

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Stage three: 行动
            // func: 主动技能的释放
            //      开火结果
            //      位置变更
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //1 主动技能的释放
            useSkill(defaultAllPlayer);
            //2 开火结果
            fire(defaultAllPlayer);
            //3 位置变更
            movePlayers(defaultAllPlayer);
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Stage four: 结算
            // func: 要 素 确 认
            //      获胜判定
            //      感染判定
            //      能量有无溢出
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //1 要素确认
            checkFactor(defaultAllPlayer);
            //2 获胜判定
            if(checkWhetherWin(defaultAllPlayer))
                break;
            else
                ;
            //3 感染判定
            checkWhetherInfect(defaultAllPlayer);
            //4 能量有无溢出
            checkWhetherEnergyOverflow(defaultAllPlayer);

        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Step three: closing
        // func: 结束
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gameOver(humanWin);
    }
    */
}