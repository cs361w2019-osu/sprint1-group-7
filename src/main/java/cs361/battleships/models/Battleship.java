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
        ship.occupiedSquares = new ArrayList<Square>();
        for(Square square : occupiedSquares){
            ship.occupiedSquares.add(new Square(square));
        }
        ship.shipType = shipType;
        return ship;
    }
}
