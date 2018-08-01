package game;

import java.io.Serializable;

public class MapEdge implements Serializable {
    int adjedg;   //The other unit sign
    int distance; //The length of the edg

    MapEdge(){}

    MapEdge(int adj,int dis){
        adjedg = adj;
        distance = dis;
    }
}
