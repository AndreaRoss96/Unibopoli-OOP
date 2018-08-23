package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.player.Player;
import model.player.RealPlayer;
import model.tiles.Obtainable;
import utilities.enumerations.InitialDistribution;

public final class GameInitializer {

	private static final GameInitializer SINGLETON = new GameInitializer();
	private boolean alreadyCalled;
	private List<Obtainable> propertiesList;
	private List<Player> playerList;

	public GameInitializer() {
	}

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
	public Model newGame(Map<String, Icon> playersMap) throws IOException {
		Objects.requireNonNull(playersMap, "NullPointerException: playersMap required non-null.");
		if (this.alreadyCalled) {
			throw new IllegalStateException("IllegalStateException: game already initialized!");
		}
		this.alreadyCalled = true;

		this.propertiesList = new ArrayList<>();
		// qui si dovrebbe leggere da file le informazioni per leggere e fillare il set

		this.playerList = new ArrayList<>();
		for (InitialDistribution v : InitialDistribution.values()) {
			if (v.getPlayerNumber() == playersMap.size()) {
				playersMap.keySet().forEach(e -> {
					Player player = new RealPlayer(e, v.getMoneyAmount()); // icona
					/* for each properties diceded by the rules (enum - InitialDistribution) a
					 * player buy a determinated number of any property
					 */
					for (int i = 0; i <= v.getContractNumber(); i++) {
						player.buyProperty(this.propertiesList.stream().filter(prop -> !prop.getOwner().isPresent())
								.findAny().get());
					}
					this.playerList.add(player);
				});
			}
		}
		return new ModelImpl(); // mi serve la board
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
		final Model model = new ModelImpl(memento.getGameBoard(), memento.getPlayers(), memento.getProperties(),
				memento.getCurrentPlayer()); // imprevisti e probabilità
		return model;
	}

}
