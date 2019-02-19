package cs361.battleships.models;

//import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class Ship{
	private List<Square> occupiedSquares;
	private String kind;
	//Should be 0 in minesweeper, 1 in destroyer, and 2 in battleship class. Should be used in function which takes a square as a parameter and determines
	//...if this ship's captains quarters is at that exact location. Returns true if so, false otherwise.
	protected int captainsIdx;
	protected int captiansHealth = 2;//Decrement when hit

	public Ship(){
		occupiedSquares = new ArrayList<Square>();
		kind = "";
	}

	public Ship(String kind){
		occupiedSquares = new ArrayList<Square>();
		this.kind = kind;
	}

	//To copy ships so that a single base ship can be used for placeShip(), as the signature is bad
	//...design and consequently requires this, though cannot be changed due to grading scripts
	public Ship(Ship ship){
		occupiedSquares = new ArrayList<Square>();
		for(Square square : ship.occupiedSquares){
			occupiedSquares.add(new Square(square));
		}
		kind = ship.kind;
	}

	public boolean checkCaptainsQuarters(Square location){
			//TODO after implementing captains quarters / child classes, uncomment the below line and delete return false;
			// return location.equals(occupiedSquares[captainsIdx]);
			return false;
	}

	public void addFeatures(int row, char col, boolean isV){
		int size = calcSize();
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

	public String getKind(){
		return kind;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public int calcSize(){
		return kind.equals("MINESWEEPER") ? 2 : (kind.equals("DESTROYER") ? 3 : (kind.equals("BATTLESHIP") ? 4 : 0));
	}
}
