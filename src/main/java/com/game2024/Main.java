package com.game2024;
import com.game2024.model.GameLogic;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private GameLogic gameLogic;
    private Label[][] tiles = new Label[4][4];
    private Stage primaryStage;
    private Label scoreLabel;
    private Label bestScoreLabel;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("2024");

        gameLogic = new GameLogic();
        gameLogic.startNewGame();

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #faf8ef;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: #bbada0; -fx-padding: 10;");

        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                Label tile = new Label("");
                tiles[row][col] = tile;
                tile.setPrefWidth(100);
                tile.setPrefHeight(100);
                int value = gameLogic.getBoard()[row][col];
                if (value != 0) {
                    tile.setText(String.valueOf(value));
                }
                tile.setStyle("-fx-background-color: #cdc1b4; -fx-alignment: center; -fx-font-size: 24; -fx-font-weight: bold;");
                grid.add(tile, col, row);
            }
        }

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #776e65;");


        bestScoreLabel = new Label("Best Score:" + gameLogic.getBestScore());
        bestScoreLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #776e65;");

        HBox scoreBoxes = new HBox(50);
        scoreBoxes.setAlignment(Pos.CENTER);
        scoreBoxes.getChildren().addAll(bestScoreLabel, scoreLabel);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(scoreBoxes, grid);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 500, 600);

        scene.setOnKeyPressed(keyEvent -> {
            boolean moved = false;
            switch (keyEvent.getCode()){
                case LEFT -> moved = gameLogic.move(GameLogic.Direction.LEFT);
                case DOWN -> moved = gameLogic.move(GameLogic.Direction.DOWN);
                case UP -> moved = gameLogic.move(GameLogic.Direction.UP);
                case RIGHT -> moved = gameLogic.move(GameLogic.Direction.RIGHT);
                default -> {}
            }
            if (moved){
                updateDisplay();
                if (gameLogic.hasWon()){
                    showWinScreen(primaryStage);
                }
                if (gameLogic.isGameOver()){
                    showGameOverScreen(primaryStage);
                }
            }
        });


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void updateDisplay(){
        int[][] board = gameLogic.getBoard();
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                int value = board[row][col];
                Label tile = tiles[row][col];

                if (value==0){
                    tile.setText("");
                }
                else{
                    tile.setText(String.valueOf(value));
                }
                tile.setStyle(getTileStyle(value));
            }
        }
        scoreLabel.setText("Score: " + gameLogic.getScore());
    }

    private String getTileStyle(int value){
        String baseStyle = "-fx-alignment: center; -fx-font-size: 24; -fx-font-weight: bold;";
        switch (value){
            case 0:
                return baseStyle + "-fx-background-color: #cdc1b4;";
            case 2:
                return baseStyle + "-fx-background-color: #eee4da; -fx-text-fill: #776e65;";
            case 4:
                return baseStyle + "-fx-background-color: #ede0c8; -fx-text-fill: #776e65;";
            case 8:
                return baseStyle + "-fx-background-color: #f2b179; -fx-text-fill: #f9f6f2;";
            case 16:
                return baseStyle + "-fx-background-color: #f59563; -fx-text-fill: #f9f6f2;";
            case 32:
                return baseStyle + "-fx-background-color: #f67c5f; -fx-text-fill: #f9f6f2;";
            case 64:
                return baseStyle + "-fx-background-color: #f65e3b; -fx-text-fill: #f9f6f2;";
            case 128:
                return baseStyle + "-fx-background-color: #edcf72; -fx-text-fill: #f9f6f2; -fx-font-size: 20;";
            case 256:
                return baseStyle + "-fx-background-color: #edcc61; -fx-text-fill: #f9f6f2; -fx-font-size: 20;";
            case 512:
                return baseStyle + "-fx-background-color: #edc850; -fx-text-fill: #f9f6f2; -fx-font-size: 20;";
            case 1024:
                return baseStyle + "-fx-background-color: #edc53f; -fx-text-fill: #f9f6f2; -fx-font-size: 18;";
            case 2048:
                return baseStyle + "-fx-background-color: #edc22e; -fx-text-fill: #f9f6f2; -fx-font-size: 18;";
            default:
                return baseStyle + "-fx-background-color: #3c3a32; -fx-text-fill: #f9f6f2;";
        }
    }

    private void showGameOverScreen(Stage stage){
        VBox gameOverLayout = new VBox(30);
        gameOverLayout.setAlignment(Pos.CENTER);
        gameOverLayout.setStyle("-fx-background-color: #faf8ef; -fx-padding: 50;");
        Label gameOverTitle = new Label("GAME OVER");
        gameOverTitle.setStyle("-fx-font-size: 60; -fx-font-weight: bold; -fx-text-fill: #776e65;");
        Label scoreLabel = new Label("YOUR SCORE: " + gameLogic.getScore());
        scoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #776e65;");
        Label bestScoreLabel = new Label("YOUR BEST SCORE: " + gameLogic.getBestScore());
        bestScoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #776e65;");

        Button playAgain = playAgainButton(stage);

        gameOverLayout.getChildren().addAll(gameOverTitle, scoreLabel, bestScoreLabel, playAgain);
        Scene gameOverScene = new Scene(gameOverLayout, 500, 600);
        stage.setScene(gameOverScene);
    }

    private void showWinScreen(Stage stage){
        VBox youWinLayout = new VBox(30);
        youWinLayout.setAlignment(Pos.CENTER);
        youWinLayout.setStyle("-fx-background-color: #faf8ef; -fx-padding: 50;");
        Label youWinTitle = new Label("YOU WIN");
        youWinTitle.setStyle("-fx-font-size: 60; -fx-font-weight: bold; -fx-text-fill: #776e65;");
        Label scoreLabel = new Label("YOUR SCORE: " + gameLogic.getScore());
        scoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #776e65;");
        Label bestScoreLabel = new Label("YOUR BEST SCORE: " + gameLogic.getBestScore());
        bestScoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: #776e65;");

        Button playAgain = playAgainButton(stage);

        youWinLayout.getChildren().addAll(youWinTitle, scoreLabel, bestScoreLabel, playAgain);
        Scene gameOverScene = new Scene(youWinLayout, 500, 600);
        stage.setScene(gameOverScene);
    }

    private Button playAgainButton(Stage stage){
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 24; -fx-padding: 15 30; -fx-background-color: #8f7a66; -fx-text-fill: white; -fx-font-weight: bold;");
        playAgainButton.setOnAction(actionEvent -> {
            gameLogic.startNewGame();
            start(stage);
        });
        return playAgainButton;
    }

    public static void main(String[] args){
        launch(args);
    }

}
