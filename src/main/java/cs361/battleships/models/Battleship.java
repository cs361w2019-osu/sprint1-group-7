package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;

public class Battleship extends Ship{
    public Battleship(){
        occupiedSquares = new ArrayList<Square>();
        shipType = "BATTLESHIP";
        size = 4;
    }
};