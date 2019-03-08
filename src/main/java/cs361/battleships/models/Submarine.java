package cs361.battleships.models;

import java.util.ArrayList;
import java.util.Random;

public class Submarine extends Ship{
    public Submarine(){
        super();
        shipType = "SUBMARINE";
        Random random = new Random();
        captainsIdx = random.nextInt(5);
    }

    public Ship clone() {
        Ship ship = new Submarine();
        ship.occupiedSquares = new ArrayList<ShipSquare>();
        for(ShipSquare square : occupiedSquares){
            ship.occupiedSquares.add(new ShipSquare(new Square(square.getLocation()), square.getHealth()));
        }
        ship.shipType = shipType;
        return ship;
    }

    @Override
    public void addFeatures(int row, char col, boolean isV){
        int size = calcSize();
        char tempcol;
        if(isV){
            tempcol = (char)(col + 1);
            occupiedSquares.add(new ShipSquare(new Square(row+2, tempcol), 5 == captainsIdx ? 2 : 1));
        }
        else{
            tempcol = (char)(col + 2);
            occupiedSquares.add(new ShipSquare(new Square(row-1, tempcol), 5 == captainsIdx ? 2 : 1));
        }
		for(int i = 0; i < size; i++){
			occupiedSquares.add(new ShipSquare(new Square(row, col), i == captainsIdx ? 2 : 1));
			if(isV) {
				row += 1;
			}
			else {
				col += 1;
			}
		}
	}
}
