package is.ru.tictactoe;

import static org.junit.Assert.*;
import org.junit.Test;

public class TicTacToeLogicTest {

	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main("is.ru.tictactoe.TicTacToeLogicTest");
	}

	private TicTacToeLogic defaultLogic() {
		return new TicTacToeLogic(new TicTacToePlayer("Player1"), new TicTacToePlayer("Player2"));
	}

	@Test
	public void testGetGrid() {
		Character[] expectedGrid = 
		{ 
			null, null, null, 
			null, null, null, 
			null, null, null,
		};

		TicTacToeLogic logic = defaultLogic();
		assertArrayEquals(expectedGrid, logic.getGrid());
	}

	@Test
	public void testInsertNextTokenToGrid() {
		Character[] expectedGrid = 
		{ 
			null, null, null, 
			null,  'X', null, 
			null, null, null,
		};

		TicTacToeLogic logic = defaultLogic();
		logic.insertNextTokenToGrid(4);
		assertArrayEquals(expectedGrid, logic.getGrid());
	}
}