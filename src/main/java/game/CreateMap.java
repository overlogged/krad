package game;

import java.io.*;

import server.Server;

public class CreateMap {
    int array_number;                      //Means the number of obj
    MapUnit[] sample;                      //Means obj array of mapunit

    /*game.CreateMap() {
    }*/

    CreateMap(int number) {                 //Create a obj array
        array_number = number;
        sample = new MapUnit[number];
    }

    void outputMap() {                      //Output map ser file
        for (int i = 0; i < array_number; i++) {
            int edge_number;
            if ((i == 7) || (i == 8) || (i == 22)) {
                edge_number = 3;
            } else if (i == 0) {
                edge_number = 1;
            } else {
                edge_number = 2;
            }

            sample[i] = new MapUnit(edge_number);
            sample[i].mark = i+1;
            sample[i].status = 0;

            if (i == 22) {
                sample[i].edge[0].adjedg = 8;
            } else {
                sample[i].edge[0].adjedg = i + 2;
            }
            sample[i].edge[0].distance = 1;

            if (edge_number >= 2) {
                sample[i].edge[1].adjedg = i ;
                sample[i].edge[1].distance = 1;
            }
            if (i < 7) {
                sample[i].rank = 1;
            } else if (i == 7) {
                sample[i].rank = 2;
            } else if (i == 15) {
                sample[i].rank = 4;
            } else {
                sample[i].rank = 3;
            }
        }

        sample[8].edge[2].adjedg = 7;
        sample[8].edge[2].distance = 2;
        sample[22].edge[2].distance = 2;
        sample[22].edge[2].adjedg = 7;
        sample[7].edge[2].adjedg = 23;
        sample[7].edge[2].distance = 1;

        sample[7].status=1;
        sample[15].status=2;
        sample[15].key.name="FATE";
        try {
            FileOutputStream fileOut = new FileOutputStream("sample.map");   //Create a Ser in the krad-backend
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i = 0; i < array_number; i++) {
                out.writeObject(sample[i]);
            }
            out.close();
            fileOut.close();
//            Server.log("serialize", "sample.map");
//            System.out.println("Serialized data is saved in krad-backend/Mapsample.map");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    void obtainMap() {                  //Obtain objection from file
        try {
            FileInputStream fileInput = new FileInputStream("sample.map");
            ObjectInputStream ois = new ObjectInputStream(fileInput);
            for (int i = 0; fileInput.available() > 0; i++) {
                try {
                    sample[i] = (MapUnit) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Serialized data has benn opened");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}


