package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Submarine extends Ship {
    public Submarine(){
        super();
        shipType = "SUBMARINE";
        Random random = new Random();
        captainsIdx = random.nextInt(4);

    }

    public Ship clone(){
        Ship ship = new Submarine();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        for(ShipSquare square : occupiedSquares){
            ship.occupiedSquares.add(new ShipSquare(new Square(square.getLocation()), square.getHealth()));
        }
        ship.shipType = shipType;
        return ship;
    }


}
