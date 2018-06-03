package game;

import game.CreateMap;
import game.MapUnit;

public class SetMapTest {

    public static int outDistance(MapUnit des_location, MapUnit src_location){
        int distance;
        if (des_location.rank!=src_location.rank){
            return -1;
        }
        if (des_location.mark==src_location.mark)
        {
            return -1;
        }
        distance=Math.abs(des_location.mark-src_location.mark);
        return  distance;
    }
    public static void main(String[] args) {
        CreateMap X=new CreateMap(23);
        X.obtainMap();
        System.out.println(X.sample[7].edge[2].adjedg);
        System.out.println(X.sample[10].rank);
        int distance=0;
        distance=outDistance(X.sample[3],X.sample[8]);
        System.out.println(distance);
    }
}