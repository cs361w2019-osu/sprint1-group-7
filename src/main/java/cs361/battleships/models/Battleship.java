package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Battleship extends Ship{
    private static final int SIZE = 4;

    public Battleship(){
        super();
        shipType = "BATTLESHIP";
        Random random = new Random();
        captainsIdx = random.nextInt(4);
        depth = 0;
    }

    public Ship opponentCopy() {
        Battleship ship = new Battleship();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        ship.shipType = shipType;
        Random random = new Random();
        ship.captainsIdx = random.nextInt(3);
        ship.depth = 0;
		return ship;
    }

    @Override
    public int calcSize(){
        return SIZE;
    }
}
