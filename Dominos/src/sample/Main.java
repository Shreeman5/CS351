package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Game logic: Dominos are made, playerTray, computerTray & boneyard get
     * dominos, board handles game logic, guiDesign works with board
     * to make GUI according to the logic.
     * @param primaryStage
     */
    public void start(Stage primaryStage){
        Domino domino = new Domino();
        PlayerTray playerTray = new PlayerTray(domino);
        ComputerTray computerTray = new ComputerTray(domino);
        BoneYard boneYard = new BoneYard(domino);
        Board board = new Board();
        GUIDesign guiDesign = new GUIDesign(playerTray, computerTray, boneYard,
                primaryStage, board);
    }

    /**
     * launch game
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
