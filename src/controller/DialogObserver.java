package controller;

import java.util.List;

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
	
	Node getContract(Obtainable property);
	
	Obtainable getPropertyByName(String propertyName);
	
	String passwordFill(String text);
	
	List<PlayerInfo> getPlayerListSize();
	
	PlayerInfo getCurrentPlayer();
	
	boolean currentCanBuy(Obtainable property); //controlla se il giocatore corrente ha abbastanza soldi e se è nella stessa posizione della proprietà interessata
	
	boolean canBuild(Buildable property, boolean build); //controlla se in quella proprietà si può costruire, quindi se ha ancora slot liberi per la costruzione delle case/alberghi e se il giocatore ha abbastanza soldi, deve controllare anche che la proprietà interessata non sia ipotecata e che il giocatorecorrente sia il proprietario della proprietà
	
	void buyProperty(Obtainable property); //setta il proprietario alla proprietà e invoca BuyProperty al player
	
	boolean buildHouse(Buildable property); //ritorna false se la proprietà interessata ha ancora degli slot liberi per le case e se il giocatore corrente ha abbastanza soldi per comprerne altre, true altrimenti

	void setMortgage(List<String> propertyList); //ipoteca le proprietà scelte dal giocatore corrente
	
	boolean unMortgage(Obtainable property);  //ritorna true se il giocatore lo clicca e riesce a pagare l'ipoteca della proprietà, mentre torna false (e un dialog di allerta) se il giocatore non ha abbastaza soldi per pagare l'ipoteca
	
	boolean executeTrade(Player firstPlayer, Player secondPlayer, List<Obtainable> firstProperties, List<Obtainable> secondProperties, Integer firstMoney, Integer secondMoney);
	
	boolean checkTextField(List<String> textList); //controlla che le stringhe siano traducibili in integer
	
	boolean isCorrect(List<PasswordField> passwordList); //Controlla che ci siano valori uguali
}
