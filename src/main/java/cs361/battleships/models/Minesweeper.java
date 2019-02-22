package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends Ship{
    public Minesweeper(){
        occupiedSquares = new ArrayList<Square>();
        shipType = "MINESWEEPER";
        size = 2;
    }
};