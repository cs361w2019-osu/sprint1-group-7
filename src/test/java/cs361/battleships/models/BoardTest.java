package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
/*
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
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.placeShip(new Ship("DESTROYER"), 9, 'A', false));
		assertTrue(board.attack(10, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(10, 'B').getResult() == AtackStatus.SUNK);
		assertTrue(board.attack(9, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'B').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'C').getResult() == AtackStatus.SURRENDER);
	}

	@Test
	public void testSonar() {
		Board board = new Board();
		assertTrue(board.placeShip(new Ship("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.placeShip(new Ship("DESTROYER"), 9, 'A', false));
		assertTrue(board.attack(10, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'A').getResult() == AtackStatus.HIT);
		assertTrue(board.attack(9, 'C').getResult() == AtackStatus.HIT);
		assertFalse(board.sonar(9, 'C'));//Out of bounds
		assertFalse(board.sonar(8, 'B'));//Out of bounds
		assertFalse(board.sonar(8, 'I'));//Out of bounds
		assertFalse(board.sonar(2, 'C'));//Out of bounds
		assertTrue(board.sonar(8, 'C'));
		for(Result result : board.getAttacks()){
				if(result.getLocation().equals(new Square(10, 'A')))
						assertTrue(result.getResult() == AtackStatus.HIT);
				else if(result.getLocation().equals(new Square(9, 'A')))
						assertTrue(result.getResult() == AtackStatus.HIT);
				else if(result.getLocation().equals(new Square(9, 'B')))
						assertTrue(result.getResult() == AtackStatus.FOUND);
				else if(result.getLocation().equals(new Square(9, 'C')))
						assertTrue(result.getResult() == AtackStatus.HIT);
				else
						assertTrue(result.getResult() == AtackStatus.EMPTY);
		}
	}

	//Miss tested in testRepeatedAttack
	//Hit tested in testSurrender
    //Sunk tested in testSurrender
*/
}
