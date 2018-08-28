package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.Model;
import model.exceptions.NotEnoughMoneyException;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Buildable;
import model.tiles.Obtainable;
import utilities.AlertFactory;

public class DialogController implements DialogObserver {

	private static final DialogController SINGLETON = new DialogController();
	private static final int NUM_BUILD_MAX = 5; // sarebbe utile un getter di questo valore nelle buildable?
	private Model model; // final

	public static DialogController getDialogController() {
		return SINGLETON;
	}

	private DialogController() {
		//mi serve un controller, lo prendo da ControllerImpl o lo passo come costruttore
	}

	@Override
	public void executeAuction(List<PlayerInfo> playerList, List<String> passwordList, Obtainable property) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		try {
			if (passwordList.stream().distinct().count() != passwordList.size()) {
				AlertFactory
						.createInformationAlert("Get a plan together", null, "Two or more player have entered the same value");
			} else {
				int moneyAmount = passwordList.stream().map(Integer::parseInt)
						.max(Comparator.comparing(Integer::valueOf)).get();
				passwordList.forEach(e -> {
					if (Integer.parseInt(e) == moneyAmount) {
						/*Si itera tutta la lista dei giocatori, dato che per ogni giocatore esiste un solo passwordFiled determino l'inidce della lista che mi interessa e faccio get nella list adei giocatori*/
						if (canPay(playerList.get(passwordList.indexOf(e)), moneyAmount)) { 
							((Player) playerList.get(passwordList.indexOf(e))).addProperty(property);
							((Player) playerList.get(passwordList.indexOf(e))).payments(moneyAmount);
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
				new SoundController("/music/plastic_house_or_hotel_drop_on_playing_board.wav").play(false);
				property.incBuildings(); 
				((Player) model.getCurrentPlayer()).payments(property.getPriceForBuilding());
			} else {
				AlertFactory.createErrorAlert("Oak's words echoed", null,
						"There's time and place for everything, but not now...");
			}
		} else {
			AlertFactory.createErrorAlert("Nope", "You don't have enought money",
					"you have only: " + this.model.getCurrentPlayer().getMoney() +"$");
		}
	}

	@Override
	public void decHouse(Buildable property) { //dato che si tratta di un metodo che modifica la proprietà bisogna metterlo nel model
			if (property.getBuildingNumber() != 0) {
				new SoundController("/music/plastic_house_or_hotel_drop_on_playing_board.wav").play(false);
				decHouse(property);
				((Player) model.getCurrentPlayer()).gainMoney(property.getPriceForBuilding()/2); // searchPlayerByName, oppure currentPlayer torna Player
			}
	}

	@Override
	public void setMortgage(List<String> propertiesList) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		propertiesList.stream().map(propertyName -> getPropertyByName(propertyName)).forEach(e -> {
//			e.setMortgage();
			((Player) model.getCurrentPlayer()).gainMoney(e.getMortgage());
			e.hasMortgage(); //non so come si imposti una proprietà ipotecata
		});
	}
	
 	public void mortgageDialogClick(Obtainable property) {
		if(property.getOwner().get() == model.getCurrentPlayer().getName()) {
//			if(property.isMortgage) {
				setMortgage(Arrays.asList(property.getNameOf()));
			// } else {
			if(canPay(model.getCurrentPlayer(), property.getMortgage() * 10/100)) {
				((Player) model.getCurrentPlayer()).unmortgageProperty(property);
			}
		} else {
			AlertFactory.createInformationAlert("Ehi", null, "That's not your turn!");
		}
	}

	@Override
	public void buyPropertyClick(Obtainable property) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
		try {
			((Player) model.getCurrentPlayer()).buyProperty(property);
		} catch (NotEnoughMoneyException e) {
			//Auction Dialog
		}
	}

	@Override
	public int accumulatedMoney(List<String> propertiesList) { //getPoperties by name
		List<Obtainable> obtainableList = propertiesList.stream().map(propertyName -> getPropertyByName(propertyName)).collect(Collectors.toList());
		return obtainableList.stream().mapToInt(property -> property.getMortgage()).sum()
				+ obtainableList.stream().filter(property -> property instanceof Buildable).map(property -> (Buildable) property)
						.mapToInt(value -> value.getPriceForBuilding() / 2 * value.getBuildingNumber()).sum();
	}

	//NON CREDO SERVA
//	@Override
//	public void executeMortgage(List<String> propertiesList) {
//		((Player) model.getCurrentPlayer()).mortgageProperties(propertiesList.stream().map(propertyName -> getPropertyByName(propertyName)).collect(Collectors.toList())); //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model														// player fosse un "Player"
//		setMortgage(propertiesList);
//	}

//	public void executeTrade(Player firstPlayer, Player secondPlayer, List<Obtainable> firstProperties, 
//			List<Obtainable> secondProperties, int firstMoney, int secondMoney) { //dato che si tratta di un metodo che modifica il giocatore bisogna metterlo nel model
	public void executeTrade(String secondPlayerName, List<String> firstPropertiesNames, 
			List<String> secondPropertiesNames, String firstMoneyInput, String secondMoneyInput) { 
		try {
			int firstMoney = Integer.parseInt(firstMoneyInput);
			int secondMoney = Integer.parseInt(secondMoneyInput);
		
			Player firstPlayer = (Player) model.getCurrentPlayer();
			Player secondPlayer = (Player) getPlayerByName(secondPlayerName);
			List<Obtainable> firstProperties = new ArrayList<>();
			List<Obtainable> secondProperties = new ArrayList<>();
			firstPropertiesNames.forEach(e -> {
				firstProperties.add(getPropertyByName(e));
			});
			secondPropertiesNames.forEach(e -> {
				secondProperties.add(getPropertyByName(e));
			});
			
			if (!canPay(firstPlayer, firstMoney) && canPay(secondPlayer, secondMoney)) {
				AlertFactory.createErrorAlert("He's trying to cheat you!", null,
						firstPlayer.getName() + "Doesn't have enought money!");
			} else if (!canPay(secondPlayer, secondMoney) && canPay(firstPlayer, firstMoney)) {
				AlertFactory.createErrorAlert("He's trying to cheat you!", null,
						secondPlayer.getName() + "Doesn't have enought money!");
			} else if (!canPay(firstPlayer, firstMoney) && !canPay(secondPlayer, secondMoney)) {
				AlertFactory.createErrorAlert("Nope", null,
						secondPlayer.getName() + " and " + firstPlayer.getName() + " check your wallets.");
			} else {
				secondProperties.forEach(e -> {
					firstPlayer.addProperty(secondPlayer.removeProperty(e));
				});
				firstPlayer.gainMoney(secondMoney);
				firstPlayer.payments(firstMoney);
	
				firstProperties.forEach(e -> {
					secondPlayer.addProperty(firstPlayer.removeProperty(e));
				});
				secondPlayer.gainMoney(firstMoney);
				secondPlayer.payments(secondMoney);
			}
		} catch (NumberFormatException ex) {
			AlertFactory.createErrorAlert("Speak (type) as you eat", "Someone did not enter a number!",
					"Please, use only numbers...");
		}
	}
	
	private Obtainable getPropertyByName(String propertyName) {
		return this.model.getProperties().stream().filter(property -> property.getNameOf().equals(propertyName)).findFirst().get();
	}

	private boolean canPay(PlayerInfo player, int moneyAmount) {
		return player.canPay(moneyAmount);
	}
	
	public PlayerInfo getPlayerByName(String playerName) {
		return this.model.getPlayers().stream().filter(player -> player.getName().equals(playerName)).findFirst().get();
	}
}
