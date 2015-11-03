package is.ru.tictactoe;

public class SlotAlreadyFilledException extends RuntimeException {
	public SlotAlreadyFilledException(String message) {
		super(message);
	}
}
