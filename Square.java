import java.util.*;
class Square {
    // init attribute
    public String name;
    public int startingNum;
    public int currentValue;
    public int column;
    public int row;
    public int box;
    public Board parent;
    public ArrayList<Integer> possible;

    public Square(Board parent, int number, int column, int row) {
        this.parent = parent;        
        this.name = "abcdefghi".substring(column,column+1)+Integer.toString(row+1);
        this.startingNum = number;
        this.currentValue = number;
        this.column = column;
        this.row = row;
        this.box = getBoxNumber(column, row); // the 3x3 square the num is in
    }
    public boolean isConcrete(){
        // means it was given at the start
        if (this.startingNum == 0){
            return false;
        } else{
            return true;
        }
    }
    public void setConcrete(int num){
        // sets the value and makes it concrete
        this.setValue(num);
        this.startingNum = num;
    }
    public boolean isBlank(){
        if (this.getValue() == 0){
            return true;
        }else{
            return false;
        }
    }
    public int getValue(){
        return this.currentValue;
    }
    public int getColumn(){
        return this.column;
    }
    public int getRow(){
        return this.row;
    }
    public int getBox(){
        return this.box;
    }
    public String getName(){
        return this.name;
    }
    private int getBoxNumber(int column, int row){
        // boxes go starting in the bottom left
        // 6 7 8
        // 3 4 5
        // 0 1 2
        int rowBox = row / 3;
        int columnBox = column / 3;

        if (columnBox == 0 && rowBox == 0){
            return 0;
        }else if (columnBox == 1 && rowBox == 0){
            return 1;
        }else if (columnBox == 2 && rowBox == 0){
            return 2;
        }else if (columnBox == 0 && rowBox == 1){
            return 3;
        }else if (columnBox == 1 && rowBox == 1){
            return 4;
        }else if (columnBox == 2 && rowBox == 1){
            return 5;
        }else if (columnBox == 0 && rowBox == 2){
            return 6;
        }else if (columnBox == 1 && rowBox == 2){
            return 7;
        }else{
            // if (rowBox == 2 && columnBox == 2)
            return 8;
        }
    }
    public void setValue(int value){
        // parent.setFarthest(this); // farthest is linear right now
        // todo add changes possibilites to surronding cells
        this.currentValue = value;

        // automatically solves one solutions
        // refresh possibilites with new value
        for (Square square : parent.squaresOne){
            // todo add one more constraint to check to see if current possiblites hodl current value
            if (square.getValue() == 0 && (square.getRow() == this.getRow() || square.getColumn() == this.getColumn() || square.getBox() == this.getBox())){
                square.setPossible();

                // no way to recurse now go back to 7:18
                if (square.getPossible().size() == 1){
                    square.setValue(square.getPossible().get(0));
                }
            }
        }
    }
    public String setPossible(){
        ArrayList<Integer> hasNums = new ArrayList<Integer>();

        // already has a value
        if (this.isBlank() == false){
            this.possible = hasNums;
            return ""; // todo dont need this return statment just sentienal value
        }

        // loop through board finding similiar cells
        for (Square square : parent.squaresOne){
            if (square.getRow() == this.getRow()){
                hasNums.add(square.getValue());
            }
            else if(square.getColumn() == this.getColumn()){
                hasNums.add(square.getValue());
            }
            else if (square.getBox() == this.getBox()){
                hasNums.add(square.getValue());
            }
        }

        // remove duplicates
        Set setItems = new LinkedHashSet(hasNums);
        hasNums.clear();
        hasNums.addAll(setItems);

        // todo seems like there should be a way to better this
        int[] extraPossible = {1,2,3,4,5,6,7,8,9};
        ArrayList<Integer> possiblites = new ArrayList<Integer>();
        for (int i = 0; i < extraPossible.length; i++){
            if (hasNums.indexOf(extraPossible[i]) == -1){
                // extraPossible[i] = 0;
                possiblites.add(extraPossible[i]);
            }
        }
        this.possible = possiblites;

        // solves
        if (possiblites.size() == 1){
            this.setValue(possiblites.get(0));
        }
        return "";
    }
    public ArrayList<Integer> getPossible(){
        return this.possible;
    }
}
