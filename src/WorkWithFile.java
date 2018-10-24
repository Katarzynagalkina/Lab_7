import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class WorkWithFile {

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

    public static List<Long>  writeData(List <Telephone> list,  RandomAccessFile file) throws IOException {

       List <Long> indexList = new ArrayList<>();
        for(Telephone i : list) {

            indexList.add(file.getFilePointer());
            byte[] arr=serialize(i);
            file.write(arr);
        }

       return indexList;
    }


    public static Telephone readData(RandomAccessFile file, int index, List <Long> indexList) throws IOException {

        file.seek(indexList.get(index));
        byte[] arr = new byte[1024];
        file.read(arr);
        return deserialize(arr);
    }
}
