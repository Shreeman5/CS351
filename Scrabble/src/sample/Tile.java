package sample;

public class Tile {

    public String letter;
    public int value;

    /**
     * Store each tile, 100 tiles in total
     * @param letters
     * @param score
     */
    public Tile(String letters, int score) {
        letter = letters;
        value = score;
    }

    /**
     * Get value of tile
     * @return
     */
    public int getValue(){
        return this.value;
    }

    public String getLetter(){
        return this.letter;
    }

}
