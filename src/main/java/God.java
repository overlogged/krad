import java.io.IOException;

public class God {
/*    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // class: store all Player
    // function initialPlayerCharacter: 1. get the choice of players from initialPlayer
    //                                  2. set the value of character value
    // function initialPlayerCharacter: 1. get the default birth unit from map
    //                                  2. set the value of position
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    class AllPlayer{
        int playerNum;
        Player[] allPlayers;

        void initialPlayer(int playerNum, Player[] allPlayers) throws IOException {
            // TODO: get the playersCharacterChoice from 前端
            int[] playersCharacterChoice=new int[playerNum];
            // begin
            // 这段要写怎么从前端搞过来
            // end

            this.initialPlayerCharacter(playerNum, allPlayers, playersCharacterChoice);
            this.initialPlayerPos();
        }

        private void initialPlayerCharacter(int playerNum, Player[] allPlayers, int[] playersCharacterChoice) {

            for(int i = 0; i < playerNum; i++){
                // TODO: 从player里面例化出来character，然后赋值
                //begin
                allPlayers[i].energyLim = Player.character[playersCharacterChoice].energyLim;
                allPlayers[i].mot = Player.character[playersCharacterChoice].mot;
                allPlayers[i].firePow = Player.character[playersCharacterChoice].firePow;
                allPlayers[i].range = Player.character[playersCharacterChoice].range;
                //end
            }
        }

        private void initialPlayerPos(int playerNum, Player[] allPlayers, MapUnit[] defaultMap) {
            for(int i = 0; i < playerNum; i++){
                //TODO: create birth place in map
                //begin
                allPlayers[i].preLoc = defaultMap.birthLoc;
                //end
            }
        }

    }




    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: start the game
    // set map
    // ask everyone to choose character
    // set everyone at its initial point
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void initialGame(int playerNum) throws IOException {
        AllPlayer defaultNewPlayer = new AllPlayer();
        defaultNewPlayer.allPlayers = new Player[playerNum];
        //TODO: setmap
        //begin
        //end
        //TODO: choose character
        defaultNewPlayer.initialPlayer(playerNum, defaultNewPlayer.allPlayers);
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
            //TODO: Player::skillDecision
            //begin
            defaultAllPlayer.allPlayers[i].skillDecision = System.in.read();
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
            defaultAllPlayer.allPlayers[i].moveDecision = System.in.read();
            //end
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
            // TODO: SkillsChecker, Player::skillDecision
            // begin
            if(defaultAllPlayer.allPlayers[i].skillDecision == Player.SKILL)
                ;
            // end
            else
                continue;
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 开火
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void firePosition(AllPlayer defaultAllPlayer){
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            // TODO: fire是什么操作, Player::skillDecision
            // begin
            if(defaultAllPlayer.allPlayers[i].moveDecision == Player.FIRE)
                ;
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
            GambleChecker.winJudge(defaultAllPlayer.playerNum, defaultAllPlayer.allPlayers);
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 判断移动距离
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void movePlayers(AllPlayer defaultAllPlayer){
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            if(defaultAllPlayer.allPlayers[i].moveDecision == Player.MOVE){
                //TODO: 懒得写了
            }
        }
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:判断胜负
    // 感染者赢：所有人都是zombie
    // 生存者赢：拿到要素
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void checkWhetherWin(AllPlayer defaultAllPlayer){
        boolean zombieWin = checkWhetherZombieWin(defaultAllPlayer);
        //TODO: if (zombieWin), tell 前端
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            if(defaultAllPlayer.allPlayers[i].team == Player.HUMAN)
                System.out.print("Zombie win, game over!");
            else
                System.out.print("Congratulation, zombies win!");
        }
        boolean humanWin = checkWhetherHumanWin(defaultAllPlayer);
        //TODO: if (humanWin), tell 前端
        for(int i = 0; i < defaultAllPlayer.playerNum; i++){
            if(defaultAllPlayer.allPlayers[i].team == Player.ZOMBIE)
                System.out.print("Human win, game over!");
            else
                System.out.print("Congratulation, human win!");
        }
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
                if(defaultAllplayer.allPlayers[i].preLoc.mark == defaultAllplayer.allPlayers[j].preLoc.mark) {
                    if(defaultAllplayer.allPlayers[i].team == Player.HUMAN &&
                            defaultAllplayer.allPlayers[j].team == Player.ZOMBIE) {
                        defaultAllplayer.allPlayers[i].team = Player.ZOMBIE;
                        //TODO:告诉前端这人被感染了然后提示
                        //begin
                        System.out.print("A human is infected!");
                        //end
                    }
                    if(defaultAllplayer.allPlayers[j].team == Player.HUMAN &&
                            defaultAllplayer.allPlayers[i].team == Player.ZOMBIE) {
                        defaultAllplayer.allPlayers[j].team = Player.ZOMBIE;
                        //TODO:告诉前端这人被感染了然后提示
                        //begin
                        System.out.print("A human is infected!");
                        //end
                    }
                }
            }
        }
    }
*/
}
