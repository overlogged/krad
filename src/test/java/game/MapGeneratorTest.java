package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static game.MapGenerator.generate;
import static game.MapGenerator.map1;

public class MapGeneratorTest {

    static void map1_test() {
        Map X = new Map("map/1.map");
        assert (X.units[7].edge[2].adjedg==22);
        assert (X.units[10].rank==3);
        assert (MapChecker.outDistance(X.units[3],X.units[8])==-1);
    }

    public static void main(String[] args) {
        map1();
        map1_test();
    }
}
