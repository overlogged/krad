import java.io.File;
import java.io
public class MapChecker {
}
class ArcNode{
    int adjvex;   //The other unit sign
    int distance;
}
class Factor{
    String name;
    void function(){};
}

class MapUnit{
    int mark;     //The sign ofh a unit
    ArcNode[] Connect;   //The fist connect
    int height;
    boolean isFactor;
    Factor  key;
}

class SetMap{             //Set mapunit attribute to make up map
    public static SetMap(int number, ArcNode[][] arc_gather, int[] height_gather, boolean status_gather, Factor[] factor_gather){
        MapUnit[] map = new MapUnit[number];    //Set mapunix object
        for (int i=0; i<number; i++){
            map[i].mark = i;                    //Set mark number,from zero to number-1
            map[i].Connect = arc_gather[i];     //Set connect
            map[i].height = height_gather[i];   //Set height
            map[i].isFactor = status_gather[i]; //Set factor
            map[i].key = factor_gather[i];
        }
    }
}


