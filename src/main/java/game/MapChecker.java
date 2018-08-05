package game;

import server.Server;

public class MapChecker {

    public static int tryMove(Map m, int i_current, int i_next, int energy) {

        // first step
        MapUnit current,next;

        int dis2org = m.distance[i_current][i_next];
        if(energy>=dis2org){
            energy-=dis2org;
            current = m.units[i_next];
            if(current.rank>m.units[i_current].rank) energy = 0;

            while (energy > 0) {
                next = null;
                int dis = 0;

                // find next
                for (MapEdge e : current.edge) {
                    if (m.distance[i_current][e.adjedg]>dis2org) {
                        if (energy >= e.distance) {
                            if (next == null || e.distance > dis) {
                                next = m.units[e.adjedg];
                                dis = e.distance;
                                dis2org = m.distance[i_current][e.adjedg];
                            }
                        }
                    }
                }

                // do move
                // assert next != null;
                StringBuffer bf = new StringBuffer();
                bf.append("tryMove ");
                bf.append(i_current);
                bf.append(" ");
                bf.append(i_next);
                bf.append(" ");
                bf.append(energy);
                bf.append("\n");

                if(next==null) Server.log("error",bf.toString());

                energy -= dis;
                if (next.rank > current.rank) {
                    energy = 0;
                }
                current = next;
            }

        }else{
            current = m.units[i_current];
        }
        return current.mark;
    }
}
