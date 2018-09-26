package search.suggest;
import java.io.*;
import java.util.ArrayList;

public class Flusher {
    private OutputStream ops = null;
    private ObjectOutputStream objOps = null;

    public Flusher(String filepath){

        try {
            ops = new FileOutputStream(filepath);
            objOps = new ObjectOutputStream(ops);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObj(ArrayList<TN> obj) {
        try {

            objOps.writeObject(obj);
            objOps.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (objOps != null) objOps.close();
            } catch (Exception ex) {
            }
        }
    }

    public void writeChar(char delimiter) {
        try {
            objOps.writeChar(delimiter);
            objOps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFlusher() throws IOException {
        objOps.close();
        ops.close();
    }
}
