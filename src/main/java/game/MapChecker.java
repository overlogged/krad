package game;

public class MapChecker {
    public static int outDistance(MapUnit des_location,MapUnit src_location){
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
}
