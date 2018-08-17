package controller;

import java.io.File;
import java.util.List;
import java.util.Objects;

import model.GameInitializer;
import model.Model;
import model.ResourceManager;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import view.View;

public class ControllerImpl implements Controller{

	private static final ControllerImpl SINGLETON = new ControllerImpl();
	private Model model; 
	private View view;
	
    /**
     * Method that return the only one instance of Controller.
     * 
     * @return instance of Controller.
     */
	public static ControllerImpl getController() {
		return SINGLETON;
	}

	@Override
	public void newGameInit(List<String> playersName, List<String> playersIcon) {
		
		
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGameFromFile(final File file) {
		Objects.requireNonNull(file, "NullPointerException, file required non-null.");
		this.model = GameInitializer.getInstance().loadGame(ResourceManager.getInstance().loadGameFromFile(file));
	}

	@Override
	public void showContract(String contractName, String currentPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void diceClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void settingsClick() {
		// TODO Auto-generated method stub
		
	}
	
	private Obtainable findPropertyByName(String propertyName) {
		return model.getProperties().stream().filter(property -> property.getNameOf().equals(propertyName)).findFirst().get();
	}
	
	private Player findPlayerByName(String playerName) {
		return (Player) model.getPlayers().stream().filter(player -> player.getName().equals(playerName)).findFirst().get();
	}

	
}
