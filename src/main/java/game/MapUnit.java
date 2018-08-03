package game;

import java.io.Serializable;

public class MapUnit implements Serializable {
    int mark;                    // The sign of a unit
    private int edge_number;     // The number of edg
    MapEdge[] edge;              // The connect edg of unit
    int status;                  // Judge the mapunit status,1 represent platform,2 represent factor area.
    Factor key = new Factor();   // Store the important thing
    int rank;                    // Store the levels of the

    MapUnit(){}

    MapUnit(int number) {
        edge_number = number;
        edge = new MapEdge[edge_number];
    }
}
