package search.suggest;
import java.io.*;

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

    public void writeObj(TN obj) {
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

    public void closeFlusher() throws IOException {
        objOps.close();
        ops.close();
    }
}
