package controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import model.Model;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Buildable;
import model.tiles.Obtainable;
import utilities.AlertFactory;
import view.gameDialog.MortgageDialog;

public class DialogController implements DialogObserver {

	private static final DialogController SINGLETON = new DialogController();
	private static final int NUM_BUILD_MAX = 5; // sarebbe utile un getter di questo valore nelle buildable?
	private Model model; // final

	public static DialogController getDialogController() {
		return SINGLETON;
	}

	private DialogController() {
		// serve un model, quindi o si implementa lo stesso metodo presente nell'altro
		// controller o si estende l'altro controller
	}

	@Override
	public Obtainable getPropertyByName(String propertyName) {
		return null; // model.getProperties().stream().filter
	}

	@Override
	public void executeAuction(List<Player> playerList, List<String> passwordList, Obtainable property) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		try {
			if (passwordList.stream().distinct().count() != passwordList.size()) {
				AlertFactory
						.createInformationAlert("Get a plan together", null, "Two o more player entered the same value");
			} else {
				int moneyAmount = passwordList.stream().map(Integer::parseInt)
						.max(Comparator.comparing(Integer::valueOf)).get();
				passwordList.forEach(e -> {
					if (Integer.parseInt(e) == moneyAmount) {
						/*Si itera tutta la lista dei giocatori, dato che per ogni giocatore esiste un solo passwordFiled determino l'inidce della lista che mi interessa e faccio get nella list adei giocatori*/
						if (canPay(playerList.get(passwordList.indexOf(e)), moneyAmount)) { 
							playerList.get(passwordList.indexOf(e)).addProperty(property);
							playerList.get(passwordList.indexOf(e)).payments(moneyAmount);
						} else {
							AlertFactory.createInformationAlert("Ehi", null,
									playerList.get(passwordList.indexOf(e)).getName()
											+ ", you don't have all those money.");
						}
					}
				});
			}
		} catch (NumberFormatException ex) {
			AlertFactory.createErrorAlert("Speak (type) as you eat", "Someone did not enter a number!",
					"Please, use only numbers...");
		}

	}

	@Override
	public void incHouse(Buildable property) { //dato che si tratta di un metodo che modifica la proprietà bisogna metterlo nel model
		if (canPay(model.getCurrentPlayer(), property.getPriceForBuilding())) {
			if (property.getBuildingNumber() < NUM_BUILD_MAX) {
				property.incBuildings(); //deve diminuire i soldi del giocatore
			} else {
				AlertFactory.createErrorAlert("Oak's words echoed", null,
						"There's time and place for everything, but not now...");
			}
		} else {
			AlertFactory.createErrorAlert("Nope", "You don't have enought money",
					"you have only: " + this.model.getCurrentPlayer().getMoney());
		}
	}

	@Override
	public void decHouse(Buildable property) { //dato che si tratta di un metodo che modifica la proprietà bisogna metterlo nel model
			if (property.getBuildingNumber() == 0) {
				decHouse(property);
				property.getOwner().get(); // searchPlayerByName, oppure currentPlayer torna Player
			} //deve aumentare i solid del giocatore
	}

	@Override
	public void setMortgage(List<String> propertyList) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		propertyList.forEach(e -> {
			// getPropertyByName
		});
	}

	@Override
	public void buyProperty(Obtainable property) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		if (canPay(model.getCurrentPlayer(), property.getPrice())) {
			if (model.getCurrentPlayer().getPosition() == property.getPosition()) {
				((Player) model.getCurrentPlayer()).buyProperty(property); //searchPlayerByName, oppure
				// currentPlayer torna Player
			}
		}
	}

	@Override
	public int accumulatedMoney(List<String> propertiesList) { //getPoperties by name
		return propertiesList.stream().mapToInt(Obtainable::getMortgage).sum() + propertiesList.stream()
				.filter(property -> property instanceof Buildable).map(property -> (Buildable) property)
				.mapToInt(value -> value.getPriceForBuilding() / 2 * value.getBuildingNumber()).sum();
	}

	@Override
	public void executeMortgage(List<String> propertiesList) {
		model.getCurrentPlayer().mortgageProperties(propertiesList); //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model														// player fosse un "Player"
	}

	@Override
	public void executeTrade(Player firstPlayer, Player secondPlayer, List<Obtainable> firstProperties, 
			List<Obtainable> secondProperties, int firstMoney, int secondMoney) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		if (!canPay(firstPlayer, firstMoney) && canPay(secondPlayer, secondMoney)) {
			AlertFactory.createErrorAlert("He's trying to cheat you!", null,
					firstPlayer.getName() + "Doesn't have enought money!");
		} else if (!canPay(secondPlayer, secondMoney) && canPay(firstPlayer, firstMoney)) {
			AlertFactory.createErrorAlert("He's trying to cheat you!", null,
					secondPlayer.getName() + "Doesn't have enought money!");
		} else if (!canPay(firstPlayer, firstMoney) && !canPay(secondPlayer, secondMoney)) {
			AlertFactory.createErrorAlert("Nope", null,
					secondPlayer.getName() + " and " + firstPlayer.getName() + " check your wallet.");
		} else {
			secondProperties.forEach(e -> {
				firstPlayer.addProperty(e);
			});
			firstPlayer.gainMoney(secondMoney);
			firstPlayer.payments(firstMoney);

			firstProperties.forEach(e -> {
				secondPlayer.addProperty(e);
			});
			secondPlayer.gainMoney(firstMoney);
			secondPlayer.payments(secondMoney);
		}
	}

	private boolean canPay(PlayerInfo player, int moneyAmount) {
		return player.canPay(moneyAmount);
	}

}
