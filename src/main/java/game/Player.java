package game;

// properties of a player
public class Player {
    //constant about gamble
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;
    //constant about teams
    public static final int HUMAN = 0;
    public static final int ZOMBIE = 1;
    //constant about strategy decision
    public static final int MOVE = 1;
    public static final int DEPOSIT = 2;
    public static final int FIRE = 3;

    String name;

    //cards{strategy,skills}
    //least energy consume = 4
    //energyLim = cardNumLimit = healthPoint

    //static properties
    int mot;                       //motility
    int firePow;                  //firepower
    int range;                    //range
    int energyLim;               //energy limit

    //dynamic properties
    int energy;                  //present energy
    int team;                    //the team of the player:zombie or human
    int gamble=PAPER;           /*
                                   gamble choices
                                   if one player does not choose his gamble choice one turn
                                   then the choice is the same as the last turn
                                  */
    int stratDecision;
    int skillsDecision;
    MapUnit preLoc;              //present location
    boolean isWin=false;       /*
                                  victory or defeat in one turn
                                  this variable should be set as false each turn ends
                                 */
    boolean hasElem;           //if the player maintains the element
}
