import java.io.Serializable;

public class MapUnit implements Serializable {

    int mark;                    // The sign ofh a unit
    int edgnumber;               // The number of edg
    MapEdge[] edg;               // The connect edg of unit
    int height;                  // The height of the unit
    int is_factor;               // Judge the map status
    Factor key = new Factor();   // Store the important thing

    MapUnit() {
    }

    MapUnit(int number) {
        edgnumber = number;
        edg = new MapEdge[edgnumber];
        for (int i = 0; i < edgnumber; i++) {
            edg[i] = new MapEdge();            //edg[0] is the next,edg[1]is the before
        }
    }


}
