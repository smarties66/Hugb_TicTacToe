package is.ru.tictactoe;

public class TicTacToeLogic {

	private TicTacToePlayer player1;
	private TicTacToePlayer player2;
	private int roundCount;
	private int tokenCounter;
	private boolean isPlayer1Turn;
	private Character[] grid;
	private Character winnerToken;
	private final char X = 'X';
	private final char O = 'O'; 
	private final int GRIDSIZE = 9;
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
		init(player1, player2, new Character[GRIDSIZE], true);
	}

	public TicTacToeLogic( TicTacToePlayer player1, TicTacToePlayer player2, Character[] grid, boolean isPlayer1Turn) {
		init(player1, player2, grid, isPlayer1Turn);
		fixTokenCount();
		searchForWin();
	}

	private void init(TicTacToePlayer player1, TicTacToePlayer player2, Character[] grid, boolean isPlayer1Turn) {
		if(grid.length != GRIDSIZE)
			throw new IllegalArgumentException("TicTacToeGrid must be of size 9. Illegal Size: " + grid.length);
		
		this.player1 = player1;
		this.player2 = player2;
		this.player1.setToken(X);
		this.player2.setToken(O);
		this.grid = grid;
		this.isPlayer1Turn = isPlayer1Turn;
		roundCount = 1;
		tokenCounter = 0;
	}

	public Character[] getGrid() {
		return grid;
	}

	public void insertNextTokenToGrid(int slotIndex){
		if(isGameOver())
			throw new GameOverException("Game is over, another token cannot be inserted");

		checkIndex(slotIndex);
		char token = (isPlayer1Turn == true) ? player1.getToken() : player2.getToken();
		grid[slotIndex] = token;
		tokenCounter++;
		searchForWin();
		isPlayer1Turn = !isPlayer1Turn;
	}

	private void checkIndex(int index) {
		if(index < 0 || index > GRIDSIZE - 1)
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

	public void newGame() {
		grid = new Character[GRIDSIZE];
		tokenCounter = 0;
		switchPlayerTokens();
		isPlayer1Turn = (player1.getToken() == X) ? true : false;
		winnerToken = null;
		roundCount++;
	}

	private void switchPlayerTokens() {
		char tempToken = player1.getToken();
		player1.setToken(player2.getToken());
		player2.setToken(tempToken);
	}

	public int getRoundNumber() {
		return roundCount;
	}

	public int getNumberOfDraws() {
		int completedRoundCount = (isGameOver() == true) ? roundCount : roundCount - 1;
		return completedRoundCount - (player1.getWinCount() + player2.getWinCount());
	}

	public boolean isGameOver() {
		return winnerToken != null || tokenCounter == GRIDSIZE;
	}

}
