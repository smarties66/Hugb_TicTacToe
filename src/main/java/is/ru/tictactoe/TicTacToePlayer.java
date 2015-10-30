package is.ru.tictactoe;

public class TicTacToePlayer {
	private final String name;
	private int winCount;
	private char token;

	public TicTacToePlayer(String name) {
		this.name = name;
		winCount = 0;
	}

	public String getName() {
		return name;
	}

	public void setToken(char token) {
		this.token = token;
	}

	public char getToken() {
		return token;
	}

	public int getWinCount() {
		return winCount;
	}

	public void incrementWinCount() {
		winCount++;
	}
}