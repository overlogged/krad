public class PlayerChecker{
    //constant about teams
    private static final int HUMAN=0;
    private static final int ZOMBIE=1;

    //element acquiring
    void elemAcq(Player playerMain,int flag){
        if(playerMain.hasElem==false)
            playerMain.hasElem=true;
    }

    //element losing
    void elemLose(Player playerMain){
        if(playerMain.hasElem==true)
            playerMain.hasElem=false;
    }

    //energy acquiring
    void energyAcq(Player playerMain,int energyVal){
        if(playerMain.energy<playerMain.energyLim)
            playerMain.energy+=energyVal;
    }

    //energy consuming
    void energyConsume(Player playerMain,int energyVal) {
        if (playerMain.energy >= 0)
            playerMain.energy -= energyVal;
    }

    void fire(Player playerA,Player playerB){
        move(playerB);
        playerA.energy=0;
    }

    void move(Player playerMain){}

    void victory(Player playerMain){}

    void infection(Player playerA,Player playerB){
        if(playerA.team==ZOMBIE&playerB.team==HUMAN)
            playerB.team=ZOMBIE;
    }
}
