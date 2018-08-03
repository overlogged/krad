import game.God;
import game.GodHelper;

public class MagicTest {
    public static void main(String[] args){
        int[] healthPointList = new int[4];
        healthPointList[0] = 1;
        healthPointList[1] = 2;
        healthPointList[2] = 3;
        healthPointList[3] = 4;
        String result = GodHelper.toFireAccount("move account",healthPointList);
        System.out.println(result);
    }
}
