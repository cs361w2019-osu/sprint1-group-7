package cs361.battleships.models;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubmarineTest {
	@Test
	public void testConstructSubmarine() {
		Submarine s = null;
		for(int i = 0; i < 20; i++){
			s = new Submarine();
			assertTrue(s.getCaptainsIdx() >= 0 && s.getCaptainsIdx() <= 4);
		}
		List<ShipSquare> desiredSquares = s.genSquares(1, 'A', false);
		s.getOccupiedSquares().addAll(desiredSquares);
		assertTrue(s.getOccupiedSquares().get(s.getCaptainsIdx()).getHealth() == 2);
		s.getOccupiedSquares().get(s.getCaptainsIdx()).takeDamage();
		assertTrue(s.getOccupiedSquares().get(s.getCaptainsIdx()).getHealth() == 1);
		assertTrue(s.checkCaptainsQuarters(s.getOccupiedSquares().get(s.getCaptainsIdx()).getLocation()));
	}

	@Test
	public void testPolymorphicCloneSubmarine() {
		Ship s = new Submarine();
		Ship s2 = s.opponentCopy();
		assertTrue(s2 instanceof Submarine);
	}
}
