package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minesweeper extends Ship{
    public Minesweeper(){
        Random rand = new Random();
        captainsIdx = rand.nextInt(1);
        occupiedSquares = new ArrayList<Square>();
        shipType = "MINESWEEPER";
        size = 2;
        
    }
};