package sample;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.*;

public class Main extends Application{

    private Cells gofL;
    private static String[][] seed;
    private static int height;
    private static int width;
    private Scene game;
    private GridPane gridPane;
    private Stage window;
    private FillTransition[][] fillTransitions;
    private Rectangle[][] squares;
    private Text text;
    private Text gen;
    private int generation;
    private VBox vBox;
    private HBox hBox;



    public static void main(String[] args) throws IOException {

        Scanner file = new Scanner(new File("seed.txt"));

        int numRows = Integer.parseInt(file.nextLine());
        int numCols = Integer.parseInt(file.nextLine());

        seed = new String[numRows][numCols];

        for (int r = 0; r < seed.length; r++) {
            seed[r] = file.nextLine().split("");
        }

        height = 1000;
        width = 1450;

        launch();
    }

    @Override
    public void start(Stage window) {
        this.window = window;
        window.setTitle("Game of Life");
        gofL = new Cells(seed);

        vBox = new VBox();

        gridPane = new GridPane();
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().setAll(hBox, gridPane);
        game = new Scene(vBox, width, height);

        window.setScene(game);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 5));
        gridPane.setVgap(2);
        gridPane.setHgap(2);

        squares = new Rectangle[gofL.getCells().length][gofL.getCells()[0].length];
        fillTransitions = new FillTransition[gofL.getCells().length][gofL.getCells()[0].length];

        text = new Text();
        String tex = "Generation ";
        text.setText(tex);
        text.setFont(new Font("Verdana",30));

        generation = 0;
        gen = new Text();
        gen.setText(String.valueOf(generation));
        gen.setFont(new Font("Verdana",30));

        hBox.getChildren().setAll(text, gen);

        for (int r = 0; r < gofL.getCells().length; r++) {
            for (int c = 0; c < gofL.getCells()[r].length; c++) {

                squares[r][c] = new Rectangle();
                squares[r][c].setHeight(15);
                squares[r][c].setWidth(15);
                GridPane.setRowIndex(squares[r][c], r);
                GridPane.setColumnIndex(squares[r][c], c);
                gridPane.getChildren().add(squares[r][c]);

                if(gofL.getCells()[r][c].isAlive()){
                    squares[r][c].setFill(Color.RED);
                }
                else{
                    squares[r][c].setFill(Color.BLACK);
                }

                fillTransitions[r][c] = new FillTransition(new Duration(1), squares[r][c]);
            }
        }

        gofL.setCells();

        generation++;
        gen.setText(String.valueOf(generation));

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                event -> run()
        ));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        run();

        window.show();


    }

    private void run(){

        generation++;
        gen.setText(String.valueOf(generation));

        for (int r = 0; r < gofL.getCells().length; r++) {
            for (int c = 0; c < gofL.getCells()[r].length; c++) {


                if(gofL.getCells()[r][c].isAlive()){
                    fillTransitions[r][c].setToValue(Color.RED);
                    fillTransitions[r][c].play();
                }
                else{
                    fillTransitions[r][c].setToValue(Color.BLACK);
                    fillTransitions[r][c].play();
                }
            }
        }

        gofL.setCells();

    }


}
