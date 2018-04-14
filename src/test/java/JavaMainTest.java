import java.io.*;
import java.util.Map;

public class JavaMainTest{
    public static void main(String[] args)throws Exception{
        MapUnit[] sample=new MapUnit[12];
        MapUnit[] show=new  MapUnit[12];
        for (int i=0;i<12;i++)
        {
            sample[i] = new MapUnit();
            sample[i].mark=i+1;
            sample[i].height=i+1;
            sample[i].is_factor=0;
            sample[i].edgnumber=2;
            if (i==6){sample[i].edgnumber=3;}
            if (i==0){sample[i].edgnumber=1;}
            sample[i].setEdg();
            sample[i].edg[0].adjedg=i+2;
            sample[i].edg[0].distance=1;
            if (i!=0)
            {
                sample[i].edg[1].adjedg = i;
                sample[i].edg[1].distance=1;
            }
        }
        sample[6].edg[2].adjedg=11;
        sample[6].edg[2].distance=1;
        sample[11].edg[0].adjedg=6;
        sample[9].is_factor=1;
        sample[9].key.name="Fate";
        try
        {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\欧阳炳濠\\Desktop\\employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i=0;i<12;i++)
            {
                out.writeObject(sample[i]);
            }
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in /tmp/employee.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
        FileInputStream fn = new FileInputStream("C:\\Users\\欧阳炳濠\\Desktop\\employee.ser");
        ObjectInputStream ois = new ObjectInputStream(fn);
        for (int i=0;fn.available()>0;i++)
        {
            show[i]=(MapUnit)ois.readObject();
            System.out.print(show[i].mark);
            System.out.print("  next is the ");
            System.out.println(show[i].edg[0].adjedg);
            System.out.print("the heigt of it is ");
            System.out.println(show[i].height);
            if ((show[i].is_factor|0)!=0) {
                System.out.print("it has factor ");
                System.out.println(show[i].key.name);
            }
        }


    }
}

