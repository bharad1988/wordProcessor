package search.suggest;

import java.io.Serializable;

public class Rank implements Comparable<Rank>, Serializable {
    char c;
    int rank;

    public Rank(char c, int rank){
        this.c = c;
        this.rank = rank;
    }
    public int compareTo(Rank r ){
        if (this.rank < r.rank) return -1;
        if (this.rank > r.rank) return 1;
        else return 0;
    }

    @Override
    public int hashCode() {
        return this.c;
    }

    public boolean equals(Object o){
        if(this.hashCode() == o.hashCode()){
            return true;
        }
        else return false;
    }
    @Override
    public String toString(){
        return "Char: " + this.c + " \t Rank: " + this.rank;
    }
}
