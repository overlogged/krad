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

            if(i==0){
                map.units[i].edge[edge_count++] = new MapEdge(1,1);
            }else if(i==7){
                map.units[i].edge[edge_count++] = new MapEdge(8,1);
                map.units[i].edge[edge_count++] = new MapEdge(24,1);
                map.units[i].edge[edge_count++] = new MapEdge(6,1);
            }else if(i==8){
                map.units[i].edge[edge_count++] = new MapEdge(9,1);
                map.units[i].edge[edge_count++] = new MapEdge(7,1);
                map.units[i].edge[edge_count++] = new MapEdge(6,2);
            }else if(i==24){
                map.units[i].edge[edge_count++] = new MapEdge(23,1);
                map.units[i].edge[edge_count++] = new MapEdge(7,1);
                map.units[i].edge[edge_count++] = new MapEdge(6,2);
            }else{
                map.units[i].edge[edge_count++] = new MapEdge(i+1,1);
                map.units[i].edge[edge_count++] = new MapEdge(i-1,1);
            }

            if (i < 7) {
                map.units[i].rank = 1;
            } else if (i == 7) {
                map.units[i].rank = 2;
            } else if (i == 16) {
                map.units[i].rank = 4;
            } else {
                map.units[i].rank = 3;
            }
        }

        map.units[8].status = 1;
        map.units[16].status = 2;
        map.units[16].key.name = "FATE";

        map.toFile("map/1.map");
    }

    public static void generate(){
        try{
            java.lang.Runtime rt = java.lang.Runtime.getRuntime();
            java.lang.Process p = rt.exec("mkdir -p map");
            map1();
        }catch (Exception ex){
            ex.printStackTrace(new PrintWriter(Server.log_file()));
        }
    }
}
