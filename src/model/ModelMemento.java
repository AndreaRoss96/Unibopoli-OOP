package model;

import java.io.Serializable;

/**
 * Captures and externalize a partial snapshot of a Model's subclass internal
 * state, so that the subclass can be serialized or loaded from a file.
 */
public class ModelMemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2066419006358603603L;

	private final Board gameBoard;
	private final Turn players;
	//private final Set<Obtainable> properties; //attenzione non sono serializzabili
	// private final Set<Imprevisti> imprevistis;
	// private final Set<Probabilità> probabilitàs;

	/**
	 * It creates an instance of ModelMemento.
	 * 
	 * @param gameBoard
	 *            the game board.
	 * @param players
	 *            the players of the game.
	 * @param currentPlayer
	 *            the current player.
	 * @param properties
	 *            the properties of the game.
	 * -- imprevisti e probabilità --
	 */
	public ModelMemento(final Board gameBoard, final Turn players) {
			 /*final Set<Obtainable> properties
												 * final Set<Imprevisti> imprevistis, final Set<Probabilità>
												 * probabilitàs
												 */
		this.gameBoard = gameBoard;
		this.players = players;
//		this.properties = properties;
		// imprevisti e probabilità
	}

	/**
	 * @return the game board.
	 */
	public Board getGameBoard() {
		return this.gameBoard;
	}

	/**
	 * @return a list of all players in game.
	 */
	public Turn getPlayers() {
		return this.players;
	}

	/**
	 * @return the all the properties.
	 */
//	public Set<Obtainable> getProperties() {
//		return this.properties;
//	}
}
