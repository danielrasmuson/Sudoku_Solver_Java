import java.util.*;
import java.util.Arrays;
class Board {
    // init objects
    private Square[][] squaresTwo; // two dimensional
    public Square[] squaresOne; // one dimensiaonl
    public ArrayList<ArrayList<Square>> boxes;
    public Square lastSquare;
    // public Square farthestSquare;

    public Board(String[][] boardNumbers) {
        // todo it would be better to have subclasses of this for box, column, square
        Square[][] squaresTwo = new Square[boardNumbers.length][boardNumbers.length];
        Square[] squaresOne = new Square[81]; // TODO change 81 to dynamic

        int counter = 0;
        for (int x = 0; x < boardNumbers.length; x++){
            for (int i = 0; i < boardNumbers[x].length; i++){
                // each square will have attributes describitn itself
                int startingNum = Integer.parseInt(boardNumbers[x][i]);
                int row = "876543210".indexOf(Integer.toString(x)); // because the puzzle is reversed
                int column = i; // because the puzzle is reversed
                Square square = new Square(this, startingNum, column, row);
                squaresTwo[x][i] = square;
                squaresOne[counter] = square;
                counter += 1;
            }
        }
        // todo better to just have one but lazy - little slower
        this.squaresTwo = squaresTwo;
        this.squaresOne = squaresOne;

        // sets last square - the reason we cant just return i9 is because i9 might not be found by nextSquare
        outerloop:
        for (int i = 0; i < squaresTwo.length; i++){
            for (int x = squaresTwo[i].length-1; x >= 0; x--){
                if (!(squaresTwo[x][i].isConcrete()))
                    this.lastSquare = squaresTwo[i][x];
                    break outerloop;
            }
        }

        setLoopBoxes();
    }
    public void setLoopBoxes(){
        ArrayList<ArrayList<Square>> boxes = new ArrayList<ArrayList<Square>>();
        // todo I can probably make this a anormal array of 9
        // add onto the array list
        for (int i = 0; i < 9; i++){
            boxes.add(new ArrayList<Square>());
        }

        for (int i = 0; i < squaresTwo.length; i++){
            for (int x = 0; x < squaresTwo[i].length; x++){
                boxes.get(squaresTwo[i][x].getBox()).add(squaresTwo[i][x]);
            }
        }
        this.boxes = boxes;
    }
    public Square getSquare(String coord){
        // should be passed like a1 --> bottom left corner (like chess)
        int y = "876543210".indexOf(coord.substring(1,2))+1;
        int x = "abcdefghi".indexOf(coord.substring(0,1));
        return squaresTwo[y][x];
    }
    public Square getSquareCord(int x, int y){
        int yR = "876543210".indexOf(Integer.toString(y));
        return squaresTwo[yR][x];
    } 
    public String printBoard(){
        String boardPrint = "---------------------\n";
        int currentRow = 8;
        for (Square square : this.squaresOne){
            if (square.getRow() != currentRow){
                boardPrint += "\n";
                currentRow = square.getRow();
            }
            boardPrint += square.getValue()+" ";
        }
        boardPrint += "\n---------------------";
        return boardPrint;
    }
    public Square nextSquare(Square currentSquare){
        int cRow = currentSquare.getRow();
        int cColumn = currentSquare.getColumn();
        if(cColumn == 8 && cRow == 8){
            // todo this might cause problems
            return currentSquare;
        }
        // todo i can cleanup the next 4 loops

        if(cColumn == 8){
            Square newSquare = getSquareCord(0,cRow+1);
            if (newSquare.isConcrete()){
                return nextSquare(newSquare);
            }else{
                return newSquare;                
            }
        }else{
            Square newSquare = getSquareCord(cColumn+1,cRow);
            if (newSquare.isConcrete()){
                return nextSquare(newSquare);
            }else{
                return newSquare;
            }
        }
    }
    public Square previousSquare(Square currentSquare){
        // currentSquare.getColumn()
        int cRow = currentSquare.getRow();
        int cColumn = currentSquare.getColumn();
        if(cColumn == 0 && cRow == 0){
            // no solution
            System.out.println("NO SOLUTION FOUND");
            System.exit(0);
            // return currentSquare; // its the last square, no next square
        }
        if(cColumn == 0){
            Square newSquare = getSquareCord(8,cRow-1);
            if (newSquare.isConcrete()){
                return previousSquare(newSquare);
            }else{
                return newSquare; // next row
            }
        }else{
            Square newSquare = getSquareCord(cColumn-1,cRow);
            if (newSquare.isConcrete()){
                return previousSquare(newSquare);
            }else{
                return newSquare; // next row
            }
        }
    }
    public Square getStartingSquare(){
        // dont want to start on cell a1 if it is concrete
        for (int i = squaresTwo.length-1; i >= 0; i--){
            for (int x = 0; x < squaresTwo[i].length; x++){
                if (squaresTwo[i][x].isConcrete() == false){
                    return squaresTwo[i][x];
                }
            }
        }
        return squaresOne[10000000]; // this will never happen to avoid error
    }
    public boolean isLastSquare(Square square){
        if (square == this.lastSquare){
            return true;
        }else{
            return false;
        }
    }
    public void initSquaresSolveOneSolutions(){
        // is possiblity is only one will set is as the solution and make it unchangable
        // if anything was changed will return true
        // loop through every square
        // sets possible for each square, i cant set this tell all the squares are made
        for (int x = 0; x < squaresTwo.length; x++){
            for (int i = 0; i < squaresTwo[x].length; i++){
                squaresTwo[x][i].setPossible();
            }
        }
    }
    public void resetAfterSquare(Square square){
        // resets every number after current square to 0
        // todo top left square
        Square nSquare = this.nextSquare(square);
        if (nSquare == square){
            if (!(nSquare.isConcrete())){
                nSquare.setValue(0);
            }
        }
        else{
            nSquare.setValue(0);
            resetAfterSquare(nSquare);
        }
    }
    // public void solveTwoSolution(){
    //     // todo only checking boxes right now
    //     // todo im hard coding this
    //     // if the possiblity is shared between the other members in the square
    //     // outerloop:

    //     // LOL THIS IS HARD TO FOLLOW!
    //     outerloop:
    //     for (ArrayList<Square> box : this.boxes){
    //         for (Square square1 : box){
    //             for (Square square2 : box){
    //                 if (square1.getPossible().equals(square2.getPossible()) && square1 != square2){
    //                     for (Square square3 : box){
    //                         if (square3.getValue() == 0){
    //                             ArrayList<Integer> updatedPossible = new ArrayList<Integer>();
    //                             for (int possible : square3.getPossible()){
    //                                 if (!(square1.getPossible().contains(possible))){
    //                                     updatedPossible.add(possible);
    //                                 }
    //                             }
    //                             if (updatedPossible.size() > 0){
    //                                 square3.possible = updatedPossible; //todo outside of loop
    //                             }

    //                             if (square3.getPossible().size() == 1){
    //                                 square3.setValue(square3.getPossible().get(0));
    //                             }
    //                             System.out.println(square3.getName());
    //                             System.out.println(square3.getValue());
    //                             System.out.println(square1.getName());
    //                             System.out.println(square1.getPossible());
    //                             System.out.println(square2.getPossible());
    //                             System.out.println(square3.getPossible());
    //                             break outerloop;
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
}
