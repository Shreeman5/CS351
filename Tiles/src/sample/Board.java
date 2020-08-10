package sample;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    ArrayList<String> color = new ArrayList<>();
    ArrayList<String> shape = new ArrayList<>();
    ArrayList<String> superStringCombos = new ArrayList<>();
    Tile[] tile;
    public int currTile;
    public boolean clicked = false;
    public int returnX;
    public int returnY;

    /**
     * Add Tiles to the board. Each Tile contains 3 elements put in randomly.
     */
    public Board(){
        Random random = new Random();
        int num = random.nextInt(4) + 1;
        if(num % 2 == 1){
            num++;
        }
        num = 42;
        tile = new Tile[num];
        color.add("Green");
        color.add("Yellow");
        color.add("Blue");
        color.add("Purple");
        shape.add("Rectangle");
        shape.add("Oval");
        shape.add("RoundedRect");
        shape.add("Text");
        int k = 0;
        while (k < num){
            if (k >= num/2){
                tile[k] = new Tile();
                int n = 0;
                for(String temp: superStringCombos){
                    if (n == 3){
                        tile[k] = new Tile();
                        n = 0;
                    }
                    int a = Character.getNumericValue(temp.charAt(0));
                    int b = Character.getNumericValue(temp.charAt(1));
                    Elements element = new Elements(color.get(a), shape.get(b));
                    n++;
                    if((n/3) == 1) {
                        tile[k].addElements(element);
                        k++;
                    }
                    else{
                        tile[k].addElements(element);
                    }
                }
            }
            else{
                tile[k] = new Tile();
                ArrayList<String> stringCombos = new ArrayList<>();
                int numberOfElements = 0;
                while (numberOfElements < 3){
                    Random rand = new Random();
                    int randomColorIndex = rand.nextInt(4);
                    int randomShapeIndex = rand.nextInt(4);
                    if (stringCombos.contains(String.valueOf(randomColorIndex) + String.valueOf(randomShapeIndex))){
                        numberOfElements--;
                    }
                    else{
                        stringCombos.add(String.valueOf(randomColorIndex) + String.valueOf(randomShapeIndex));
                        superStringCombos.add(String.valueOf(randomColorIndex) + String.valueOf(randomShapeIndex));
                        Elements element = new Elements(color.get(randomColorIndex), shape.get(randomShapeIndex));
                        tile[k].addElements(element);
                    }
                    numberOfElements++;
                }
                k++;
            }
        }
    }

    /**
     * Helper method so that the first click is ignored.
     * @param tileNumber
     * @param borX
     * @param borY
     */
    public void initBoardFunc(int tileNumber, int borX, int borY) {
        this.returnX = borX;
        this.returnY = borY;
        this.clicked = true;
        this.currTile = tileNumber;
    }

    public boolean firstClick() {
        return clicked;
    }

    /**
     * Helper method when returning the first tile
     * @return
     */
    public int getPrevTile() {
        return this.currTile;
    }

    public Tile[] tiles(){
        return tile;
    }

    public void setBoardClick(){this.clicked = true;}
}
