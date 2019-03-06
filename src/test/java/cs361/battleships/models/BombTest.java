package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BombTest {
	//Placement is tested in GameTest.java

	@Test
	public void testWeaponGen() {
		Board b = new Board();
        assertTrue(b.determineWeapon() instanceof Bomb);
        assertTrue(b.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(b.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
        assertTrue(b.determineWeapon().attack(b, 10, 'A'));
		assertTrue(b.determineWeapon().attack(b, 10, 'B'));
        assertTrue(b.determineWeapon() instanceof Bomb);
        assertTrue(b.determineWeapon().attack(b, 9, 'A'));
		assertTrue(b.determineWeapon().attack(b, 9, 'B'));
        assertTrue(b.determineWeapon().attack(b, 9, 'C'));
        //assertFalse(b.determinWeapon() instanceof Bomb); //Use this once space laser is added
        assertTrue(b.determineWeapon() instanceof Bomb);
	}
}
