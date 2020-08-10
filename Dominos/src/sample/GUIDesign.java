package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.awt.*;
import java.util.ArrayList;

public class GUIDesign {

    /**
     * Starts animation
     */
    AnimationTimer animationTimer;
    /**
     * Initiates class MouseFunction
     */
    MouseFunction m;

    /**
     * Designs the background color, sets the stage, shows the scene,
     * incorporates canvas at the center of borderpane.
     * @param playerTray
     * @param computerTray
     * @param boneYard
     * @param primaryStage
     * @param board
     */
    public GUIDesign(PlayerTray playerTray, ComputerTray computerTray, BoneYard
            boneYard, Stage primaryStage, Board board){
        BorderPane bp = new BorderPane();
        Canvas canvas = new Canvas (1400 ,800);
        GraphicsContext gc = canvas.getGraphicsContext2D ();
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0,0,700,600);
        Label leftLabel = new Label("Click on any of the light blue pixels to " +
                "play on the left!");
        leftLabel.setFont(new Font("Arial", 50));
        BorderPane.setAlignment(leftLabel, Pos.CENTER);
        Label rightLabel = new Label("Click on any of the light grey pixels to " +
                "play on the right!");
        rightLabel.setFont(new Font("Arial", 50));
        BorderPane.setAlignment(rightLabel, Pos.CENTER);
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(700,0,700,600);
        gc.setFill(Color.LIGHTSALMON);
        gc.fillRect(0,600,1400,200);
        designPieces(playerTray, gc, board, 8, null);
        startAnimation(canvas, playerTray, gc, computerTray, boneYard, board);
        bp.setTop(leftLabel);
        bp.setBottom(rightLabel);
        bp.setCenter(canvas);
        Scene scene = new Scene(bp, 1400, 1200);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * start animation once the background color has been set up.
     * initiates GUI functionality and logic once mouse is clicked.
     * right click rotates dominos.
     * @param canvas
     * @param playerTray
     * @param gc
     * @param computerTray
     * @param boneYard
     * @param board
     */
    public void startAnimation(Canvas canvas, PlayerTray playerTray,
                               GraphicsContext gc, ComputerTray computerTray, BoneYard boneYard, Board board){
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                canvas.setOnMousePressed(e -> {
                    if(e.getButton() == MouseButton.SECONDARY){
                        int xCor = (int) e.getX()/50;
                        playerTray.swapNumbers(xCor);
                        designPieces(playerTray, gc, board, xCor, e.getButton());
                    }
                    else {
                        m = new MouseFunction(e.getX(), e.getY(), playerTray, gc,
                                computerTray, boneYard, board, animationTimer);
                        designPieces(playerTray, gc, board, (int) (e.getX()/50), e.getButton());
                    }
                });
            }
        };
        animationTimer.start();
    }

    /**
     * designs player pieces(playerTray) after every click. calls
     * designdotsinthisClass method in class board to make dots for the dominos
     * @param playerTray
     * @param gc
     * @param board
     */
    public void designPieces(PlayerTray playerTray, GraphicsContext gc, Board board,
                             int xCor, MouseButton button){
        ArrayList<Point> playerDominos = playerTray.checkPlayerDominos();
        int counter = 0;
        int x = 0;
        int y = 680;
        int w = 50;
        int h = 50;
        for(Point p: playerDominos){
            int middle = (x+x+w)/2;
            gc.setFill(Color.LIGHTCYAN);
            gc.fillRect (x ,y, w, h);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(middle, y, middle, y+h);
            int leftSide = p.x;
            board.designDotsinThisClass(leftSide, middle, gc, x, y);
            int rightSide = p.y;
            board.designDotsinThisClass(rightSide, middle, gc, x + 50, y);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(4);
            gc.strokeRect(x, y, w, h);
            if(counter == xCor && button == MouseButton.SECONDARY){
                gc.setStroke(Color.DARKGOLDENROD);
                gc.setLineWidth(4);
                gc.strokeRect(x, y, w, h);
            }
            else if(counter == xCor && button == MouseButton.PRIMARY){
                gc.setStroke(Color.DARKMAGENTA);
                gc.setLineWidth(4);
                gc.strokeRect(x, y, w, h);
            }
            counter++;
            x += 50;
        }
    }

}
