package game;

public class SetMapTest {
    public static void main(String[] args) {
        Map X = new Map("map/1.map");
        System.out.println(X.units[7].edge[2].adjedg);
        System.out.println(X.units[10].rank);
        int distance=MapChecker.outDistance(X.units[3],X.units[8]);
        System.out.println(distance);
    }
}