package game;

import java.io.*;

public class Map {
    MapUnit[] units;                 // Means obj array of mapunit

    Map(int number) {                 // Create a obj array
        units = new MapUnit[number];
    }

    Map(String filename){
        try {
            FileInputStream fileInput = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fileInput);
            int number = ois.readInt();
            units = new MapUnit[number];
            for (int i = 0; fileInput.available() > 0; i++) {
                try {
                    units[i] = (MapUnit) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void toFile(String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);   // Create a Ser in the krad-backend
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(units.length);
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

