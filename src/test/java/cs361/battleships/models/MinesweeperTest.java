package cs361.battleships.models;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinesweeperTest {
	@Test
	public void testConstructMinesweeper() {
		Minesweeper m = null;
		for(int i = 0; i < 20; i++){
			m = new Minesweeper();
			assertTrue(m.getCaptainsIdx() >= 0 && m.getCaptainsIdx() <= 1);
		}
		List<ShipSquare> desiredSquares = m.genSquares(1, 'A', false);
		m.getOccupiedSquares().addAll(desiredSquares);
		assertTrue(m.getOccupiedSquares().get(m.getCaptainsIdx()).getHealth() == 2);
		m.getOccupiedSquares().get(m.getCaptainsIdx()).takeDamage();
		assertTrue(m.getOccupiedSquares().get(m.getCaptainsIdx()).getHealth() == 1);
		assertTrue(m.checkCaptainsQuarters(m.getOccupiedSquares().get(m.getCaptainsIdx()).getLocation()));
	}

	@Test
	public void testPolymorphicCloneMinesweeper() {
		Ship s = new Minesweeper();
		Ship s2 = s.opponentCopy();
		assertTrue(s2 instanceof Minesweeper);
	}
}
