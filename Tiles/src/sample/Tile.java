package sample;

import java.util.ArrayList;

public class Tile {

    private ArrayList<Elements> elementsOfTile = new ArrayList<>();

    /**
     * add elements to the tile
     * @param elements
     */
    public void addElements(Elements elements) {
        elementsOfTile.add(elements);
    }

    /**
     * remove elements from the tile
     * @param elements
     */
    public void removeElements(Elements elements){
        this.elementsOfTile.remove(elements);
    }

    /**
     * show what elements the tile has
     * @return
     */
    public ArrayList<Elements> showElements(){
        return elementsOfTile;
    }
}
