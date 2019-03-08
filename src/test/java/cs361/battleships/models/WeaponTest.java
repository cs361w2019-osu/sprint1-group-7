package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WeaponTest {
	@Test
	public void testAttack() {
		Board b = new Board();
        assertTrue(b.determineWeapon() instanceof Bomb);
        assertTrue(b.placeShip(ShipFactory.createShip("MINESWEEPER"), 10, 'A', false));
		assertTrue(b.placeShip(ShipFactory.createShip("DESTROYER"), 9, 'A', false));
		Submarine s = new Submarine();
		s.setDepth(-1);
		assertTrue(b.placeShip(s, 8, 'A', false));

		assertTrue(b.determineWeapon().attack(b, 8, 'A'));//Bomb should miss submarine
		assertTrue(b.getAttacks().get(0).getResult() == AtackStatus.MISS);

		assertTrue(b.determineWeapon().attack(b, 10, 'A'));
		assertTrue(b.determineWeapon().attack(b, 10, 'B'));
		assertTrue(b.determineWeapon().attack(b, 10, 'A'));
		assertTrue(b.determineWeapon().attack(b, 10, 'B'));

        assertTrue(b.determineWeapon() instanceof SpaceLaser);
		assertTrue(b.determineWeapon().attack(b, 9, 'A'));
		assertTrue(b.determineWeapon().attack(b, 9, 'B'));
        assertTrue(b.determineWeapon().attack(b, 9, 'C'));
		assertTrue(b.determineWeapon().attack(b, 9, 'A'));
		assertTrue(b.determineWeapon().attack(b, 9, 'B'));
        assertTrue(b.determineWeapon().attack(b, 9, 'C'));
        assertTrue(b.determineWeapon() instanceof SpaceLaser);

		assertTrue(b.determineWeapon().attack(b, 8, 'A'));
		AtackStatus status = b.getAttacks().get(0).getResult();//attack status should now be updated to HIT or OUCH
		assertTrue(status == AtackStatus.HIT || status == AtackStatus.OUCH);
	}
}
