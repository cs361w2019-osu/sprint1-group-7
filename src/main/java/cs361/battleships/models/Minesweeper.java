package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Minesweeper extends Ship{
    public Minesweeper(){
        super();
        shipType = "MINESWEEPER";
        Random random = new Random();
        captainsIdx = random.nextInt(2);
    }

    public Ship clone() {
        Ship ship = new Minesweeper();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        for(ShipSquare square : occupiedSquares){
            ship.occupiedSquares.add(new ShipSquare(new Square(square.getLocation()), square.getHealth()));
        }
        ship.shipType = shipType;
        return ship;
    }
}
