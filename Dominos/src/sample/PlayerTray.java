package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerTray {

    /**
     * Arraylist which stores the player's pieces.
     */
    ArrayList<Point> playerPieces = new ArrayList<>();
    /**
     * used when player selects a domino. After selecting
     * they can play by clicking on the board at a specific
     * direction.
     */
    boolean isClicked = false;
    /**
     * record which piece was clicked
     */
    int keepX;

    /**
     * extracts 7 dominos from
     * domino class
     * @param domino
     */
    public PlayerTray(Domino domino){
        int n = 0;
        while(n < 7){
            playerPieces.add(domino.getDominosForPlayers());
            domino.removeDominoPiece();
            n++;
        }
    }

    /**
     * checks whether player can play any piece.
     * if not, boolean true is sent and player extracts
     * from boneyard, unless boneyard is empty.
     * @param leftPiece
     * @param rightPiece
     * @return
     */
    public boolean findEligibility(int leftPiece, int rightPiece){
        if(playerPieces.isEmpty()){
            return true;
        }
        if(leftPiece == 0 || rightPiece == 0){
            return false;
        }
        for(Point p: playerPieces){
            if(p.x == 0 || p.y == 0){
                return false;
            }
            if(leftPiece == p.y || rightPiece == p.x || leftPiece == p.x || rightPiece == p.y){
                return false;
            }
        }
        return true;
    }

    /**
     * remove domino after it is played
     * @param g
     */
    public void removeDomino(Point g){
        Iterator<Point> iter = playerPieces.iterator();
        while (iter.hasNext()) {
            Point p = iter.next();
            if (g.equals(p)) {
                iter.remove();
                break;
            }
        }
    }

    /**
     * returns the current pieces stored in the arraylist
     * @return
     */
    public ArrayList checkPlayerDominos(){
        return playerPieces;
    }

    /**
     * add domino to playerTray once player has no playing
     * piece
     * @param p
     */
    public void addDomino(Point p) {
        playerPieces.add(p);
    }

    /**
     *
     * @return isClicked
     */
    public boolean initClick(){
        return isClicked;
    }

    /**
     * once domino is selected,
     * makes isClicked true such that
     * piece can be played at a specific
     * direction
     */
    public void setBoardClickTrue(){
        isClicked = true;
    }

    /**
     * store keepX
     * @param xCor
     */
    public void setVal(int xCor){
        keepX = xCor;
    }

    /**
     *
     * @return keepX
     */
    public int getVal(){
        return keepX;
    }

    /**
     * Once domino is set at the playing field,
     * player can only click a piece to continue
     */
    public void setBoardClickFalse(){
        isClicked = false;
    }

    /**
     * if right button is clicked in the mouse,
     * flip the numbers
     * @param xCor
     */
    public void swapNumbers(int xCor){
        int num = 0;
        for(Point p: playerPieces){
            if(num == xCor){
                int temp = p.x;
                p.x = p.y;
                p.y = temp;
            }
            num++;
        }
    }

    /**
     * find playerPiece from the
     * board. If 1st domino was clicked in the board,
     * return first domino from playerPieces.
     * @param xCor
     * @return
     */
    public Point getDominoFromNumber(int xCor){
        int num = 0;
        for(Point p: playerPieces){
            if(num == xCor){
                return p;
            }
            num++;
        }
        return null;
    }

}
