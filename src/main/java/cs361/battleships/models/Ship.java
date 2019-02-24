package cs361.battleships.models;

//import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonSubTypes.Type(value = Battleship.class, name = "Battleship")
})
public abstract class Ship{
	protected List<Square> occupiedSquares;
	protected String shipType;
	//Should be 0 in minesweeper, 1 in destroyer, and 2 in battleship class. Should be used in function which takes a square as a parameter and determines
	//...if this ship's captains quarters is at that exact location. Returns true if so, false otherwise.
	protected int captainsIdx;
	protected int captainsHealth = 2;//Decrement when hit

	public Ship(){
		occupiedSquares = new ArrayList<Square>();
	}

	public abstract Ship clone();

	// public boolean checkCaptainsQuarters(Square location){
	// 		//TODO after implementing captains quarters / child classes, uncomment the below line and delete return false;
	// 		// return location.equals(occupiedSquares[captainsIdx]);
	// 		return false;
	// }

	public boolean checkCaptainsQuarters(Square location){
		return location.equals(occupiedSquares.get(captainsIdx));
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

	public int getCaptainsHealth(){
		return captainsHealth;
	}

	public void setCaptainsHealth(int captainsHealth){
		this.captainsHealth = captainsHealth;
	}

	public void takeCaptainDamage(){
		captainsHealth--;
	}

	public int calcSize(){
		return shipType.equals("MINESWEEPER") ? 2 : (shipType.equals("DESTROYER") ? 3 : (shipType.equals("BATTLESHIP") ? 4 : 0));
	}
}
