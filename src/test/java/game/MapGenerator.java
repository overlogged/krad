package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MapGenerator {
    static void map1() {
        int number = 23;
        Map map = new Map(number);
        
        for (int i = 0; i < map.units.length; i++) {
            int edge_number;
            if ((i == 7) || (i == 8) || (i == 22)) {
                edge_number = 3;
            } else if (i == 0) {
                edge_number = 1;
            } else {
                edge_number = 2;
            }

            map.units[i] = new MapUnit(edge_number);
            map.units[i].mark = i+1;
            map.units[i].status = 0;

            if (i == 22) {
                map.units[i].edge[0].adjedg = 8;
            } else {
                map.units[i].edge[0].adjedg = i + 2;
            }
            map.units[i].edge[0].distance = 1;

            if (edge_number >= 2) {
                map.units[i].edge[1].adjedg = i ;
                map.units[i].edge[1].distance = 1;
            }
            if (i < 7) {
                map.units[i].rank = 1;
            } else if (i == 7) {
                map.units[i].rank = 2;
            } else if (i == 15) {
                map.units[i].rank = 4;
            } else {
                map.units[i].rank = 3;
            }
        }

        map.units[8].edge[2].adjedg = 7;
        map.units[8].edge[2].distance = 2;
        map.units[22].edge[2].distance = 2;
        map.units[22].edge[2].adjedg = 7;
        map.units[7].edge[2].adjedg = 23;
        map.units[7].edge[2].distance = 1;

        map.units[7].status=1;
        map.units[15].status=2;
        map.units[15].key.name="FATE";

        map.toFile("map/1.map");
    }

    public static void main(String[] args){
        map1();
    }
}
