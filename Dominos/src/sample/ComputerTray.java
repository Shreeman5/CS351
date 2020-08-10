package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ComputerTray {

    /**
     * Arraylist which stores the computer's pieces.
     */
    ArrayList<Point> computerPieces = new ArrayList<>();

    /**
     * extracts 7 dominos from
     * domino class
     * @param domino
     */
    public ComputerTray(Domino domino){
        int n = 0;
        while(n < 7){
            computerPieces.add(domino.getDominosForPlayers());
            domino.removeDominoPiece();
            n++;
        }
    }

    /**
     * checks whether computer can play any piece.
     * if not, boolean true is sent and player extracts
     * from boneyard, unless boneyard is empty.
     * @param a
     * @param b
     * @return
     */
    public boolean findComputerEligibility(String a, String b){
        if(computerPieces.isEmpty()){
            return true;
        }
        if(a.equals("0") || b.equals("0")){
            return false;
        }
        for (Point p : computerPieces) {
            if(a.equals(String.valueOf(p.x)) || a.equals(String.valueOf(p.y)) ||
                    b.equals(String.valueOf(p.x)) || b.equals(String.valueOf(p.y))){
                return false;
            }
            if(p.x == 0 || p.y == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * find match according to the playable variables.Return domino accordingly.
     * @param a
     * @param b
     * @return
     */
    public Point findMatch(String a, String b){
        if(a.equals("0") || b.equals("0")){
            Random rand = new Random();
            int luckyNum = rand.nextInt(computerPieces.size());
            return computerPieces.get(luckyNum);
        }
        for (Point p : computerPieces) {
            if(a.equals(String.valueOf(p.x)) || a.equals(String.valueOf(p.y))
                    || b.equals(String.valueOf(p.x)) || b.equals(String.valueOf(p.y))){
                return p;
            }
            if(p.x == 0 || p.y == 0){
                return p;
            }
        }
        return null;
    }

    /**
     * remove domino after it is played
     * @param g
     */
    public void removeDomino(Point g){
        Iterator<Point> iter = computerPieces.iterator();
        int n = 0;
        while (iter.hasNext()) {
            Point p = iter.next();
            if (g.equals(p)) {
                iter.remove();
                break;
            }
        }
        System.out.println("ComputerPieces[After Removal of Domino]: "
                +computerPieces);
    }

    /**
     * returns the current pieces stored in the arraylist
     * @return
     */
    public ArrayList checkComputerDominos(){
        return computerPieces;
    }

    /**
     * add domino to compputerTray once player has no playing
     * @param p
     */
    public void addDomino(Point p) {
        computerPieces.add(p);
        System.out.println("ComputerPieces[After Addition of Dominos]: "
                +computerPieces);
    }

}
