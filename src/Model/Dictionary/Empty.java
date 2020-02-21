package Model.Dictionary;

public class Empty {
    private int emptySpace;
    public Empty(){emptySpace = 0;}
    public int getEmptySpace(){
        emptySpace++;
        return emptySpace;
    }
}
