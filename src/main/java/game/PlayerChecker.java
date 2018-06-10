package game;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class PlayerChecker{
    static MapChecker myMap = new MapChecker();
    private static int distance;

    //element acquiring and losing
    static void elemAcq(Player playerMain){
        if((!playerMain.hasElem)&(playerMain.team==Player.HUMAN))
            playerMain.hasElem=true;
    }
    static void elemLose(Player playerMain){
        if(playerMain.hasElem)
            playerMain.hasElem=false;
    }

    //energy acquiring and consuming
    static void energyAcq(Player playerMain,int energyVal){
        playerMain.energy+=energyVal;
    }
    static void energyConsume(Player playerMain,int energyVal) {
        if(playerMain.energy>=energyVal)
            playerMain.energy-=energyVal;
        else
            playerMain.energy=0;
    }
//    static void gambleAward(Player playerMain,FrontEndData awardData){
//        if(playerMain.isWin){
//            switch(playerMain.stratDecision){
//                case Player.MOVE:
//                    move(playerMain,playerMain.energy,awardData.moveDirection);
//                    break;
//                case Player.FIRE:
//                    fire(awardData.playerPos,awardData.playerPas);
//                    break;
//                case Player.DEPOSIT:
//                    if(playerMain.energy<playerMain.healthPoint)
//                        energyAcq(playerMain,1);
//                    break;
//            }
//        }
//    }

    static void fire(Map map,Player playerPos,Player playerPas){
        distance = MapChecker.distance(map.units[playerPas.preLoc],map.units[playerPos.preLoc]);
        if(playerPos.range>=distance) {
            if(playerPas.healthPoint - playerPos.energyConsume > 4)
                playerPas.healthPoint -= playerPos.energyConsume;
            else
                playerPas.healthPoint = 4;
        }
    }

    //team changing
    static Boolean infection(Map map, Player playerPos, Player playerPas){
        if(playerPos.preLoc == playerPas.preLoc) {
            if(map.units[playerPos.preLoc].status==1) {
                if (playerPos.team == Player.ZOMBIE & playerPas.team == Player.HUMAN ) {
                    playerPas.team = Player.ZOMBIE;
                    playerPas.energy = 0;
                    playerPas.hasElem = false;
                    playerPas.firePow = 0;
                    playerPas.range = 0;
                    return true;
                }
            }
        }
        return false;
    }
}