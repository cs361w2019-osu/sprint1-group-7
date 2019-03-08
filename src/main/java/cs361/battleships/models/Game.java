package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

	private Board playersBoard = new Board();
	private Board opponentsBoard = new Board();
	
	@JsonProperty int sunkShips = 0;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.

	Let's pay attention to something here; the same ship is passed into playersBoard.placeShip and
	opponentsBoard.placeShip. This is a large issue. While Java is pass by value, that value is a
	shallow copy by default. The only way to get around this is to define either a clone() or
	copy constructor for ships, and then pass that into the Board.placeShip() function. In reality,
	this function is badly designed, but it cannot be changed due to the grading scripts.

	*/
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		boolean successful = playersBoard.placeShip(ship.clone(), x, y, isVertical);
		if (!successful)
		return false;

		boolean opponentPlacedSuccessfully;
		do {
			// AI places random ships, so it might try and place overlapping ships
			// let it try until it gets it right
			opponentPlacedSuccessfully = opponentsBoard.placeShip(ship.clone(), randRow(), randCol(), randVertical());
		} while (!opponentPlacedSuccessfully);

		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	*/
	public boolean attack(int x, char  y) {
		
		System.out.println("ATTAKINGFROMGAME");
		boolean playerAttack = opponentsBoard.determineWeapon().attack(opponentsBoard, x, y);
		if (!playerAttack) {
			return false;
		}

		boolean opponentAttackResult;
		do {
			opponentAttackResult = playersBoard.determineWeapon().attack(playersBoard, randRow(), randCol());
		} while(!opponentAttackResult);

		sunkShips = opponentsBoard.sunkenShips();
		return true;
	}

	public boolean sonar (int x, char y) {
		return opponentsBoard.sonar(x, y);
	}

	private char randCol() {
		Random rand = new Random();
		return (char)(rand.nextInt(10)+ 65);
	}

	private int randRow() {
		Random rand = new Random();
		return rand.nextInt(10) + 1;
	}

	private boolean randVertical() {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	public Board getPlayersBoard(){
		return playersBoard;
	}
	public Board getOpponentsBoard(){
		return opponentsBoard;
	}
	public void setPlayersBoard(Board b){
		playersBoard = b;
	}
	public void setOpponentsBoard(Board b){
		opponentsBoard = b;
	}

	public boolean move(int direction){
		System.out.println("entered game");
		int rowAdd = 0;
		int colAdd = 0;
		boolean bad = false;
		if(direction == -2){colAdd = -1;}
		else if(direction == 2){colAdd = 1;}
		else{rowAdd = direction;}
		for(Ship curShip : playersBoard.getShips()){
			bad = false;
			if(curShip.canMove(rowAdd, colAdd)){
				if(!(curShip.getShipType().equals("SUBMARINE"))){
					for(ShipSquare square : curShip.getOccupiedSquares()){
						Square newLoc = new Square(square.getLocation());
						newLoc.setRow(newLoc.getRow() + rowAdd);
						newLoc.setColumn((char)(newLoc.getColumn() + colAdd));
						for(Ship tmpShip: playersBoard.getShips()){
							if(!(tmpShip.getShipType().equals(curShip.getShipType())) && !(tmpShip.getShipType().equals("SUBMARINE"))){
								if(tmpShip.findSquareWithLocation(newLoc) != null){bad = true;}
							}
						}
					}
				}
				if(bad == false){
					for(ShipSquare square : curShip.getOccupiedSquares()){
						Square newLoc = new Square(square.getLocation());
						newLoc.setRow(newLoc.getRow() + rowAdd);
						newLoc.setColumn((char)(newLoc.getColumn() + colAdd));
						square.setLocation(newLoc);
					}
				}
			}
		}
		return true;
	}
}
