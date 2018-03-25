public class Player {
        //constant about gamble
        private static final int PAPER=1;
        private static final int SCISSORS=2;
        private static final int STONE=3;
        //constant about teams
        private static final int HUMAN=0;
        private static final int ZOMBIE=1;

        int team;
        int mot;  //motility
        int firePow; //firepower
        int range;  //range
        int energyLim;  //energy limit
        int energy; //present energy
        int[] coord=new int[3]; //coordinate(x,y,h)，h is height
        boolean hasElem; //if the player maintains the element
        int gamble;  //gamble
}
