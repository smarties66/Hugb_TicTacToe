package is.ru.tictactoe;

public class SlotAlreadyFilledException extends Exception {
	public SlotAlreadyFilledException(String message) {
		super(message);
	}
}
