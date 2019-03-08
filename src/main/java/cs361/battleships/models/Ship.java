package cs361.battleships.models;

//import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

//No unit tests necessary. Abstract class -- subtypes tested in JUnit tests.

//The following annotations register the sub types associated with
//the ship class so that the Jackson serializer (used by Ninja for POJO/JSON parsing)
//knows to record the type of a ship when it is serialized so that it can later
//be deserialized back into the correct subtype POJO (necessary for polymorphism)
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Minesweeper.class, name = "Minesweeper"),
	@JsonSubTypes.Type(value = Destroyer.class, name = "Destroyer"),
	@JsonSubTypes.Type(value = Battleship.class, name = "Battleship"),
	@JsonSubTypes.Type(value = Submarine.class, name = "Submarine")
})
public abstract class Ship{

	protected List<ShipSquare> occupiedSquares;
	protected String shipType;
	//Should be 0 in minesweeper, 1 in destroyer, and 2 in battleship class. Should be used in function which takes a square as a parameter and determines
	//...if this ship's captains quarters is at that exact location. Returns true if so, false otherwise.
	protected int captainsIdx;
	protected int depth;//0 is ocean surface, -1 is submarine


	public Ship(){
		occupiedSquares = new ArrayList<ShipSquare>();
	}

	public abstract Ship clone();

	public boolean checkCaptainsQuarters(Square location){
		return location.equals(occupiedSquares.get(captainsIdx).getLocation());
	}

	//Remember to override for submarines, as they are oddly shaped
	public void addFeatures(int row, char col, boolean isV){
		int size = calcSize();
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

	public ShipSquare findSquareWithLocation(Square location){
		for(ShipSquare square : occupiedSquares) {
			if(square.getLocation().equals(location))
				return square;
		}
		return null;
	}

	public int calcSize(){
		return shipType.equals("MINESWEEPER") ? 2 : (shipType.equals("DESTROYER") ? 3 : (shipType.equals("BATTLESHIP") ? 4 : (shipType.equals("SUBMARINE")) ? 4 : 0));
	}

	public List<ShipSquare> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void setOccupiedSquares(List<ShipSquare> occupiedSquares){
		this.occupiedSquares = occupiedSquares;
	}

	public String getShipType(){
		return shipType;
	}

	public void setShipType(String shipType){
		this.shipType = shipType;
	}

	public int getCaptainsIdx(){
		return captainsIdx;
	}

	public void setCaptainsIdx(int captainsIdx){
		this.captainsIdx = captainsIdx;
	}

	public boolean canMove(int rowAdd, int colAdd){
		for(ShipSquare square: occupiedSquares){
			if(square.getLocation().getRow() + rowAdd > 10 || square.getLocation().getRow() + rowAdd < 1){return false;}
			if(square.getLocation().getColumn() + colAdd < 'A' || square.getLocation().getColumn() + colAdd > 'J'){return false;}
		}
		return true;
	}

	// public int getDepth() {
	// 	return depth;
	// }
	//
	// public void setDepth (int depth){
	// 	this.depth = depth;
	// }
}
