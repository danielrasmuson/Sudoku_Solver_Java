import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class run {
    public static void main(String[] args) {
        // start timer
        long startTime = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < 10000000; i++) {
            total += i;
        }

        // main
<<<<<<< HEAD
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
        recurse(board.getStartingSquare(), board);

        

=======
        String[][] boardNumbers = getBoardFromFile("test1.txt");
        Board board = new Board(boardNumbers);
        System.out.println(board.printBoard());
        // add in contraint searching
        recurse(board.getStartingSquare(), board);
>>>>>>> parent of 027c106... Lots of work, not much success.
        System.out.println(board.printBoard());

        // end timer
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Milliseconds: "+elapsedTime);
    }
    public static int recurse(Square square, Board b){
        // sentienal value
        if (square.getName().equals("i9")){ //todo top right cell dynamic
            square.setValue(square.getPossible().get(0));
            return 1; // solved
        }

        // recursion
        for (int possible : square.getPossible()){
            // todo add fartherest variable so it doesnt loop throughr every square
            b.resetAfterSquare(square); // jumped back
            if (possible > square.getValue()){
                square.setValue(possible);
                if (recurse(b.nextSquare(square),b) == 1){
                    return 1; // solved
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