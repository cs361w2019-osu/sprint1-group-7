package cs361.battleships.models;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DestroyerTest {
	@Test
	public void testConstructDestroyer() {
		Destroyer d = null;
		for(int i = 0; i < 20; i++){
			d = new Destroyer();
			assertTrue(d.getCaptainsIdx() >= 0 && d.getCaptainsIdx() <= 2);
		}
		List<ShipSquare> desiredSquares = d.genSquares(1, 'A', false);
		d.getOccupiedSquares().addAll(desiredSquares);
		assertTrue(d.getOccupiedSquares().get(d.getCaptainsIdx()).getHealth() == 2);
		d.getOccupiedSquares().get(d.getCaptainsIdx()).takeDamage();
		assertTrue(d.getOccupiedSquares().get(d.getCaptainsIdx()).getHealth() == 1);
		assertTrue(d.checkCaptainsQuarters(d.getOccupiedSquares().get(d.getCaptainsIdx()).getLocation()));
	}

	@Test
	public void testPolymorphicCloneDestroyer() {
		Ship s = new Destroyer();
		Ship s2 = s.opponentCopy();
		assertTrue(s2 instanceof Destroyer);
	}
}
