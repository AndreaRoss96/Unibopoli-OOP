package controller;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Buildable;
import model.tiles.Obtainable;

/**
 * This interface contains all method used by Dialog's classes.
 */
public interface DialogObserver {
	
//	Node getContract(Obtainable property); con la nuova classe "Contract non credo che serva"
	
//	Obtainable getPropertyByName(String propertyName); è privata
	
	/**
	 * metodi per AuctionDialog 
	 */
	void executeAuction(List<PlayerInfo> playerList, List<String> passwordList, Obtainable property);
			
	/**
	 * metodi per cardDialog
	 */
	/**
	 * Increseases
	 * @param property
	 */
	void incHouse(Buildable property); 
	void decHouse(Buildable property);
	void setMortgage(List<String> propertyList); //se la proprietà non è ipotecata la ipoteca, il contrario altirmenti --> chiedi conferma
	void buyPropertyClick(Obtainable property); //compra la proprietà interessata, solo se il giocatore è nella stessa posizione del tile --> altrimenti dialog esplicativo
	
	/**
	 * metodi per mortgageDialog
	 */
	int accumulatedMoney(List<String> propertiesList); //altrimenti una lista di stringhe
	
	
	/**
	 * metodi per TradeDialog
	 */
//	void executeTrade(Player firstPlayer, Player secondPlayer, List<Obtainable> firstProperties, List<Obtainable> secondProperties, int firstMoney, int secondMoney);
	void executeTrade(String secondPlayerName, List<String> firstPropertiesNames, List<String> secondPropertiesNames, String firstMoney, String secondMoney);
	
	
	PlayerInfo getPlayerByName(String playerName);
}
