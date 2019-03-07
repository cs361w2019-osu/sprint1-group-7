package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Battleship extends Ship{
    public Battleship(){
        super();
        shipType = "BATTLESHIP";
        Random random = new Random();
        captainsIdx = random.nextInt(4);
    }

    public Ship clone() {
        Ship ship = new Battleship();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        for(ShipSquare square : occupiedSquares){
            ship.occupiedSquares.add(new ShipSquare(new Square(square.getLocation()), square.getHealth()));
        }
        ship.shipType = shipType;
        return ship;
    }
}
