package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import model.player.Player;
import model.player.RealPlayer;
import model.tiles.Obtainable;
import utilities.enumerations.InitialDistribution;

public final class GameInitializer {

	private static final GameInitializer SINGLETON = new GameInitializer();
	private boolean alreadyCalled;
	private List<Player> playerList;

	public GameInitializer() {
	}

	/**
	 * The instance of GameInitializer
	 * 
	 * @return the singleton
	 */
	public static GameInitializer getInstance() {
		return SINGLETON;
	}

	/**
	 * Initializes the game creating the basic structures for the model.
	 * 
	 * @param playersMap
	 *            a map with name of the players and their respective icon
	 * @return a model already initialized
	 * 
	 * @throws IOException
	 *             when one of the initialization files not is found.
	 */
	public Model newGame(final String mode, final Map<String, String> playersMap) throws IOException {
		Objects.requireNonNull(playersMap, "NullPointerException: playersMap required non-null.");
		if (this.alreadyCalled) {
			throw new IllegalStateException("IllegalStateException: game already initialized!");
		}
		
		this.alreadyCalled = true;
		
		Board board = new Board(mode);
		
		this.playerList = new ArrayList<>();
		
		
		for (InitialDistribution v : InitialDistribution.values()) {
			if (v.getPlayerNumber() == playersMap.size()) {
				playersMap.keySet().forEach(e -> {
					Player player = new RealPlayer(e, v.getMoneyAmount(), playersMap.get(e));
					/* for each properties diceded by the rules (enum - InitialDistribution) a
					 * player buy a determinated number of any property
					 */
					for (int i = 0; i < v.getContractNumber(); i++) {
						player.buyProperty(board.getTiles(t -> t instanceof Obtainable).stream().map(t -> (Obtainable) t).filter(prop -> !prop.getOwner().isPresent())
								.findFirst().get()); //addProperties invece di buy, per mettere buy nel model
					}
//					player.decMoney(player.getProperties().stream().mapToInt(Obtainable::getPrice).sum());
					this.playerList.add(player);
				});
			}
		}
		return new ModelImpl(board, new TurnImpl(playerList));
	}

	/**
	 * Loads a game previously saved.
	 * 
	 * @param memento
	 *            the memento loaded
	 * @return a model already initialized.
	 */
	public Model loadGame(final ModelMemento memento) {
		Objects.requireNonNull(memento);
		if (this.alreadyCalled) {
			throw new IllegalStateException("IllegalStateException: game already initialized!");
		}
		this.alreadyCalled = true;
		final Model model = new ModelImpl(memento.getGameBoard(), memento.getPlayers()); // imprevisti e probabilità
		return model;
	}

}
