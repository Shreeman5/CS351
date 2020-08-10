package sample;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ComputerTray {

    ArrayList<Point> viableCoord = new ArrayList<>();
    Letters letters;
    String tray = "";
    int cScore = 0;
    static ArrayList<Point> wildCardCor = new ArrayList<>();
    static String anotherWildCardLetter = "";


    /**
     * computer gets 7 tiles at the start
     * @param letters
     * @param score
     */
    public ComputerTray(Letters letters, Score score){
        this.letters = letters;
        tray = letters.getTiles(tray);
        if(tray.contains(" ")){
            int index = tray.indexOf(' ');
            tray = tray.substring(0, index) + "*" + tray.substring(index + 1);
        }
    }

    /**
     * return current tray of computer
     * @return
     */
    public String getTray(){
        return tray;
    }

    /**
     * used in the console part
     * @param parent
     * @param score
     * @param letters
     */
    public ComputerTray(TrieNode parent, Score score, Letters letters) {
        int arrLen = 0;
        String dobArr[][] = null;
        int counterX = 0;
        int counterY = 0;

        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            while(line != null){
                if(line.equals("#########")){
                    System.out.println("Exiting out of program");
                    System.exit(0);
                }
                while(line.length() != 7){
                    if(line.length() == 1 || line.length() == 2){
                        arrLen = Integer.valueOf(line);
                        dobArr = new String[arrLen][arrLen];
                    }
                    else{
                        int length = line.length();
                        int i = 0;
                        while(i < length){
                            dobArr[counterX][counterY] = line.substring(i, i+2);
                            counterY++;
                            i = i + 3;
                        }
                        counterY = 0;
                        counterX++;
                    }
                    line = reader.readLine();
                    if(line.length() == 7){
                        starterPack(dobArr, line, parent, score, letters);
                        counterX = 0;
                        counterY = 0;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Computer uses this method to give its move
     * @param board
     * @param tray
     * @param parent
     * @param score
     * @param letters
     */
    public void starterPack(String[][] board, String tray, TrieNode parent, Score score,
                            Letters letters){
        int countering = 0;
        for(int i = 0; i < tray.length(); i ++){
            if (tray.charAt(i) == '*'){
                countering++;
            }
        }
        if(countering == 2){
            int index = tray.indexOf('*');
            tray = tray.substring(0, index) + "a" + tray.substring(index + 1);
        }

        System.out.println();
        System.out.println("Input Board: ");
        for(int j = 0; j < board.length; j++){
            for(int k = 0; k < board.length; k++){
                System.out.print(board[j][k] + " ");
                if(k == board.length - 1){
                    System.out.println();
                }
            }
        }

        List<String> letterCombos = new ArrayList<>();
        letterCombos.add(tray);
        System.out.println("Tray: "+tray);
        int combo = 1;
        while(combo < tray.length()){
            char[] stringToArray = tray.toCharArray();
            int r = stringToArray.length - combo;
            int n = stringToArray.length;
            printCombination(stringToArray, n, r, letterCombos);
            combo++;
        }
        List<String> letterPermuts = new ArrayList<>();

        for(String combination: letterCombos) {
            printPermutation(combination, "", letterPermuts);
        }
        Collections.reverse(letterPermuts);
        findCoordinates(board);

        Left left = new Left();
        Right right = new Right();
        Top top = new Top();
        Bottom bottom = new Bottom();

        findWord(board, letterPermuts, parent, left, right, top, bottom, score, letters);

        if(!score.getBestWord().isEmpty()) {
            //System.out.println("Board length: "+board.length);
            String word = score.getBestWord().get(0);
            //System.out.println("Tray: "+tray);

            ArrayList<Integer> index = new ArrayList<>();
            int ix = 0;
            while(ix < word.length()){
                int ij = 0;
                while(ij < tray.length()){
                    if(tray.charAt(ij) == word.charAt(ix)){
                        if(!index.contains(ij) && index.size() < word.length()) {
                            index.add(ij);
                            break;
                        }
                    }
                    ij++;
                }
                ix++;
            }
            //System.out.println(index);
            Collections.sort(index);
            //System.out.println(index);

            String newTray = "";
            for(int w = 0; w < index.size(); w++){
                if(w == 0){
                    //System.out.println(index.get(0));
                    newTray += tray.substring(0, index.get(0));
                }
                if(index.get(w) + 1 < 7) {
                    //System.out.println(index.get(w) + 1);
                    if(w < index.size() - 1) {
                        newTray += tray.substring(index.get(w) + 1, index.get(w + 1));
                    }
                    else{
                        newTray += tray.substring(index.get(w) + 1);
                    }
                }
                //System.out.println("NewTray: "+newTray);
            }
            tray = newTray;
            this.tray = letters.getTiles(tray);
            //System.out.println("tray: "+this.tray);
            index.clear();




            //System.out.println(score.getBestCoordinate());
            if (score.getBestCoordinate().get(0).x == score.getBestCoordinate().get(1).x) {
                int i = score.getBestCoordinate().get(0).y;
                int j = score.getBestCoordinate().get(1).y;
                int k = score.getBestCoordinate().get(0).x;
                int counter = 0;
                while (i < j) {
                    if (!board[k][i].equals("..") && !board[k][i].equals("3.") &&
                            !board[k][i].equals("2.") && !board[k][i].equals(".2") &&
                            !board[k][i].equals(".3")) {
                        i++;
                    }
                    else {
                        board[k][i] = " " + word.charAt(counter);
                        counter++;
                    }
                }
            }
            else {
                int i = score.getBestCoordinate().get(0).x;
                //System.out.println("I: " + i);
                int j = score.getBestCoordinate().get(1).x;
                //System.out.println("J: " + j);
                int k = score.getBestCoordinate().get(0).y;
                int counter = 0;

                while (i < j) {
                    if (!board[i][k].equals("..") && !board[i][k].equals("3.") &&
                            !board[i][k].equals("2.") && !board[i][k].equals(".2") &&
                            !board[i][k].equals(".3")) {
                        i++;
                    } else {
                        board[i][k] = " " + word.charAt(counter);
                        counter++;
                    }
                }
            }
            cScore += score.getBestScore().get(0);
            compScore();
            System.out.println("Solution "+score.getSendingWord()+" has "
                    +score.getBestScore()+" points.");

        }

        score.getBestWord().clear();
        score.getBestScore().clear();

        System.out.println("Solution Board: ");
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                System.out.print(board[i][j] + " ");
                if(j == (board.length - 1)){
                    System.out.println();
                }
            }
        }
        if(!score.getBestCoordinate().isEmpty()) {
            if (score.getBestCoordinate().get(0).x == score.getBestCoordinate().get(1).x) {
                int rr = 0;
                int b = score.getBestCoordinate().get(0).x;
                //System.out.println("b: " + b);
                int a = score.getBestCoordinate().get(0).y;
                //System.out.println("a: " + a);
                while (rr < score.getBestCoordinate().get(1).y -
                        score.getBestCoordinate().get(0).y) {
                    //System.out.println(board[b][a].charAt(1));
                    if (Character.isUpperCase(board[b][a].charAt(1))) {
                        wildCardCor.add(new Point(b, a));
                        anotherWildCardLetter = board[b][a].charAt(1) + "";
                        //System.out.println("anotherWildCardLetter: " + anotherWildCardLetter);
                    }
                    a++;
                    rr++;
                }
            } else if (score.getBestCoordinate().get(0).y == score.getBestCoordinate().get(1).y) {
                int rr = 0;
                int b = score.getBestCoordinate().get(0).x;
                //System.out.println("b: " + b);
                int a = score.getBestCoordinate().get(0).y;
                //System.out.println("a: " + a);
                while (rr < score.getBestCoordinate().get(1).x -
                        score.getBestCoordinate().get(0).x) {
                    //System.out.println(board[b][a].charAt(1));
                    if (Character.isUpperCase(board[b][a].charAt(1))) {
                        wildCardCor.add(new Point(b, a));
                        anotherWildCardLetter = board[b][a].charAt(1) + "";
                        //System.out.println("anotherWildCardLetter: " + anotherWildCardLetter);
                    }
                    b++;
                    rr++;
                }
            }
            //System.out.println("wildCardCor: " + wildCardCor);
            //System.out.println("anotherWildCardLetter: " + anotherWildCardLetter);
        }
        score.getBestCoordinate().clear();
        System.out.println();
        viableCoord.clear();
    }

    /**
     * return score of computer
     * @return
     */
    public int compScore(){
        return cScore;
    }

    /**
     * find the best word
     * @param board
     * @param letterPermuts
     * @param parent
     * @param left
     * @param right
     * @param top
     * @param bottom
     * @param score
     * @param letters
     */
    public void findWord(String[][] board, List<String> letterPermuts, TrieNode parent,
                         Left left, Right right, Top top, Bottom bottom, Score score, Letters letters) {
        //ArrayList<String> test = new ArrayList<>();
        //test.add("an");
        for(int i = 0; i < viableCoord.size(); i++){
            for(int j = 0; j < letterPermuts.size(); j++){
                String word = letterPermuts.get(j);
                //System.out.println();
                //System.out.println("testingletter: " +word);
                left.checkLeft(i, word, board, parent, viableCoord, score, letters);
                right.checkRight(i, word, board, parent, viableCoord, score, letters);
                top.checkTop(i, word, board, parent, viableCoord, score, letters);
                bottom.checkBottom(i, word, board, parent, viableCoord, score, letters);
            }
        }
    }


    /**
     * this method and the method directly beneath this make combination of each letter of the tray
     * @param arr
     * @param n
     * @param r
     * @param letterCombos
     */
    public void printCombination(char[] arr, int n, int r, List<String> letterCombos){
        char data[] = new char[r];
        combinationUtil(arr, data, 0, n-1, 0, r, letterCombos);
    }

    public void combinationUtil(char[] arr, char[] data, int start, int end, int index,
                                int r, List<String> letterCombos){
        if (index == r)
        {
            String miniComb = "";
            for (int j=0; j<r; j++) {
                miniComb = miniComb + data[j];
            }
            if(!letterCombos.contains(miniComb)) {
                letterCombos.add(miniComb);
            }
            return;
        }
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, letterCombos);
        }
    }

    /**
     * method makes every permutation of the combinations of a tray
     * @param str
     * @param ans
     * @param letterPermuts
     */
    public void printPermutation(String str, String ans, List<String> letterPermuts){
        if (str.length() == 0) {
            //System.out.println(ans);
            if(!letterPermuts.contains(ans)) {
                letterPermuts.add(ans);
            }
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String ros = str.substring(0, i) + str.substring(i + 1);
            printPermutation(ros, ans + ch, letterPermuts);
        }
    }

    /**
     * find the coordinates at which the computer can play
     * @param board
     */
    public void findCoordinates(String[][] board){
        for (int i = 0; i < board.length; i++){
            for(int j =  0; j < board.length; j++){
                if(!board[i][j].equals("..") && !board[i][j].equals("3.") &&
                        !board[i][j].equals("2.") && !board[i][j].equals(".3") &&
                        !board[i][j].equals(".2") && !board[i][j].equals(".4")
                            && !board[i][j].equals("4.")){
                    if(j != 0) {
                        String left = board[i][j - 1];
                        if(left.equals("..") || left.equals("3.") || left.equals("2.")
                                || left.equals(".3") || left.equals(".2") ||
                            left.equals("4.") || left.equals(".4")){
                            if(!viableCoord.contains(new Point(i, (j - 1 )))) {
                                viableCoord.add(new Point(i, (j - 1)));
                            }
                        }
                    }
                    if(j != (board.length - 1)) {
                        String right = board[i][j + 1];
                        if(right.equals("..") || right.equals("3.") || right.equals("2.")
                                || right.equals(".3") || right.equals(".2")
                            || right.equals(".4") || right.equals("4.")){
                            if(!viableCoord.contains(new Point(i, (j+1)))) {
                                viableCoord.add(new Point(i, (j + 1)));
                            }
                        }
                    }
                    if(i != 0) {
                        String top = board[i - 1][j];
                        if(top.equals("..") || top.equals("3.") || top.equals("2.")
                                || top.equals(".3") || top.equals(".2")
                            || top.equals("4.") || top.equals(".4")){
                            if(!viableCoord.contains(new Point((i - 1), j))) {
                                viableCoord.add(new Point((i - 1), j));
                            }
                        }
                    }
                    if(i != (board.length - 1)) {
                        String bottom = board[i + 1][j];
                        if(bottom.equals("..") || bottom.equals("3.") || bottom.equals("2.")
                                || bottom.equals(".3") || bottom.equals(".2")
                        || bottom.equals("4.") || bottom.equals(".4")){
                            if(!viableCoord.contains(new Point(i + 1, j ))) {
                                viableCoord.add(new Point((i + 1), j));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * return the playable coordinates
     * @return
     */
    public ArrayList<Point> getViableCoord(){
        return viableCoord;
    }

}
