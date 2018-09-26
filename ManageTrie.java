package search.suggest;

import java.io.*;
import java.util.*;

public class ManageTrie {
    private TN root;
    private char test;
    private static final int LIMIT = 3;
    private static final int SET_WORD_LIMIT = 100;
    private static final char DELIMITER = '-';
    private static final char NOLEAF = '$';
    private Flusher flusher;
    private Loader loader;
    private String filepath;
    public ManageTrie(String filepath){
        this.root = new TN();
        try {
            this.filepath = filepath;
            loader = new Loader(filepath);
            //flusher = new Flusher(filepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addWord(String s){
        StringBuilder sb = new StringBuilder(s);
        TN next = root;
        while (sb.capacity() != 0){
            char ch = sb.charAt(0);
            sb.deleteCharAt(0);
            sb.trimToSize();
            next = next.addChar(ch);
        }
        next.setTN();
    }
    public boolean lookupWord(String s){
        StringBuilder sb = new StringBuilder(s);
        StringBuilder ds = new StringBuilder();
        TN next = root;
        List<StringBuilder> slist;
        boolean lastFound= false;
        String lastFoundString = "";
        while (sb.capacity() != 0){
            char ch = sb.charAt(0);
            sb.deleteCharAt(0);
            sb.trimToSize();
            if (next.getTN()){
                lastFound = true;
                lastFoundString = ds.toString();
            }
            ds.append(ch);
            next = next.lookUp(ch);
        }
        //System.out.println(next.getRank());
        if (next.getTN()){
            System.out.println(ds.toString() + " : Found");
            return true;
        }
        else if (next.getRank() > SET_WORD_LIMIT) {
            next.setTN();
            return true;
        }
        else {
            System.out.println("\"" + ds.toString() + "\"" + ": Not Found..");
            if(lastFound){
                System.out.println("Did you mean : "+ lastFoundString);
            }
            slist = suggestWords(next);
            if (!slist.isEmpty()) {
                System.out.println("Try the following: ");

                for (ListIterator<StringBuilder> iter = slist.listIterator(); iter.hasNext(); ) {
                    System.out.println(ds.toString() + iter.next().toString());
                }
            }

            return false;
        }
    }

    private StringBuilder lookupWord(TN next){
        StringBuilder sb = new StringBuilder();
        while(!next.getTN()){
            sb.append(next.getKey());
            char nextc = next.getToppers().peek().c;
            next = next.getTM().get(nextc);
        }
        sb.append(next.getKey());
        return sb;
    }
    public List<StringBuilder> suggestWords(TN s){
        int size = s.getToppers().size();
        List<StringBuilder> sugg = new ArrayList<>();
        Iterator<Rank> it = s.getToppers().iterator();
        char nextc;
        while(it.hasNext()){
            Rank r = it.next();
            nextc = r.c;
            StringBuilder sn = lookupWord(s.getTM().get(nextc));
            sugg.add(sn);
        }
        return sugg;
    }

    private PriorityQueue<Rank> addEntry(PriorityQueue<Rank> toppers,char c,int rank){
        Rank r = new Rank(c,rank);
        if (toppers.size() <= LIMIT){
            //System.out.println(toppers.contains(r));
            //System.out.println(c);
            if (!toppers.contains(r))
            toppers.add(r);
        }
        else{
            if (toppers.peek().rank < rank){
                if (!toppers.contains(r)) {
                    toppers.remove();
                    //System.out.println(r.c);
                    //System.out.println(c);
                    //toppers.remove();
                    toppers.add(r);
                }
            }
        }
        return toppers;

    }

    public void curateToppers(){
        // iterate over hash map
        HashMap<Character,TN> map = root.getTM();
        if (map.isEmpty()){
            return;
        }
        else {
            PriorityQueue<Rank> toppers = root.getToppers();
            for (Map.Entry<Character, TN> entry : map.entrySet()) {
                char c = entry.getKey();
                int rank = entry.getValue().getRank();
                addEntry(toppers, c, rank);
            }
            root.setToppers(toppers);
            for (Map.Entry<Character,TN> entry: map.entrySet()) {
                curateToppers(entry.getValue());
            }
        }
    }

    private void curateToppers(TN n){
        HashMap<Character,TN> map = n.getTM();
        if (map.isEmpty()){
            return;
        }
        else {
            PriorityQueue<Rank> toppers = n.getToppers();
            for (Map.Entry<Character, TN> entry : map.entrySet()) {
                char c = entry.getKey();
                int rank = entry.getValue().getRank();
                addEntry(toppers, c, rank);
            }
            n.setToppers(toppers);
            for (Map.Entry<Character,TN> entry: map.entrySet()) {
                curateToppers(entry.getValue());
            }
        }
    }

    public void writeTrie(){
        flusher = new Flusher(filepath);
        ArrayList<TN> nodelist = new ArrayList<>();
        nodelist.add(this.root);
        System.out.println(this.root);
        HashMap<Character,TN> map = root.getTM();
        if (map.isEmpty()){
            return;
        }
        for (Map.Entry<Character,TN> entry: map.entrySet()) {
            nodelist = writeTrie(nodelist, entry.getValue());

        }
        flusher.writeObj(nodelist);
        this.closeTriefile();
    }

    private ArrayList<TN> writeTrie(ArrayList<TN> nodelist, TN node){
        HashMap<Character,TN> map = node.getTM();
        System.out.println(node);
        nodelist.add(node);
        if (map.isEmpty()){
            return nodelist;
        }
        else {
            for (Map.Entry<Character,TN> entry: map.entrySet()) {
                nodelist = writeTrie(nodelist,entry.getValue());
            }
            return nodelist;
        }
    }

    public void closeTriefile(){
        try {
            flusher.closeFlusher();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTrie(){
        ArrayList<TN> nodelist = loader.readObj();
        if (nodelist.size() <= 0){
            return;
        }
        this.root = nodelist.get(0);
        nodelist.remove(0);
        System.out.println(nodelist.size());
        System.out.println(nodelist.get(0));

        HashMap<Character,TN> map = this.root.getTM();
        if (map.isEmpty()){
            this.curateToppers();
            return;
        }
        else {
            TN original = this.root;
            for (Map.Entry<Character, TN> entry : map.entrySet()) {
                this.root = readTrie(original,nodelist);
            }
            this.curateToppers();
            return;
        }
    }


    private TN readTrie(TN root,ArrayList<TN> nodelist){
        if (nodelist.size() <= 0){
            return root;
        }
        TN current = nodelist.get(0);
        nodelist.remove(0);
        root.tm.put(current.getKey(),current);
        System.out.println(current);
        HashMap<Character,TN> map = root.getTM();
        if (map.isEmpty()){
            return root;
        }
        else {
            TN original = current;
            for (Map.Entry<Character, TN> entry : map.entrySet()) {
                current = readTrie(original,nodelist);
            }
            return root;
        }
    }
}
