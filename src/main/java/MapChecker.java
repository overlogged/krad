
import java.io.*;
public class MapChecker {
}
class ArcNode{
    int adjvex;   //The other unit sign
    int distance;
}
class Factor{
    String name;
    void function(){}
}
class MapUnit{
    int mark;     //The sign ofh a unit
    ArcNode[] Connect;   //The fist connect
    int height;    //The height of the unit
    boolean isFactor;
    Factor  key;
}

class SetMap{             //Set mapunit attribute to make up map
    public static void SetMap(int number, ArcNode[][] arc_gather, int[] height_gather, boolean[] status_gather, Factor[] factor_gather){
        MapUnit[] GameMap = new MapUnit[number];    //Set mapunix object
        for (int i=0; i<number; i++){
            GameMap[i].mark = i;                    //Set mark number,from zero to number-1
            GameMap[i].Connect = arc_gather[i];     //Set connect
            GameMap[i].height = height_gather[i];   //Set height
            GameMap[i].isFactor = status_gather[i]; //Set factor
            GameMap[i].key = factor_gather[i];
        }
    }
}
class LoadFile{
    public static  void LoadFile(String filepath) throws IOException{
        try{
            FileInputStream fis = new FileInputStream(filepath);
            byte[] buffer= new byte[1024];
            int count;
            count = fis.read(buffer);
        }
        catch (Exception ioe){
            System.out.println("File default");
        }
    }
    }