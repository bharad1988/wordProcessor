package search.suggest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

public class TN  implements Serializable {
    private static final int LIMIT = 3;
    private char key;
    private int rank;
    private boolean set;
    private String meaning;
    public HashMap<Character, TN> tm;
    private PriorityQueue<Rank> toppers = new PriorityQueue<>();

    public PriorityQueue<Rank> getToppers(){
        return toppers;
    }

    public void setToppers(PriorityQueue<Rank> toppers) {
        this.toppers = toppers;
    }

    public TN(){
        this.key = '#';

        this.rank = 0;
        this.set = false;
        this.tm = new HashMap<>();
        //this.toppers = new char[LIMIT];

    }

    public TN(char  key){
        this.key = key;
        this.rank = 0;
        this.set = false;
        this.tm = new HashMap<>();
        //this.toppers = new char[LIMIT];

    }

    public TN addChar(Character c) {
        TN next;
        if (this.tm.containsKey(c)) {
            next = this.tm.get(c);
            next.rank++;
            //maintainToppers(c);

            //System.out.println("found");
        }
        else{
            TN n = new TN(c);
            //System.out.println("not found");
            this.tm.put(c, n);
            next = this.tm.get(c);
        }
        return next;
    }

    public HashMap<Character, TN> getTM(){
        return this.tm;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }

    void setTN(){
        this.set = true;
    }
    boolean getTN(){
        return this.set;
    }
    int getRank(){
        return this.rank;
    }
    TN lookUp(Character c){
        TN next = null;

        if (this.tm.containsKey(c)) {
            next = this.tm.get(c);
            next.rank++;
            //System.out.println("found");
        }
        else {
            TN n = new TN(c);
            //System.out.println("not found");
            this.tm.put(c, n);
            next = this.tm.get(c);
        }
        return next;

    }
    public char getKey() {
        return key;
    }
    @Override
    public String toString(){
        return String.valueOf(this.key) + String.valueOf(this.rank);
    }
    public void printNode(){
        System.out.println(set);
        Set<Character> ts = tm.keySet();
        for(Character key:ts){
            System.out.println(key + "===" + tm.get(key).rank);
        }
    }
}