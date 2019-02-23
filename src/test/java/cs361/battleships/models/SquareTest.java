package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {
	@Test
	public void testSquareEquals() {
		assertTrue(new Square(5, 'E').equals(new Square(5, 'E')));
		assertFalse(new Square(5, 'E').equals(new Square(5, 'F')));
		assertFalse(new Square(5, 'E').equals(new Square(6, 'E')));
		assertFalse(new Square(5, 'E').equals(new Square(6, 'F')));
	}
}
