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
        ship.occupiedSquares = new ArrayList<Square>();
        for(Square square : occupiedSquares){
            ship.occupiedSquares.add(new Square(square));
        }
        ship.shipType = shipType;
        return ship;
    }
}
