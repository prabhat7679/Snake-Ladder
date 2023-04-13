package com.example.snakeladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    // create variable for board
    public static final int tileSize=40,width=10,height=10;
    // create some variable for button
    public static final int buttonLine=height*tileSize+50,infoLine=buttonLine-30;

    private static Dice dice= new Dice();
    private Player playerOne,playerTwo;
    private boolean gameStarted=false,playerOneTurn= false,playerTwoTurn = false;


    // create a Pane methods
     private Pane createContent(){
         Pane root = new Pane();
         root.setPrefSize(width*tileSize, height*tileSize+100); // size of total area
         // for creating 2D grid
         for (int i = 0; i <height; i++) {
             for(int j=0;j<width;j++){
                 // create tiles
                 Tile tile= new Tile(tileSize);  // create Tile class and call
                 //  set to their right position
                 tile.setTranslateX(j*tileSize);
                 tile.setTranslateY(i*tileSize);
                 root.getChildren().add(tile);  // function to add tiles to root
             }
         }

         Image img = new Image("C:\\Users\\pkg80\\IdeaProjects\\SnakeLadder\\src\\main\\istockphoto-531466314-612x612.jpg");
         ImageView board = new ImageView();
         board.setImage(img);
         board.setFitHeight(height*tileSize);
         board.setFitWidth(width*tileSize);


         // create botton
         Button playerOneButton= new Button("Player One");
         Button playerTwoButton= new Button("Player Two");
         Button startButton= new Button("Start");
          //   translate  or set to their right position
         playerOneButton.setTranslateY(buttonLine);
         playerOneButton.setTranslateX(20);
         playerOneButton.setDisable(true);
         playerTwoButton.setTranslateY(buttonLine);
         playerTwoButton.setTranslateX(310);
         playerTwoButton.setDisable(true);
         startButton.setTranslateY(buttonLine);
         startButton.setTranslateX(180);

         // info display
         Label playerOneLevel= new Label("");
         Label playerTwoLevel= new Label("");
         Label diceLevel= new Label("Start The Game");
        // translate or set to their right position
         playerOneLevel.setTranslateY(infoLine);
         playerOneLevel.setTranslateX(25);
         playerTwoLevel.setTranslateY(infoLine);
         playerTwoLevel.setTranslateX(310);
         diceLevel.setTranslateY(infoLine);
         diceLevel.setTranslateX(165);

         playerOne= new Player(tileSize, Color.BLACK,"Prabhat");
         playerTwo= new Player(tileSize-5, Color.WHITE,"Apu");

         // player action
         playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 if(gameStarted){
                     if(playerOneTurn){
                         // 3 steps    // play the game
                         int diceValue = dice.getRolledDiceValue();     // 1st .. roll the dice
                         diceLevel.setText("Dice Value : "+ diceValue);    // 2nd ... see the value
                         playerOne.movePlayer(diceValue);    // move the player

                         // check the wining condition

                         if(playerOne.isWinner()) {
                             diceLevel.setText("Winner is " + playerOne.getName());

                             playerTwoTurn = false;
                             playerTwoButton.setDisable(true);
                             playerTwoLevel.setText("");

                             // enable 1st player to paly the game
                             playerOneTurn = false;
                             playerOneButton.setDisable(true);
                             playerOneLevel.setText("");

                             startButton.setDisable(false);
                             startButton.setText("Restart");
                             gameStarted= false;

                         }else {
                             // disable 1st player from the game
                             playerOneTurn = false;
                             playerOneButton.setDisable(true);
                             playerOneLevel.setText("");

                             // enable 2nd player to paly the game
                             playerTwoTurn = true;
                             playerTwoButton.setDisable(false);
                             playerTwoLevel.setText("Your Turn " + playerTwo.getName());
                         }
                     }
                 }
             }
         });

         playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 if(gameStarted){
                     if(playerTwoTurn){
                         // 3 steps    // play the game
                         int diceValue = dice.getRolledDiceValue();     // 1st .. roll the dice
                         diceLevel.setText("Dice Value : "+ diceValue);    // 2nd ... see the value
                         playerTwo.movePlayer(diceValue);    // move the player

                         // check the wining condition
                         if(playerTwo.isWinner()){
                             diceLevel.setText("Winner is "+playerTwo.getName());

                             playerTwoTurn = false;
                             playerTwoButton.setDisable(true);
                             playerTwoLevel.setText("");

                             // enable 1st player to paly the game
                             playerOneTurn = false;
                             playerOneButton.setDisable(true);
                             playerOneLevel.setText("");

                             startButton.setDisable(false);
                             startButton.setText("Restart");
                             gameStarted= false;

                         }else {
                             // disable 2nd player from the game
                             playerTwoTurn = false;
                             playerTwoButton.setDisable(true);
                             playerTwoLevel.setText("");

                             // enable 1st player to paly the game
                             playerOneTurn = true;
                             playerOneButton.setDisable(false);
                             playerOneLevel.setText("Your Turn " + playerOne.getName());
                         }
                     }
                 }
             }
         });

         startButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 gameStarted= true;
                 diceLevel.setText("Game Started ");
                 startButton.setDisable(true);

                 playerOneTurn= true;
                 playerOneLevel.setText("Your Turn "+ playerOne.getName());
                 playerOneButton.setDisable(false);
                 playerOne.startingPosition();

                 playerTwoTurn= false;
                 playerTwoLevel.setText("");
                 playerTwoButton.setDisable(true);
                 playerTwo.startingPosition();
             }
         });

         root.getChildren().addAll(board,playerOneButton,playerTwoButton,startButton,
                 playerOneLevel,playerTwoLevel,diceLevel,
                 playerOne.getCoin(),playerTwo.getCoin()
         );
         return root;
     }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake&Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}