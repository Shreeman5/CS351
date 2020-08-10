package sample;
import java.util.ArrayList;
import java.util.List;

public class MouseFunc {

    private int tileNum;
    static int currScore;

    /**
     * returns current tile
     * @param x
     * @param y
     */
    public MouseFunc(double x, double y){
        int corX = (int) x/80;
        int corY = (int) y/80;
        tileNum = 6*corY + corX;
    }

    /**
     * compares elements of two clicked cells. Returns various numbers according to
     * the given case. Say two full cells are compared and nothing matches, return 2.
     * Say two cells are compared and the clicked one becomes empty due to matches,
     * return 1. And so on.
     * @param board
     * @param scores
     * @param borX
     * @param borY
     * @return
     */
    public int click(Board board, ArrayList scores, int borX, int borY){
        Tile [] tile = board.tiles();
        int i = 0;
        int j = 0;
        int counter = 0;
        List<Elements> currentDesign = new ArrayList<>();
        List<Elements> clickedDesign = new ArrayList<>();
        if(GUIDesign.empty == 1 && !tile[tileNum].showElements().isEmpty())
        {
            board.initBoardFunc(tileNum, borX, borY);
            return 0;
        }
        if(GUIDesign.empty == 1 && clickedDesign.isEmpty()){
            board.setBoardClick();
            return 1;
        }
        if(board.firstClick() == false) {
            board.initBoardFunc(tileNum, borX, borY);
        }
        else {
            if(board.getPrevTile() == tileNum) {
                System.out.println("Please choose another tile");
            }
            else {
                currentDesign = tile[board.getPrevTile()].showElements();
                clickedDesign = tile[tileNum].showElements();
                if(clickedDesign.isEmpty()){
                    if(!currentDesign.isEmpty()){
                        return 3;
                    }
                }
                int currTileNum = currentDesign.size();
                int clickedTileNum = clickedDesign.size();
                Elements[] currentElements = new Elements[currTileNum];
                Elements[] clickedElements = new Elements[clickedTileNum];
                for (Elements design1 : currentDesign) {
                    currentElements[i] = design1;
                    i++;
                }
                for (Elements design2 : clickedDesign) {
                    clickedElements[j] = design2;
                    j++;
                }
                for (i = 0; i < currTileNum; i++) {
                    for (j = 0; j < clickedTileNum; j++) {
                        if ((currentElements[i].getColor().equals(clickedElements[j].getColor())) &&
                                (currentElements[i].getShape().equals(clickedElements[j].getShape()))) {
                            tile[board.getPrevTile()].removeElements(currentElements[i]);
                            tile[tileNum].removeElements(clickedElements[j]);
                            counter++;
                        }
                    }
                }
                if (counter > 0 && clickedDesign.isEmpty()){
                    this.currScore++;
                    return 1;
                }
                else if (counter > 0){
                    this.currScore++;
                }
                else if (GUIDesign.empty != 1){
                    scores.add(this.currScore);
                    this.currScore = 0;
                    return 2;
                }
                else if (counter == 0 && clickedDesign.isEmpty()){
                    return 0;
                }
                board.initBoardFunc(tileNum, borX, borY);
                if (clickedDesign.isEmpty()) {
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * returns current score
     * @return
     */
    public int getCombo(){
        return this.currScore;
    }

}
