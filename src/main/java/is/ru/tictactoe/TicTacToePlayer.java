package is.ru.tictactoe;

public class TicTacToePlayer {
	private final String name;
	private int winCount;
	private char token;

	public TicTacToePlayer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}