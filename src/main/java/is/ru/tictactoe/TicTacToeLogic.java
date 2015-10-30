package is.ru.tictactoe;

public class TicTacToeLogic {

	private TicTacToePlayer player1;
	private TicTacToePlayer player2;
	private int roundCount;
	private boolean isPlayer1Turn;
	private Character[] grid;

	/******************************
	[0][1][2]
	[3][4][5]
	[6][7][8]
	*******************************/

	public TicTacToeLogic(TicTacToePlayer player1, TicTacToePlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.player1.setToken('X');
		this.player2.setToken('O');
		roundCount = 0;
		isPlayer1Turn = true;
		grid = new Character[9];
	}

	public Character[] getGrid() {
		return grid;
	}

	public void insertNextTokenToGrid(int slotIndex) throws SlotAlreadyFilledException {
		checkIndex(slotIndex);
		char token = (isPlayer1Turn == true) ? player1.getToken() : player2.getToken();
		grid[slotIndex] = token;
		isPlayer1Turn = !isPlayer1Turn;
	}

	private void checkIndex(int index) throws SlotAlreadyFilledException {
		if(index < 0 || index > 8)
			throw new IndexOutOfBoundsException("Index of slot ranges between 0 and 8. Invalid index: " + index);
		else if(grid[index] != null) 
			throw new SlotAlreadyFilledException("Slot already has token");
	}

}