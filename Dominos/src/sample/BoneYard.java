package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BoneYard {

    /**
     * Arraylist which stores the boneyard's pieces.
     */
    ArrayList<Point> boneYardPieces = new ArrayList<>();

    /**
    * extracts 14 dominos from
    * domino class
    ***/
    public BoneYard(Domino domino){
        int n = 0;
        while(n < 14){
            boneYardPieces.add(domino.getDominosForPlayers());
            domino.removeDominoPiece();
            n++;
        }
    }

    /**
     * give domino to player or computer
     * when either playertray or computray
     * run out
     * @return
     */
    public Point addDominoToPlayers(){
        if(!boneYardPieces.isEmpty()) {
            Random rand = new Random();
            int luckyNum = rand.nextInt(boneYardPieces.size());
            return boneYardPieces.get(luckyNum);
        }
        return null;
    }

    /**
     * remove domino after it is added
     * to either player or computer.
     * @param g
     */
    public void removeDomino(Point g){
        Iterator<Point> iter = boneYardPieces.iterator();
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
    public ArrayList checkBoneyardDominos(){
        return boneYardPieces;
    }

}
