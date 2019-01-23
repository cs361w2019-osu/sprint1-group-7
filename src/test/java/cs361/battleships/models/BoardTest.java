package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	@Test
	public void testInvalidPlacement() {
		Board board = new Board();
		assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
	}

	@Test
	public void testRepeatedPlacement() {
		Board board = new Board();
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
		assertFalse(board.placeShip(new Ship("DESTROYER"), 1, 'A', true));//Repeated location invalid
		assertFalse(board.placeShip(new Ship("MINESWEEPER"), 5, 'E', true));//Repeated type invaild
	}

	@Test
	public void testValidPlacement(){
		Board board = new Board();
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
	}

	@Test
	public void testInvalidAttack(){
		Board board = new Board();
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.attack(0, 'A').getResult() == AtackStatus.INVALID);
	}

	
	@Test
	public void testRepeatedAttack(){
		Board board = new Board();
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.attack(1, 'A').getResult() == AtackStatus.MISS);
		assertTrue(board.attack(1, 'A').getResult() == AtackStatus.INVALID);
	}

	@Test
	public void testSurrender() {
		Board board = new Board();
		System.out.println("NEXT TEST");
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.placeShip(new Ship("DESTROYER"), 9, 'A', false));
		assertTrue(board.attack(10, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(10, 'B').getResult() == AtackStatus.SUNK);
		assertTrue(board.attack(9, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'B').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'C').getResult() == AtackStatus.SURRENDER);
	}

	//Miss tested in testRepeatedAttack
	//Hit tested in testSurrender
	//Sunk tested in testSurrender
}
