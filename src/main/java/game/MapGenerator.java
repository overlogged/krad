package game;

import server.Server;

import java.io.PrintWriter;

public class MapGenerator {

    public static void map1() {
        int number = 25;
        Map map = new Map(number);
        map.poisoner_init = 0;
        map.fighter_init = 4;
        map.fighter_evacuate = 0;

        for (int i = 0; i < number; i++) {
            int edge_number;
            if ((i == 7) || (i == 8) || (i == 24)) {
                edge_number = 3;
            } else if (i == 0) {
                edge_number = 1;
            } else {
                edge_number = 2;
            }

            map.units[i] = new MapUnit(edge_number);
            map.units[i].mark = i;
            map.units[i].status = 0;

            int edge_count = 0;

            if (i == 0) {
                map.units[i].edge[edge_count++] = new MapEdge(1, 1);
            } else if (i == 7) {
                map.units[i].edge[edge_count++] = new MapEdge(8, 1);
                map.units[i].edge[edge_count++] = new MapEdge(24, 1);
                map.units[i].edge[edge_count++] = new MapEdge(6, 1);
            } else if (i == 8) {
                map.units[i].edge[edge_count++] = new MapEdge(9, 1);
                map.units[i].edge[edge_count++] = new MapEdge(7, 1);
                map.units[i].edge[edge_count++] = new MapEdge(6, 2);
            } else if (i == 24) {
                map.units[i].edge[edge_count++] = new MapEdge(23, 1);
                map.units[i].edge[edge_count++] = new MapEdge(7, 1);
                map.units[i].edge[edge_count++] = new MapEdge(6, 2);
            } else {
                map.units[i].edge[edge_count++] = new MapEdge(i + 1, 1);
                map.units[i].edge[edge_count++] = new MapEdge(i - 1, 1);
            }

            if (i < 7) {
                map.units[i].rank = 1;
            } else if (i == 7) {
                map.units[i].rank = 2;
            } else if (i == 16) {
                map.units[i].rank = 3;
            } else {
                map.units[i].rank = 2;
            }


            final int LEFT = 7, RIGHT = 3, UP = 1, DOWN = 5;

            if (i == 0) {
                map.units[i].neighbors[LEFT] = 1;
            } else if (i == 1) {
                map.units[i].neighbors[UP] = 2;
                map.units[i].neighbors[RIGHT] = 0;
            } else if (i == 6) {
                map.units[i].neighbors[RIGHT] = 7;
                map.units[i].neighbors[DOWN] = 5;
            } else if (i == 7) {
                map.units[i].neighbors[0] = 8;
                map.units[i].neighbors[RIGHT] = 24;
                map.units[i].neighbors[LEFT] = 6;
            } else if (i == 8) {
                map.units[i].neighbors[UP] = 9;
                map.units[i].neighbors[4] = 7;
            } else if (i == 11) {
                map.units[i].neighbors[DOWN] = 10;
                map.units[i].neighbors[LEFT] = 12;
            } else if (i == 12) {
                map.units[i].neighbors[UP] = 13;
                map.units[i].neighbors[RIGHT] = 11;
            } else if (i == 14) {
                map.units[i].neighbors[DOWN] = 13;
                map.units[i].neighbors[RIGHT] = 15;
            } else if (i == 15) {
                map.units[i].neighbors[LEFT] = 14;
                map.units[i].neighbors[RIGHT] = 16;
            } else if (i == 16) {
                map.units[i].neighbors[LEFT] = 15;
                map.units[i].neighbors[RIGHT] = 17;
            } else if (i == 17) {
                map.units[i].neighbors[LEFT] = 16;
                map.units[i].neighbors[DOWN] = 18;
            } else if (i == 23) {
                map.units[i].neighbors[LEFT] = 24;
                map.units[i].neighbors[UP] = 22;
            } else if (i == 24) {
                map.units[i].neighbors[LEFT] = 7;
                map.units[i].neighbors[RIGHT] = 23;
            } else if (i < 16) {
                map.units[i].neighbors[UP] = i + 1;
                map.units[i].neighbors[DOWN] = i - 1;
            } else {
                map.units[i].neighbors[UP] = i - 1;
                map.units[i].neighbors[DOWN] = i + 1;
            }
        }

        map.units[8].status = 1;
        map.units[16].status = 2;
        map.units[16].key.name = "FATE";

        map.toFile("map/1.map");
    }

    public static void generate() {
        try {
            java.lang.Runtime rt = java.lang.Runtime.getRuntime();
            java.lang.Process p = rt.exec("mkdir -p map");
            map1();
        } catch (Exception ex) {
            ex.printStackTrace(new PrintWriter(Server.log_file()));
        }
    }
}
