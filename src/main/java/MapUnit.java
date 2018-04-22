import java.io.Serializable;

public class MapUnit implements Serializable {

    int mark;                    // The sign ofh a unit
    private int edge_number;     // The number of edg
    MapEdge[] edge;              // The connect edg of unit
    int height;                  // The height of the unit
    int status;               // Judge the map status
    Factor key = new Factor();   // Store the important thing

    MapUnit() {
    }

    MapUnit(int number) {
        edge_number = number;
        edge = new MapEdge[edge_number];
        for (int i = 0; i < edge_number; i++) {
            edge[i] = new MapEdge();            //edg[0] is the next,edg[1]is the before
        }
    }

}
