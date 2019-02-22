package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battleship extends Ship{
    public Battleship(){
        Random rand = new Random();
        captainsIdx = rand.nextInt(3);
        occupiedSquares = new ArrayList<Square>();
        shipType = "BATTLESHIP";
        size = 4;
    }
};