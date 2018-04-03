
import java.io.*;
public class MapChecker {
}
class MapEdge{
    int adjedg;   //The other unit sign
    int distance;  //The length of the edg
}
class Factor{      //The important thing in the map
    String name;
    void function(){}
}
class MapUnit{
    int mark;     //The sign ofh a unit
    MapEdge[] edg;   //The  connect edg
    int height;    //The height of the unit
    int is_factor;  //Judge the map status
    Factor  key;    //Store the important thing
}

class SetMap{             //Set mapunit attribute to make up map
    public static void SetMap(int number, MapEdge[][] edge_gather, int[] height_gather, int[] status_gather, Factor[] factor_gather){
        MapUnit[] GameMap = new MapUnit[number];    //Set MapUnit object
        for (int i=0; i<number; i++){
            GameMap[i].mark = i;                    //Set mark number,from zero to number-1
            GameMap[i].edg = edge_gather[i];     //Set connect
            GameMap[i].height = height_gather[i];   //Set height
            GameMap[i].is_factor = status_gather[i]; //Set factor
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