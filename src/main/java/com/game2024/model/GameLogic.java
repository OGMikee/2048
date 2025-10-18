package com.game2024.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

public class GameLogic {
    private int[][] board;
    private int score;
    private Random random;
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private int bestScore;
    private Preferences prefs;


    public GameLogic() {
        board = new int[4][4];
        score = 0;
        random = new Random();
        prefs = Preferences.userNodeForPackage(GameLogic.class);
        loadBestScore();
    }

    public void addRandomTile(){
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                if (this.board[row][col] == 0){
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        if (!emptyCells.isEmpty()){
            int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            int value = random.nextDouble() < 0.9 ? 2 : 4;
            board[randomCell[0]][randomCell[1]] = value;
        }
    }

    public void startNewGame() {
        score = 0;
        board = new int[4][4];
        addRandomTile();
        addRandomTile();
    }

    public boolean move(Direction direction) {
        boolean moved = false;
        if (direction == Direction.LEFT) {
            for (int row = 0; row < 4; row++) {
                int[] original = Arrays.copyOf(board[row], 4);
                int[] rowNoZero = Arrays.stream(board[row])
                        .filter(n -> n != 0)
                        .toArray();
                for (int col = 0; col < rowNoZero.length - 1; col ++){
                    if (rowNoZero[col] == rowNoZero[col + 1]){
                        rowNoZero[col] *=2;
                        score += rowNoZero[col];
                        rowNoZero[col + 1] = 0;
                    }
                }
                int [] mergedNoZero = Arrays.stream(rowNoZero)
                        .filter(n -> n != 0)
                        .toArray();
                board[row] = Arrays.copyOf(mergedNoZero, 4);
                if (!Arrays.equals(original, board[row])){
                    moved = true;
                }
            }
        }
        if (direction == Direction.RIGHT) {
            for (int row = 0; row < 4; row++) {
                int[] original = Arrays.copyOf(board[row], 4);
                int[] rowNoZero = Arrays.stream(board[row])
                        .filter(n -> n != 0)
                        .toArray();
                for (int col = rowNoZero.length - 1; col > 0; col --){
                    if (rowNoZero[col] == rowNoZero[col - 1]){
                        rowNoZero[col] *=2;
                        score += rowNoZero[col];
                        rowNoZero[col - 1] = 0;
                        col--;
                    }
                }
                int[] mergedNoZero = Arrays.stream(rowNoZero)
                        .filter(n -> n != 0)
                        .toArray();
                int[] finalRow = new int[4];
                int startIndex = 4 - mergedNoZero.length;
                for (int i = 0; i < mergedNoZero.length; i++){
                    finalRow[startIndex + i] = mergedNoZero[i];
                }
                board[row] = finalRow;
                if (!Arrays.equals(original, board[row])){
                    moved = true;
                }
            }
        }


        if (direction == Direction.UP){
            for (int col = 0; col < 4; col++) {
                int[] original = new int[4];
                int[] columnNoZero = new int[4];
                int index = 0;

                for (int row = 0; row < 4; row ++){
                    original[row] = board[row][col];
                    if (board[row][col] != 0){
                        columnNoZero[index++] = board[row][col];
                    }
                }

                for (int row = 0; row < index - 1; row++){
                    if (columnNoZero[row] == columnNoZero[row + 1]){
                        columnNoZero[row]*=2;
                        score+=columnNoZero[row];
                        columnNoZero[row + 1] = 0;
                        row--;
                    }
                }
                int[] mergedNoZero = new int[4];
                int mergedIndex = 0;
                for (int row = 0; row < 4; row++){
                    if (columnNoZero[row] != 0 ){
                        mergedNoZero[mergedIndex++] = columnNoZero[row];
                    }
                }
                int[] finalColumn = new int[4];
                for (int row = 0; row < mergedIndex; row++){
                    finalColumn[row] = mergedNoZero[row];
                }
                for (int row = 0; row < 4; row++){
                    board[row][col]= finalColumn[row];
                }
                for (int row = 0; row < 4; row++){
                    if (original[row] != board[row][col]){
                        moved = true;
                        break;
                    }
                }
            }
        }

        if (direction == Direction.DOWN){
            for (int col = 0; col < 4; col++) {
                int[] original = new int[4];
                for (int row = 0; row < 4; row++){
                    original[row] = board[row][col];
                }
                int[] columnNoZero = new int[4];
                int index = 0;

                for (int row = 0; row < 4; row ++){
                    if (board[row][col] != 0){
                        columnNoZero[index++] = board[row][col];
                    }
                }

                for (int row = index -1; row > 0; row--){
                    if (columnNoZero[row] == columnNoZero[row - 1]){
                        columnNoZero[row]*=2;
                        score+=columnNoZero[row];
                        columnNoZero[row - 1] = 0;
                        row--;
                    }
                }
                int[] mergedNoZero = new int[4];
                int mergedIndex = 0;
                for (int row = 0; row < 4; row++){
                    if (columnNoZero[row] != 0 ){
                        mergedNoZero[mergedIndex++] = columnNoZero[row];
                    }
                }
                int[] finalColumn = new int[4];
                if (mergedIndex > 0){
                    int startRow = 4 - mergedIndex;
                    for (int i = 0; i < mergedIndex; i++) {
                        finalColumn[startRow + i] = mergedNoZero[i];
                    }
                }

                for (int row = 0; row < 4; row++){
                    board[row][col]= finalColumn[row];
                }
                for (int row = 0; row < 4; row++){
                    if (original[row] != board[row][col]){
                        moved = true;
                        break;
                    }
                }
            }
        }



        if (moved) {
            addRandomTile();
            updateBestScore();
        }
        return moved;
    }

    public boolean isGameOver(){
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                if (board[row][col] == 0){
                    return false;
                }
                if (hasAdjacentNeighbour(row, col, board[row][col])){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasAdjacentNeighbour(int x, int y, int value){
        if (x != 0 && board[x-1][y] == value){
            return true;
        }
        if (x != 3 && board[x+1][y] == value){
            return true;
        }
        if (y != 0 && board[x][y-1] == value){
            return true;
        }
        if (y != 3 && board[x][y+1] == value){
            return true;
        }
        return false;
    }

    public boolean hasWon(){
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                if (board[row][col] == 2048){
                    return true;
                }
            }
        }
        return false;
    }

    private void loadBestScore(){
        bestScore = prefs.getInt("bestScore", 0);
    }

    private void saveBestScore(){
        prefs.putInt("bestScore", bestScore);
    }

    private void updateBestScore(){
        if (score > bestScore){
            bestScore = score;
            saveBestScore();
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }
}