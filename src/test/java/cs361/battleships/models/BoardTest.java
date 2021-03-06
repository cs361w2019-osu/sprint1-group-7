package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
	//Placement is tested in GameTest.java

	@Test
	public void testAttack() {
		Board b = new Board();
		System.out.println("Placing minesweeper");
		assertTrue(b.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(b.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
		assertFalse(b.determineWeapon().attack(b, 0, 'A'));
		assertTrue(b.determineWeapon().attack(b, 1, 'A'));
		assertTrue(b.determineWeapon().attack(b, 1, 'A'));
		for(int i = 0; i < 2; i++){
			assertTrue(b.determineWeapon().attack(b, 10, (char) ('A' + i)));
		}

		for(int i = 0; i < 3; i++){
			assertTrue(b.determineWeapon().attack(b, 9, (char) ('A' + i)));
		}
	}

	@Test
	public void testSonar() {
		for(int k = 0; k < 10; k++){
			Board board = new Board();
			assertTrue(board.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
			assertTrue(board.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
			assertTrue(board.placeShip(ShipFactory.createShip("BATTLESHIP"), 1, 'A', false));
			assertFalse(board.sonar(8, 'C'));//Has not destroyed a ship yet, invalid.
			board.determineWeapon().attack(board, 9, 'C');
			for(int i = 0; i < 2; i++){
				board.determineWeapon().attack(board, 10, (char) ('A' + i));
				board.determineWeapon().attack(board, 10, (char) ('A' + i));//attack twice just in case a captain
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
	}

	@Test
	public void testMove() {
		Game g = new Game();
		Submarine s = new Submarine();
		s.setDepth(-1);
		g.placeShip(s, 2, 'A', false);
		g.placeShip(ShipFactory.createShip("MINESWEEPER"), 3, 'A', false);
		// System.out.println("MOVING UP!");
		g.move(-1);
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getLocation().equals(new Square(2, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(1).getLocation().equals(new Square(2, 'B')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(2).getLocation().equals(new Square(2, 'C')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(3).getLocation().equals(new Square(2, 'D')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(4).getLocation().equals(new Square(1, 'C')));

		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getLocation().equals(new Square(2, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(1).getLocation().equals(new Square(2, 'B')));

		// System.out.println("MOVING DOWN!");
		g.move(1);
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'B')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(2).getLocation().equals(new Square(3, 'C')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(3).getLocation().equals(new Square(3, 'D')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(4).getLocation().equals(new Square(2, 'C')));

		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'B')));

		// System.out.println("MOVING RIGHT!");
		g.move(2);
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'B')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'C')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(2).getLocation().equals(new Square(3, 'D')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(3).getLocation().equals(new Square(3, 'E')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(4).getLocation().equals(new Square(2, 'D')));

		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'B')));
		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'C')));

		// System.out.println("MOVING LEFT!");
		g.move(-2);
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'B')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(2).getLocation().equals(new Square(3, 'C')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(3).getLocation().equals(new Square(3, 'D')));
		assertTrue(g.getPlayersBoard().getShips().get(0).getOccupiedSquares().get(4).getLocation().equals(new Square(2, 'C')));

		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(0).getLocation().equals(new Square(3, 'A')));
		assertTrue(g.getPlayersBoard().getShips().get(1).getOccupiedSquares().get(1).getLocation().equals(new Square(3, 'B')));
	}

	//Miss tested in testRepeatedAttack
	//Hit tested in testSurrender
	//Sunk tested in testSurrender
}
