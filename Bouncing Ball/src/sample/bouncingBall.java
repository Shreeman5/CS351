package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class bouncingBall extends Application {

    private Timeline timeline;
    int score = 0;
    List<Integer> myList;
    ArrayList<Point> centers;
    Bounds bounds;
    List<Integer> wallValues;
    double dx,dy;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bouncing Ball Game");

        Pane pane = new Pane();
        Scene scene = new Scene(pane, 500, 550);

        getRandomNumber();
        makeBoard(pane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void getRandomNumber() {
        System.out.println("NEW GAME!!!!");
        myList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++){
            int randNum = rand.nextInt(121);
            if (!myList.contains(randNum)){
                myList.add(randNum);
            }
            else{
                i--;
            }
        }
        System.out.println(myList);
    }

    public void makeBoard(Pane pane) {
        Random rand = new Random();
        dx = (double)rand.nextInt(10) + 1; //Step on x or velocity
        dy = (double)rand.nextInt(10) + 1; //Step on y
        System.out.println("dx: " +dx);
        System.out.println("dy: " +dy);
        wallValues = new ArrayList<>();
        centers = new ArrayList<>();
        int spacing = 5;
        int k = 0;
        Canvas canvas = new Canvas(500, 500);

        bounds = canvas.getBoundsInLocal();
        System.out.println(bounds);


        Label text = new Label("Score: ");
        text.setLayoutX(205);
        text.setLayoutY(522);

        Label visualScore = new Label("0000");
        visualScore.setLayoutX(240);
        visualScore.setLayoutY(522);


        Button reset = new Button("Reset");
        reset.setLayoutX(20.0);
        reset.setLayoutY(515.0);
        reset.setDisable(true);

        Button play = new Button("Play");
        play.setLayoutX(450.0);
        play.setLayoutY(515.0);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0,0,500,445);
        for (int j = 0; j < 11; j++){
            for (int i = 0; i < 11; i++){
                if (myList.contains(k)){
                    gc.setFill(Color.BLUE);
                }
                else{
                    gc.setFill(Color.ORANGE);
                }
                gc.fillRect(spacing + i * 45, spacing + j * 40,
                        50 - 2 * spacing, 45 - 2 * spacing);
                int a = spacing + i * 45;
                int b = spacing + j * 40;
                int c = 50 - 2 * spacing;
                int d = 45 - 2 * spacing;
                int x = (a+(a+c))/2;
                int y = (b+(b+d))/2;
                centers.add(new Point(x, y));
                k++;
            }
        }
        //System.out.println(centers);
        gc.setFill(Color.GRAY);
        gc.fillRect(0,445,500,55);
        pane.getChildren().addAll(canvas, reset, play, visualScore, text);

        moveBall(gc, play, reset, pane, visualScore, canvas);
    }

    public void moveBall(GraphicsContext gc, Button play, Button reset, Pane pane, Label visualScore, Canvas canvas){
        Circle ball = new Circle(4, Color.RED);
        ball.relocate(248, 473);

        pane.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();

                if (y < 500 && y > 445 && x > 0 && x < 500) {
                    ball.relocate(x, y);
                }
                else{
                    System.out.println("You cannot relocate here!");
                }

            }
        });

        play.setOnAction(e -> {
            timeline = new Timeline(new KeyFrame(Duration.millis(50),
                    new EventHandler<>() {
                        int x;
                        int y;
                        int startScore = 0;
                        boolean resetStatus = false;

                        @Override
                        public void handle(ActionEvent t) {
                            //move the ball
                            System.out.println("LayoutX: " +ball.getLayoutX());
                            System.out.println("LayoutY: " +ball.getLayoutY());

                            ball.setLayoutX(ball.getLayoutX() + dx);
                            ball.setLayoutY(ball.getLayoutY() + dy);



                            //System.out.println(bounds);

                            //If the ball reaches the left or right border make the step negative
                            if(ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius()) ||
                                    ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius()) ){
                                int val = score - startScore;
                                if (checkForWallHits(val)) {
                                    resetStatus = true;
                                    reset(timeline, play, reset, pane, canvas);
                                }
                                dx = -dx;
                                startScore = score;
                            }

                            //If the ball reaches the bottom or top border make the step negative
                            if((ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius() - 5)) ||
                                    (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius() - 5))){
                                int val = score - startScore;
                                if (checkForWallHits(val)) {
                                    resetStatus = true;
                                    reset(timeline, play, reset, pane, canvas);
                                }
                                dy = -dy;
                                startScore = score;
                            }

                            if (!resetStatus) {
                                x = (int) ball.getLayoutX();
                                y = (int) ball.getLayoutY();

                                /*System.out.println("Current X position: " +x);
                                System.out.println("Current Y position: " +y);*/

                                if (y > 445) {
                                    System.out.println("I'm outside the canvas!");
                                } else {
                                    int currTile = 0;
                                    int blueTile = 0;
                                    int reqX = 0;
                                    int reqY = 0;
                                    int box = 0;
                                    int xDiff;
                                    int yDiff;
                                    int a;
                                    int b = 2000;
                                    for (Point q : centers) {
                                        xDiff = Math.abs(q.x - x);
                                        yDiff = Math.abs(q.y - y);
                                        a = xDiff + yDiff;
                                        if (a < b) {
                                            reqX = q.x;
                                            reqY = q.y;
                                            b = a;
                                            currTile = blueTile;
                                        }
                                        //  System.out.println("Difference for Box " +box+ " is " +a);
                                        box++;
                                        blueTile++;
                                    }
                                    System.out.println("CurrTile: " + currTile);
                                    //System.out.println("Box x: " +reqX+ " Box y: " +reqY);
                                    if (checkForBlueColor(currTile, visualScore)) {
                                        colorBox(reqX, reqY, gc);
                                    }
                                }
                            }
                        }
                    }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            reset.setDisable(false);
            play.setDisable(true);
            //System.out.println("System MalFunction");
            EventHandler<MouseEvent> handler = MouseEvent::consume;
            canvas.addEventFilter(MouseEvent.ANY, handler);
            reset.setOnAction(f -> reset(timeline, play, reset, pane, canvas));

        });
        pane.getChildren().add(ball);
    }


    public boolean checkForWallHits(int val){
        wallValues.add(val);
        //System.out.println(wallValues.size());
        if (wallValues.size() > 2){
            for (int i = 0; i < wallValues.size() - 2; i++){
                if (wallValues.get(i) == 0){
                    if (wallValues.get(i + 1) == 0){
                        if (wallValues.get(i + 2) == 0){
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println(wallValues);
        return false;
    }

    public boolean checkForBlueColor(int currTile, Label visualScore){
        if(myList.contains(currTile)){
            System.out.println("Tile already blue");
            return false;
        }
        else{
            System.out.println("Tile not blue");
            if (!myList.contains(currTile)) {
                score += 10;
                String visNum = Integer.toString(score);
                visualScore.setText("Score: ");
                visualScore.setText(visNum);
                //System.out.println("Score: " +score);
                myList.add(currTile);
            }
            return true;
        }
    }

    public void colorBox(int reqX, int reqY, GraphicsContext gc){
        System.out.println("Coloring this box");
        gc.setFill(Color.BLUE);
        gc.fillRect(reqX - 20, reqY - 17, 40, 35);
    }

    public void reset(Timeline timeline, Button play, Button reset, Pane pane, Canvas canvas){
        //System.out.println("Your score is " +score);
        timeline.stop();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your score is "
                + score + ". Do you want to play again?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        alert.setOnHidden(evt -> {
            if (alert.getResult() == ButtonType.YES) {
                EventHandler<MouseEvent> handler = MouseEvent::consume;
                canvas.removeEventFilter(MouseEvent.ANY, handler);
                dx = 0;
                dy = 0;
                score = 0;
                reset.setDisable(true);
                play.setDisable(false);
                myList.clear();
                centers.clear();
                wallValues.clear();
                pane.getChildren().clear();
                getRandomNumber();
                makeBoard(pane);
            }
            else{
                System.exit(0);
            }
        });
        alert.show();

    }
}
