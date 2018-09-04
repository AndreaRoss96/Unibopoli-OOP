package controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import model.exceptions.NotEnoughMoneyException;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Buildable;
import model.tiles.Obtainable;
import utilities.AlertFactory;
import view.gameDialog.AuctionDialog;
import view.gameDialog.CardDialog;
import view.gameDialog.TradeDialog;

public class DialogController implements DialogObserver {

	private static final DialogController SINGLETON = new DialogController();
	private static final int NUM_BUILD_MAX = 5; // sarebbe utile un getter di questo valore nelle buildable?
	private static final double UNMORTGAGE_FEE = 10/100.0;

	private final ControllerImpl controller;

	public static DialogController getDialogController() {
		return SINGLETON;
	}

	private DialogController() {
		this.controller = ControllerImpl.getController();
	}

	@Override
	public void executeAuction() {
		final List<String> betsList = AuctionDialog.getAuctionDialog().getBetsList();
		final Obtainable property = AuctionDialog.getAuctionDialog().getAuctionedProperty();
		final List<PlayerInfo> playerList = ControllerImpl.getController().getPlayers();
		try {
			if (betsList.stream().distinct().count() != betsList.size()) {
				AlertFactory.createInformationAlert("Get a plan together",
						"Two or more player have entered the same value");
			} else {
				int moneyAmount = betsList.stream().map(Integer::parseInt).max(Comparator.comparing(Integer::valueOf))
						.get();
				betsList.forEach(bet -> {
					if (Integer.parseInt(bet) == moneyAmount) {
						/*
						 * Si itera tutta la lista dei giocatori, dato che per ogni giocatore esiste un
						 * solo passwordFiled determino l'inidce della lista che mi interessa e faccio
						 * get nella list adei giocatori
						 */
						if (canPay(playerList.get(betsList.indexOf(bet)), moneyAmount)) {
							((Player) playerList.get(betsList.indexOf(bet))).addProperty(property);
							((Player) playerList.get(betsList.indexOf(bet))).payments(moneyAmount);
						} else {
							AlertFactory.createInformationAlert("Ehi", playerList.get(betsList.indexOf(bet)).getName()
									+ ", you don't have all those money.");
						}
					}
				});
			}
		} catch (NumberFormatException ex) {
			AlertFactory.createErrorAlert("Speak (type) as you eat",
					"Someone did not enter a number!\nPlease, use only numbers...");
		}
		this.controller.updateView();
	}

	@Override
	public boolean incHouse() {
		final Buildable property = CardDialog.getCardDialog().getProperty();
		if (canPay(this.controller.getCurrentPlayer(), property.getPriceForBuilding())) {
			if (property.getBuildingNumber() < NUM_BUILD_MAX) {
				controller.getSound().playSound(("/music/plastic_house_or_hotel_drop_on_playing_board.wav"));
				property.incBuildings(); // deve diminuire i soldi del giocatore
				((Player) this.controller.getCurrentPlayer()).payments(property.getPriceForBuilding());
			}
		} else {
			AlertFactory.createErrorAlert("Nope", "You don't have enought money\nyou have only: "
					+ this.controller.getCurrentPlayer().getMoney() + "$");
		}
		this.controller.updateView();
		CardDialog.getCardDialog().updateBuildingLabel();
		return property.getBuildingNumber() >= NUM_BUILD_MAX;
	}

	@Override
	public boolean decHouse() {
		final Buildable property = CardDialog.getCardDialog().getProperty();
		System.out.println(property.getNameOf());
		if (property.getBuildingNumber() != 0) {
			controller.getSound().playSound("/music/plastic_house_or_hotel_drop_on_playing_board.wav");
			property.decBuildings();
			((Player) this.controller.getCurrentPlayer()).gainMoney(property.getPriceForBuilding() / 2);
		}
		this.controller.updateView();
		CardDialog.getCardDialog().updateBuildingLabel();
		return property.getBuildingNumber() == 0;
	}

