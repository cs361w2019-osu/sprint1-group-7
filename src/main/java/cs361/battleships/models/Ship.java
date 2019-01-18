package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    @JsonProperty private List<Square> occupiedSquares;
    @JsonProperty private String kind;

	public Ship() {
        kind = new String();
		occupiedSquares = new ArrayList<>();
	}

	public Ship(String kind) {
        this.kind = kind;
        occupiedSquares = new ArrayList<>();
	}

    public void addFeatures(int x, char y, boolean isV, int size){
        for(int i = 0; i < size; i++){
            Square sq = new Square(x, y);
            occupiedSquares.add(sq);
            if(isV == false) { y += 1;}
            else { x += 1;}
        }
        return;
    }

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
    }
    
    public String getKind(){
        return kind;
    }

}
