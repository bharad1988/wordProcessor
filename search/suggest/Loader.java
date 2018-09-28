package search.suggest;

import java.io.*;

public class Loader {

    private String filepath;

    public Loader(String filepath)  {

        try {
            this.filepath = filepath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TN readObj() {
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        TN node = null;
        try{
            fileIs = new FileInputStream(this.filepath);
            objIs = new ObjectInputStream(fileIs);

            node = (TN) objIs.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objIs != null) objIs.close();
            } catch (Exception ex) {
            }
        }
        return node;
    }

}
