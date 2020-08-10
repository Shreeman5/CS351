package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    /**
     * Setup the backend code using Board class and
     * the GUI using the GUIDesign class
     * @param primaryStage
     */
    public void start(Stage primaryStage){
        primaryStage.setTitle("Tiles");
        Board board = new Board();
        ArrayList<Integer> scores = new ArrayList<>();
        GUIDesign designGUI = new GUIDesign(primaryStage, board, scores);
    }

    /**
     * Start Program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
