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
	public void testInsertOneTokenToGrid() {
		Character[] expectedGrid = 
		{ 
			null, null, null, 
			null,  'X', null, 
			null, null, null,
		};

		TicTacToeLogic logic = defaultLogic();
		
		try{
			logic.insertNextTokenToGrid(4);
		}catch(SlotAlreadyFilledException e) {}

		assertArrayEquals(expectedGrid, logic.getGrid());
	}

	@Test
	public void testInsertTwoTokensToGrid() {
		Character[] expectedGrid = 
		{ 
			null, null, null, 
			null,  'X', null, 
			null, null, null,
		};

		TicTacToeLogic logic = defaultLogic();
		
		try{
			logic.insertNextTokenToGrid(4);
		}catch(SlotAlreadyFilledException e) {}

		assertArrayEquals(expectedGrid, logic.getGrid());
		
		expectedGrid[8] = 'O';
		
		try{
			logic.insertNextTokenToGrid(8);
		}catch(SlotAlreadyFilledException e) {}

		assertArrayEquals(expectedGrid, logic.getGrid());
	}

	@Test
	public void testInsertTokenToSlotThatIsNotEmpty() {
		TicTacToeLogic logic = defaultLogic();
		
		try{
			logic.insertNextTokenToGrid(4);
		}catch(SlotAlreadyFilledException e) {}

		try {
			logic.insertNextTokenToGrid(4);
			fail("Should have thrown SlotAlreadyFilledException");
		}catch(SlotAlreadyFilledException e) {
			assertEquals("Slot already has token", e.getMessage());
		}
	}

	@Test
	public void testInsertTokenOutOfBounds() {
		TicTacToeLogic logic = defaultLogic();
		try{
			logic.insertNextTokenToGrid(12);
			fail("Should have thrown IndexOutOfBoundsException");
		}catch(IndexOutOfBoundsException e) {
			assertEquals("Index of slot ranges between 0 and 8. Invalid index: 12", e.getMessage());
		}catch(Exception e){}
	}

	@Test
	public void testSetTicTacToeLogicStateConstructor() {
		Character[] inputGrid = 
		{ 
			'X', 'O', 'X', 
			'O', 'O', 'X', 
			'X', 'X', 'O',
		};

		TicTacToePlayer p1 = new TicTacToePlayer("Player1");
		TicTacToePlayer p2 = new TicTacToePlayer("Player2");
		TicTacToeLogic logic = new TicTacToeLogic(p1, p2, inputGrid, true);
		assertArrayEquals(inputGrid, logic.getGrid());
	}

	@Test
	public void testInitWithInvalidSizeGrid() {
		Character[] inputGrid = new Character[10];
		TicTacToePlayer p1 = new TicTacToePlayer("Player1");
		TicTacToePlayer p2 = new TicTacToePlayer("Player2");

		try {
			TicTacToeLogic logic = new TicTacToeLogic(p1, p2, inputGrid, true);
			fail("Should have thrown IllegalArgumentException");
		}catch(IllegalArgumentException e) {
			assertEquals("TicTacToeGrid must be of size 9. Illegal Size: 10", e.getMessage());
		}

		inputGrid = new Character[7];
		try {
			TicTacToeLogic logic = new TicTacToeLogic(p1, p2, inputGrid, true);
			fail("Should have thrown IllegalArgumentException");
		}catch(IllegalArgumentException e) {
			assertEquals("TicTacToeGrid must be of size 9. Illegal Size: 7", e.getMessage());
		}
	}
}