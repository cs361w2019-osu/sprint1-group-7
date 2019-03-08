package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

	private Board playersBoard = new Board();
	private Board opponentsBoard = new Board();

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

		return true;
	}

	public boolean move(int direction){
		return false;
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
}
