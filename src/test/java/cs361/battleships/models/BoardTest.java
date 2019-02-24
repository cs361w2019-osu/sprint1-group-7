package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
	//Placement is tested in GameTest.java

	@Test
	public void testAttack() {
		Board b = new Board();
		assertTrue(b.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(b.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
		assertTrue(b.attack(0, 'A').getResult() == AtackStatus.INVALID);
		assertTrue(b.attack(1, 'A').getResult() == AtackStatus.MISS);
		assertTrue(b.attack(1, 'A').getResult() == AtackStatus.INVALID);
		for(int i = 0; i < 2; i++){
			AtackStatus status = b.attack(10, (char) ('A' + i)).getResult();
			assertTrue(status == AtackStatus.HIT || status == AtackStatus.OUCH);
			if(status == AtackStatus.OUCH){
				assertTrue(b.attack(10, (char) ('A' + i)).getResult() == AtackStatus.SUNK);
				break;
			}
		}

		for(int i = 0; i < 3; i++){
			AtackStatus status = b.attack(9, (char) ('A' + i)).getResult();
			assertTrue(status == AtackStatus.HIT || status == AtackStatus.OUCH);
			if(status == AtackStatus.OUCH){
				assertTrue(b.attack(9, (char) ('A' + i)).getResult() == AtackStatus.SURRENDER);
				break;
			}
		}
	}

	@Test
	public void testSonar() {
		Board board = new Board();
		assertTrue(board.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(board.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
		assertTrue(board.placeShip(ShipFactory.createShip("BATTLESHIP"), 1, 'A', false));
		assertFalse(board.sonar(8, 'C'));//Has not destroyed a ship yet, invalid.
		AtackStatus status;
		assertTrue(( status = board.attack(9, 'C').getResult()) == AtackStatus.HIT || status == AtackStatus.OUCH);
		for(int i = 0; i < 2; i++){
			status = board.attack(10, (char) ('A' + i)).getResult();
			assertTrue(status == AtackStatus.HIT || status == AtackStatus.OUCH);
			if(status == AtackStatus.OUCH){
				assertTrue(board.attack(10, (char) ('A' + i)).getResult() == AtackStatus.SUNK);
				break;
			}
		}
		assertTrue(board.sonar(8, 'C'));//Has destroyed a ship, valid.
		assertFalse(board.sonar(9, 'C'));//Out of bounds
		assertFalse(board.sonar(8, 'B'));//Out of bounds
		assertFalse(board.sonar(8, 'I'));//Out of bounds
		assertFalse(board.sonar(2, 'C'));//Out of bounds
		for(Result result : board.getAttacks()){
				if(result.getLocation().equals(new Square(10, 'A')))
						assertTrue(result.getResult() == AtackStatus.SUNK);
				else if(result.getLocation().equals(new Square(10, 'B')))
						assertTrue(result.getResult() == AtackStatus.SUNK);
				else if(result.getLocation().equals(new Square(9, 'B')))
						assertTrue(result.getResult() == AtackStatus.FOUND);
				else if(result.getLocation().equals(new Square(9, 'C')))
						assertTrue(result.getResult() == AtackStatus.HIT || result.getResult() == AtackStatus.OUCH);
				else
						assertTrue(result.getResult() == AtackStatus.EMPTY);
		}
	}

	//Miss tested in testRepeatedAttack
	//Hit tested in testSurrender
	//Sunk tested in testSurrender
}
