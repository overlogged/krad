
import java.io.*;

public class MapChecker {
}

class MapEdge implements Serializable {
    int adjedg;   //The other unit sign
    int distance;  //The length of the edg
}

class Factor implements Serializable {      //The important thing in the map
    String name;

    void function() {
    }
}

class MapUnit implements Serializable {
    int mark;                    // The sign ofh a unit
    int edgnumber;               // The number of edg
    MapEdge[] edg;               // The connect edg of unit
    int height;                  // The height of the unit
    int is_factor;               // Judge the map status
    Factor key = new Factor();   // Store the important thing

    MapUnit() {
    }

    MapUnit(int number) {
        edgnumber = number;
        edg = new MapEdge[edgnumber];
        for (int i = 0; i < edgnumber; i++) {
            edg[i] = new MapEdge();   //edg[0] is the next,edg[1]is the before
        }
    }

}

class SetMap {           //It just a sample to make others test map
    public static void main(String[] args) throws Exception {
        MapUnit[] sample = new MapUnit[12];
        MapUnit[] show = new MapUnit[12];
        for (int i = 0; i < 12; i++) {
            int number;
            if (i == 6) {
                number = 3;
            } else if (i == 0) {
                number = 1;
            } else {
                number = 2;
            }

            sample[i] = new MapUnit(number);
            sample[i].mark = i + 1;
            sample[i].height = i + 1;
            sample[i].is_factor = 0;
            sample[i].edg[0].adjedg = i + 2;
            sample[i].edg[0].distance = 1;
            if (i != 0) {
                sample[i].edg[1].adjedg = i;
                sample[i].edg[1].distance = 1;
            }
        }
        sample[6].edg[2].adjedg = 11;
        sample[6].edg[2].distance = 1;
        sample[11].edg[0].adjedg = 6;
        sample[9].is_factor = 1;
        sample[9].key.name = "Fate";
        try {
            FileOutputStream fileOut = new FileOutputStream("Mapsample.ser");   //Create a Ser in the krad-backend
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i = 0; i < 12; i++) {
                out.writeObject(sample[i]);
            }
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in krad-backend/Mapsample.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        FileInputStream fn = new FileInputStream("Mapsample.ser");
        ObjectInputStream ois = new ObjectInputStream(fn);
        for (int i = 0; fn.available() > 0; i++) {
            show[i] = (MapUnit) ois.readObject();
            System.out.print(show[i].mark);
            System.out.print("  next is the ");
            System.out.println(show[i].edg[0].adjedg);
            System.out.print("the heigt of it is ");
            System.out.println(show[i].height);
            if ((show[i].is_factor | 0) != 0) {
                System.out.print("it has factor ");
                System.out.println(show[i].key.name);
            }
        }


    }
}

