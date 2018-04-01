
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
    void gambleAward(Player playerMain){
        if(playerMain.isWin){
            switch(playerMain.decision){
            }
        }
    }

    //location changing
    MapUnit destCal(MapUnit preLoc,int engery, ArcNode direction){
        MapUnit dest=new MapUnit();
        return dest;
    } // in mapchecker
    void move(Player playerMain,ArcNode direction){
        MapUnit dest=destCal(playerMain.preLoc, playerMain.energy, direction);
        //energy cost
        energyConsume(playerMain,playerMain.energy);
        playerMain.preLoc=dest;
    }
    void fire(Player playerA,Player playerB){
     //   move(playerB);
        playerA.energy=0;
    }

    //team changing
    void infection(Player playerA,Player playerB){
        if(playerA.team==Player.ZOMBIE&playerB.team== Player.HUMAN)
            playerB.team=Player.ZOMBIE;
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