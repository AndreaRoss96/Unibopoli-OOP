package model;

import java.util.Objects;

public class GameInitializer {
	
	private static final GameInitializer SINGLETON = new GameInitializer();

	public GameInitializer() {
		// TODO Auto-generated constructor stub
	}
	
	public static GameInitializer getInstance() {
		return SINGLETON;
	}
	
	public void initGame() {
		
	}
	
	public Model loadGame(final ModelMemento memento) {
		Objects.requireNonNull(memento);
		final Model model = new ModelImpl(memento.getGameBoard(), memento.getPlayers(), memento.getProperties(), memento.getCurrentPlayer()); //imprevisti e probabilità
		return model;
	}

}
