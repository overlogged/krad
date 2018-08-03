package game;

import server.Server;

class MapChecker {

    static int tryMove(Map m, int i_current, int i_next, int energy) {

        // first step
        MapUnit back,current,next;

        int dis = m.distance[i_current][i_next];
        if(energy>=dis){
            energy-=dis;
            back = m.units[i_current];
            current = m.units[i_next];
            if(current.rank>back.rank) energy = 0;

            while (energy > 0) {
                next = null;
                dis = 0;

                // find next
                for (MapEdge e : current.edge) {
                    if (back.mark != e.adjedg) {
                        if (energy >= e.distance) {
                            if (next == null || e.distance > dis) {
                                next = m.units[e.adjedg];
                                dis = e.distance;
                            }
                        }
                    }
                }

                // do move
                // assert next != null;
                if(next==null) Server.log("error","tryMove 38");

                energy -= dis;
                if (next.rank > current.rank) {
                    energy = 0;
                }
                back = current;
                current = next;
            }

        }else{
            current = m.units[i_current];
        }
        return current.mark;
    }
}
