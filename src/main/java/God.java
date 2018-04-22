public class God {
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // class: store all character
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    class Character{
        int energyLim;
        int mot;
        int firePow;
        int range;
    }
    class AllCharacter{
        int playerNum;
        Character[] allCharacters;
        void initialAllCharacters(){
            //TODO: get character
        };
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // class: store all Player
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    class AllPlayer{
        int playerNum;
        Player[] allPlayers;

        void initialPlayer(int playerNum, Player[] allPlayers){
            // TODO: initial all players
            initialPlayerValue();

        }

        private void initialPlayerValue(int playerNum, Player[] allPlayers, Character[] character) {

            for(int i = 0; i < playerNum; i++){
                allPlayers[i].energyLim = character[i].energyLim;
                allPlayers[i].mot = character[i].mot;
                allPlayers[i].firePow = character[i].firePow;
                allPlayers[i].range = character[i].range;
            }
        }

        private void initialPlayerPos(int playerNum, Player[] allPlayers, MapUnit defaultUnit) {
            for(int i = 0; i < playerNum; i++){
                allPlayers[i].preLoc = defaultUnit;
            }
        }

    }




    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: start the game
    // set map
    // ask everyone to choose character
    // set everyone at its initial point
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void initialGame(int playerNum){
        AllPlayer defaultNewPlayer = new AllPlayer();
        defaultNewPlayer.allPlayers = new Player[playerNum];
        //TODO: setmap

        //TODO: choose character
        defaultNewPlayer.initialPlayer(playerNum, defaultNewPlayer.allPlayers);


    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: at the beginning of each term, player tell if he uses skill
    // all players choose whether use skill
    //     default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void tellSkill(){

    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: at the beginning of each term, player tell the action for this term
    // all players choose which action to use
    //     default: getEnergy
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void tellAction(){

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 按某个顺序使用技能
    // 根据技能调整人物
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void useSkill(){

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:
    // 开火
    // 位置变更
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void firePosition(){

    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func:判断胜负
    // 感染者赢：所有人都是zombie
    // 生存者赢：拿到要素
    //    default: no
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    void checkWhetherWin(AllPlayer defaultAllPlyer){
        boolean zombieWin = checkWhetherZombieWin(defaultAllPlyer);
        //TODO: if (zombieWin)
        boolean humanWin = checkWhetherHumanWin(defaultAllPlyer);
        //TODO: if (humanWin)


    }
    boolean checkWhetherZombieWin(AllPlayer defaultAllPlyer){
        boolean win = true;
        for (int i = 0; i < defaultAllPlyer.playerNum; i++)
            if(defaultAllPlyer.allPlayers[i].team == Player.HUMAN){
                win = false;
                break;
            }
        return win;
    }
    boolean checkWhetherHumanWin(AllPlayer defaultAllPlyer){
        boolean win = false;
        for (int i = 0; i < defaultAllPlyer.playerNum; i++)
            if(defaultAllPlyer.allPlayers[i].team == Player.HUMAN &&
                    defaultAllPlyer.allPlayers[i].preLoc.isLeaveLoc == true &&
                    defaultAllPlyer.allPlayers[i].hasElem == true){
                win = true;
                break;
            }

        return win;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // func: 大家感染状态
    // 感染：human和zombie在同一格子
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


}
