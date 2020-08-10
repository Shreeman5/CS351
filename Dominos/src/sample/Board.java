package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

public class Board {

    /**
     * Map for domino chain on the right of the middle of the board
     */
    Map<Integer, String> dominoRightString = new HashMap<>();
    /**
     * Map for domino chain on the left of the middle of the board
     */
    Map<Integer, String> dominoLeftString = new HashMap<>();
    /**
     * stores the left playable number of the domino chain
     */
    String a = "";
    /**
     * stores the right playable number of the domino chain
     */
    String b = "";

    /**
     * Initiate board class
     */
    public Board(){}

    /**
     * Main logic of the game. If player cannot play at a certain direction,
     * alert box is shown and method returns back. If player can play,
     * a method is initiated that will construct maps according to direction.
     * Computer identifies the playable variables and plays accordingly.
     * If player and computer cannot play, they draw from boneyard.
     * If boneyard is empty and playertray is empty, computer is given chance
     * to put as much pieces as it can. Same goes for the player.
     * If player cannot play, but has pieces, and computer can play and has
     * pieces, while boneyard is empty, computer plays and player is free to
     * play again. Same goes for the computer. If both player and computer
     * cannot play, and boneyard is empty, game is over. While boneyard is
     * empty, player has nothing and computer has pieces but cannot play, game
     * is over. While boneyard is empty, computer has nothing and player
     * has pieces but cannot play, game is over.
     * @param playerTray
     * @param computerTray
     * @param boneYard
     * @param xCor
     * @param gc
     * @param direction
     * @param animationTimer
     */
    public void startGame(PlayerTray playerTray, ComputerTray computerTray,
                          BoneYard boneYard, int xCor, GraphicsContext gc, String direction,
                          AnimationTimer animationTimer){
        if(!dominoRightString.isEmpty()) {
            Point p = playerTray.getDominoFromNumber(xCor);
            boolean checkPlayerEligibility;
            if(direction.equals("L")) {
                checkPlayerEligibility = checkForPlayerMatch(a, String.valueOf(p.y));
                if(checkPlayerEligibility == false){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot play here. Press " +
                            "OK to continue.");
                    alert.show();
                    return;
                }
            }
            else if(direction.equals("R")){
                checkPlayerEligibility = checkForPlayerMatch(b, String.valueOf(p.x));
                if(checkPlayerEligibility == false){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You cannot play here. Press " +
                            "OK to continue.");
                    alert.show();
                    return;
                }
            }
        }
        if (dominoRightString.isEmpty()) {
            Point p = playerTray.getDominoFromNumber(xCor);
            a = String.valueOf(p.x);
            b = String.valueOf(p.y);
            constructChain(a, b, "", "", gc, direction);
            playerTray.removeDomino(p);
        }
        else{
            Point p = playerTray.getDominoFromNumber(xCor);
            String c = String.valueOf(p.x);
            String d = String.valueOf(p.y);
            constructChain(a, b, c, d, gc, direction);
            playerTray.removeDomino(p);
        }
        boolean needToExtractFromBoneyardComputer = true;
        while(needToExtractFromBoneyardComputer == true){
            needToExtractFromBoneyardComputer = computerTray.findComputerEligibility(a, b);
            if(needToExtractFromBoneyardComputer == true){
                Point x = boneYard.addDominoToPlayers();
                if(x != null) {
                    computerTray.addDomino(x);
                    boneYard.removeDomino(x);
                }
                else{
                    needToExtractFromBoneyardComputer = false;
                }
            }
        }
        System.out.println("Wrong commit, I was doing scrabble");
        computerLogic(computerTray, gc);
        boolean needToExtractFromBoneyard = true;
        while(needToExtractFromBoneyard == true){
            needToExtractFromBoneyard = playerTray.findEligibility(Integer.parseInt(a),
                    Integer.parseInt(b));
            if(needToExtractFromBoneyard == true){
                Point x = boneYard.addDominoToPlayers();
                if(x != null) {
                    playerTray.addDomino(x);
                    boneYard.removeDomino(x);
                }
                else{
                    needToExtractFromBoneyard = false;
                    boolean playerUnEligible = playerTray.findEligibility(Integer.valueOf(a),
                            Integer.valueOf(b));
                    while(boneYard.checkBoneyardDominos().isEmpty() && playerUnEligible == true){
                        computerLogic(computerTray, gc);
                        playerUnEligible = playerTray.findEligibility(Integer.valueOf(a),
                                Integer.valueOf(b));
                        if(computerTray.findComputerEligibility(a, b) == true){
                            endOfGameScoring(playerTray, computerTray, animationTimer);
                            playerUnEligible = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * method which allows computer to make move.
     * @param computerTray
     * @param gc
     */
    public void computerLogic(ComputerTray computerTray, GraphicsContext gc){
        Point g = computerTray.findMatch(a, b);
        if (g != null) {
            constructChain(a, b, String.valueOf(g.x), String.valueOf(g.y), gc, "");
            computerTray.removeDomino(g);
        }
    }

    /**
     * put dominos in maps according to direction. If left, put at
     * dominoLeftString. If right, put at dominoRightString. Depending
     * whether piece has been rotated, put piece in the map accordingly.
     * Used by both computer and player.
     * @param aa
     * @param bb
     * @param cc
     * @param dd
     * @param gc
     * @param direction
     */
    public void constructChain(String aa, String bb, String cc, String dd, GraphicsContext gc, String direction){
        int placement;
        if(cc.equals("") && dd.equals("")){
            dominoRightString.put(0, "[" + a + "," + b + "]");
            makeBoardDominos(gc);
        }
        else if(direction.equals("L")){
            a = cc;
            placement = dominoLeftString.size() + 1;
            dominoLeftString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(direction.equals("R")){
            b = dd;
            placement = dominoRightString.size();
            dominoRightString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(aa.equals("0")){
            a = cc;
            placement = dominoLeftString.size() + 1;
            dominoLeftString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(bb.equals("0")){
            b = dd;
            placement = dominoRightString.size();
            dominoRightString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(cc.equals("0")){
            b = dd;
            placement = dominoRightString.size();
            dominoRightString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(dd.equals("0")){
            a = cc;
            placement = dominoLeftString.size() + 1;
            dominoLeftString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(aa.equals(cc)){
            a = dd;
            placement = dominoLeftString.size() + 1;
            dominoLeftString.put(placement, "[" +dd+ "," +cc+ "]");
            makeBoardDominos(gc);
        }
        else if(aa.equals(dd)){
            a = cc;
            placement = dominoLeftString.size() + 1;
            dominoLeftString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(bb.equals(cc)){
            b = dd;
            placement = dominoRightString.size();
            dominoRightString.put(placement, "[" +cc+ "," +dd+ "]");
            makeBoardDominos(gc);
        }
        else if(bb.equals(dd)){
            b = cc;
            placement = dominoRightString.size();
            dominoRightString.put(placement, "[" +dd+ "," +cc+ "]");
            makeBoardDominos(gc);
        }
    }

    /**
     * If dominoLeftString and if map element key
     * value is odd, put at the bottom of the 2 lines
     * of the board. If dominoLeftString and if map element
     * key value is even, put at the top of the 2 lines
     * of the board. If dominoRightString and if map element key
     * value is odd, put at the bottom of the 2 lines
     * of the board. If dominoRightString and if map element key
     * value is odd, put at the top of the 2 lines
     * of the board. Put top or bottom by specifying
     * pixels and increasing/decreasing pixels with
     * constant value. Also call method that makes dots.
     * @param gc
     */
    public void makeBoardDominos(GraphicsContext gc) {
        int r = 0;
        while(r < dominoRightString.size()){
            if(dominoRightString.containsKey(r)){
                if(r % 2 == 0){
                    int x = 675 + (r * 25);
                    gc.setFill(Color.LIGHTCYAN);
                    gc.fillRect(x,275,50,50);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(x, 275, 50, 50);
                    String value = dominoRightString.get(r);
                    int leftNum = value.charAt(1) - 48;
                    int rightNum = value.charAt(3) - 48;
                    designDotsinThisClass(leftNum, (x+x+50)/2, gc, x, 275);
                    designDotsinThisClass(rightNum,(x+x+50)/2, gc, x+50, 275);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeLine((x+x+50)/2, 275, (x+x+50)/2, 325);
                }
                else{
                    int x = 675+(r*25);
                    gc.setFill(Color.LIGHTCYAN);
                    gc.fillRect(x,325,50,50);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(x, 325, 50, 50);
                    String value = dominoRightString.get(r);
                    int leftNum = value.charAt(1) - 48;
                    int rightNum = value.charAt(3) - 48;
                    designDotsinThisClass(leftNum, (x+x+50)/2, gc, x, 325);
                    designDotsinThisClass(rightNum,(x+x+50)/2, gc, x+50, 325);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeLine((x+x+50)/2, 325, (x+x+50)/2, 375);
                }
            }
            r++;
        }

        int n = 1;
        while(n <= dominoLeftString.size()){
            if(dominoLeftString.containsKey(n)){
                if(n % 2 == 1){
                    int x = 675-(n*25);
                    gc.setFill(Color.LIGHTCYAN);
                    gc.fillRect(x,325,50,50);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(x, 325, 50, 50);
                    String value = dominoLeftString.get(n);
                    int leftNum = value.charAt(1) - 48;
                    int rightNum = value.charAt(3) - 48;
                    designDotsinThisClass(leftNum, (x+x+50)/2, gc, x, 325);
                    designDotsinThisClass(rightNum,(x+x+50)/2, gc, x+50, 325);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeLine((x+x+50)/2, 325, (x+x+50)/2, 375);
                }
                else if(n % 2 == 0){
                    int x = 675-(n*25);
                    gc.setFill(Color.LIGHTCYAN);
                    gc.fillRect(x,275,50,50);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(x, 275, 50, 50);
                    String value = dominoLeftString.get(n);
                    int leftNum = value.charAt(1) - 48;
                    int rightNum = value.charAt(3) - 48;
                    designDotsinThisClass(leftNum, (x+x+50)/2, gc, x, 275);
                    designDotsinThisClass(rightNum,(x+x+50)/2, gc, x+50, 275);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeLine((x+x+50)/2, 275, (x+x+50)/2, 325);
                }
            }
            n++;
        }
    }

    /**
     * design dots according to the number sent. Each number is
     * made at specified picels.
     * @param dots
     * @param middle
     * @param gc
     * @param bounds
     * @param y
     */
    public void designDotsinThisClass(int dots, int middle, GraphicsContext gc,
                                      int bounds, int y) {
        gc.setFill(Color.BLACK);
        int midMid = (bounds+middle)/2;
        if(dots == 1){
            gc.fillRect(midMid - 2, y + 23, 4, 4);
        }
        else if(dots == 2){
            gc.fillRect(midMid - 2, y + 13, 4, 4);
            gc.fillRect(midMid - 2, y + 33, 4, 4);
        }
        else if(dots == 3){
            gc.fillRect(midMid - 2, y + 13, 4, 4);
            gc.fillRect(midMid - 2, y + 23, 4, 4);
            gc.fillRect(midMid - 2, y + 33, 4, 4);
        }
        else if(dots == 4){
            gc.fillRect((bounds+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((bounds+midMid)/2 - 2, y + 33, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 33, 4, 4);
        }
        else if(dots == 5){
            gc.fillRect((bounds+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((bounds+midMid)/2 - 2, y + 33, 4, 4);
            gc.fillRect(midMid - 2, y + 23, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 33, 4, 4);
        }
        else if(dots == 6){
            gc.fillRect((bounds+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((bounds+midMid)/2 - 2, y + 23, 4, 4);
            gc.fillRect((bounds+midMid)/2 - 2, y + 33, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 13, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 23, 4, 4);
            gc.fillRect((middle+midMid)/2 - 2, y + 33, 4, 4);
        }
    }

    /**
     * finds whether player can play. If not, returns false.
     * @param left
     * @param right
     * @return
     */
    public boolean checkForPlayerMatch(String left, String right){
        if(left.equals("0") || right.equals("0")){
            return true;
        }
        if(left.equals(right)){
            return true;
        }
        return false;
    }

    /**
     * Compares score of player and computer. Whoever has more points loses.
     * Points are calculated by adding up all the individual numbers from
     * the remaining dominos of each player. Make alert messages which
     * displays who won and game exits.
     * @param playerTray
     * @param computerTray
     * @param animationTimer
     */
    public void endOfGameScoring(PlayerTray playerTray, ComputerTray computerTray,
                                 AnimationTimer animationTimer){
        animationTimer.stop();
        int playerScore = 0;
        ArrayList<Point> player = playerTray.checkPlayerDominos();
        for(Point p : player){
            playerScore += p.x + p.y;
        }
        System.out.println("PlayerScore: " +playerScore);
        int computerScore = 0;
        ArrayList<Point> computer = computerTray.checkComputerDominos();
        for(Point p : computer){
            computerScore += p.x + p.y;
        }
        System.out.println("ComputerScore: " +computerScore);

        if(playerScore > computerScore){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your score is "
                    + playerScore + ". Computer's score is " + computerScore + ". GAME OVER! " +
                    "Computer has Won!", ButtonType.CLOSE);
            alert.setOnHidden(evt -> {
                if (alert.getResult() == ButtonType.CLOSE) {
                    System.exit(0);
                }
            });
            alert.show();
        }
        else if(computerScore > playerScore){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your score is "
                    + playerScore + ". Computer's score is " + computerScore + ". GAME OVER! " +
                    "Player has Won!", ButtonType.CLOSE);
            alert.setOnHidden(evt -> {
                if (alert.getResult() == ButtonType.CLOSE) {
                    System.exit(0);
                }
            });
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your score is "
                    + playerScore + ". Computer's score is " + computerScore + ". GAME OVER! " +
                    "It's a draw!", ButtonType.CLOSE);
            alert.setOnHidden(evt -> {
                if (alert.getResult() == ButtonType.CLOSE) {
                    System.exit(0);
                }
            });
            alert.show();
        }
    }

}
