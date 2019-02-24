package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Destroyer extends Ship{
    public Destroyer(){
        super();
        shipType = "DESTROYER";
        Random random = new Random();
        captainsIdx = random.nextInt(3);
    }

    public Ship clone() {
        Ship ship = new Destroyer();
        ship.occupiedSquares = new ArrayList<Square>();
        for(Square square : occupiedSquares){
            ship.occupiedSquares.add(new Square(square));
        }
        ship.shipType = shipType;
        return ship;
    }
}
