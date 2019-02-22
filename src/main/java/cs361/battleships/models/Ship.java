package cs361.battleships.models;

//import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Ship{
    protected List<Square> occupiedSquares;
    protected String shipType;
    protected int size;
	//Should be 0 in minesweeper, 1 in destroyer, and 2 in battleship class. Should be used in function which takes a square as a parameter and determines
	//...if this ship's captains quarters is at that exact location. Returns true if so, false otherwise.

    protected int captainsIdx;
    
    public Ship(){
		occupiedSquares = new ArrayList<Square>();
	}

    public String getShipType(){
        return shipType;
    }

    public int getSize(){
        return size;
    }

	public boolean checkCaptainsQuarters(Square location){
        return location.equals(occupiedSquares.get(captainsIdx));
	}

	public void addFeatures(int row, char col, boolean isV){
		for(int i = 0; i < size; i++){
			occupiedSquares.add(new Square(row, col));
			if(isV) {
				row += 1;
			}
			else {
				col += 1;
			}
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<Square> occupiedSquares){
		this.occupiedSquares = occupiedSquares;
	}

    
    public static Ship makeShip(String kind){
        if(kind.equals("MINESWEEPER")){
            return new Minesweeper();
        }
        else if(kind.equals("BATTLESHIP")){
            return new Battleship();
        }
        else{ 
            return new Destroyer();
        }
    }
}
