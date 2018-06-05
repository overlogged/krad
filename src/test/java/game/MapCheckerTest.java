package game;

public class MapCheckerTest {
    public static void main(String[] args){
        Map m = new Map("map/1.map");
        int r1 = MapChecker.tryMove(m,0,1,2);
        assert r1==2;
        int r2 = MapChecker.tryMove(m,r1,1,1);
        assert r2==1;
        int r3 = MapChecker.tryMove(m,r2,r2+1,10);
        assert r3==7;
        int r4 = MapChecker.tryMove(m,r3,22,3);
        assert r4==22;
        int r5 = MapChecker.tryMove(m,r4,21,3);
        assert r5==22-3;
    }
}
