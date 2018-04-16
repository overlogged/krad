import java.io.*;

public class CreateMap {
    int array_number;                      //Means the number of obj
    MapUnit[] sample;                      //Means obj array of mapunit

    CreateMap() {
    }

    CreateMap(int number) {                 //Create a obj array
        array_number = number;
        sample = new MapUnit[number];
    }

    void outputMap() {                      //Output map ser file
        for (int i = 0; i < array_number; i++) {
            int edge_number;
            if (i == 6) {
                edge_number = 3;
            } else if (i == 0) {
                edge_number = 1;
            } else {
                edge_number = 2;
            }

            sample[i] = new MapUnit(edge_number);
            sample[i].mark = i + 1;
            sample[i].height = i + 1;
            sample[i].is_factor = 0;
            sample[i].edge[0].adjedg = i + 2;
            sample[i].edge[0].distance = 1;
            if (i != 0) {
                sample[i].edge[1].adjedg = i;
                sample[i].edge[1].distance = 1;
            }
        }
        sample[6].edge[2].adjedg = 11;
        sample[6].edge[2].distance = 1;
        sample[11].edge[0].adjedg = 6;
        sample[9].is_factor = 1;
        sample[9].key.name = "Fate";
        try {
            FileOutputStream fileOut = new FileOutputStream("Mapsample.map");   //Create a Ser in the krad-backend
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i = 0; i < array_number; i++) {
                out.writeObject(sample[i]);
            }
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in krad-backend/Mapsample.map");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void obtainMap() {                  //Obtain objection from file
        try {
            FileInputStream fn = new FileInputStream("Mapsample.ser");
            ObjectInputStream ois = new ObjectInputStream(fn);
            for (int i = 0; fn.available() > 0; i++) {
                try {
                    sample[i] = (MapUnit) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Serialized data is saved in krad-backend/Mapsample.map");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}


