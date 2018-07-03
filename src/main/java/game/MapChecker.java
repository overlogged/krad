package game;

import static java.lang.Math.abs;

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
        distance=abs(des_location.mark-src_location.mark);
        return  distance;
    }

    static int distance(MapUnit u1,MapUnit u2){
        int m1 = u1.mark,m2=u2.mark;
        if(m1>m2){
            m1 = u2.mark;
            m2 = u1.mark;
        }
        if(m1<=7 && m2>=15){
            return (22-m2+8)-m1;
        }
        return m2-m1;
    }

    public static int tryMove(Map m,int i_current,int i_next,int energy){
        MapUnit current = m.units[i_current];
        MapUnit next = m.units[i_next];
        MapUnit back;
//        assert abs(next.mark - current.mark) == 1;    // just the next
        assert energy>=0;

        while(energy>0){
            if(next.rank>current.rank){
                energy = 0;
            } else {
                energy -= distance(next,current);
            }
            back = current;
            current = next;
            for (MapEdge e:current.edge) {
                MapUnit try_next = m.units[e.adjedg];
                int dis = e.distance;
                // the same direction
                if(try_next != back){
                    if(dis > distance(current,next) && dis<=energy){
                        next = try_next;
                    }
                }
            }
            if(current == next) break;     // no dead loop
        }
        return current.mark;
    }
}
