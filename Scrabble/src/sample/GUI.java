package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class GUI {

    boolean clickBoard = false;
    boolean anotherCB = false;
    AnimationTimer animationTimer;
    int letterVal;
    String chosenString = "";
    String usedLetters = "";
    int X;
    int Y;
    ArrayList<Point> playerPlayCoordinates = new ArrayList<>();
    String finTray;
    boolean trayClicking = false;
    String replacingLetters = "";
    int counterForWildcard = 65;
    String checkWord = "";
    ArrayList<Point> newOne = new ArrayList<>();
    int playerScore = 0;
    boolean initialMove = true;
    ArrayList<Point> steadyPlace = new ArrayList<>();
    String wildCardLetter = "";
    int wildCardCounter = 0;
    int anotherC = 0;


    /**
     * start GUI
     * @param primaryStage
     * @param board
     * @param playerTray
     * @param letters
     * @param parent
     * @param computerTray
     * @param score
     */
    public GUI(Stage primaryStage, Board board, PlayerTray playerTray, Letters letters,
               TrieNode parent, ComputerTray computerTray, Score score){
        BorderPane bp = new BorderPane();
        Canvas canvas = new Canvas(825, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D ();
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0,0, 825, 1000);
        bp.setCenter(canvas);
        designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
        startAnimation(canvas, gc, playerTray, board, letters, parent, computerTray, score);
        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * start animation
     * @param canvas
     * @param gc
     * @param playerTray
     * @param board
     * @param letters
     * @param parent
     * @param computerTray
     * @param score
     */
    public void startAnimation(Canvas canvas, GraphicsContext gc, PlayerTray playerTray, Board board,
                               Letters letters, TrieNode parent, ComputerTray computerTray, Score score) {
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                canvas.setOnMousePressed(e ->{
                    if(e.getButton() == MouseButton.PRIMARY){
                        evaluateCoordinates(e.getX(), e.getY(), board, gc, playerTray,
                                letters, parent, computerTray, score);
                    }
                    else if(e.getButton() == MouseButton.SECONDARY){
                        returnPieceBack(e.getX(), e.getY(), board, gc, playerTray,
                                letters, computerTray);
                    }
                });
            }
        };
        animationTimer.start();
    }

    /**
     * used when right click returns tile back
     * @param x
     * @param y
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param computerTray
     */
    public void returnPieceBack(double x, double y, Board board, GraphicsContext gc,
                                PlayerTray playerTray,
                                Letters letters, ComputerTray computerTray) {
        if(x >= 0 && y >= 0 && x <= 825 && y <= 825){
            int coX = (int) (y/55);
            int coY = (int) (x/55);
            if(!board.getFakeBoard()[coX][coY].equals("..") &&
                    !board.getFakeBoard()[coX][coY].equals("2.") &&
                    !board.getFakeBoard()[coX][coY].equals("3.") &&
                    !board.getFakeBoard()[coX][coY].equals(".2") &&
                    !board.getFakeBoard()[coX][coY].equals(".3") &&
                    (board.sendBoard()[coX][coY].equals("..") || board.sendBoard()[coX][coY].equals("2.") ||
                            board.sendBoard()[coX][coY].equals("3.") || board.sendBoard()[coX][coY].equals(".2")
                            || board.sendBoard()[coX][coY].equals(".3")))
            {
                String former = board.sendBoard()[coX][coY];
                //System.out.println("MAN-deep");
                String playerHas = playerTray.getTray();
                //System.out.println(playerHas);
                char pressed;
                if(Character.isUpperCase(board.getFakeBoard()[coX][coY].charAt(1))){
                    pressed = ' ';
                }
                else{
                    pressed = board.getFakeBoard()[coX][coY].charAt(1);
                }
                board.getFakeBoard()[coX][coY] = former;
                //System.out.println(pressed);
                playerTray.addStringChar(pressed);
                designFakeScrabbleBoard(board, gc, playerTray, letters, computerTray);
            }
        }
    }

    /**
     * evaluate left mouse clicked coordinates depending upon where they were clicked
     * @param x
     * @param y
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param parent
     * @param computerTray
     * @param score
     */
    public void evaluateCoordinates(double x, double y, Board board, GraphicsContext gc,
                                    PlayerTray playerTray,
                                    Letters letters, TrieNode parent, ComputerTray computerTray, Score score) {
        if(x >= 220 && x <= 605 && y >= 886 && y <= 941){
            setAnotherCBFalse();
            String playerHas = playerTray.getTray();
            gc.setFill(Color.DARKMAGENTA);
            gc.setLineWidth(1);
            if(x + 55 > 605){
                gc.strokeRect(550, 886, 55,55);
                chosenString = " " + playerHas.substring(6, 7);
                replacingLetters += playerHas.substring(6, 7);
            }
            else if(x + 55 > 550){
                gc.strokeRect(495, 886, 55,55);
                chosenString = " " + playerHas.substring(5, 6);
                replacingLetters += playerHas.substring(5,6);
            }
            else if(x + 55 > 495){
                gc.strokeRect(440, 886, 55,55);
                chosenString = " " + playerHas.substring(4, 5);
                replacingLetters += playerHas.substring(4,5);
            }
            else if(x + 55 > 440){
                gc.strokeRect(385, 886, 55,55);
                chosenString = " " + playerHas.substring(3,4);
                replacingLetters += playerHas.substring(3,4);
            }
            else if(x + 55 > 385){
                gc.strokeRect(330, 886, 55,55);
                chosenString = " " + playerHas.substring(2,3);
                replacingLetters += playerHas.substring(2,3);
            }
            else if(x + 55 > 330){
                gc.strokeRect(275, 886, 55,55);
                chosenString = " " + playerHas.substring(1,2);
                replacingLetters += playerHas.substring(1,2);
            }
            else{
                gc.strokeRect(220, 886, 55,55);
                chosenString = " " + playerHas.substring(0,1);
                replacingLetters += playerHas.substring(0, 1);
            }
            if(trayClicking == false) {
                setClickBoardTrue();
            }
            else{
                //System.out.println("ReplacableLetters: "+replacingLetters);
            }
        }
        else if(clickBoard == true && x >= 0 && y >= 0 && x <= 825 && y <= 825){
            //System.out.println("NOW I DO WHAT I WANT");
            int coX = (int) (y/55);
            int coY = (int) (x/55);
            //System.out.println("cox:" +coX);
           // System.out.println("coy:" +coY);
            if(board.getFakeBoard()[coX][coY].equals("..") ||
                    board.getFakeBoard()[coX][coY].equals("2.") ||
                    board.getFakeBoard()[coX][coY].equals("3.")
                    || board.getFakeBoard()[coX][coY].equals(".2") ||
                    board.getFakeBoard()[coX][coY].equals(".3")){
                if(anotherCB == true){
                    board.getFakeBoard()[X][Y] = usedLetters;
                }
                else{
                    playerTray.removeStringChar(chosenString);
                }
                X = coX;
                Y = coY;
                usedLetters = board.getFakeBoard()[coX][coY];
                //System.out.println("UsedLetters: "+chosenString);
                if(chosenString.equals("  ")){
                    board.getFakeBoard()[coX][coY] = " " + (char) counterForWildcard;
                }
                else {
                    board.getFakeBoard()[coX][coY] = chosenString;
                }
                setAnotherCBTrue();
                designFakeScrabbleBoard(board, gc, playerTray, letters, computerTray);
            }
        }
        else if(x >= 670 && x <= 770 && y >= 886 && y <= 941){
            //System.out.println("DIV 1");
            for(int i = 0; i < board.sendBoard().length; i++){
                for(int j = 0; j < board.sendBoard().length; j++){
                    if(!board.sendBoard()[i][j].equals(board.getFakeBoard()[i][j])){
                        playerPlayCoordinates.add(new Point(i, j));
                    }
                }
            }
            //System.out.println(playerPlayCoordinates);
            boolean checkHorizontal = false;
            boolean checkVertical = false;
            int n = 0;
            int counterX = 0;
            int counterY = 0;
            while(n < playerPlayCoordinates.size() - 1){
                int cooorX = playerPlayCoordinates.get(n).x;
                int anotherCooorX = playerPlayCoordinates.get(n+1).x;
                if(cooorX == anotherCooorX){
                    //System.out.println("check horizontally");
                    if(playerPlayCoordinates.get(n+1).y == playerPlayCoordinates.get(n).y + 1 &&
                            playerPlayCoordinates.get(n).x == playerPlayCoordinates.get(n+1).x){
                        counterX++;
                    }
                }
                else{
                    //System.out.println("check vertically");
                    if(playerPlayCoordinates.get(n+1).x == playerPlayCoordinates.get(n).x + 1 &&
                            playerPlayCoordinates.get(n).y == playerPlayCoordinates.get(n+1).y){
                        counterY++;
                    }
                }
                n++;
            }

            if(counterX == playerPlayCoordinates.size() - 1){
                checkHorizontal = true;
            }
            else if(counterY == playerPlayCoordinates.size() - 1){
                checkVertical = true;
            }


            Boolean game = false;
            computerTray.findCoordinates(board.sendBoard());
            int bbx = 0;
            while(bbx < computerTray.getViableCoord().size()){
                int bby = 0;
                while(bby < playerPlayCoordinates.size()){
                    if(playerPlayCoordinates.get(bby).equals(computerTray.getViableCoord().get(bbx))){
                        game = true;
                    }
                    bby++;
                }
                bbx++;
            }
            if(game == false && initialMove == true &&
                    playerPlayCoordinates.contains(new Point(7, 7))
                    && (checkHorizontal == true || checkVertical == true) &&
                    playerPlayCoordinates.size() > 1){
                //System.out.print("Intent");
                game = true;
            }

            computerTray.getViableCoord().clear();

            if(game == true) {
                //System.out.println(checkHorizontal);
                //System.out.println(checkVertical);

                if (counterX == playerPlayCoordinates.size() - 1 || counterY == playerPlayCoordinates.size() - 1) {
                    if(playerPlayCoordinates.size() == 1){
                        //System.out.println("monkey");
                        if(checkHorizontal(board, parent) == false) {
                            if(checkVertical(board, parent) == false){
                                int i = 0;
                                while (i < playerPlayCoordinates.size()) {
                                    playerTray.addStringChar(board.getFakeBoard()
                                            [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                                    i++;
                                }
                                fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                                //System.out.println("sadsdreturn pieces back");
                            }
                            else{
                               // System.out.println("MAMAM MADE IT");
                               // System.out.println(playerTray.getTray());
                               // System.out.println("Checkword: " + checkWord);
                                score.calculateAltTBcore(playerPlayCoordinates, board, letters);
                                playerScore += score.getMegaScore();
                                wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
                               // System.out.println("Wildcardletter: "+wildCardLetter);
                               // System.out.print("PlayerScore: " + playerScore);
                                playerTray.addTray(playerTray.getTray());
                                realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                            }
                        }
                        else{
                            //System.out.println("MAMAM MADE IT");
                            System.out.println(playerTray.getTray());
                            //System.out.println("Checkword: " + checkWord);
                            score.calculateAltLRScore(playerPlayCoordinates, board, letters);
                            playerScore += score.getMegaScore();
                            wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
                           // System.out.println("Wildcardletter: "+wildCardLetter);
                           // System.out.print("PlayerScore: " + playerScore);
                            playerTray.addTray(playerTray.getTray());
                            realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                        }
                    }
                    else if (checkHorizontal == true) {
                        if (checkHorizontal(board, parent) == false) {
                            int i = 0;
                            while (i < playerPlayCoordinates.size()) {
                                playerTray.addStringChar(board.getFakeBoard()
                                        [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                                i++;
                            }
                            fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                            //System.out.println("return pieces back");
                        }
                        else {
                            //System.out.println("MAMAM MADE IT");
                            //System.out.println(playerTray.getTray());
                            //System.out.println("Checkword: " + checkWord);
                            ArrayList<Point> anotherNewOne = new ArrayList<>();
                            //System.out.println("Newone: " + newOne);
                            anotherNewOne.add(newOne.get(0));
                            anotherNewOne.add(new Point(newOne.get(newOne.size() - 1).x,
                                    newOne.get(newOne.size() - 1).y + 1));
                            score.calculateLeftRightScore(anotherNewOne, board.sendBoard(), letters, checkWord);
                            playerScore += score.getMegaScore();
                            wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
                           // System.out.println("Wildcardletter: "+wildCardLetter);
                            //System.out.print("PlayerScore: " + playerScore);
                            playerTray.addTray(playerTray.getTray());
                            realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                            anotherNewOne.clear();
                        }
                    }
                    else if (checkVertical == true) {
                        if (checkVertical(board, parent) == false) {
                            int i = 0;
                            while (i < playerPlayCoordinates.size()) {
                                playerTray.addStringChar(board.getFakeBoard()[playerPlayCoordinates.get(i).x]
                                        [playerPlayCoordinates.get(i).y].charAt(1));
                                i++;
                            }
                            fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                            //System.out.println("return pieces back");
                        }
                        else {
                            //System.out.println("MAMAM MADE IT");
                            //System.out.println(playerTray.getTray());
                            //System.out.println("Checkword: " + checkWord);
                            ArrayList<Point> anotherNewOne = new ArrayList<>();
                            anotherNewOne.add(newOne.get(0));
                            anotherNewOne.add(new Point(newOne.get(newOne.size() - 1).x + 1,
                                    newOne.get(newOne.size() - 1).y));
                            score.calculateTopBottomScore(anotherNewOne, board.sendBoard(), letters, checkWord);
                            playerScore += score.getMegaScore();
                            wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
                            //System.out.println("Wildcardletter: "+wildCardLetter);
                            //System.out.print("PlayerScore: " + playerScore);
                            playerTray.addTray(playerTray.getTray());
                            realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                        }
                    }
                    checkWord = "";
                    //System.out.println("CounterX: " + counterX);
                    //System.out.println("CounterY: " + counterY);
                    computerTray.starterPack(board.sendBoard(), computerTray.getTray(), parent, score, letters);
                    designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
                    fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                    score.removePlayerScore();
                    playerPlayCoordinates.clear();
                }
                else{
                    boolean anotherGameX = false;
                    boolean anotherGameY = false;
                    int xCo = playerPlayCoordinates.get(0).x;
                    int anotherXCo = playerPlayCoordinates.get(playerPlayCoordinates.size() - 1).x;
                    int yCo = playerPlayCoordinates.get(0).y;
                    int anotherYCo = playerPlayCoordinates.get(playerPlayCoordinates.size() - 1).y;
                   // System.out.println("xco: "+xCo);
                   // System.out.println("anotherxco: "+anotherXCo);
                    if(xCo == anotherXCo){
                        while(yCo <= anotherYCo){
                            if(!board.sendBoard()[xCo][yCo].equals("..") &&
                                    !board.sendBoard()[xCo][yCo].equals("2.")
                                    && !board.sendBoard()[xCo][yCo].equals("3.") &&
                                    !board.sendBoard()[xCo][yCo].equals(".2")
                                    && !board.sendBoard()[xCo][yCo].equals(".3")){
                                anotherGameX = true;
                            }
                            if(!board.getFakeBoard()[xCo][yCo].equals("..") &&
                                    !board.getFakeBoard()[xCo][yCo].equals("2.")
                                    && !board.getFakeBoard()[xCo][yCo].equals("3.") &&
                                    !board.getFakeBoard()[xCo][yCo].equals(".2")
                                    && !board.getFakeBoard()[xCo][yCo].equals(".3")){
                                anotherGameX = true;
                            }
                            yCo++;
                        }
                    }
                    else if(yCo == anotherYCo){
                        while(xCo <= anotherXCo){
                            if(!board.sendBoard()[xCo][yCo].equals("..") &&
                                    !board.sendBoard()[xCo][yCo].equals("2.")
                                    && !board.sendBoard()[xCo][yCo].equals("3.") &&
                                    !board.sendBoard()[xCo][yCo].equals(".2")
                                    && !board.sendBoard()[xCo][yCo].equals(".3")){
                                anotherGameY = true;
                            }
                            if(!board.getFakeBoard()[xCo][yCo].equals("..") &&
                                    !board.getFakeBoard()[xCo][yCo].equals("2.")
                                    && !board.getFakeBoard()[xCo][yCo].equals("3.") &&
                                    !board.getFakeBoard()[xCo][yCo].equals(".2")
                                    && !board.getFakeBoard()[xCo][yCo].equals(".3")){
                                anotherGameY = true;
                            }
                            xCo++;
                        }
                    }
                    else{
                        System.out.println("Don't play");
                        int i = 0;
                        while(i < playerPlayCoordinates.size()){
                            playerTray.addStringChar(board.getFakeBoard()
                                    [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                            i++;
                        }
                        fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                        //System.out.println("return pieces back");
                        return;
                    }
                    //System.out.println("AnotherGameX: "+anotherGameX);
                    //System.out.println("AnotherGameY: "+anotherGameY);
                    if(anotherGameX == true){
                        anotherGameXX(playerPlayCoordinates, score, board, parent, playerTray,
                                gc, letters, computerTray);
                    }
                    else if(anotherGameY == true){
                        anotherGameYY(playerPlayCoordinates, score, board, parent, playerTray,
                                gc, letters, computerTray);
                    }
                    playerPlayCoordinates.clear();
                }
            }
            else{
                //System.out.println("Don't play");
                int i = 0;
                while(i < playerPlayCoordinates.size()){
                    playerTray.addStringChar(board.getFakeBoard()
                            [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                    i++;
                }
                fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                //System.out.println("return pieces back");
            }
        }
        else if(x >= 50 && x <= 190 && y >= 886 && y <= 941){
            //System.out.println("REPLACE");
            if(trayClicking == false) {
                replacingLetters = "";
                //System.out.println("REPLACE");
                setTrayClickingTrue();
            }
            else if(trayClicking == true){
                setTrayClickingFalse();
                //System.out.println("ReplacableLetters: "+replacingLetters);
               // System.out.println("Tray: "+playerTray.getTray());
                //System.out.println("Word: "+replacingLetters);
                ArrayList<Integer> index = new ArrayList<>();
                int ix = 0;
                while(ix < replacingLetters.length()){
                    int ij = 0;
                    while(ij < playerTray.getTray().length()){
                        if(playerTray.getTray().charAt(ij) == replacingLetters.charAt(ix)){
                            if(!index.contains(ij) && index.size() < replacingLetters.length()) {
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
               // System.out.println(index);

                String newTray = "";
                for(int w = 0; w < index.size(); w++){
                    if(w == 0){
                        System.out.println(index.get(0));
                        newTray += playerTray.getTray().substring(0, index.get(0));
                    }
                    if(index.get(w) + 1 < 7) {
                        System.out.println(index.get(w) + 1);
                        if(w < index.size() - 1) {
                            newTray += playerTray.getTray().substring(index.get(w) + 1, index.get(w + 1));
                        }
                        else{
                            newTray += playerTray.getTray().substring(index.get(w) + 1);
                        }
                    }
                    //System.out.println("NewTray: "+newTray);
                }
                int n = 0;
                while(n < 26){
                    char newLet = (char) (n + 97);
                    //System.out.println("NewLet: "+newLet);
                    int value = findLetterVal(Character.toUpperCase(newLet));
                    //System.out.println("Value: "+value);
                    int an = 0;
                    int counter = 0;
                    while(an < replacingLetters.length()){
                        if(replacingLetters.charAt(an) == newLet){
                            counter++;
                        }
                        an++;
                    }
                    //System.out.println("Counter: "+counter);
                    letters.addTile(String.valueOf(newLet), value, counter);
                    n++;
                }
                //System.exit(0);
                playerTray.addTray(newTray);
                index.clear();
                setClickBoardTrue();
                computerTray.starterPack(board.sendBoard(), computerTray.getTray(),
                        parent, score, letters);
                designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
                fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
                replacingLetters = "";
            }
        }
        newOne.clear();
        playerPlayCoordinates.clear();
    }

    /**
     * for in-between plays horizontally by player
     * @param playerPlayCoordinates
     * @param score
     * @param board
     * @param parent
     * @param playerTray
     * @param gc
     * @param letters
     * @param computerTray
     */
    public void anotherGameXX(ArrayList<Point> playerPlayCoordinates, Score score, Board board,
                              TrieNode parent, PlayerTray playerTray, GraphicsContext gc, Letters letters,
                              ComputerTray computerTray){
        //System.out.println("PlayerCoord: "+playerPlayCoordinates);
        ArrayList<String> words = new ArrayList<>();
        int ax = playerPlayCoordinates.get(0).x;
        int ay = playerPlayCoordinates.get(0).y;
        String test = "";
        String upCheck = "";
        ay--;
        while(ay > -1 && !board.sendBoard()[ax][ay].equals("..") &&
                !board.sendBoard()[ax][ay].equals("2.") &&
                !board.sendBoard()[ax][ay].equals("3.") &&
                !board.sendBoard()[ax][ay].equals(".2") &&
                !board.sendBoard()[ax][ay].equals(".3")){
            test = board.sendBoard()[ax][ay] + test;
            ay--;
        }
        int n = 0;
        while(n < playerPlayCoordinates.size()){
            test = test + board.getFakeBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y];
            ax = playerPlayCoordinates.get(n).x;
            //System.out.println("ax: "+ax);
            ay = playerPlayCoordinates.get(n).y + 1;
            //System.out.println("ay: "+ay);
            //System.out.println("Board: "+board.sendBoard()[ax][ay]);
            while(ay < 15 && !board.sendBoard()[ax][ay].equals("..") &&
                    !board.sendBoard()[ax][ay].equals("2.") &&
                    !board.sendBoard()[ax][ay].equals("3.") &&
                    !board.sendBoard()[ax][ay].equals(".2") &&
                    !board.sendBoard()[ax][ay].equals(".3")) {
                test = test + board.sendBoard()[ax][ay];
                ay++;
            }
            n++;
        }
        words.add(test.replaceAll("\\s", ""));
        int nr = 0;
        while(nr < playerPlayCoordinates.size()) {
            int bb = playerPlayCoordinates.get(nr).x;
            int zz = playerPlayCoordinates.get(nr).y;
            while (bb > 0 && !board.getFakeBoard()[bb - 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".3")) {
                upCheck = board.getFakeBoard()[bb - 1][zz] + upCheck;
                bb--;
            }
            //System.out.println("BB: " + bb);
            bb = playerPlayCoordinates.get(nr).x;
            upCheck += board.getFakeBoard()[bb][zz];
            while (bb < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb + 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb + 1][zz];
                bb++;
            }
            upCheck = upCheck.replaceAll("\\s", "");
            //System.out.println("Upcheck: "+upCheck);
            if(upCheck.length() > 1) {
                words.add(upCheck);
            }
            upCheck = "";
            nr++;
        }
        int aq = 0;
        boolean wordValid;
        while(aq < words.size()){
            //System.out.println("OUR WORD: "+words.get(aq));
            wordValid = parent.validity(words.get(aq), parent);
            if(wordValid == false){
                //System.out.println("Don't play");
                int i = 0;
                while(i < playerPlayCoordinates.size()){
                    playerTray.addStringChar(board.getFakeBoard()
                            [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                    i++;
                }
                fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                computerTray.starterPack(board.sendBoard(), computerTray.getTray(), parent, score, letters);
                realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                //System.out.println("return pieces back");
                return;
            }
            aq++;
        }
        wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
        //System.out.println("Wildcardletter: "+wildCardLetter);
        score.calculateAltLRScore(playerPlayCoordinates, board, letters);
        playerScore += score.getMegaScore();
        realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
        computerTray.starterPack(board.sendBoard(),
                computerTray.getTray(), parent, score, letters);
        score.removePlayerScore();
        playerTray.addTray(playerTray.getTray());
        fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
        realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
    }

    /**
     * for in-between plays vertically by player
     * @param playerPlayCoordinates
     * @param score
     * @param board
     * @param parent
     * @param playerTray
     * @param gc
     * @param letters
     * @param computerTray
     */
    public void anotherGameYY(ArrayList<Point> playerPlayCoordinates, Score score, Board board,
                              TrieNode parent, PlayerTray playerTray, GraphicsContext gc, Letters letters, ComputerTray computerTray){
        //System.out.println("PlayerCoord: "+playerPlayCoordinates);
        ArrayList<String> words = new ArrayList<>();
        int ax = playerPlayCoordinates.get(0).x;
        int ay = playerPlayCoordinates.get(0).y;
        String test = "";
        String upCheck = "";
        ax--;
        while(ax > -1 && !board.sendBoard()[ax][ay].equals("..") &&
                !board.sendBoard()[ax][ay].equals("2.") &&
                !board.sendBoard()[ax][ay].equals("3.") &&
                !board.sendBoard()[ax][ay].equals(".2") &&
                !board.sendBoard()[ax][ay].equals(".3")){
            test = board.sendBoard()[ax][ay] + test;
            ax--;
        }
        int n = 0;
        while(n < playerPlayCoordinates.size()){
            test = test + board.getFakeBoard()
                    [playerPlayCoordinates.get(n).x][playerPlayCoordinates.get(n).y];
            ax = playerPlayCoordinates.get(n).x + 1;
            //System.out.println("ax: "+ax);
            ay = playerPlayCoordinates.get(n).y;
            //System.out.println("ay: "+ay);
           // System.out.println("Board: "+board.sendBoard()[ax][ay]);
            while(ax < 15 && !board.sendBoard()[ax][ay].equals("..") &&
                    !board.sendBoard()[ax][ay].equals("2.") &&
                    !board.sendBoard()[ax][ay].equals("3.") &&
                    !board.sendBoard()[ax][ay].equals(".2") &&
                    !board.sendBoard()[ax][ay].equals(".3")) {
                test = test + board.sendBoard()[ax][ay];
                ax++;
            }
            n++;
        }
        words.add(test.replaceAll("\\s", ""));

        int nr = 0;
        while(nr < playerPlayCoordinates.size()) {
            int bb = playerPlayCoordinates.get(nr).x;
            int zz = playerPlayCoordinates.get(nr).y;
            while (zz > 0 && !board.getFakeBoard()[bb][zz - 1].equals("..") &&
                    !board.getFakeBoard()[bb][zz - 1].equals("3.") &&
                    !board.getFakeBoard()[bb][zz - 1].equals("2.") &&
                    !board.getFakeBoard()[bb][zz - 1].equals(".2") &&
                    !board.getFakeBoard()[bb][zz - 1].equals(".3")) {
                upCheck = board.getFakeBoard()[bb][zz - 1] + upCheck;
                zz--;
            }
           // System.out.println("ZZ: " + zz);
            zz = playerPlayCoordinates.get(nr).y;
            upCheck += board.getFakeBoard()[bb][zz];

            while (zz < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb][zz + 1].equals("..") &&
                    !board.getFakeBoard()[bb][zz + 1].equals("3.") &&
                    !board.getFakeBoard()[bb][zz + 1].equals("2.") &&
                    !board.getFakeBoard()[bb][zz + 1].equals(".2") &&
                    !board.getFakeBoard()[bb][zz + 1].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb][zz + 1];
                zz++;
            }
            upCheck = upCheck.replaceAll("\\s", "");
            //System.out.println("Upcheck: "+upCheck);
            if(upCheck.length() > 1) {
                words.add(upCheck);
            }
            upCheck = "";
            nr++;
        }
        //System.out.println(words);
        int aq = 0;
        boolean wordValid;
        while(aq < words.size()){
            //System.out.println("OUR WORD: "+words.get(aq));
            wordValid = parent.validity(words.get(aq), parent);
            if(wordValid == false){
                //System.out.println("Don't play");
                int i = 0;
                while(i < playerPlayCoordinates.size()){
                    playerTray.addStringChar(board.getFakeBoard()
                            [playerPlayCoordinates.get(i).x][playerPlayCoordinates.get(i).y].charAt(1));
                    i++;
                }
                fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
                //System.out.println("Moist pieces back");
                computerTray.starterPack(board.sendBoard(), computerTray.getTray(), parent, score, letters);
                //System.out.println("dsdfs pieces back");
                realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
                //System.out.println("return pieces back");
                return;
            }
            aq++;
        }
        wildCardLetter = String.valueOf((char)(counterForWildcard - 1));
        //System.out.println("Wildcardletter: "+wildCardLetter);
        score.calculateAltTBcore(playerPlayCoordinates, board, letters);
        playerScore += score.getMegaScore();

        realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);
        computerTray.starterPack(board.sendBoard(),
                computerTray.getTray(), parent, score, letters);
        score.removePlayerScore();
        playerTray.addTray(playerTray.getTray());
        fakeBoardCopiesRealBoard(board, gc, playerTray, letters, computerTray, score);
        realBoardCopiesFakeBoard(board, gc, playerTray, letters, computerTray, score);

    }


    public void setTrayClickingFalse(){
        trayClicking = false;
    }

    public void setTrayClickingTrue() {
        trayClicking = true;
    }

    /**
     * used when a move by player was invalid
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param computerTray
     * @param score
     */
    public void fakeBoardCopiesRealBoard(Board board, GraphicsContext gc, PlayerTray playerTray,
                                         Letters letters, ComputerTray computerTray, Score score) {
        setClickBoardFalse();
        for(int i = 0; i < board.sendBoard().length; i++){
            for(int j = 0; j < board.sendBoard().length; j++){
                if(!board.sendBoard()[i][j].equals(board.getFakeBoard()[i][j])){
                    board.getFakeBoard()[i][j] = board.sendBoard()[i][j];
                }
            }
        }
        designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
    }

    /**
     * used when a move by player was valid
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param computerTray
     * @param score
     */
    public void realBoardCopiesFakeBoard(Board board, GraphicsContext gc, PlayerTray playerTray,
                                         Letters letters, ComputerTray computerTray, Score score){
        for(int j = 0; j < 15; j++){
            for(int k = 0; k < 15; k++) {
                //System.out.print(board.getFakeBoard()[j][k] + " ");
            }
            //System.out.println();
        }
        setClickBoardFalse();
        /*if(wildCardCounter >= 1) {
            for (int i = 0; i < board.sendBoard().length; i++) {
                for (int j = 0; j < board.sendBoard().length; j++) {
                    if (!board.sendBoard()[i][j].equals(board.getFakeBoard()[i][j])) {
                        if (Character.isUpperCase(board.getFakeBoard()[i][j].charAt(1))) {
                            if(!steadyPlace.contains(new Point(i,j))){
                                anotherSteadyPlace.add(new Point(i,j));
                            }
                            wildCardCounter++;
                        }
                        board.sendBoard()[i][j] = board.getFakeBoard()[i][j];
                    }
                }
            }
        }*/
        if(wildCardCounter == 0) {
            for (int i = 0; i < board.sendBoard().length; i++) {
                for (int j = 0; j < board.sendBoard().length; j++) {
                    if (!board.sendBoard()[i][j].equals(board.getFakeBoard()[i][j])) {
                        if (Character.isUpperCase(board.getFakeBoard()[i][j].charAt(1))) {
                            steadyPlace.add(new Point(i, j));
                            wildCardCounter++;
                            anotherC = 1;
                        }
                        board.sendBoard()[i][j] = board.getFakeBoard()[i][j];
                    }
                }
            }
        }
        designScrabbleBoard(board, gc, playerTray, letters, computerTray, score);
    }

    /**
     * check vertical play by player
     * @param board
     * @param parent
     * @return
     */
    public boolean checkVertical(Board board, TrieNode parent) {
        ArrayList<String> groupStrings = new ArrayList<>();
        String play = "";
        int z = 0;
        while(z < playerPlayCoordinates.size()){
            String upCheck = "";
            int bb = playerPlayCoordinates.get(z).x;
            int zz = playerPlayCoordinates.get(z).y;
            if(z == 0) {
                while (bb > 0 && !board.getFakeBoard()[bb - 1][zz].equals("..") &&
                        !board.getFakeBoard()[bb - 1][zz].equals("3.")
                        && !board.getFakeBoard()[bb - 1][zz].equals("2.") &&
                        !board.getFakeBoard()[bb - 1][zz].equals(".2")
                        && !board.getFakeBoard()[bb - 1][zz].equals(".3")) {
                    play = board.getFakeBoard()[bb - 1][zz] + play;
                    newOne.add(new Point(bb - 1, zz));
                    bb--;
                }
            }
            bb = playerPlayCoordinates.get(z).x;
            newOne.add(new Point(bb, zz));
            while (zz > 0 && !board.getFakeBoard()[bb][zz - 1].equals("..") &&
                    !board.getFakeBoard()[bb][zz - 1].equals("3.")
                    && !board.getFakeBoard()[bb][zz - 1].equals("2.") &&
                    !board.getFakeBoard()[bb][zz - 1].equals(".2")
                    && !board.getFakeBoard()[bb][zz - 1].equals(".3")) {
                upCheck = board.getFakeBoard()[bb][zz - 1] + upCheck;
                zz--;
            }
            //System.out.println("Upchecking: " + upCheck);
            zz = playerPlayCoordinates.get(z).y;
            upCheck += board.getFakeBoard()[bb][zz];
            while (zz < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb][zz + 1].equals("..")
                    && !board.getFakeBoard()[bb][zz + 1].equals("3.") &&
                    !board.getFakeBoard()[bb][zz + 1].equals("2.")
                    && !board.getFakeBoard()[bb][zz + 1].equals(".2") &&
                    !board.getFakeBoard()[bb][zz + 1].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb][zz + 1];
                //System.out.println("Upchecking: " + upCheck);
                zz++;
            }
            //System.out.println("Upchecking: " + upCheck);
            if(upCheck.replaceAll("\\s", "").length() > 1) {
                groupStrings.add(upCheck.replaceAll("\\s", ""));
            }
            play = play + board.getFakeBoard()
                    [playerPlayCoordinates.get(z).x][playerPlayCoordinates.get(z).y];
            //System.out.println("Play: "+play);
            zz = playerPlayCoordinates.get(z).y;
            if(z == playerPlayCoordinates.size() - 1){
                bb++;
                while (bb < (board.getFakeBoard().length) && bb >= 0 &&
                        !board.getFakeBoard()[bb][zz].equals("..")
                        && !board.getFakeBoard()[bb][zz].equals("3.") &&
                        !board.getFakeBoard()[bb][zz].equals("2.") &&
                        !board.getFakeBoard()[bb][zz].equals(".2") &&
                        !board.getFakeBoard()[bb][zz].equals(".3")) {
                    play += board.getFakeBoard()[bb][zz];
                    newOne.add(new Point(bb, zz));
                    bb++;
                }
            }
            bb++;
            z++;
        }
        //System.out.println("Play: "+play);
        //System.out.println("Play: " + play.replaceAll("\\s", ""));
        checkWord = play.replaceAll("\\s", "");
        groupStrings.add(play.replaceAll("\\s", ""));
        //System.out.println("GroupString: "+groupStrings);
        return checkValidity(groupStrings, parent);
        //System.out.println("validwords: "+validWords);
    }

    /**
     * check if word is valid
     * @param groupStrings
     * @param parent
     * @return
     */
    public boolean checkValidity(ArrayList<String> groupStrings, TrieNode parent) {
        int i = 0;
        while(i < groupStrings.size()){
            if(parent.validity(groupStrings.get(i), parent) == false){
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * check horizontal play by player
     * @param board
     * @param parent
     * @return
     */
    public boolean checkHorizontal(Board board, TrieNode parent){
        ArrayList<String> groupStrings = new ArrayList<>();
        String play = "";
        int z = 0;
        while(z < playerPlayCoordinates.size()){
            String upCheck = "";
            int bb = playerPlayCoordinates.get(z).x;
            int zz = playerPlayCoordinates.get(z).y;
            if(z == 0){
                //System.out.println("BANDB");
                while (zz > 0 && !board.getFakeBoard()[bb][zz - 1].equals("..") &&
                        !board.getFakeBoard()[bb][zz - 1].equals("3.") &&
                        !board.getFakeBoard()[bb][zz - 1].equals("2.") &&
                        !board.getFakeBoard()[bb][zz - 1].equals(".2") &&
                        !board.getFakeBoard()[bb][zz - 1].equals(".3")) {
                    play = board.getFakeBoard()[bb][zz - 1] + play;
                    newOne.add(new Point(bb, zz - 1));
                    zz--;
                }
            }
            //System.out.println("newone: " +newOne);
            zz = playerPlayCoordinates.get(z).y;
            newOne.add(new Point(bb, zz));
            //System.out.println("BB: "+bb);
            //System.out.println("Up: " +board.getFakeBoard()[bb - 1][zz]);
            while (bb > 0 && !board.getFakeBoard()[bb - 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb - 1][zz].equals(".3")) {
                upCheck = board.getFakeBoard()[bb - 1][zz] + upCheck;
                bb--;
            }
            //System.out.println("BB: "+bb);
            bb = playerPlayCoordinates.get(z).x;
            upCheck += board.getFakeBoard()[bb][zz];
            while (bb < (board.getFakeBoard().length - 1) &&
                    !board.getFakeBoard()[bb + 1][zz].equals("..") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("3.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals("2.") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".2") &&
                    !board.getFakeBoard()[bb + 1][zz].equals(".3")) {
                upCheck = upCheck + board.getFakeBoard()[bb + 1][zz];
                bb++;
            }
            //System.out.println("UpCheckers: " + upCheck.replaceAll("\\s", ""));
            if(upCheck.replaceAll("\\s", "").length() > 1) {
                groupStrings.add(upCheck.replaceAll("\\s", ""));
            }
            play = play + board.getFakeBoard()
                    [playerPlayCoordinates.get(z).x][playerPlayCoordinates.get(z).y];
            //System.out.println("Play: "+play);
            if(z == playerPlayCoordinates.size() - 1){
                bb = playerPlayCoordinates.get(z).x;
                //System.out.println("CAIN BELASQUEZ");
                zz++;
                while (zz < board.getFakeBoard().length && zz >= 0 &&
                        !board.getFakeBoard()[bb][zz].equals("..") &&
                        !board.getFakeBoard()[bb][zz].equals("3.") &&
                        !board.getFakeBoard()[bb][zz].equals("2.") &&
                        !board.getFakeBoard()[bb][zz].equals(".2") &&
                        !board.getFakeBoard()[bb][zz].equals(".3")) {
                    play += board.getFakeBoard()[bb][zz];
                    newOne.add(new Point(bb, zz));
                    //System.out.println("newpone: " +newOne);
                    zz++;
                }
            }
            zz++;
            z++;
        }

        //System.out.println("Play: "+play);
        //System.out.println("Play: " + play.replaceAll("\\s", ""));
        checkWord = play.replaceAll("\\s", "");
        groupStrings.add(play.replaceAll("\\s", ""));
        //System.out.println("GroupString: "+groupStrings);
        return checkValidity(groupStrings, parent);
        //System.out.println("validwords: "+validWords);
    }

    /**
     * design board in-between submitted plays by player
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param computerTray
     */
    public void designFakeScrabbleBoard(Board board, GraphicsContext gc,
                                        PlayerTray playerTray, Letters letters, ComputerTray computerTray) {
        //System.out.println("boy");
        for(int j = 0; j < 15; j++){
            for(int k = 0; k < 15; k++) {
                //System.out.print(board.sendBoard()[j][k] + " ");
            }
            //System.out.println();
        }
        //System.out.println("dank");
        for(int j = 0; j < 15; j++){
            for(int k = 0; k < 15; k++) {
                //System.out.print(board.getFakeBoard()[j][k] + " ");
            }
            //System.out.println();
        }
        //System.out.println("temporray");
        int length = board.boardLength();
        int x = 0;
        int y = 0;
        int w = 55;
        int h = 55;
        for(int j = 0; j < length; j++){
            for(int k = 0; k < length; k++){
                if(board.getFakeBoard()[j][k].equals(".3")){
                    gc.setFill(Color.DARKBLUE);
                    gc.fillRect(x, y,w,h);
                }
                else if(board.getFakeBoard()[j][k].equals("2.")){
                    gc.setFill(Color.PINK);
                    gc.fillRect(x, y,w,h);
                }
                else if(board.getFakeBoard()[j][k].equals(".2")){
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillRect(x, y,w,h);
                }
                else if(board.getFakeBoard()[j][k].equals("3.")){
                    gc.setFill(Color.RED);
                    gc.fillRect(x, y,w,h);
                }
                else if(board.getFakeBoard()[j][k].equals("..")){
                    gc.setFill(Color.SANDYBROWN);
                    gc.fillRect(x, y,w,h);
                }
                else{
                    gc.setFill(Color.BURLYWOOD);
                    gc.fillRect(x, y,w,h);
                    Boolean okay = false;
                    Boolean unOkay = false;
                    if(ComputerTray.wildCardCor.contains(new Point(j, k))){
                        //System.out.println("Found it again");
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        //System.out.println("AnotherWildCrad: "+computerTray.anotherWildCardLetter);
                        gc.fillText(computerTray.anotherWildCardLetter, x + 5, y + 45);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(15));
                        gc.fillText(String.valueOf(0), x + 45, y + 45);
                        okay = true;
                    }
                    else if(steadyPlace.contains(new Point(j,k)) && okay == false){
                        //System.out.println("Found it");
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        gc.fillText(wildCardLetter, x + 5, y + 45);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(15));
                        gc.fillText(String.valueOf(0), x + 45, y + 45);
                        unOkay = true;
                    }
                    else if(Character.isUpperCase(board.getFakeBoard()[j][k].charAt(1)) &&
                            unOkay == false && okay == false){
                        char thing = (char)counterForWildcard;
                        //System.out.println("Char: "+thing);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        gc.fillText(String.valueOf(thing), x + 5, y + 45);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(15));
                        gc.fillText(String.valueOf(0), x + 45, y + 45);
                        counterForWildcard++;
                        if(counterForWildcard == 91){
                            counterForWildcard = 65;
                        }
                    }
                    else if(unOkay == false && okay == false){
                        char letter = Character.toUpperCase(board.getFakeBoard()[j][k].charAt(1));
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        gc.fillText(String.valueOf(letter), x, y + 45);
                        letterVal = findLetterVal(letter);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(15));
                        gc.fillText(String.valueOf(letterVal), x + 45, y + 45);
                    }
                }
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(x, y, w, h);
                x += 55;
            }
            x = 0;
            y += 55;
        }


        int bx = 220;
        int by = 886;
        int bw = 55;
        int bh = 55;
        //System.out.println("Tray: "+playerTray.getTray());
        for(int i = 0; i < 7; i++){
            gc.setFill(Color.WHITE);
            gc.fillRect(bx, by,bw,bh);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(bx, by, bw, bh);
            bx += 55;
        }

        int ax = 220;
        int ay = 886;
        int aw = 55;
        int ah = 55;
        //System.out.println("Tray: "+playerTray.getTray());
        for(int i = 0; i < playerTray.getTray().length(); i++){
           // System.out.println("Trayings: "+playerTray.getTray());
            char letter = Character.toUpperCase(playerTray.getTray().charAt(i));
           // System.out.println("letter : "+letter);
            if(letter == ' '){
                gc.setFill(Color.BURLYWOOD);
                gc.fillRect(ax, ay, aw, ah);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(ax, ay, aw, ah);
                //System.out.println("Found the wildcard");
                /*System.out.println("CounterForWildcard: "+counterForWildacrd);
                char thing = (char)counterForWildacrd;
                System.out.println("Char: "+thing);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(50));
                gc.fillText(String.valueOf(thing), ax + 5, 931);*/
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(15));
                gc.fillText(String.valueOf(0), ax + 45, ay + 45);
                /*counterForWildacrd++;
                if(counterForWildacrd == 91){
                    counterForWildacrd = 65;
                }
                stopCounter = counterForWildacrd - 1;*/
            }
            else {
                String value = String.valueOf(findLetterVal(letter));
               // System.out.println("value: " + value);
               // System.out.println(letter);
                gc.setFill(Color.BURLYWOOD);
                gc.fillRect(ax, ay, aw, ah);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(ax, ay, aw, ah);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(50));
                gc.fillText(String.valueOf(letter), ax + 5, ay + 45);
                gc.setFill(Color.BLACK);
                gc.setFont(new Font(15));
                gc.fillText(value, ax + 45, ay + 45);
            }
            ax += 55;
        }

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(670, 886, 100, 55);
        String play = "PLAY";
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(45));
        gc.fillText(play, 670, 930);

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(50, 886, 140, 55);
        String replace = "REPLACE";
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(35));
        gc.fillText(replace, 50, 930);
    }

    public void setAnotherCBTrue(){
        anotherCB = true;
    }

    public void setAnotherCBFalse(){
        anotherCB = false;
    }

    public void setClickBoardFalse(){
        clickBoard = false;
    }

    public void setClickBoardTrue() {
        clickBoard = true;
    }

    /**
     * make the real board after submit is pressed
     * @param board
     * @param gc
     * @param playerTray
     * @param letters
     * @param computerTray
     * @param score
     */
    public void designScrabbleBoard(Board board, GraphicsContext gc, PlayerTray playerTray,
                                    Letters letters, ComputerTray computerTray, Score score) {
        //System.out.println("permanent");
        String[][] changingBoard = board.sendBoard();
        /*for(int qww = 0; qww < 15; qww++){
            for (int jaa = 0; jaa < 15; jaa++){
                System.out.print(changingBoard[qww][jaa] + " ");
            }
            System.out.println();
        }*/
        int length = board.boardLength();
        int x = 0;
        int y = 0;
        int w = 55;
        int h = 55;
        for(int j = 0; j < length; j++){
            for(int k = 0; k < length; k++){
                if(changingBoard[j][k].equals(".3")){
                    gc.setFill(Color.DARKBLUE);
                    gc.fillRect(x, y,w,h);
                }
                else if(changingBoard[j][k].equals("2.")){
                    gc.setFill(Color.PINK);
                    gc.fillRect(x, y,w,h);
                }
                else if(changingBoard[j][k].equals(".2")){
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillRect(x, y,w,h);
                }
                else if(changingBoard[j][k].equals("3.")){
                    gc.setFill(Color.RED);
                    gc.fillRect(x, y,w,h);
                }
                else if(changingBoard[j][k].equals("..")){
                    gc.setFill(Color.SANDYBROWN);
                    gc.fillRect(x, y,w,h);
                }
                else{
                    gc.setFill(Color.BURLYWOOD);
                    gc.fillRect(x, y, w, h);
                    //System.out.println("J: "+j+" K: "+k);
                    boolean okay = false;
                    if(ComputerTray.wildCardCor.contains(new Point(j, k))){
                        //System.out.println("Found it again");
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        //System.out.println("AnotherWildCrad: "+computerTray.anotherWildCardLetter);
                        gc.fillText(computerTray.anotherWildCardLetter, x + 5, y + 45);
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(15));
                        gc.fillText(String.valueOf(0), x + 45, y + 45);
                        okay = true;
                    }
                    //System.out.println("Okay: "+okay);
                    if(okay == false) {
                        if (steadyPlace.contains(new Point(j, k)) && anotherC == 1) {
                            String dank = wildCardLetter;
                            System.out.println(steadyPlace);
                            //System.out.println("Found it");
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font(50));
                            gc.fillText(dank, x + 5, y + 45);
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font(15));
                            gc.fillText(String.valueOf(0), x + 45, y + 45);
                        }
                        else{
                            //System.out.println("Found it sadnojasd");
                            char letter = Character.toUpperCase(changingBoard[j][k].charAt(1));
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font(50));
                            gc.fillText(String.valueOf(letter), x, y + 45);
                            letterVal = findLetterVal(letter);
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font(15));
                            gc.fillText(String.valueOf(letterVal), x + 45, y + 45);
                        }
                    }
                }
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(x, y, w, h);
                x += 55;
            }
            x = 0;
            y += 55;
        }


        int bx = 220;
        int by = 886;
        int bw = 55;
        int bh = 55;
        //System.out.println("Tray: "+playerTray.getTray());
        for(int i = 0; i < 7; i++){
            gc.setFill(Color.WHITE);
            gc.fillRect(bx, by,bw,bh);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(bx, by, bw, bh);
            bx += 55;
        }


        int ax = 220;
        int ay = 886;
        int aw = 55;
        int ah = 55;
        finTray = playerTray.getTray();
        //System.out.println("Tray: "+playerTray.getTray());
        for(int i = 0; i < playerTray.getTray().length(); i++){
            char letter = Character.toUpperCase(playerTray.getTray().charAt(i));
            //System.out.println("LETTERS:" +letter);
            String value = String.valueOf(findLetterVal(letter));
            //System.out.println("value: "+value);
            //System.out.println(letter);
            gc.setFill(Color.BURLYWOOD);
            gc.fillRect(ax, ay,aw,ah);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(ax, ay, aw, ah);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(50));
            gc.fillText(String.valueOf(letter), ax + 5, ay + 45);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(15));
            gc.fillText(value, ax + 45, ay + 45);
            ax += 55;
        }

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(670, 886, 100, 55);
        String play = "PLAY";
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(45));
        gc.fillText(play, 670, 930);

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(50, 886, 140, 55);
        String replace = "REPLACE";
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(35));
        gc.fillText(replace, 50, 930);

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(50, 960, 200, 35);
        String here = "PScore: "+playerScore;
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(35));
        gc.fillText(here, 50, 990);

        gc.setFill(Color.CADETBLUE);
        gc.fillRect(570, 960, 200, 35);
        String anHere = "Cscore: "+ computerTray.compScore();
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(35));
        gc.fillText(anHere, 570, 990);
    }


    /**
     * find value of letter
     * @param letter
     * @return
     */
    public int findLetterVal(char letter) {
        if(letter == 'A' || letter == 'E' || letter == 'I' || letter == 'L' ||
                letter == 'N' || letter == 'O' ||
                letter == 'R' || letter == 'S' || letter == 'T' || letter == 'U'){
            return 1;
        }
        if(letter == 'D' || letter == 'G'){
            return 2;
        }
        if(letter == 'B' || letter == 'C' || letter == 'M' || letter == 'P'){
            return 3;
        }
        if(letter == 'F' || letter == 'H' || letter == 'V' || letter == 'W' ||
                letter == 'Y'){
            return 4;
        }
        if(letter == 'K'){
            return 5;
        }
        if(letter == 'J' || letter == 'X'){
            return 8;
        }
        if(letter == 'Q' || letter == 'Z'){
            return 10;
        }
        return 0;
    }


}
