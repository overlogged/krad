package game;

import scala.collection.mutable.StringBuilder;
import server.Server;

import java.io.*;

public class Map {
    MapUnit[] units;                 // Means obj array of mapunit
    int fighter_init;                // Store the first location of fighter
    int poisoner_init;               // Store the first location of poisoner
    int fighter_evacuate;            // Store the evacuate location of fighters

    int[][] distance;

    Map(int number) {                 // Create a obj array
        units = new MapUnit[number];
        distance = new int[number][number];
    }

    Map(String filename) {
        try {
            FileInputStream fileInput = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fileInput);
            int number = ois.readInt();
            fighter_init = ois.readInt();
            poisoner_init = ois.readInt();
            fighter_evacuate = ois.readInt();
            units = new MapUnit[number];
            for (int i = 0; fileInput.available() > 0; i++) {
                try {
                    units[i] = (MapUnit) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            distance = new int[number][number];
            floyd();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void floyd() {
        int number = units.length;
        for(int i=0;i<number;i++){
            for(int j=0;j<number;j++){
                distance[i][j] = 100000;
            }
        }
        for (MapUnit u : units) {
            for (MapEdge e : u.edge) {
                distance[u.mark][e.adjedg] = e.distance;
            }
        }
        for (int k = 0; k < number; k++) {
            for (int i = 0; i < number; i++) {
                for (int j = 0; j < number; j++) {
                    if (i != j && i != k) {
                        if(distance[i][j] > distance[i][k]+distance[k][j]){
                            distance[i][j] = distance[i][k] + distance[k][j];
                        }
                    }
                }
            }
        }
        StringBuffer s = new StringBuffer();
        for(int i=0;i<number;i++){
            for(int j=0;j<number;j++){
                s.append(distance[i][j]);
                s.append(" ");
            }
            s.append("\n");
        }
//        Server.log("floyd",s.toString());
    }

    public void toFile(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);   // Create a Ser in the krad-backend
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(units.length);
            out.writeInt(fighter_init);
            out.writeInt(poisoner_init);
            out.writeInt(fighter_evacuate);
            for (MapUnit e : units) {
                out.writeObject(e);
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}


