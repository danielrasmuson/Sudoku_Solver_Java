import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class run {
    public static void main(String[] args) {
        // todo maybe I should have a refresh board taht looks for one and then sovles those to one and adds possiblites to squares



        // start timer
        long startTime = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < 10000000; i++) {
            total += i;
        }
        String[][] boardNumbers = getBoardFromFile("test2.txt");
        Board board = new Board(boardNumbers); // this could speed me up by 8ms
        System.out.println(board.printBoard());

        // main
        // System.out.println(board.getSquare("d1").getName());
        board.initSquaresSolveOneSolutions(); // solving squares with only 1 possiblity
        // System.out.println(board.printBoard());
        // board.solveTwoSolution();
        // board.solveOneSolutions(); // solving squares with only 1 possiblity
        // for (Square square : board.squaresOne){
        //     if (square.getBox() == 4 && square.getValue() == 0){
        //         System.out.print(square.getName()+" ---> ");
        //         System.out.println(square.getPossible());
        //     }
        // }

        

        System.out.println(board.printBoard());


        // end timer
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Milliseconds: "+elapsedTime);

    }
    public static int recurse(Square square, Board board){
        // sentienal value
        if (board.isLastSquare(square)){ //todo top right cell dynamic
            square.setValue(square.getPossible().get(0));
            return 1; // solved
        }

        // recursion
        for (int possible : square.getPossible()){
            if (possible > square.getValue()){
                square.setValue(possible);
                Square nSquare = board.nextSquare(square);
                if (recurse(nSquare,board) == 1){
                    return 1; // solved
                }else{
                    nSquare.setValue(0); // jumped back
                    // some way to undo recursion
                }
            }
        }

        // cant solve
        return 0;
    }
    public static String[][] getBoardFromFile(String filePath){
        int SIZE_OF_BOARD = 9;
        String[][] board = new String[SIZE_OF_BOARD][];

        // read file and add to board array
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filePath));
            int i = 0; // counter
            while ((sCurrentLine = br.readLine()) != null) {
                board[i] = sCurrentLine.split(" ");
                i += 1;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null)br.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return board;
    }
}