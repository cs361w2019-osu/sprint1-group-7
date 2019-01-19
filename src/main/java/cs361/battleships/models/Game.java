package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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
		boolean successful = playersBoard.placeShip(new Ship(ship), x, y, isVertical);
		if (!successful)
			return false;

		boolean opponentPlacedSuccessfully;
		do {
			// AI places random ships, so it might try and place overlapping ships
			// let it try until it gets it right
			opponentPlacedSuccessfully = opponentsBoard.placeShip(new Ship(ship), randRow(), randCol(), randVertical());
		} while (!opponentPlacedSuccessfully);

		return true;
	}

	/*
	   DO NOT change the signature of this method. It is used by the grading scripts.
	   */
	public boolean attack(int x, char  y) {
		Result playerAttack = opponentsBoard.attack(x, y);
		if (playerAttack.getResult() == INVALID) {
			return false;
		}

		Result opponentAttackResult;
		do {
			// AI does random attacks, so it might attack the same spot twice
			// let it try until it gets it right
			// TODO how about we implement it so that it doesn't ever attack the same place twice in a
			// ...new Feature-opponent-attack issue corresponding to another user story?
			opponentAttackResult = playersBoard.attack(randRow(), randCol());
		} while(opponentAttackResult.getResult() != INVALID);

		return true;
	}

	private char randCol() {
		Random rand = new Random();
		return (char)(rand.nextInt(9)+ 65);
	}

	private int randRow() {
		Random rand = new Random();
		return rand.nextInt(9);
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
