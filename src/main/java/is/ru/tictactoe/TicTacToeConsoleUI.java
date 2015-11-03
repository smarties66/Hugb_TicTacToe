package is.ru.tictactoe;

import java.util.Scanner;

public class TicTacToeConsoleUI {

	private TicTacToeLogic logic;
	private Scanner in = new Scanner(System.in);

	public void start() {
		System.out.println("Welcome to TicTacToe!\nEnter the names of players");
		System.out.println();
		System.out.print("Player 1: ");
		TicTacToePlayer player1 = new TicTacToePlayer(in.next());
		System.out.print("Player 2: ");
		TicTacToePlayer player2 = new TicTacToePlayer(in.next());
		System.out.println();
		logic = new TicTacToeLogic(player1, player2);

		String newGame = "yes";
		while(newGame.equals("yes") || newGame.equals("Yes")){
			printGameBoard();
			playRound();
			System.out.println("Do you want to play another round? Yes / No?: ");
			newGame = in.next();
			logic.newGame();
		}
	}

	private void printGameBoard() {
		Character[] gameBoard = logic.getGrid();
		System.out.println();
		System.out.println("   |   |   ");
		printSlot(gameBoard[0], 1);
		System.out.print("|");
		printSlot(gameBoard[1], 2);
		System.out.print("|");
		printSlot(gameBoard[2], 3);
		System.out.println();
		System.out.println("___|___|___");
		System.out.println("   |   |   ");
		printSlot(gameBoard[3], 4);
		System.out.print("|");
		printSlot(gameBoard[4], 5);
		System.out.print("|");
		printSlot(gameBoard[5], 6);
		System.out.println();
		System.out.println("___|___|___");
		System.out.println("   |   |   ");
		printSlot(gameBoard[6], 7);
		System.out.print("|");
		printSlot(gameBoard[7], 8);
		System.out.print("|");
		printSlot(gameBoard[8], 9);
		System.out.println();
		System.out.println("   |   |   ");
		System.out.println();
	}

	private void printSlot(Character slot, int index) {
		if(slot == null) 
			System.out.print(" " + index + " ");
		else
			System.out.print(" " + slot + " ");
	}

	private void playRound() {
		while(!logic.isGameOver()) {
			playerMakeMove();
			printGameBoard();
		}
		if(logic.isWin()){
			TicTacToePlayer winner = logic.getWinner();
			System.out.println("After round " + logic.getRoundNumber() + ", "
								+ winner.getName() + " is the winner");
		}
		else {
			System.out.println("Draw");
		}
		System.out.println(logic.getPlayer1Name() + " score: " + logic.getPlayer1Score());
		System.out.println(logic.getPlayer2Name() + " score: " + logic.getPlayer2Score());
		System.out.println("Number of draws: " + logic.getNumberOfDraws());
		System.out.println();
	}

	private void playerMakeMove() {
		String playerName = logic.getPlayerWhoHasTurn().getName();
		System.out.println(playerName + ". Insert token into slot [1-9]: ");
		
		try{
			logic.insertNextTokenToGrid(in.nextInt() - 1);
		}catch(SlotAlreadyFilledException e) {
			System.out.println(playerName + " you made an invalid move. Slot already has a token.\nPlease make another move.");
		}catch(IndexOutOfBoundsException e) {
			System.out.println(playerName + " you made an invalid move. Please enter an integer of range [1-9]");
		}
	}
}