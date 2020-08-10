package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Domino {

    /**
     * Arraylist which stores all of the pieces.
     */
    ArrayList<Point> dominos = new ArrayList<Point>();
    /**
     * integer which will determine domino to be
     * sent to either player, computer or boneyard
     */
    public int pieceSent;

    /**
     * make all of the dominos
     */
    public Domino(){
        int num = 0;
        for(int i = 0; i < 7; i++){
            for(int j = num; j < 7; j++){
                dominos.add(new Point(i,j));
            }
            num++;
        }
    }

    /**
     * give dominos to players or boneyard
     * @return
     */
    public Point getDominosForPlayers(){
        Random rand = new Random();
        pieceSent = rand.nextInt(dominos.size());
        return dominos.get(pieceSent);
    }

    /**
     * remove domino once player or boneyard
     * gets it
     */
    public void removeDominoPiece(){
        dominos.remove(pieceSent);
    }

}
