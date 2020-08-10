package sample;

import java.awt.*;
import java.util.ArrayList;

public class Right {

    /**
     * check the right places of a playable square
     * @param i
     * @param word
     * @param board
     * @param parent
     * @param viableCoord
     * @param score
     * @param letters
     */
    public void checkRight(int i, String word, String[][] board, TrieNode parent,
                           ArrayList<Point> viableCoord, Score score, Letters letters){
        //System.out.println("RANking");
        ArrayList<Point> leftToRight = new ArrayList<>();
        ArrayList<String> replacableLetters = new ArrayList<>();
        boolean[] alphabets = new boolean[26];
        boolean validWord = false;
        int x = viableCoord.get(i).x;
        int y = viableCoord.get(i).y;
        //System.out.println("X: "+x);
        //System.out.println("Y: "+y);

        int playableSquaresRight = board.length - viableCoord.get(i).y;
        //System.out.println();
        //System.out.println("PlayableSquaresRight: "+playableSquaresRight);
        int temp = y;
        //System.out.println("temp: "+temp);

        if(word.length() > playableSquaresRight){
            //do nothing;
        }
        else{
            //System.out.println("y + 1 : "+(y + 1));
            if(y < (board.length - 1)) {
                if (!board[x][y + 1].equals("..") && !board[x][y + 1].equals("3.") &&
                        !board[x][y + 1].equals("2.") && !board[x][y + 1].equals(".2") &&
                        !board[x][y + 1].equals(".3") && !board[x][y + 1].equals("4.")
                        && !board[x][y + 1].equals(".4")) {
                    //System.out.println("No need to check");
                    return;
                }
            }
            int bb = x;
            int zz = temp;
            //System.out.println("BBB: " + bb);
            //System.out.println("ZZZ: " + zz);
            int counter = 0;
            while (zz < board.length) {
                if (board[bb][zz].equals("..") || board[bb][zz].equals("3.") ||
                        board[bb][zz].equals("2.")
                        || board[bb][zz].equals(".2") || board[bb][zz].equals(".3") ||
                        board[bb][zz].equals(".4") ||
                        board[bb][zz].equals("4.")) {
                    counter++;
                }
                else{
                    break;
                }
                zz++;
            }
            //System.out.println("Counter: "+counter);

            if (counter >= word.length()) {
                String check = "";
                check += word;
                //System.out.println("Check: " + check);
                bb = x;
                //System.out.println("BBA: " + bb);
                zz = temp - 1;
                //System.out.println("ZZA: " + zz);
                if (zz < 0) {
                    //System.out.println("Cannot play here");
                }
                else {
                    while (zz < (board.length - 1) && zz >= 0 && !board[bb][zz].equals("..") &&
                            !board[bb][zz].equals("3.")
                            && !board[bb][zz].equals("2.") && !board[bb][zz].equals(".2") &&
                            !board[bb][zz].equals(".3")
                            && !board[bb][zz].equals("4.") && !board[bb][zz].equals(".4")) {
                        check = board[bb][zz] + check;
                        zz--;
                    }
                    //System.out.println("Checkers: " + check.replaceAll("\\s", ""));
                }
               // System.out.println("BBA: " + bb);
                //System.out.println("ZZA: " + (zz+1));
                leftToRight.add(new Point(bb, (zz + 1)));
                //System.out.println("Counter: " + counter);
                bb = x;
                zz = temp;
                int k = word.length() - 1;
                while (k > 0) {
                    zz++;
                    k--;
                }
                if (zz < (board.length - 1)) {
                    //System.out.println("Board: " + board[bb][zz + 1]);
                }

                if (zz > (board.length - 1)) {
                    //do nothing
                }
                else {
                    while (zz < (board.length - 1) && !board[bb][zz + 1].equals("..") &&
                            !board[bb][zz + 1].equals("3.") &&
                            !board[bb][zz + 1].equals("2.") &&
                            !board[bb][zz + 1].equals(".2")
                            && !board[bb][zz + 1].equals(".3") &&
                            !board[bb][zz + 1].equals(".4")
                            && !board[bb][zz + 1].equals("4.")) {
                        check += board[bb][zz + 1];
                        zz++;
                    }
                }
                //System.out.println("BBBBBBB: " + bb);
                //System.out.println("ZZZZZZZ: " + (zz+1));
                leftToRight.add(new Point(bb, (zz + 1)));
                //System.out.println("Checkshdgsahdgsa: " + check);
                String checking = check.replaceAll("\\s", "");
                //System.out.println("Checkerssegsdfsdfsdfsdfdfsdf: " + checking);

                //System.out.println("Check.length: "+checking.length());
                if(checking.length() >  1){
                    if(checking.contains("*")){
                        letterSubs(replacableLetters);
                        int n = 0;
                        while(n < replacableLetters.size()){
                            int index = checking.indexOf('*');
                            String wildCardWord = checking.substring(0, index) +
                                    replacableLetters.get(n) + checking.substring(index + 1);
                            //System.out.println("WildcardWord: "+wildCardWord);
                            alphabets[n] = parent.validity(wildCardWord, parent);
                            //System.out.println("ValidWord: " + validWord);
                            n++;
                        }
                        //System.out.println("Alphabets length: "+alphabets.length);
                        for(int m = 0; m < alphabets.length; m++){
                            //System.out.print("At index "+m+" :");
                            if(alphabets[m] == true){
                                //System.out.println(alphabets[m]);
                                validWord = true;
                            }
                            else{
                                //System.out.println(alphabets[m]);
                            }
                        }
                        //System.out.println();
                    }
                    else {
                        validWord = parent.validity(checking, parent);
                        //System.out.println("ValidWord: " + validWord);
                    }
                }
                else{
                    validWord = true;
                }
            }

            if (counter >= word.length() && validWord == true) {
                //System.out.println("WHATTTT");
                //System.out.println("Word lenght: " + word.length());
                //System.out.println("CounterStrike: " + counter);
                zz = temp;
                //System.out.println("ZZ: " + zz);
                int k = 0;
                while (k < word.length()) {
                    String upCheck = "";
                    bb = x;
                    //System.out.println("BBBBBBBB: " + bb);
                    //System.out.println("ZZZZZZZZZZZ: " + zz);
                    upCheck = word.substring(k, k + 1);
                    //System.out.println("WAKANDA: " + upCheck);

                    while (bb > 0 && !board[bb - 1][zz].equals("..") &&
                            !board[bb - 1][zz].equals("3.")
                            && !board[bb - 1][zz].equals("2.") &&
                            !board[bb - 1][zz].equals(".2")
                            && !board[bb - 1][zz].equals(".3") &&
                            !board[bb - 1][zz].equals(".4")
                            && !board[bb - 1][zz].equals("4.")) {
                        upCheck = board[bb - 1][zz] + upCheck;
                        bb--;
                    }
                    //System.out.println("UpCheckers: " + upCheck.replaceAll("\\s", ""));
                    bb = x;
                    //System.out.println("BBBBBBBB: " + bb);
                    while (bb < (board.length - 1) && !board[bb + 1][zz].equals("..") &&
                            !board[bb + 1][zz].equals("3.") &&
                            !board[bb + 1][zz].equals("2.")
                            && !board[bb + 1][zz].equals(".2") &&
                            !board[bb + 1][zz].equals(".3")
                        && !board[bb + 1][zz].equals("4.") &&
                            !board[bb + 1][zz].equals(".4")) {
                        upCheck = upCheck + board[bb + 1][zz];
                        //System.out.println("Upchecking: " + upCheck);
                        bb++;
                    }

                    //System.out.println("upCheck: " + upCheck);
                    String upChecking = upCheck.replaceAll("\\s", "");
                    //System.out.println("Upcheckingasadsdasdas: "+upChecking);

                    //System.out.println("Check.length: "+upChecking.length());
                    if(upChecking.length() >  1){
                        if(upChecking.contains("*")){
                            //System.out.println("Alphabets length: "+alphabets.length);
                            for(int m = 0; m < alphabets.length; m++){
                                if(alphabets[m] == true){
                                    int index = upChecking.indexOf('*');
                                    //System.out.println("upchecking: "+upChecking);
                                    //System.out.println("1st: "+upChecking.substring(0, index));
                                    //System.out.println("2nd: "+replacableLetters.get(m));
                                    //System.out.println("3rd: "+upChecking.substring(index + 1));
                                    String wildCardWord = upChecking.substring(0, index) +
                                            replacableLetters.get(m) + upChecking.substring(index + 1);
                                    //System.out.println("WildcardWord: "+wildCardWord);
                                    alphabets[m] = parent.validity(wildCardWord, parent);
                                    //System.out.println("At index " +m+ ":"+alphabets[m]);
                                }
                                else{
                                    //System.out.println("At index " +m+ ":"+alphabets[m]);
                                }
                            }
                            //System.out.println();

                            int countering = 0;
                            for(int q = 0; q < alphabets.length; q++){
                                //System.out.print("At index "+q+" :");
                                if(alphabets[q] == true){
                                    countering++;
                                    //System.out.println(alphabets[q]);
                                }
                                else{
                                    //System.out.println(alphabets[q]);
                                }
                            }
                            if(countering == 0){
                                validWord = false;
                            }
                            else{
                                //System.out.println("Countering: "+countering);
                            }
                            //System.out.println();
                        }
                        else{
                            validWord = parent.validity(upChecking, parent);
                            //System.out.println("ValidWord: " + validWord);
                        }
                    }

                    if(validWord == false){
                        //System.out.println(""+upChecking+" is not a word!");
                        return;
                    }

                    zz++;
                    //System.out.println("ZZ: " + zz);
                    k++;
                }
            }
            for(int w = 0; w < alphabets.length; w++){
                //System.out.println("At index 1: " +alphabets[w]);
            }
            if(validWord == true){
                //System.out.println("" +word+ " can be played on the board from the start to the end of this list: "+leftToRight);
                if(word.contains("*")){
                    for(int w = 0; w < alphabets.length; w++){
                        if(alphabets[w] == true){
                            //System.out.println("Dang son");
                            int index = word.indexOf('*');
                            char lettering = (char)(w + 65);
                            String newWord = word.substring(0, index) + lettering + word.substring(index + 1);
                            //System.out.println("newWord: "+newWord);
                            score.calculateLeftRightScore(leftToRight, board, letters, newWord);
                        }
                    }
                }
                else{
                    score.calculateLeftRightScore(leftToRight, board, letters, word);
                }
            }
        }
    }

    /**
     * for wildcard
     * @param replacableLetters
     * @return
     */
    public ArrayList<String> letterSubs(ArrayList<String> replacableLetters){
        replacableLetters.add("a");
        replacableLetters.add("b");
        replacableLetters.add("c");
        replacableLetters.add("d");
        replacableLetters.add("e");
        replacableLetters.add("f");
        replacableLetters.add("g");
        replacableLetters.add("h");
        replacableLetters.add("i");
        replacableLetters.add("j");
        replacableLetters.add("k");
        replacableLetters.add("l");
        replacableLetters.add("m");
        replacableLetters.add("n");
        replacableLetters.add("o");
        replacableLetters.add("p");
        replacableLetters.add("q");
        replacableLetters.add("r");
        replacableLetters.add("s");
        replacableLetters.add("t");
        replacableLetters.add("u");
        replacableLetters.add("v");
        replacableLetters.add("w");
        replacableLetters.add("x");
        replacableLetters.add("y");
        replacableLetters.add("z");
        return replacableLetters;
    }
}
