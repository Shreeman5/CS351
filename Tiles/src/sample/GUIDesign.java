package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIDesign{
    MouseFunc m;
    List<Elements> elements = new ArrayList<>();
    Tile[] tile;
    Board board;
    static int empty;
    AnimationTimer animationTimer;

    /**
     * design GUI according to backend data
     * @param primaryStage
     * @param board
     * @param scores
     */
    public GUIDesign(Stage primaryStage, Board board, ArrayList scores){
        BorderPane bp = new BorderPane();
        Canvas canvas = new Canvas (480 ,560);
        GraphicsContext gc = canvas.getGraphicsContext2D ();
        tile = board.tiles();
        System.out.println("Tile: "+tile);
        this.board = board;
        int rowNum = 6;
        int colNum = 7;
        int x = 0;
        int y = 0;
        int tileNum = 0;
        for(int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                gc.setFill(Color.RED);
                gc.fillRect (x ,y,80 ,80);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                gc.strokeRect(x, y, 80, 80);
                elements = tile[tileNum].showElements();
                for(Elements element: elements){
                    setTilesOnRectangle(tileNum, this.board, (x + 80 + x) / 2, (y + y + 80) / 2, gc);
                }
                tileNum++;
                x+= 80;
                if (x == 480){
                    x = 0;
                    y += 80;
                }
            }
        }
        startAnimation();

        /**
         * calls GUI update method after every click. designs borders to indicate where mouse is at.
         * updates current and longest score.
         */
        canvas.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                m = new MouseFunc(event.getX(), event.getY());
                empty = m.click(board, scores, (int)event.getX(), (int)event.getY());
                if(empty != 3 && empty != 2 && empty != 4) {
                    canvasUpdate(gc);
                }
                int borderX = (int) event.getX()/80;
                int borderY = (int) event.getY()/80;
                if (empty == 1){
                    gc.setStroke(Color.GRAY);

                    gc.setLineWidth(4);
                    gc.strokeRect(borderX*80, borderY*80, 80, 80);
                }
                else if (empty == 0){
                    gc.setStroke(Color.GREEN);
                    gc.setLineWidth(4);
                    gc.strokeRect(borderX*80, borderY*80, 80, 80);
                }
                else if(empty == 3){
                    int x = board.returnX;
                    int y = board.returnY;
                    gc.setStroke(Color.GREEN);
                    gc.setLineWidth(4);
                    gc.strokeRect(y*80, x*80, 80, 80);
                }
                else if(empty == 2){
                    int x = board.returnX;
                    int y = board.returnY;
                    gc.setStroke(Color.GREEN);
                    gc.setLineWidth(4);
                    gc.strokeRect(y*80, x*80, 80, 80);
                }
                Label label = new Label("Current Score: " + m.getCombo());
                label.setFont(new Font("Arial", 100));
                BorderPane.setAlignment(label, Pos.CENTER);
                bp.setBottom(label);
                if (!scores.isEmpty()) {
                    Collections.sort(scores);
                    Label label1 = new Label("Longest Score: " + scores.get(scores.size() - 1));
                    label1.setFont(new Font("Arial", 100));
                    BorderPane.setAlignment(label1, Pos.CENTER);
                    bp.setTop(label1);
                }
                else{
                    Label label1 = new Label("Longest Score: " + m.getCombo());
                    label1.setFont(new Font("Arial", 100));
                    BorderPane.setAlignment(label1, Pos.CENTER);
                    bp.setTop(label1);
                }
                int value = checkGameFinish();
                if(value == 1){
                    animationTimer.stop();
                    int finVal = 0;
                    if(scores.isEmpty()){
                        finVal = m.getCombo();
                    }
                    else{
                        finVal = (int)scores.get(scores.size() - 1);
                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your score is "
                            + finVal + ". GAME OVER! Press Close to exit.",
                            ButtonType.CLOSE);
                    alert.setOnHidden(evt -> {
                        if (alert.getResult() == ButtonType.CLOSE) {
                            System.exit(0);
                        }
                    });
                    alert.show();
                }
            }
        });
        bp.setCenter(canvas);
        Scene scene = new Scene(bp, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Finds whether the board is cleared or not
     * @return
     */
    public int checkGameFinish(){
        int findFinish = 0;
        int counter = 0;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++) {
                elements = tile[findFinish].showElements();
                if(elements.isEmpty()){
                    counter++;
                }
                findFinish++;
            }
        }
        if(counter == 42){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Starts animation once the initial board is setup.
     */
    public void startAnimation(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            }
        };
        animationTimer.start();
    }

    /**
     * updates canvas after every click.
     * @param gc
     */
    public void canvasUpdate(GraphicsContext gc){
        int counter = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                gc.setFill(Color.RED);
                gc.fillRect(x, y , 80, 80);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                gc.strokeRect(x, y, 80, 80);
                elements = tile[counter].showElements();
                for(Elements element: elements){
                    setTilesOnRectangle(counter, this.board, (x + 80 + x) / 2, (y + y + 80) / 2, gc);
                }
                x+= 80;
                if (x == 480){
                    x = 0;
                    y += 80;
                }
                counter++;
            }
        }
    }

    /**
     * Called at the start to setup the board and after every mouse click,
     * to update the board.
     * @param tileNum
     * @param board
     * @param x
     * @param y
     * @param gc
     */
    public void setTilesOnRectangle(int tileNum, Board board, int x, int y, GraphicsContext gc){
        Tile[] tiles = board.tiles();
        elements = tiles[tileNum].showElements();
        if (0 < elements.size()) {
            String color0 = elements.get(0).getColor();
            String shape0 = elements.get(0).getShape();
            makeShape(color0, shape0, x, y, gc);
        }
        if (1 < elements.size()) {
            String color1 = elements.get(1).getColor();
            String shape1 = elements.get(1).getShape();
            makeShape(color1, shape1, x - 20, y, gc);
        }
        if(2 < elements.size()) {
            String color2 = elements.get(2).getColor();
            String shape2 = elements.get(2).getShape();
            makeShape(color2, shape2, x, y - 2, gc);
        }
    }

    /**
     * Make colored shape according to the backend data
     * @param color
     * @param shape
     * @param x
     * @param y
     * @param gc
     */
    public void makeShape(String color, String shape, int x, int y, GraphicsContext gc){
        if (color.equals("Green") && shape.equals("Rectangle")){
            gc.setFill(Color.GREEN);
            gc.fillRect(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Yellow") && shape.equals("Rectangle")){
            gc.setFill(Color.YELLOW);
            gc.fillRect(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Blue") && shape.equals("Rectangle")){
            gc.setFill(Color.BLUE);
            gc.fillRect(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Purple") && shape.equals("Rectangle")){
            gc.setFill(Color.PURPLE);
            gc.fillRect(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Green") && shape.equals("Oval")){
            gc.setFill(Color.GREEN);
            gc.fillOval(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Yellow") && shape.equals("Oval")){
            gc.setFill(Color.YELLOW);
            gc.fillOval(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Blue") && shape.equals("Oval")){
            gc.setFill(Color.BLUE);
            gc.fillOval(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Purple") && shape.equals("Oval")){
            gc.setFill(Color.PURPLE);
            gc.fillOval(x,y,20,20);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, 20, 20);
        }
        else if (color.equals("Green") && shape.equals("RoundedRect")){
            gc.setFill(Color.GREEN);
            gc.fillRoundRect(x,y,30,30,10.0,10.0);
        }
        else if (color.equals("Yellow") && shape.equals("RoundedRect")){
            gc.setFill(Color.YELLOW);
            gc.fillRoundRect(x,y,30,30,10.0,10.0);
        }
        else if (color.equals("Blue") && shape.equals("RoundedRect")){
            gc.setFill(Color.BLUE);
            gc.fillRoundRect(x,y,30,30,10.0,10.0);
        }
        else if (color.equals("Purple") && shape.equals("RoundedRect")){
            gc.setFill(Color.PURPLE);
            gc.fillRoundRect(x,y,30,30,10.0,10.0);
        }
        else if (color.equals("Green") && shape.equals("Text")){
            gc.setFill(Color.GREEN);
            gc.setFont(new Font(50));
            gc.fillText("A", x, y);
        }
        else if (color.equals("Yellow") && shape.equals("Text")){
            gc.setFill(Color.YELLOW);
            gc.setFont(new Font(50));
            gc.fillText("A", x, y);
        }
        else if (color.equals("Blue") && shape.equals("Text")){
            gc.setFill(Color.BLUE);
            gc.setFont(new Font(50));
            gc.fillText("A", x, y);
        }
        else if (color.equals("Purple") && shape.equals("Text")){
            gc.setFill(Color.PURPLE);
            gc.setFont(new Font(50));
            gc.fillText("A", x, y);
        }
    }
}
