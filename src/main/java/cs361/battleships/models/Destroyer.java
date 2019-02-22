package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Destroyer extends Ship{
    public Destroyer(){
        Random rand = new Random();
        captainsIdx = rand.nextInt(2);
        occupiedSquares = new ArrayList<Square>();
        shipType = "DESTROYER";
        size = 3;
    }
};