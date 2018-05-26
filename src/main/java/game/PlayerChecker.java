package game;

public class PlayerChecker{
    MapChecker myMap = new MapChecker();
    private int distance;

    //element acquiring and losing
    void elemAcq(Player playerMain){
        if((!playerMain.hasElem)&(playerMain.team==Player.HUMAN))
            playerMain.hasElem=true;
    }
    void elemLose(Player playerMain){
        if(playerMain.hasElem)
            playerMain.hasElem=false;
    }

    //energy acquiring and consuming
    void energyAcq(Player playerMain,int energyVal){
        if(playerMain.energy<playerMain.energyLim)
            playerMain.energy+=energyVal;
    }
    void energyConsume(Player playerMain,int energyVal) {
        if(playerMain.energy>=energyVal)
            playerMain.energy-=energyVal;
        else
            playerMain.energy=0;
    }
    void gambleAward(Player playerMain,FrontEndData awardData){
        if(playerMain.isWin){
            switch(playerMain.stratDecision){
                case Player.MOVE:
                    move(playerMain,playerMain.energy,awardData.moveDirection);
                    break;
                case Player.FIRE:
                    fire(awardData.playerPos,awardData.playerPas,awardData.fireDirection);
                    break;
                case Player.DEPOSIT:
                    if(playerMain.energy<playerMain.energyLim)
                        energyAcq(playerMain,1);
                    break;
            }
        }
    }

    //location changing
    // in mapchecker
    MapUnit destCal(MapUnit preLoc,int engery, MapEdge direction){
        MapUnit dest=new MapUnit();
        return dest;
    }
    //in playerchecker
    void move(Player playerMain,int energy,MapEdge direction){
        MapUnit dest=destCal(playerMain.preLoc, playerMain.energy, direction);
        //energy cost
        energyConsume(playerMain,energy);
        playerMain.preLoc=dest;
    }
    void fire(Player playerPos,Player playerPas,MapEdge direction){
        distance = myMap.outDistance(playerPas.preLoc,playerPos.preLoc);
        if(playerPos.range>=distance) {
            playerPas.energyLim -= playerPos.firePow;
            //TODO:energy consuming
        }
    }

    //team changing
    void infection(Player playerPos,Player playerPas){
        if(playerPos.preLoc == playerPas.preLoc) {
            if(playerPos.preLoc.status==1) {
                if (playerPos.team == Player.ZOMBIE & playerPas.team == Player.HUMAN) {
                    playerPas.team = Player.ZOMBIE;
                    playerPas.energy = 0;
                    playerPas.hasElem = false;
                    playerPas.firePow = 0;
                    playerPas.range = 0;
                    playerPas.energyLim = 0;
                }
            }
        }
    }
}