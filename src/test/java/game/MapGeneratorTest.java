package game;

import static game.MapGenerator.map1;

public class MapGeneratorTest {

    static void map1_test() {
        Map X = new Map("map/1.map");
        assert (X.units[7].edge[2].adjedg==22);
        assert (X.units[10].rank==3);
    }

    public static void main(String[] args) {
        map1();
        map1_test();
    }
}