	@Override
	public void setMortgage(List<Optional<String>> list) {
		list.stream().map(property -> property.get()).map(propertyName -> getPropertyByName(propertyName)).forEach(property -> {
			if(!property.hasMortgage()) {
				((Player) this.controller.getCurrentPlayer()).gainMoney(property.getMortgage());
			} else {
				((Player) this.controller.getCurrentPlayer()).payments((int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)));
				System.out.println(property.getMortgage() + " + " + (int) (property.getMortgage() * UNMORTGAGE_FEE));
			}
			property.changeMortgageStatus();
		});
		this.controller.updateView();
	}

	@Override
	public void mortgageDialogClick() {
		final Obtainable property = CardDialog.getCardDialog().getProperty();
		 if(property.getOwner().get().equals(this.controller.getCurrentPlayer().getName())) {
			 if(property.hasMortgage()) {
				 this.setMortgage(Arrays.asList(Optional.of(property.getNameOf())));
			 } else {
				 if (this.canPay(this.controller.getCurrentPlayer(), (int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)))) {
					 this.setMortgage(Arrays.asList(Optional.of(property.getNameOf())));
				 } else {
					 AlertFactory.createInformationAlert("I'm sorry", "You don't have enough money...");
				 }
			 }
		 } else {
			 AlertFactory.createInformationAlert("Ehi", "Do it in your turn!");
		 }
	}

	@Override
	public void buyPropertyClick() { // dato che si tratta di un metodo che modifica il giocatore bisogna metterlo
										// nel model
		Obtainable property = CardDialog.getCardDialog().getProperty();
		try {
			((Player) this.controller.getCurrentPlayer()).buyProperty(property);
			this.controller.getSound().playSound("/music/CashRegister.wav");
		} catch (NotEnoughMoneyException e) {
			// Auction Dialog
			// bisogna vedere come viene gestito l'acquisto
		}
		this.controller.updateView();
	}

	@Override
	public int accumulatedMoney(List<String> list) {
		List<Obtainable> obtainableList = list.stream().map(propertyName -> getPropertyByName(propertyName))
				.collect(Collectors.toList());
		return obtainableList.stream().mapToInt(property -> property.getMortgage()).sum() + obtainableList.stream()
				.filter(property -> property instanceof Buildable).map(property -> (Buildable) property)
				.mapToInt(value -> value.getPriceForBuilding() / 2 * value.getBuildingNumber()).sum();
	}

	public void executeTrade() {
		final TradeDialog tradeDialog = TradeDialog.getTradeDialog();
		try {
			int firstMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getFirst());
			int secondMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getSecond());

			final Player firstPlayer = (Player) this.controller.getCurrentPlayer();
			final Player secondPlayer = (Player) getPlayerByName(tradeDialog.getSecondPlayer());
			final List<Obtainable> firstProperties = tradeDialog.getSelectedProperties().getFirst().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());
			final List<Obtainable> secondProperties = tradeDialog.getSelectedProperties().getSecond().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());
			
			System.out.println("Primoo: " + tradeDialog.getSelectedProperties().getFirst() + " Secondo: " + tradeDialog.getSelectedProperties().getSecond());

			if (!this.canPay(firstPlayer, firstMoney) && this.canPay(secondPlayer, secondMoney)) {
				AlertFactory.createErrorAlert("He's trying to cheat you!",
						firstPlayer.getName() + "Doesn't have enought money!");
			} else if (!this.canPay(secondPlayer, secondMoney) && this.canPay(firstPlayer, firstMoney)) {
				AlertFactory.createErrorAlert("He's trying to cheat you!",
						secondPlayer.getName() + "Doesn't have enought money!");
			} else if (!this.canPay(firstPlayer, firstMoney) && !this.canPay(secondPlayer, secondMoney)) {
				AlertFactory.createErrorAlert("Nope",
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
			System.out.println("-----escuzione------\nPrimoo: "+ firstPlayer.getProperties().stream().map(Obtainable::getNameOf).collect(Collectors.toList()) + " Secondo: " + secondPlayer.getProperties().stream().map(Obtainable::getNameOf).collect(Collectors.toList()));
		} catch (NumberFormatException ex) {
			AlertFactory.createErrorAlert("Speak (type) as you eat",
					"Someone did not enter a number!\nPlease, use only numbers...");
		}
		this.controller.updateView();
	}

	private Obtainable getPropertyByName(String propertyName) {
		return this.controller.getProperties().stream().filter(property -> property.getNameOf().equals(propertyName))
				.findFirst().get();
	}

	private boolean canPay(PlayerInfo player, int moneyAmount) {
		return player.canPay(moneyAmount);
	}

	public PlayerInfo getPlayerByName(String playerName) {
		return this.controller.getPlayers().stream().filter(player -> player.getName().equals(playerName)).findFirst()
				.get();
	}
}
