package is.ru.tictactoe;

import static org.junit.Assert.*;
import org.junit.Test;

public class TicTacToePlayerTest {

	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("is.ru.tictactoe.TicTacToePlayerTest");
	}

	@Test
	public void testGetName() {
		TicTacToePlayer player = new TicTacToePlayer("Svessi");
		assertEquals("Svessi", player.getName());
	}
}