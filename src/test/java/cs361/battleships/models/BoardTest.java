package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	@Test
	public void testInvalidPlacement() {
		Board board = new Board();
		assertFalse(board.placeShip(Ship.makeShip("MINESWEEPER"), 11, 'C', true));
	}

	@Test
	public void testRepeatedPlacement() {
		Board board = new Board();
		assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 1, 'A', true));
		assertFalse(board.placeShip(Ship.makeShip("DESTROYER"), 1, 'A', true));//Repeated location invalid
	}

	@Test
	public void testValidPlacement(){
		Board board = new Board();
		assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 10, 'A', false));
	}

	@Test
	public void testInvalidAttack(){
		Board board = new Board();
		assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.attack(0, 'A').getResult() == AtackStatus.INVALID);
	}


	@Test
	public void testRepeatedAttack(){
		Board board = new Board();
		assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.attack(1, 'A').getResult() == AtackStatus.MISS);
		assertTrue(board.attack(1, 'A').getResult() == AtackStatus.INVALID || board.attack(1, 'A').getResult() == AtackStatus.SUNK);
	}

	@Test
	public void testSurrender() {
        Board board = new Board();
        Result attack;
        assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 10, 'A', false));
        assertTrue(board.placeShip(Ship.makeShip("DESTROYER"), 9, 'A', false));
        attack = board.attack(10, 'A');
		assertTrue( attack.getResult() == AtackStatus.HIT || attack.getResult() == AtackStatus.OUCH);
		assertTrue(board.attack(10, 'B').getResult() == AtackStatus.SUNK);
        attack = board.attack(9, 'A');
        assertTrue(attack.getResult() == AtackStatus.HIT|| attack.getResult() == AtackStatus.OUCH);
        attack = board.attack(9, 'B');
        assertTrue(attack.getResult() == AtackStatus.HIT|| attack.getResult() == AtackStatus.OUCH);
        attack = board.attack(9, 'C');
        assertTrue(attack.getResult() == AtackStatus.SURRENDER|| attack.getResult() == AtackStatus.INVALID);
	}

	@Test
	public void testSonar() {
		Board board = new Board();
		assertTrue(board.placeShip(Ship.makeShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.placeShip(Ship.makeShip("DESTROYER"), 9, 'A', false));
        Result attack = board.attack(9, 'A');
        assertTrue(attack.getResult() == AtackStatus.HIT|| attack.getResult() == AtackStatus.OUCH);
		assertFalse(board.sonar(9, 'C'));//Out of bounds
		assertFalse(board.sonar(8, 'B'));//Out of bounds
		assertFalse(board.sonar(8, 'I'));//Out of bounds
		assertFalse(board.sonar(2, 'C'));//Out of bounds
		assertTrue(board.sonar(8, 'C'));
		for(Result result : board.getAttacks()){
				if(result.getLocation().equals(new Square(9, 'A')))
						assertTrue(result.getResult() == AtackStatus.HIT|| result.getResult() == AtackStatus.OUCH);
				else if(result.getLocation().equals(new Square(9, 'B')))
						assertTrue(result.getResult() == AtackStatus.FOUND);
		}
	}

	//Miss tested in testRepeatedAttack
	//Hit tested in testSurrender
    //Sunk tested in testSurrender
}
