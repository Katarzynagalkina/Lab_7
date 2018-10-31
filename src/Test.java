import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.*;

public class Test {



    public static void main(String[] args) throws IOException
    {

        ArrayList<Telephone> telephoneList = new ArrayList<>();
        Telephone obj1= new Telephone("Artem", "Dmitrievich","Dachevski","7254325123", 40, 5 );
        Telephone obj2= new Telephone("Maxim", "Olegovich","Smirnov","4254323", 12, 50 );
        Telephone obj3= new Telephone("Elena", "Vladimirovna","Smirnova","425635", 13, 18 );
        telephoneList.add(obj1);
        telephoneList.add(obj2);
        telephoneList.add(obj3);


        RandomAccessFile file=new  RandomAccessFile("data.dat", "rw");
        ArrayList <Long> indexList= Connector.writeData(telephoneList, file);
        for(int i=0;i<indexList.size();i++)
        {
            Telephone res= Connector.readData(file, i, indexList);
            System.out.println(res.toString());
        }


        System.out.println("\nИндексирование по полю\n");
        try {
            Map<Object, Long> IndexMap = Connector.writeDataByField(telephoneList, file, "rate");
            Object[] keys=IndexMap.keySet().toArray();
            Arrays.sort(keys);

            System.out.println("Сортировка по возрастанию");
            for(Object i:keys)
            {
                Telephone res= Connector.readDataByField(file,i,IndexMap);
                assert res != null;
                System.out.println(res.toString());
            }

            Arrays.sort(keys, Collections.reverseOrder());
            System.out.println("\nСортировка по убыванию");
            for(Object i:keys)
            {
                Telephone res= Connector.readDataByField(file,i,IndexMap);
                assert res != null;
                System.out.println(res.toString());
            }


            ArrayList<Telephone>  res = new ArrayList<>();

            System.out.println("\nПоиск объекта по индексу");
            res= Connector.searchByIndex(IndexMap, obj3.getFieldName("rate"), file, 0);
            assert res != null;
            for(Telephone i: res) {
                System.out.println(i.toString());
            }

            res.clear();
            System.out.println("\nВывод всех объектов по индексу больше указанного");
            res= Connector.searchByIndex(IndexMap, obj3.getFieldName("rate"), file, 1);
            assert res != null;
            for(Telephone i: res)
                System.out.println(i.toString());

            res.clear();
            System.out.println("\nВывод всех объектов по индексу меньше указанного");
            res= Connector.searchByIndex(IndexMap, obj3.getFieldName("rate"), file, -1);
            assert res != null;
            for(Telephone i: res)
                System.out.println(i.toString());


            System.out.println("\nУдаление по индексу");
          Connector.removeByIndex(IndexMap,obj3.getFieldName("rate"),file);
           for(Object i:keys)
            {
                Telephone res3= Connector.readDataByField(file,i,IndexMap);
                if(res3!=null){
                System.out.println(res3.toString());
            }}
        }
        catch (IllegalAccessException | NoSuchFieldException e){

            e.printStackTrace();

        }

    }

}


