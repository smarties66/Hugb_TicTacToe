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
		roundCount = 0;
		isPlayer1Turn = true;
		grid = new Character[9];
	}

	public Character[] getGrid() {
		return grid;
	}
}