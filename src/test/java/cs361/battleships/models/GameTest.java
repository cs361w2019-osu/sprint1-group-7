package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

	@Test
	public void testPlacement() {
		Game g = new Game();
		assertFalse(g.placeShip(ShipFactory.createShip("MINESWEEPER"), 11, 'C', true));
		assertTrue(g.placeShip(ShipFactory.createShip("MINESWEEPER"), 1, 'A', true));
		assertFalse(g.placeShip(ShipFactory.createShip("DESTROYER"), 1, 'A', true));//Repeated location invalid
		assertFalse(g.placeShip(ShipFactory.createShip("MINESWEEPER"), 5, 'E', true));//Repeated type invaild
	}

	//Tested in greater depth in the board class. This test just makes sure it passes through to the board correctly.
	//It's easier to test in the board class as Board.attack() returns the result, whereas Game.attack() returns a boolean
	//representing if it was successful.
	@Test
	public void testAttack() {
		Game g = new Game();
		assertFalse(g.attack(0, 'A'));//out of bounds
		assertTrue(g.attack(1, 'A'));//valid
	}

	//Tested in greater depth in the board class. This test just makes sure it passes through to the opponent board correctly.
	//It's easier to test in the board class as g.sonar sonars the opponent's board, which cannot be manually constructed from
	//a game object
	@Test
	public void testSonar() {
		Game g = new Game();
		assertTrue(g.sonar(3, 'C'));
		assertTrue(g.getOpponentsBoard().getAttacks().size() == 13);
	}
}
