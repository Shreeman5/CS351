package sample;

import java.awt.*;
import java.util.ArrayList;

public class Score {

    int megaScore = 0;
    ArrayList<Point> bestCoordinate = new ArrayList<>();
    ArrayList<Integer> score = new ArrayList<>();
    ArrayList<String> bestWord = new ArrayList<>();
    ArrayList<Integer> playerScore = new ArrayList<>();
    String sendingWord = "";
    String anotherSendingWord = "";

    /**
     * return best move coordinates
     * @return
     */
    public ArrayList<Point> getBestCoordinate(){
        return bestCoordinate;
    }

    /**
     * return best move score
     * @return
     */
    public ArrayList<Integer> getBestScore(){
        return score;
    }

    /**
     * return best word
     * @return
     */
    public ArrayList<String> getBestWord(){
        return bestWord;
    }

    /**
     * calculate score from top to bottom if move was vertical
     * @param topToBottom
     * @param board
     * @param letters
     * @param word
     */
    public void calculateTopBottomScore(ArrayList<Point> topToBottom,
                                        String[][] board, Letters letters, String word) {
        //System.out.println("sdkjshd");
        //System.out.println("Word: "+word);
        if(word.length() == 7){
            megaScore += 50;
        }
        int topBottomScore = 0;
        int twoMult = 0;
        int threeMult = 0;
        int fourMult = 0;
        //System.out.println("TopToBottom: "+topToBottom);
        int counter = topToBottom.get(1).x - topToBottom.get(0).x;
        //System.out.println("Counter: "+counter);
        int i = 0;
        int j = 0;
        int startingPointX = topToBottom.get(0).x;
        int startingPointY = topToBottom.get(0).y;
        while(i < counter) {
            char letter = ' ';
            if(j < word.length()) {
                letter = word.charAt(j);
                sendingWord += word.charAt(j);
            }
          //  System.out.println("X: "+startingPointX);
            //System.out.println("Y: "+startingPointY);
            if(board[startingPointX][startingPointY].equals("..") ||
                    board[startingPointX][startingPointY].equals("3.") ||
                    board[startingPointX][startingPointY].equals("2.")
                    || board[startingPointX][startingPointY].equals(".3") ||
                    board[startingPointX][startingPointY].equals(".2")
                    || board[startingPointX][startingPointY].equals(".4") ||
                    board[startingPointX][startingPointY].equals("4.")){
                //System.out.println("free to put here");
                if(letter != ' ') {
                    calculateLeftAndRight(startingPointX, startingPointY,
                            board, letters, letter);
                    if (board[startingPointX][startingPointY].equals(".3")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        topBottomScore = topBottomScore +
                                (3 * letters.TileScoresRecorder(" " + letter));
                    } else if (board[startingPointX][startingPointY].equals(".2")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        topBottomScore = topBottomScore +
                                (2 * letters.TileScoresRecorder(" " + letter));
                    }
                    else if(board[startingPointX][startingPointY].equals(".4")){
                        topBottomScore = topBottomScore +
                                (4 * letters.TileScoresRecorder(" " + letter));
                    }
                    else {
                        topBottomScore = topBottomScore +
                                (letters.TileScoresRecorder(" " + letter));
                        //System.out.println("Letter: " + " " + letter);
                    }

                    if (board[startingPointX][startingPointY].equals("3.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        threeMult++;
                    }
                    if (board[startingPointX][startingPointY].equals("2.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        twoMult++;
                    }
                    if (board[startingPointX][startingPointY].equals("4.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        fourMult++;
                    }
                    j++;
                }
            }
            else{
                topBottomScore = topBottomScore +
                        (letters.TileScoresRecorder(board[startingPointX][startingPointY]));
                sendingWord += board[startingPointX][startingPointY].charAt(1);
              //  System.out.println("TopBottomScore: "+topBottomScore);
                //System.out.println("piece already here");
                j++;
            }
            startingPointX++;
            i++;
        }

        int cal = 0;
        while(cal < threeMult){
            topBottomScore *= 3;
            cal++;
        }
        //System.out.println("twomult: "+twoMult);
        //System.out.println("LeftRightScore: "+topBottomScore);
        cal = 0;
        while(cal < twoMult){
            topBottomScore = topBottomScore * 2;
            //System.out.println("LeftRightScore: "+topBottomScore);
            cal++;
        }

        cal = 0;
        while(cal < fourMult){
            topBottomScore = topBottomScore * 4;
            //System.out.println("LeftRightScore: "+topBottomScore);
            cal++;
        }
        megaScore += topBottomScore;

        if(score.size() == 1 && bestCoordinate.size() == 2){
            if(megaScore > score.get(0)){
                bestWord.clear();
                bestCoordinate.clear();
                score.clear();
                anotherSendingWord = sendingWord;
                sendingWord = "";
                bestWord.add(word);
                bestCoordinate.add(topToBottom.get(0));
                bestCoordinate.add(topToBottom.get(1));
                score.add(megaScore);
            }
        }
        else{
            bestCoordinate.add(topToBottom.get(0));
            bestCoordinate.add(topToBottom.get(1));
            score.add(megaScore);
            bestWord.add(word);
        }

        if(playerScore.size() < 1){
            playerScore.add(megaScore);
        }
        //System.out.println("MegaScore: "+megaScore);
        sendingWord = "";
        megaScore = 0;
    }

    public String getSendingWord(){
        return anotherSendingWord;
    }

    /**
     * calculate score from left to right if move was horizontal
     * @param leftToRight
     * @param board
     * @param letters
     * @param word
     */
    public void calculateLeftRightScore(ArrayList<Point> leftToRight,
                                        String[][] board, Letters letters, String word){
        //System.out.println("sdkjsasdassadsdahd");
        /*for(int g = 0; g < 15; g++){
            for(int h = 0; h < 15; h++){
                System.out.print(board[g][h] + " ");
            }
            System.out.println();
        }*/
        if(word.length() == 7){
            megaScore += 50;
        }
        //System.out.println("Word: "+word);
        int leftRightScore = 0;
        int twoMult = 0;
        int threeMult = 0;
        int fourMult = 0;
        //System.out.println("LeftToRight: "+leftToRight);
        int counter = leftToRight.get(1).y - leftToRight.get(0).y;
        //System.out.println("Counter: "+counter);
        int i = 0;
        int j = 0;
        int startingPointX = leftToRight.get(0).x;
        int startingPointY = leftToRight.get(0).y;
        while(i < counter){
            char letter = ' ';
            if(j < word.length()) {
                letter = word.charAt(j);
                sendingWord += word.charAt(j);
                //      System.out.println("letter: "+letter);
            }
            //System.out.println("X: "+startingPointX);
            //System.out.println("Y: "+startingPointY);
            if(board[startingPointX][startingPointY].equals("..") ||
                    board[startingPointX][startingPointY].equals("3.") ||
                    board[startingPointX][startingPointY].equals("2.")
                || board[startingPointX][startingPointY].equals(".3") ||
                    board[startingPointX][startingPointY].equals(".2")
                    || board[startingPointX][startingPointY].equals(".4") ||
                    board[startingPointX][startingPointY].equals("4.")){
                //System.out.println("free to put here");
                if(letter != ' ') {
                    calculateUpAndDown(startingPointX, startingPointY, board, letters, letter);
                    if (board[startingPointX][startingPointY].equals(".3")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        leftRightScore = leftRightScore +
                                (3 * letters.TileScoresRecorder(" " + letter));
                    }
                    else if (board[startingPointX][startingPointY].equals(".2")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        leftRightScore = leftRightScore +
                                (2 * letters.TileScoresRecorder(" " + letter));
                    }
                    else if(board[startingPointX][startingPointY].equals(".4")){
                        leftRightScore = leftRightScore +
                                (4 * letters.TileScoresRecorder(" " + letter));
                    }
                    else {
                        leftRightScore = leftRightScore +
                                (letters.TileScoresRecorder(" " + letter));
                        //System.out.println("Letter: " + " " + letter);
                    }

                    if (board[startingPointX][startingPointY].equals("3.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        threeMult++;
                    }
                    if (board[startingPointX][startingPointY].equals("2.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        twoMult++;
                    }
                    if (board[startingPointX][startingPointY].equals("4.")) {
                        //System.out.println("Noard at this point: " + board[startingPointX][startingPointY]);
                        fourMult++;
                    }
                    j++;
                }
            }
            else{
                leftRightScore = leftRightScore +
                        (letters.TileScoresRecorder(board[startingPointX][startingPointY]));
                sendingWord += board[startingPointX][startingPointY].charAt(1);
              //  System.out.println("LeftRightScore: "+leftRightScore);
                //System.out.println("piece already here");
                j++;
            }
            startingPointY++;
            i++;
        }

        int cal = 0;
        while(cal < threeMult){
            leftRightScore *= 3;
            cal++;
        }
        //System.out.println("twomult: "+twoMult);
        //System.out.println("LeftRightScore: "+leftRightScore);
        cal = 0;
        while(cal < twoMult){
            leftRightScore = leftRightScore * 2;
            //System.out.println("LeftRightScore: "+leftRightScore);
            cal++;
        }
        cal = 0;
        while(cal < fourMult){
            leftRightScore = leftRightScore * 4;
            //System.out.println("LeftRightScore: "+leftRightScore);
            cal++;
        }

        if(word.length() > 1) {
            megaScore += leftRightScore;
        }
        if(score.size() == 1 && bestCoordinate.size() == 2){
            if(megaScore > score.get(0)){
                bestWord.clear();
                bestCoordinate.clear();
                score.clear();
                anotherSendingWord = sendingWord;
                sendingWord = "";
                bestCoordinate.add(leftToRight.get(0));
                bestCoordinate.add(leftToRight.get(1));
                score.add(megaScore);
                bestWord.add(word);
            }
        }
        else{
            bestCoordinate.add(leftToRight.get(0));
            bestCoordinate.add(leftToRight.get(1));
            score.add(megaScore);
            bestWord.add(word);
        }

        if(playerScore.size() < 1){
            playerScore.add(megaScore);
        }

        //System.out.println("MegaScore: "+megaScore);
        sendingWord = "";
        megaScore = 0;
    }

    public int getMegaScore(){
        return playerScore.get(0);
    }


    /**
     * method utilized by calculateTopBottomScore.
     * Basically, calculate score if vertical move touched
     * adjacent squares and made a new string
     * @param startingPointX
     * @param startingPointY
     * @param board
     * @param letters
     * @param letter
     */
    public void calculateLeftAndRight(int startingPointX, int startingPointY,
                                      String[][] board, Letters letters, char letter) {
        //System.out.println("rdgrfgfdsgdfgdfg");
        int score = 0;
        boolean leftRight = false;

        int temp = startingPointY;
        startingPointY--;
        //System.out.println("StartingpointX: "+ startingPointX);
        //System.out.println("StartingpointY: "+ startingPointY);
        //System.out.println("board: " +board[startingPointX][startingPointY]);

        while(startingPointY > -1 && !board[startingPointX][startingPointY].equals("..") &&
                !board[startingPointX][startingPointY].equals("3.") &&
                !board[startingPointX][startingPointY].equals("2.")
                && !board[startingPointX][startingPointY].equals(".3") &&
                !board[startingPointX][startingPointY].equals(".2")
                && !board[startingPointX][startingPointY].equals(".4") &&
                !board[startingPointX][startingPointY].equals("4.")){
            //System.out.println("StartingPointY: "+startingPointY);
            score += letters.TileScoresRecorder(board[startingPointX][startingPointY]);
            startingPointY--;
            leftRight = true;
        }

        startingPointY = temp + 1;
        while(startingPointY < board.length && !board[startingPointX][startingPointY].equals("..")
                && !board[startingPointX][startingPointY].equals("3.")
                && !board[startingPointX][startingPointY].equals("2.")
                && !board[startingPointX][startingPointY].equals(".3") &&
                !board[startingPointX][startingPointY].equals(".2")
                && !board[startingPointX][startingPointY].equals(".4") &&
                !board[startingPointX][startingPointY].equals("4.")){
           // System.out.println("StartingPointY: "+startingPointY);
            score += letters.TileScoresRecorder(board[startingPointX][startingPointY]);
            startingPointY++;
            leftRight = true;
        }

        if (leftRight == true) {
            if (board[startingPointX][temp].equals(".3")) {
                //System.out.println("Noard at this point: " + board[startingPointX][temp]);
                score = score + (3 * letters.TileScoresRecorder(" " + letter));
            } else if (board[startingPointX][temp].equals(".2")) {
                //System.out.println("Noard at this point: " + board[startingPointX][temp]);
                score = score + (2 * letters.TileScoresRecorder(" " + letter));
            }
            else if(board[startingPointX][temp].equals(".4")){
                score = score + (4 * letters.TileScoresRecorder(" " + letter));
            }
            else {
                score = score + (letters.TileScoresRecorder(" " + letter));
                //System.out.println("Letter: " + " " + letter);
            }
        }

        if(board[startingPointX][temp].equals("3.")){
            //System.out.println("Noard at this point: "+board[startingPointX][temp]);
            score *= 3;
        }
        if(board[startingPointX][temp].equals("2.")){
            //System.out.println("Noard at this point: "+board[startingPointX][temp]);
            score *= 2;
        }
        if(board[startingPointX][temp].equals("4.")){
            //System.out.println("Noard at this point: "+board[startingPointX][temp]);
            score *= 4;
        }

        //System.out.println("Score: "+score);
        megaScore += score;
        //System.out.println();
    }

    /**
     * method utilized by calculateLeftRightScore.
     * Basically, calculate score if horizontal move touched
     * adjacent squares and made a new string
     * @param startingPointX
     * @param startingPointY
     * @param board
     * @param letters
     * @param letter
     */
    public void calculateUpAndDown(int startingPointX, int startingPointY,
                                   String[][] board, Letters letters, char letter) {
        //System.out.println("sdkjsadasdsadsadshd");
        int score = 0;
        boolean upDown = false;

        int temp = startingPointX;
        startingPointX--;
       //System.out.println("StartingpointX: "+ startingPointX);
        //System.out.println("StartingpointY: "+ startingPointY);
        //System.out.println("board: " +board[startingPointX][startingPointY]);

        while(startingPointX > -1 && !board[startingPointX][startingPointY].equals("..") &&
                !board[startingPointX][startingPointY].equals("3.") &&
                !board[startingPointX][startingPointY].equals("2.")
                && !board[startingPointX][startingPointY].equals(".3") &&
                !board[startingPointX][startingPointY].equals(".2")
                && !board[startingPointX][startingPointY].equals(".4") &&
                !board[startingPointX][startingPointY].equals("4.")){
          //  System.out.println("StartingPointX: "+startingPointX);
            score += letters.TileScoresRecorder(board[startingPointX][startingPointY]);
            startingPointX--;
            upDown = true;
        }

        startingPointX = temp + 1;
        while(startingPointX < board.length && !board[startingPointX][startingPointY].equals("..") &&
                !board[startingPointX][startingPointY].equals("3.") &&
                !board[startingPointX][startingPointY].equals("2.")
                && !board[startingPointX][startingPointY].equals(".3") &&
                !board[startingPointX][startingPointY].equals(".2")
                && !board[startingPointX][startingPointY].equals(".4") &&
                !board[startingPointX][startingPointY].equals("4.")){
            //System.out.println("StartingPointX: "+startingPointX);
            score += letters.TileScoresRecorder(board[startingPointX][startingPointY]);
            startingPointX++;
            upDown = true;
        }


        if (upDown == true) {
            if (board[temp][startingPointY].equals(".3")) {
               // System.out.println("Noard at this point: " + board[temp][startingPointY]);
                score = score + (3 * letters.TileScoresRecorder(" " + letter));
            } else if (board[temp][startingPointY].equals(".2")) {
              //  System.out.println("Noard at this point: " + board[temp][startingPointY]);
                score = score + (2 * letters.TileScoresRecorder(" " + letter));
            }
            else if(board[temp][startingPointY].equals(".4")){
                score = score + (4 * letters.TileScoresRecorder(" " + letter));
            }
            else {
                score = score + (letters.TileScoresRecorder(" " + letter));
                //System.out.println("Letter: " + " " + letter);
            }
        }

        if(board[temp][startingPointY].equals("3.")){
            //System.out.println("Noard at this point: "+board[temp][startingPointY]);
            score *= 3;
        }
        if(board[temp][startingPointY].equals("2.")){
            //System.out.println("Noard at this point: "+board[temp][startingPointY]);
            score *= 2;
        }
        if(board[temp][startingPointY].equals("4.")){
            //System.out.println("Noard at this point: "+board[temp][startingPointY]);
            score *= 4;
        }
        //System.out.println("Score: "+score);
        megaScore += score;
        //System.out.println();
    }

    public void removePlayerScore() {
        playerScore.clear();
    }

    /**
     * utilized if a player made an in-between move horizontally
     * @param playerPlayCoordinates
     * @param board
     * @param letters
     */
    public void calculateAltLRScore(ArrayList<Point> playerPlayCoordinates,
                                    Board board, Letters letters) {
        if(playerPlayCoordinates.size() == 7){
            megaScore += 50;
        }
        int ax = playerPlayCoordinates.get(0).x;
        int ay = playerPlayCoordinates.get(0).y;
        int score = 0;
        ay--;
        while(ay > -1 && !board.sendBoard()[ax][ay].equals("..") &&
                !board.sendBoard()[ax][ay].equals("2.") &&
                !board.sendBoard()[ax][ay].equals("3.") &&
                !board.sendBoard()[ax][ay].equals(".2") &&
                !board.sendBoard()[ax][ay].equals(".3")){
            score += letters.TileScoresRecorder(board.sendBoard()[ax][ay]);
            ay--;
        }
        //System.out.println("SOCREEEEE: "+score);
        int n = 0;
        String recorder;
        int twoMult = 0;
        int threeMult = 0;
        while(n < playerPlayCoordinates.size()){
            int dankscore = 0;
            dankscore = letters.TileScoresRecorder(board.getFakeBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y]);
            recorder = board.sendBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y];
            if(recorder.equals(".2")){
                dankscore *= 2;
            }
            if(recorder.equals(".3")){
                dankscore *= 3;
            }
            score += dankscore;
            if(recorder.equals("3.")){
                threeMult++;
            }
            if(recorder.equals("2.")){
                twoMult++;
            }
            ax = playerPlayCoordinates.get(n).x;
            //System.out.println("ax: "+ax);
            ay = playerPlayCoordinates.get(n).y + 1;
            //System.out.println("ay: "+ay);
            //System.out.println("Board: "+board.sendBoard()[ax][ay]);
            while(!board.sendBoard()[ax][ay].equals("..") &&
                    !board.sendBoard()[ax][ay].equals("2.") &&
                    !board.sendBoard()[ax][ay].equals("3.") &&
                    !board.sendBoard()[ax][ay].equals(".2") &&
                    !board.sendBoard()[ax][ay].equals(".3")) {
                score += letters.TileScoresRecorder(board.sendBoard()[ax][ay]);
                ay++;
            }
            n++;
        }
        int counter = 0;
        while(counter < twoMult){
            score *= 2;
            counter++;
        }
        counter = 0;
        while(counter < threeMult){
            score *= 3;
            counter++;
        }
        //System.out.println("SOCREEEEE: "+score);
        int nr = 0;
        String upCheck = "";
        int anotherTwoMult = 0;
        int anotherThreeMult = 0;
        while(nr < playerPlayCoordinates.size()) {
            int anScore = 0;
            int bb = playerPlayCoordinates.get(nr).x;
            int zz = playerPlayCoordinates.get(nr).y;
            while (bb > 0 && !board.getFakeBoard()[bb - 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".3")) {
                upCheck = board.getFakeBoard()[bb - 1][zz] + upCheck;
                anScore += letters.TileScoresRecorder(board.getFakeBoard()[bb - 1][zz]);
                //System.out.println("AxZXnscore: "+anScore);
                bb--;
            }
            //System.out.println("BB: " + bb);
            bb = playerPlayCoordinates.get(nr).x;
            upCheck += board.getFakeBoard()[bb][zz];
            int panScore = letters.TileScoresRecorder(board.getFakeBoard()[bb][zz]);
            if(board.sendBoard()[bb][zz].equals(".2")){
                panScore *= 2;
            }
            if(board.sendBoard()[bb][zz].equals(".3")){
                panScore *= 3;
            }
            if(board.sendBoard()[bb][zz].equals("3.")){
                anotherThreeMult++;
            }
            if(board.sendBoard()[bb][zz].equals("2.")){
                anotherTwoMult++;
            }
            anScore += panScore;
            //System.out.println("Ansxzcxcore: "+anScore);
            while (bb < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb + 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb + 1][zz];
                anScore += letters.TileScoresRecorder(board.getFakeBoard()[bb + 1][zz]);
                bb++;
            }
            upCheck = upCheck.replaceAll("\\s", "");
            //System.out.println("Upcheck: "+upCheck);
            if(upCheck.length() > 1) {
                counter = 0;
                while(counter < anotherTwoMult){
                    anScore *= 2;
                    counter++;
                }
                counter = 0;
                while(counter < anotherThreeMult){
                    anScore *= 3;
                    counter++;
                }
                score += anScore;
                //System.out.println("scxczore: "+score);
            }
            upCheck = "";
            nr++;
        }
        //System.out.println("SOCREEEEE: "+score);
        megaScore += score;
        if(playerScore.size() < 1){
            playerScore.add(megaScore);
        }
        //System.out.println("Pscore:" +playerScore);
        //System.out.println("MegaScore: "+megaScore);
        megaScore = 0;
    }

    /**
     * utilized if a player made an in-between move vertically
     * @param playerPlayCoordinates
     * @param board
     * @param letters
     */
    public void calculateAltTBcore(ArrayList<Point> playerPlayCoordinates,
                                   Board board, Letters letters) {
        if(playerPlayCoordinates.size() == 7){
            megaScore += 50;
        }
        int ax = playerPlayCoordinates.get(0).x;
        int ay = playerPlayCoordinates.get(0).y;
        int score = 0;
        ax--;
        while(ax > -1 && !board.sendBoard()[ax][ay].equals("..") &&
                !board.sendBoard()[ax][ay].equals("2.") &&
                !board.sendBoard()[ax][ay].equals("3.") &&
                !board.sendBoard()[ax][ay].equals(".2") &&
                !board.sendBoard()[ax][ay].equals(".3")){
            score += letters.TileScoresRecorder(board.sendBoard()[ax][ay]);
            ax--;
        }
        //System.out.println("SOCREEEEE: "+score);
        int n = 0;
        String recorder;
        int twoMult = 0;
        int threeMult = 0;
        while(n < playerPlayCoordinates.size()){
            int dankscore = 0;
            dankscore = letters.TileScoresRecorder(board.getFakeBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y]);
            recorder = board.sendBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y];
            if(recorder.equals(".2")){
                dankscore *= 2;
            }
            if(recorder.equals(".3")){
                dankscore *= 3;
            }
            score += dankscore;
            if(recorder.equals("3.")){
                threeMult++;
            }
            if(recorder.equals("2.")){
                twoMult++;
            }
            ax = playerPlayCoordinates.get(n).x + 1;
            //System.out.println("ax: "+ax);
            ay = playerPlayCoordinates.get(n).y;
            //System.out.println("ay: "+ay);
            //System.out.println("Board: "+board.sendBoard()[ax][ay]);
            while(!board.sendBoard()[ax][ay].equals("..") &&
                    !board.sendBoard()[ax][ay].equals("2.") &&
                    !board.sendBoard()[ax][ay].equals("3.") &&
                    !board.sendBoard()[ax][ay].equals(".2") &&
                    !board.sendBoard()[ax][ay].equals(".3")) {
                score += letters.TileScoresRecorder(board.sendBoard()[ax][ay]);
                ax++;
            }
            n++;
        }
        int counter = 0;
        while(counter < twoMult){
            score *= 2;
            counter++;
        }
        counter = 0;
        while(counter < threeMult){
            score *= 3;
            counter++;
        }
        //System.out.println("SOCREEEEE: "+score);
        int nr = 0;
        String upCheck = "";
        int anotherTwoMult = 0;
        int anotherThreeMult = 0;
        while(nr < playerPlayCoordinates.size()) {
            int anScore = 0;
            int bb = playerPlayCoordinates.get(nr).x;
            int zz = playerPlayCoordinates.get(nr).y;
            while (zz > -1 && !board.getFakeBoard()[bb][zz - 1].equals("..") &&
                    !board.getFakeBoard()[bb][zz - 1].equals("3.") &&
                    !board.getFakeBoard()[bb][zz - 1].equals("2.") &&
                    !board.getFakeBoard()[bb][zz - 1].equals(".2") &&
                    !board.getFakeBoard()[bb][zz - 1].equals(".3")) {
                upCheck = board.getFakeBoard()[bb][zz - 1] + upCheck;
                anScore += letters.TileScoresRecorder(board.getFakeBoard()[bb][zz - 1]);
                //System.out.println("AxZXnscore: "+anScore);
                zz--;
            }
            //System.out.println("ZZ: " + zz);
            zz = playerPlayCoordinates.get(nr).y;
            upCheck += board.getFakeBoard()[bb][zz];
            int panScore = letters.TileScoresRecorder(board.getFakeBoard()[bb][zz]);
            if(board.sendBoard()[bb][zz].equals(".2")){
                panScore *= 2;
            }
            if(board.sendBoard()[bb][zz].equals(".3")){
                panScore *= 3;
            }
            if(board.sendBoard()[bb][zz].equals("3.")){
                anotherThreeMult++;
            }
            if(board.sendBoard()[bb][zz].equals("2.")){
                anotherTwoMult++;
            }
            anScore += panScore;
            //System.out.println("Ansxzcxcore: "+anScore);
            while (zz < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb][zz + 1].equals("..") &&
                    !board.getFakeBoard()[bb][zz + 1].equals("3.") &&
                    !board.getFakeBoard()[bb][zz + 1].equals("2.") &&
                    !board.getFakeBoard()[bb][zz + 1].equals(".2") &&
                    !board.getFakeBoard()[bb][zz + 1].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb][zz + 1];
                anScore += letters.TileScoresRecorder(board.getFakeBoard()[bb][zz + 1]);
                zz++;
            }
            upCheck = upCheck.replaceAll("\\s", "");
            //System.out.println("Upcheck: "+upCheck);
            if(upCheck.length() > 1) {
                counter = 0;
                while(counter < anotherTwoMult){
                    anScore *= 2;
                    counter++;
                }
                counter = 0;
                while(counter < anotherThreeMult){
                    anScore *= 3;
                    counter++;
                }
                score += anScore;
                //System.out.println("scxczore: "+score);
            }
            upCheck = "";
            nr++;
        }
        //System.out.println("SOCREEEEE: "+score);
        megaScore += score;
        if(playerScore.size() < 1){
            //System.out.println("MAX PAYNE");
            playerScore.add(megaScore);
        }
        //System.out.println("Pscore:" +playerScore);
        //System.out.println("MegaScore: "+megaScore);
        megaScore = 0;
    }
}
