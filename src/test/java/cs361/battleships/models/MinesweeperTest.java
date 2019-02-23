package cs361.battleships.models;

import org.junit.Test;

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
		m.addFeatures(1, 'A', false);
		assertTrue(m.getCaptainsHealth() == 2);
		m.takeCaptainDamage();
		assertTrue(m.getCaptainsHealth() == 1);
		assertTrue(m.checkCaptainsQuarters(m.getOccupiedSquares().get(m.getCaptainsIdx())));
	}

	@Test
	public void testPolymorphicCloneMinesweeper() {
		Ship s = new Minesweeper();
		Ship s2 = s.clone();
		assertTrue(s2 instanceof Minesweeper);
	}
}
