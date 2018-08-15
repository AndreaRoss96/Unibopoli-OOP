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
 * 
 * @author Rossolini Andrea
 */
public interface DialogObserver {
	
//	Node getContract(Obtainable property); con la nuova classe "Contract non credo che serva"
	
	Obtainable getPropertyByName(String propertyName);
	
	/**
	 * metodi per AuctionDialog 
	 */
	void executeAuction(List<Player> playerList, List<String> passwordList, Obtainable property);
			
	/**
	 * metodi per cardDialog
	 */
	void incHouse(Buildable property); //ritorna false se la proprietà interessata ha ancora degli slot liberi per le case e se il giocatore corrente ha abbastanza soldi per comprerne altre, true altrimenti
	void decHouse(Buildable property);
	void setMortgage(List<String> propertyList); //se la proprietà non è ipotecata la ipoteca, il contrario altirmenti --> chiedi conferma
	void buyProperty(Obtainable property); //compra la proprietà interessata, solo se il giocatore è nella stessa posizione del tile --> altrimenti dialog esplicativo
	
	/**
	 * metodi per mortgageDialog
	 */
	int accumulatedMoney(List<Obtainable> propertiesList); //altrimenti una lista di stringhe
	void executeMortgage(List<Obtainable> propertiesList);
	
	
	/**
	 * metodi per TradeDialog
	 */
	void executeTrade(Player firstPlayer, Player secondPlayer, List<Obtainable> firstProperties, List<Obtainable> secondProperties, int firstMoney, int secondMoney);
	
}
