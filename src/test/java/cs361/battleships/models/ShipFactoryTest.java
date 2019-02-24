package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipFactoryTest {
	@Test
	public void testCreateShips() {
		assertTrue(ShipFactory.createShip("MINESWEEPER") instanceof Minesweeper);
		assertTrue(ShipFactory.createShip("DESTROYER") instanceof Destroyer);
		assertTrue(ShipFactory.createShip("BATTLESHIP") instanceof Battleship);
	}
}
