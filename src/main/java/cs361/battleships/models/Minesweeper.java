package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Minesweeper extends Ship{
    private static final int SIZE = 2;
    public Minesweeper(){
        super();
        shipType = "MINESWEEPER";
        Random random = new Random();
        captainsIdx = random.nextInt(2);
        depth = 0;
    }

    public Ship opponentCopy() {
        Minesweeper ship = new Minesweeper();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        ship.shipType = shipType;
        Random random = new Random();
        ship.captainsIdx = random.nextInt(2);
        ship.depth = 0;
        return ship;
    }

    @Override
    public int calcSize(){
        return SIZE;
    }
}
