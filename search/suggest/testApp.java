package search.suggest;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class testApp {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\ajay\\testing.txt";
        ManageTrie mt = new ManageTrie(filepath);
        mt.readTrie();


        mt.addWord("test");
        mt.addWord("best");
        mt.addWord("being");
        mt.addWord("bent");
        mt.addWord("bezzt");
        mt.addWord("bear");
        mt.addWord("bezos");
        mt.addWord("tes");
        mt.lookupWord("testi");
        mt.curateToppers();
        mt.lookupWord("being");
        mt.lookupWord("bear");
        mt.lookupWord("bezzt");
        mt.lookupWord("being");
        mt.lookupWord("bezzt");
        mt.lookupWord("bezzt");
        mt.lookupWord("bezos");

        int option;
        System.out.println("Enter an option");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("1. To Add a word\n2. To lookup a word\n3. To Exit");
                option = sc.nextInt();
                String nw;
                mt.curateToppers();

                switch (option) {
                    case 1:
                        System.out.println("Enter a word to be added");
                        nw = sc.next();
                        mt.addWord(nw);
                        break;
                    case 2:
                        System.out.println("Enter a word to be looked up");
                        nw = sc.next();
                        mt.lookupWord(nw);
                        break;
                    case 3:
                        System.out.println("Exiting now");
                        sc.close();
                        exit(0);
                }
            }
            catch ( InputMismatchException e){
                System.out.println("Invalid option");
                exit(0);
            }
        }
    }

}


