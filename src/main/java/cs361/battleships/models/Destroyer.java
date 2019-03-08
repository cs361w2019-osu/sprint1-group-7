package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Destroyer extends Ship{
    private static final int SIZE = 3;

    public Destroyer(){
        super();
        shipType = "DESTROYER";
        Random random = new Random();
        captainsIdx = random.nextInt(3);
        depth = 0;
    }

    public Ship opponentCopy() {
        Destroyer ship = new Destroyer();
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
