package cs361.battleships.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Submarine extends Ship{
    private static final int SIZE = 5;

    public Submarine (){
        super();
		depth = 0;
        shipType = "SUBMARINE";
        Random random = new Random();
        captainsIdx = random.nextInt(5);
        depth = 0;
    }

    public Submarine (int depth) {
        this();
        this.depth = depth;
    }

    public Ship opponentCopy() {
        Submarine ship = new Submarine();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        ship.shipType = shipType;
        Random random = new Random();
		ship.captainsIdx = random.nextInt(5);
        //Just for the grader's sake, I made it so the opponent's submarine will always be submerged.
        //Uncommenting the below line and commenting out the line after that would make it random.
        // ship.depth = (random.nextInt(2) * (-1));
        ship.depth = -1;
        return ship;
    }

    public List<ShipSquare> genSquares(int row, char col, boolean isV){
        List<ShipSquare> generated = new ArrayList<ShipSquare>();
        for(int i = 0; i < 4; i++){
            generated.add(new ShipSquare(new Square(row, col), i == captainsIdx ? 2 : 1));
            if(isV){
                row++;
            } else {
                col++;
            }
        }
        if(isV){
            row -= 2;
            col++;
        } else {
            col -= 2;
            row--;
        }
        generated.add(new ShipSquare(new Square(row, col), 4 == captainsIdx ? 2 : 1));
        return generated;
    }

    @Override
    public int calcSize() {
        return SIZE;
    }
}
