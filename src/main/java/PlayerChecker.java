
public class PlayerChecker{
    //element acquiring and losing
    void elemAcq(Player playerMain){
        if((!playerMain.hasElem)&(playerMain.team!=Player.ZOMBIE))
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
            switch(playerMain.decision){
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
    MapUnit destCal(MapUnit preLoc,int engery, MapEdge direction){
        MapUnit dest=new MapUnit();
        return dest;
    } // in mapchecker
    void move(Player playerMain,int energy,MapEdge direction){
        MapUnit dest=destCal(playerMain.preLoc, playerMain.energy, direction);
        //energy cost
        energyConsume(playerMain,energy);
        playerMain.preLoc=dest;
    }
    void fire(Player playerPos,Player playerPas,MapEdge direction){
        move(playerPas,playerPos.energy,direction);
        playerPos.energy=0;
    }

    //team changing
    void infection(Player playerPos,Player playerPas){
        if(playerPos.team==Player.ZOMBIE&playerPas.team== Player.HUMAN) {
            playerPas.team = Player.ZOMBIE;
            playerPas.energy=0;
            playerPas.hasElem=false;
            playerPas.firePow=0;
            playerPas.range=0;
            playerPas.energyLim=0;
        }
    }
}

