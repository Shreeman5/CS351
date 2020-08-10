package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Letters {

    public List<Tile> tiles = new ArrayList<>();
    ArrayList<Integer> points = new ArrayList<>();

    /**
     * Store the frequency of each letter according to Scrabble rules
     */
    public Letters(){
        addTile("a", 1, 9);
        addTile("b", 3, 2);
        addTile("c", 3, 2);
        addTile("d", 2, 4);
        addTile("e",1, 12);
        addTile("f",4, 2);
        addTile("g", 2, 3);
        addTile("h", 4, 2);
        addTile("i", 1, 9);
        addTile("j",8, 1);
        addTile("k", 5, 1);
        addTile("l", 1, 4);
        addTile("m", 3, 2);
        addTile("n", 1, 6);
        addTile("o", 1, 8);
        addTile("p", 3, 2);
        addTile("q", 10, 1);
        addTile("r", 1, 6);
        addTile("s", 1, 4);
        addTile("t", 1, 6);
        addTile("u", 1, 4);
        addTile("v", 4, 2);
        addTile("w",4, 2);
        addTile("x", 8, 1);
        addTile("y", 4, 2);
        addTile("z", 10, 1);
        addTile(" ", 0, 2);
    }


    /**
     * get tiles for player and computer after they play
     * a certain amount of tiles
     * @param tray
     * @return
     */
    public String getTiles(String tray){
        int neededLetters = 7 - tray.length();
        int counter = 0;
        for(int i = 0; i < neededLetters; i++){
            Random rand = new Random();
            int val = rand.nextInt(tiles.size());
            String a = tiles.get(val).getLetter();
            if(a.equals(" ")){
                counter++;
            }
            if(a.equals(" ") && counter == 1){
                val = rand.nextInt(tiles.size());
                a = tiles.get(val).getLetter();
            }
            tray += a;
            int score = tiles.get(val).getValue();
            points.add(score);
            tiles.remove(val);
        }
        return tray;
    }


    public ArrayList<Integer> getPoints(){
        return points;
    }

    /**
     * Store the tiles in a bag
     * @param letter
     * @param score
     * @param count
     */
    public void addTile(String letter, int score, int count)
    {
        for (int i = 0; i < count; i++)
        {
            Tile tile = new Tile(letter, score); // Pass the score along to the tile as well
            tiles.add(tile);
        }
    }

    /**
     * Calculate Score
     * @param letter
     * @return
     */
    public int TileScoresRecorder(String letter) {
        int score = 0;
        char testingLetter = letter.charAt(1);

        if(testingLetter == 'a'){
            score = score + 1;
        }
        if(testingLetter == 'b'){
            score = score + 3;
        }
        if(testingLetter == 'c'){
            score = score + 3;
        }
        if(testingLetter == 'd'){
            score = score + 2;
        }
        if(testingLetter == 'e'){
            score = score + 1;
        }
        if(testingLetter == 'f'){
            score = score + 4;
        }
        if(testingLetter == 'g'){
            score = score + 2;
        }
        if(testingLetter == 'h'){
            score = score + 4;
        }
        if(testingLetter == 'i'){
            score = score + 1;
        }
        if(testingLetter == 'j'){
            score = score + 8;
        }
        if(testingLetter == 'k'){
            score = score + 5;
        }
        if(testingLetter == 'l'){
            score = score + 1;
        }
        if(testingLetter == 'm'){
            score = score + 3;
        }
        if(testingLetter == 'n'){
            score = score + 1;
        }
        if(testingLetter == 'o'){
            score = score + 1;
        }
        if(testingLetter == 'p'){
            score = score + 3;
        }
        if(testingLetter == 'q'){
            score = score + 10;
        }
        if(testingLetter == 'r'){
            score = score + 1;
        }
        if(testingLetter == 's'){
            score = score + 1;
        }
        if(testingLetter == 't'){
            score = score + 1;
        }
        if(testingLetter == 'u'){
            score = score + 1;
        }
        if(testingLetter == 'v'){
            score = score + 4;
        }
        if(testingLetter == 'w'){
            score = score + 4;
        }
        if(testingLetter == 'x'){
            score = score + 8;
        }
        if(testingLetter == 'y'){
            score = score + 4;
        }
        if(testingLetter == 'z'){
            score = score + 10;
        }
        return score;
    }
}
