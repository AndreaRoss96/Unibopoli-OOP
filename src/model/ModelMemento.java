package model;

import java.io.Serializable;

/**
 * Captures and externalize a partial snapshot of a Model's subclass internal
 * state, so that the subclass can be serialized or loaded from a file.
 */
public class ModelMemento implements Serializable {

	private static final long serialVersionUID = 5814591792686123738L;
	
	private final Board gameBoard;
	private final Turn players;

	/**
	 * It creates an instance of ModelMemento.
	 * 
	 * @param gameBoard
	 *            the game board.
	 * @param Turn
	 *            the players of the game.
	 */
	public ModelMemento(final Board gameBoard, final Turn players) {
		this.gameBoard = gameBoard;
		this.players = players;
	}

	/**
	 * @return the game board.
	 */
	public Board getGameBoard() {
		return this.gameBoard;
	}

	/**
	 * @return the CiruclarList of all players in game.
	 */
	public Turn getPlayers() {
		return this.players;
	}
}
