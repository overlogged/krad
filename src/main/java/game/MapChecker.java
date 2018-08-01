package game;

import akka.stream.impl.MaybeSource;

import static java.lang.Math.abs;

public class MapChecker {
    public static int outDistance(MapUnit des_location, MapUnit src_location) {
        int distance;
        if (des_location.rank != src_location.rank) {
            return -1;
        }
        if (des_location.mark == src_location.mark) {
            return -1;
        }
        distance = abs(des_location.mark - src_location.mark);
        return distance;
    }

    public static int tryMove(Map m, int i_current, int i_next, int energy) {

        // first step
        MapUnit back = null;
        MapUnit current = m.units[i_current];
        MapUnit next = m.units[i_next];

        boolean flag = false;
        for (MapEdge e : current.edge) {
            if (e.adjedg == i_next && energy >= e.distance) {
                energy -= e.distance;
                if (next.rank > current.rank) {
                    energy = 0;
                }
                flag = true;
                back = current;
                current = next;
                break;
            }
        }
        assert flag;

        while (energy > 0) {
            next = null;
            int dis = 0;

            // find next
            for (MapEdge e : current.edge) {
                if (back.mark != e.adjedg) {
                    MapUnit try_next = m.units[e.adjedg];
                    if (energy >= e.distance) {
                        if (next == null || e.distance > dis) {
                            next = try_next;
                            dis = e.distance;
                        }
                    }
                }
            }

            // do move
            assert next!=null;
            energy -= dis;
            if(next.rank>current.rank){
                energy = 0;
            }
            back = current;
            current = next;
        }

        return current.mark;
    }
}
