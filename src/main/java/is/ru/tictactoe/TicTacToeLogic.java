package is.ru.tictactoe;

public class TicTacToeLogic {

	private TicTacToePlayer player1;
	private TicTacToePlayer player2;
	private int roundCount;
	private int tokenCounter;
	private boolean isPlayer1Turn;
	private Character[] grid;
	private Character winnerToken;
	private int[][] possibleWins = 
	{
		{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
		{0, 3, 6}, {1, 4, 7}, {2, 5, 8},
		{0, 4, 8}, {2, 4, 6}
	};

	/******************************
	[0][1][2]
	[3][4][5]
	[6][7][8]
	*******************************/

	public TicTacToeLogic(TicTacToePlayer player1, TicTacToePlayer player2) {
		init(player1, player2, new Character[9], true);
	}

	public TicTacToeLogic(TicTacToePlayer player1, TicTacToePlayer player2, Character[] grid, boolean isPlayer1Turn) {
		init(player1, player2, grid, isPlayer1Turn);
		fixTokenCount();
		searchForWin();
	}

	private void init(TicTacToePlayer player1, TicTacToePlayer player2, Character[] grid, boolean isPlayer1Turn) {
		if(grid.length != 9)
			throw new IllegalArgumentException("TicTacToeGrid must be of size 9. Illegal Size: " + grid.length);
		
		this.player1 = player1;
		this.player2 = player2;
		this.player1.setToken('X');
		this.player2.setToken('O');
		this.grid = grid;
		this.isPlayer1Turn = isPlayer1Turn;
		roundCount = 0;
		tokenCounter = 0;
	}

	public Character[] getGrid() {
		return grid;
	}

	public void insertNextTokenToGrid(int slotIndex) throws SlotAlreadyFilledException {
		checkIndex(slotIndex);
		char token = (isPlayer1Turn == true) ? player1.getToken() : player2.getToken();
		grid[slotIndex] = token;
		tokenCounter++;
		searchForWin();
		isPlayer1Turn = !isPlayer1Turn;
	}

	private void checkIndex(int index) throws SlotAlreadyFilledException {
		if(index < 0 || index > 8)
			throw new IndexOutOfBoundsException("Index of slot ranges between 0 and 8. Invalid index: " + index);
		else if(grid[index] != null) 
			throw new SlotAlreadyFilledException("Slot already has token");
	}

	private void fixTokenCount() {
		tokenCounter = 0;
		for(Character token : grid) {
			if(token != null) tokenCounter++;
		}
	}

	public boolean isWin() {
		return winnerToken != null;
	}

	public boolean isDraw() {
		return tokenCounter == grid.length && winnerToken == null;
	}

	private void searchForWin() {
		for(int[] i : possibleWins) {
			if(grid[i[0]] != null && grid[i[0]].equals(grid[i[1]]) && grid[i[1]].equals(grid[i[2]])) {
				winnerToken = grid[i[0]];
				updateWinnerScore();
			}
		}
	}

	private void updateWinnerScore() {
		if(winnerToken == null) return;
		getWinner().incrementWinCount();
	}

	public TicTacToePlayer getWinner() {

		if(winnerToken != null)
			return (winnerToken == player1.getToken()) ? player1 : player2;
		return null; 
	}

	public int getPlayer1Score() {
		return player1.getWinCount();
	}

	public int getPlayer2Score() {
		return player2.getWinCount();
	}

	public TicTacToePlayer getPlayerWhoHasTurn() {
		return (isPlayer1Turn == true) ? player1 : player2;
	}

}