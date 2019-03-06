package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleshipTest {
	@Test
	public void testConstructBattleship() {
		Battleship b = null;
		for(int i = 0; i < 20; i++){
			b = new Battleship();
			assertTrue(b.getCaptainsIdx() >= 0 && b.getCaptainsIdx() <= 3);
		}
		b.addFeatures(1, 'A', false);
		assertTrue(b.getOccupiedSquares().get(b.getCaptainsIdx()).getHealth() == 2);
		b.getOccupiedSquares().get(b.getCaptainsIdx()).takeDamage();
		assertTrue(b.getOccupiedSquares().get(b.getCaptainsIdx()).getHealth() == 1);
		assertTrue(b.checkCaptainsQuarters(b.getOccupiedSquares().get(b.getCaptainsIdx()).getLocation()));
	}

	@Test
	public void testPolymorphicCloneBattleship() {
		Ship s = new Battleship();
		Ship s2 = s.clone();
		assertTrue(s2 instanceof Battleship);
	}
}
