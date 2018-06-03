package game;

// properties of a player
public class Player {
    //constant about teams
    public static final int HUMAN = 0;
    public static final int ZOMBIE = 1;
    //constant about gamble cards
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;
    public static final int STONE = 3;
    //constant about strategy decision
    public static final int MOVE = 4;
    public static final int DEPOSIT = 5;
    public static final int FIRE = 6;
    public static final int SKILLS = 7;

    UserModel.User user_info;

    int SID;                            //session ID


    //static properties
    int mot;                             //motility
    int firePow;                        //firepower
    int range;                          //range
    String chara;

    //dynamic properties
    int healthPoint;                    //energy limit
    int energy;                         //present energy
    int team;                           //the team of the player:zombie or human

    //properties about gamble
    int gamble = PAPER;                /*
                                         gamble choices
                                         if one player does not choose his gamble choice one turn
                                         then the choice is the same as the last turn
                                        */
    int gambleNum = 1;                 /*the number of the same gamble cards of one player in one turn
                                           for example, one player shows 3 PAPERS in one turn, then gamble == PAPER
                                           and gambleNum == 3
                                         */
    int stratDecision;
    int skillsDecision;
    boolean isWin = false;            /*
                                        victory or defeat in one turn
                                        this variable should be set as false each turn ends
                                       */

    //properties about cards
    int handCardsNum = 0;
    int[] handCards;
    int cardsDesertNum = 0;
    int[] cardsDesertList;           //if there is no card to desert, then the element is -1

    //properties about location
    MapUnit preLoc;                    //present location
    boolean hasElem;                  //if the player maintains the element
}
