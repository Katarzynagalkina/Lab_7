import java.io.*;
import java.util.ArrayList;
import java.util.*;

class Connector {

    private static byte[] serialize(Telephone obj) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static Telephone deserialize(byte[] arr) throws IOException {
        ByteArrayInputStream baos=new ByteArrayInputStream(arr);
        try (ObjectInputStream oin = new ObjectInputStream(baos)) {
            return (Telephone)oin.readObject();
        }
        catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }


    static ArrayList <Long>  writeData(List<Telephone> list, RandomAccessFile file) throws IOException {

        ArrayList <Long> indexList = new ArrayList<>();
        for(Telephone i : list) {

            indexList.add(file.getFilePointer());
            byte[] arr=serialize(i);
            assert arr != null;
            file.write(arr);
        }

       return indexList;
    }


    static Telephone readData(RandomAccessFile file, int index, ArrayList<Long> indexList) throws IOException {

        file.seek(indexList.get(index));
        byte[] arr = new byte[1024];
        file.read(arr);
        return deserialize(arr);
    }



  public  static Map<Object,Long> writeDataByField(List<Telephone> list, RandomAccessFile file, String fieldName) throws IOException,IllegalAccessException, NoSuchFieldException
    {
        Map<Object,Long> indexList = new HashMap<Object,Long>();
        for(Telephone i : list) {

            indexList.put(i.getFieldName(fieldName),file.getFilePointer());
            byte[] arr=serialize(i);
            assert arr != null;
            file.write(arr);
        }
        return indexList;
    }


    public static Telephone readDataByField(RandomAccessFile file, Object index, Map<Object, Long> indexList) throws IOException {

        file.seek(indexList.get(index));
        byte[] arr = new byte[1024];
        file.read(arr);
        Telephone obj= deserialize(arr);
        assert obj != null;
        if(!(obj.isDeleted())) return obj;
        else return null;
    }

    public static ArrayList <Telephone> searchByIndex(Map<Object, Long> map, Object index, RandomAccessFile file, int mode) throws IOException
    {
        ArrayList<Telephone> res = new ArrayList<>();
        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        switch (mode)  {
            case 0:
            {
                 for(Object i:keys)
                     if(i==index)
                     {
                         file.seek(map.get(i));
                         byte[] arr = new byte[1024];
                         file.read(arr);
                         res.add(deserialize(arr));
                         return res;
                     }
            }

            case -1: {
                boolean check=true;
                   for (Object i : keys) {
                        if (i.equals(index))  break;
                        file.seek(map.get(i));
                        byte[] arr = new byte[1024];
                        file.read(arr);
                        res.add(deserialize(arr));
                    }
                return res;
            }

            case 1: {
                boolean check=true;
                Arrays.sort(keys, Collections.reverseOrder());
                    for (Object i : keys) {
                       if (i.equals(index)) break;
                        file.seek(map.get(i));
                        byte[] arr = new byte[1024];
                        file.read(arr);
                        res.add(deserialize(arr));
                    }
                }

                return res;

            }

        return null;
    }


    public static void removeByIndex(Map<Object, Long> map, Object index, RandomAccessFile file) throws IOException
    {
        Object[] keys = map.keySet().toArray();
        for(Object i:keys)
        {
            if(i.equals(index))
            {
                Telephone deletedObject=readDataByField(file,index, map);
                assert deletedObject != null;
                deletedObject.setDeleted();;
                file.seek(map.get(index));
                byte[] arr=serialize(deletedObject);
                assert arr != null;
                file.write(arr);
            }
        }

    }

}
