package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application{

    /**
     * @param primaryStage Setting the stage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception{
        Vocabulary vocab = new Vocabulary("sowpods.txt");
        TrieNode parent = new TrieNode();
        vocab.vocabInTrie(parent);
        Score score = new Score();
        Letters letters = new Letters();
        PlayerTray playerTray = new PlayerTray(letters);
        Board board = new Board("scrabble_board.txt");
        ComputerTray computerTray = new ComputerTray(letters, score);
        GUI gui = new GUI(primaryStage, board, playerTray, letters, parent, computerTray, score);

        //ComputerTray computerTray = new ComputerTray(parent, score, letters, "example_input.txt");
    }

    /**
     * Start program
     * @param args
     */
    public static void main(String[] args) {
        /*Vocabulary vocab = new Vocabulary(args[0]); //args[0]
        TrieNode parent = new TrieNode();
        vocab.vocabInTrie(parent);
        Score score = new Score();
        Letters letters = new Letters();
        ComputerTray computerTray = new ComputerTray(parent, score, letters);*/
        launch(args);
    }
}
