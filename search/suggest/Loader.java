package search.suggest;

import java.io.*;
import java.util.ArrayList;

public class Loader {

    private String filepath;

    public Loader(String filepath)  {

        try {
            this.filepath = filepath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TN> readObj() {
        ArrayList<TN> nodelist = null;
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try{
            fileIs = new FileInputStream(this.filepath);
            objIs = new ObjectInputStream(fileIs);
            nodelist = (ArrayList<TN>) objIs.readObject();

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
        return nodelist;
    }


}
