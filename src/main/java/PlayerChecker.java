
public class PlayerChecker{
    //element acquiring and losing
    void elemAcq(Player playerMain){
        if(!playerMain.hasElem)
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
        if (playerMain.energy >= 0)
            playerMain.energy -= energyVal;
    }
    void gambleAward(Player playerMain,FrontendData awardData){
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
                        energyAcq(playerMain,1); //each one can only acquire
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
        if(playerPos.team==Player.ZOMBIE&playerPas.team== Player.HUMAN)
            playerPas.team=Player.ZOMBIE;
    }
}

//Properties of a player
class Player {
    //constant about gamble
    private static final int PAPER = 1;
    private static final int SCISSORS = 2;
    private static final int STONE = 3;
    //constant about teams
    public static final int HUMAN=0;
    public static final int ZOMBIE=1;
    //constant about decision
    public static final int MOVE=1;
    public static final int DEPOSIT=2;
    public static final int FIRE=3;
    public static final int SKILL=4;

    //static properties
    int mot;  //motility
    int firePow; //firepower
    int range;  //range
    int energyLim;  //energy limit

    //dynamic properties
    int energy; //present energy
    int team;  //the team of the player:zombie or human
    int gamble;  //gamble choices
    int decision;
    MapUnit preLoc;  //present location
    boolean isWin;   //victory or defeat in one turn
    boolean hasElem; //if the player maintains the element
}

class FrontendData{
    Player playerPos,playerPas;
    MapEdge moveDirection,fireDirection;
}