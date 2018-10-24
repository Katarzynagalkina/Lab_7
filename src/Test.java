import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
public class Test {
    public static void main(String[] args) throws IOException
    {

        List<Telephone> telephoneList = new ArrayList<>();
        Telephone obj1= new Telephone("Artem", "Dmitrievich","Dachevski","7254325123", 40, 5 );
        Telephone obj2= new Telephone("Maxim", "Olegovich","Smirnov","4254323", 13, 50 );
        telephoneList.add(obj1);
        telephoneList.add(obj2);

        RandomAccessFile file=new  RandomAccessFile("data.dat", "rw");
        List <Long> indexList= WorkWithFile.writeData(telephoneList, file);
        for(int i=0;i<indexList.size();i++)
        {
            Telephone res=WorkWithFile.readData(file, i, indexList);
            System.out.println(res.toString());
        }

    }

}


