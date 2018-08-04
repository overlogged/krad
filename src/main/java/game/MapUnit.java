package game;

import java.io.Serializable;

public class MapUnit implements Serializable {
    int mark;                    // The sign of a unit
    private int edge_number;     // The number of edg
    MapEdge[] edge;              // The connect edg of unit
    int status;                  // Judge the mapunit status,1 represent platform,2 represent factor area.
    Factor key = new Factor();   // Store the important thing
    int rank;                    // Store the levels of the
    int neighbors[] = new int[8];

    MapUnit(){
        for(int i=0;i<8;i++){
            neighbors[i]=-1;
        }
    }

    MapUnit(int number) {
        edge_number = number;
        edge = new MapEdge[edge_number];
        for(int i=0;i<8;i++){
            neighbors[i]=-1;
        }
    }

    int[] availableDir(){
        int[] avail = new int[8];
        for(int i=0;i<8;i++){
            if(neighbors[i]!=-1) avail[i]=1;
            else avail[i]=0;
        }
        return avail;
    }
}
