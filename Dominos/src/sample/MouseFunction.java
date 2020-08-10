package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class MouseFunction {

    /**
     * First click selects domino, second click deposits
     * the domino at a specific direction.
     * @param x
     * @param y
     * @param playerTray
     * @param gc
     * @param computerTray
     * @param boneYard
     * @param board
     * @param animationTimer
     */
    public MouseFunction(double x, double y, PlayerTray playerTray, GraphicsContext gc,
                         ComputerTray computerTray, BoneYard boneYard, Board board, AnimationTimer animationTimer){
        if(x >= 0 && x < (playerTray.checkPlayerDominos().size() * 50) && y >= 680 && y <= 730){
            if(playerTray.initClick() == false){
                int xCor = (int) x/50;
                playerTray.setBoardClickTrue();
                playerTray.setVal(xCor);
            }
        }
        else if(x >= 0 && x <= 700 && y >= 0 && y <= 600) {
            if (playerTray.initClick() == true) {
                board.startGame(playerTray,computerTray,boneYard, playerTray.getVal(), gc,
                        "L", animationTimer);
                gc.clearRect(0,680, (playerTray.checkPlayerDominos().size() + 1) * 50, 50);
                playerTray.setBoardClickFalse();
            }
        }
        else if(x >= 700 && x <= 1400 && y >= 0 && y <= 600) {
            if (playerTray.initClick() == true) {
                board.startGame(playerTray,computerTray,boneYard, playerTray.getVal(), gc,
                        "R", animationTimer);
                gc.clearRect(0,680, (playerTray.checkPlayerDominos().size() + 1) * 50, 50);
                playerTray.setBoardClickFalse();
            }
        }
    }

}
