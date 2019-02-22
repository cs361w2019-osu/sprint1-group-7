package cs361.battleships.models;
import java.util.ArrayList;
import java.util.List;

public class Destroyer extends Ship{
    public Destroyer(){
        occupiedSquares = new ArrayList<Square>();
        shipType = "DESTROYER";
        size = 3;
    }
};