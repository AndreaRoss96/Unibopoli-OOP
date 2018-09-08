package controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import model.Model;
import model.exceptions.NotEnoughMoneyException;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.AdapterBuildable;
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import view.AlertFactory;
import view.gameDialog.AuctionDialog;
import view.gameDialog.CardDialog;
import view.gameDialog.MortgageDialog;
import view.gameDialog.TradeDialog;

public class DialogController implements DialogObserver {

	private static final DialogController SINGLETON = new DialogController();
	private static final int NUM_BUILD_MAX = 5; // sarebbe utile un getter di questo valore nelle buildable?
	private static final double UNMORTGAGE_FEE = 10 / 100.0;

	private final ControllerImpl controller;
	private Model model;

	public static DialogController getDialogController() {
		return SINGLETON;
	}

	private DialogController() {
		this.controller = ControllerImpl.getController();
	}
	
	@Override
	public void setModel(Model model) {
		if(this.model == null) {
			this.model = model;
		}
	}

	@Override
	public void executeAuction() {
		final List<String> betsList = AuctionDialog.getAuctionDialog().getBetsList();
		final Obtainable property = AuctionDialog.getAuctionDialog().getAuctionedProperty();
		final List<PlayerInfo> playerList = this.controller.getPlayers();
		try {
			if (betsList.stream().distinct().count() != betsList.size()) {
				AlertFactory.createInformationAlert("Get a plan together",
						"Two or more player have entered the same value");
			} else {
				int moneyAmount = betsList.stream().map(Integer::parseInt).max(Comparator.comparing(Integer::valueOf))
						.get();
				betsList.forEach(bet -> {
					if (Integer.parseInt(bet) == moneyAmount) {
						if (canPay(playerList.get(betsList.indexOf(bet)), moneyAmount)) {
							this.model.playerAddProperty(playerList.get(betsList.indexOf(bet)), property);
							this.model.playerPayment(playerList.get(betsList.indexOf(bet)), moneyAmount);
//							((Player) playerList.get(betsList.indexOf(bet))).addProperty(property);
//							((Player) playerList.get(betsList.indexOf(bet))).payments(moneyAmount);
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
	public void incHouseClick() {
		final BuildableImpl property = (BuildableImpl) CardDialog.getCardDialog().getProperty();
		final List<Boolean> areOtherMortgaged = this.controller.getCurrentPlayer().getPopertiesByColor().get(property.getColorOf()).stream().map(Obtainable::hasMortgage).collect(Collectors.toList());
		if (areOtherMortgaged.stream().filter(e -> e.booleanValue()).count() == 0) {
			if (canPay(this.controller.getCurrentPlayer(), property.getPriceForBuilding())) {
				if (property.getBuildingNumber() < NUM_BUILD_MAX) {
					controller.getSound().playSound(("/music/plastic_house_or_hotel_drop_on_playing_board.wav"));
					property.incBuildings();
					this.model.playerPayment(this.controller.getCurrentPlayer(), property.getPriceForBuilding());
				}
			} else {
				AlertFactory.createErrorAlert("Nope", "You don't have enought money\nyou have only: "
						+ this.controller.getCurrentPlayer().getMoney() + "$");
			}
		} else {
			AlertFactory.createErrorAlert("Nope", "One or more property of this color are mortgaged!");
		}
		this.controller.updateView();
		CardDialog.getCardDialog().updateCardDialog();
	}

	@Override
	public void decHouseClick() {
		final AdapterBuildable property = (AdapterBuildable) CardDialog.getCardDialog().getProperty();
		if (property.getBuildingNumber() != 0) {
			controller.getSound().playSound("/music/plastic_house_or_hotel_drop_on_playing_board.wav");
//			this.decHouse(property, (Player) this.controller.getCurrentPlayer());
			property.decBuildings();
			this.model.playerGainMoney(this.controller.getCurrentPlayer(), property.getPriceForBuilding() / 2);
		}
		this.controller.updateView();
		CardDialog.getCardDialog().updateCardDialog();
	}

	@Override
	public void setMortgage(List<Optional<String>> list) {
		list.stream().map(property -> property.get()).map(propertyName -> getPropertyByName(propertyName))
				.forEach(property -> {
					if (!property.hasMortgage()) {
//						((Player) this.controller.getCurrentPlayer()).gainMoney(property.getMortgage());
						this.model.playerGainMoney(this.controller.getCurrentPlayer(), property.getMortgage());
						this.model.unbuild(property, (Player) this.controller.getCurrentPlayer());
					} else {
//						((Player) this.controller.getCurrentPlayer()).payments(
//								(int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)));
						this.model.playerPayment(this.controller.getCurrentPlayer(), (int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)));
					}
					property.changeMortgageStatus();
				});
		this.controller.updateView();
	}

	@Override
	public void mortgageDialogClick() {
		final Obtainable property = CardDialog.getCardDialog().getProperty();
		if (property.getOwner().get().equals(this.controller.getCurrentPlayer().getName())) {
			if (property.hasMortgage()) {
				this.setMortgage(Arrays.asList(Optional.of(property.getNameOf())));
			} else {
				if (this.canPay(this.controller.getCurrentPlayer(),
						(int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)))) {
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
	public void buyPropertyClick() { 
		final Obtainable property = CardDialog.getCardDialog().getProperty();
		try {
//			((Player) this.controller.getCurrentPlayer()).buyProperty(property);
			//model
			this.model.buyProperty(this.controller.getCurrentPlayer(), property);
			this.controller.getSound().playSound("/music/CashRegister.wav");
		} catch (NotEnoughMoneyException e) {
			// Auction Dialog
		}
		this.controller.updateView();
	}

	@Override
	public void accumulatedMoney() {
		final List<Obtainable> obtainableList = MortgageDialog.getMortgageDialog().getSelected().stream().map(propertyName -> getPropertyByName(propertyName))
				.collect(Collectors.toList());
		MortgageDialog.getMortgageDialog().updateObtainedMoney(obtainableList.stream().mapToInt(property -> property.getMortgage()).sum() + obtainableList.stream()
				.filter(property -> property instanceof AdapterBuildable).map(property -> (AdapterBuildable) property)
				.mapToInt(value -> value.getPriceForBuilding() / 2 * value.getBuildingNumber()).sum());
	}

	@Override
	public void dialogTradeClick() { // se la mettessi nel model?
		final TradeDialog tradeDialog = TradeDialog.getTradeDialog();
		try {
			int firstMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getFirst());
			int secondMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getSecond());

			final PlayerInfo firstPlayer = this.controller.getCurrentPlayer();
			final PlayerInfo secondPlayer = getPlayerByName(tradeDialog.getSecondPlayer());
			final List<Obtainable> firstProperties = tradeDialog.getSelectedProperties().getFirst().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());
			final List<Obtainable> secondProperties = tradeDialog.getSelectedProperties().getSecond().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());

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
				this.model.executeTrade((Player) secondPlayer, firstMoney, secondMoney, firstProperties, secondProperties);
				//tipo se mettessi nel model sto pezzo
//				secondProperties.forEach(prop -> {
//					this.unbuild(prop, secondPlayer);
//					firstPlayer.addProperty(secondPlayer.removeProperty(prop));
//				});
//				firstPlayer.gainMoney(secondMoney);
//				firstPlayer.payments(firstMoney);
//
//				firstProperties.forEach(prop -> {
//					this.unbuild(prop, firstPlayer);
//					secondPlayer.addProperty(firstPlayer.removeProperty(prop));
//				});
//				secondPlayer.gainMoney(firstMoney);
//				secondPlayer.payments(secondMoney);
			}
		} catch (NumberFormatException ex) {
			AlertFactory.createErrorAlert("Speak (type) as you eat",
					"Someone did not enter a number!\nPlease, use only numbers...");
		}
		this.controller.updateView();
	}

	private PlayerInfo getPlayerByName(String playerName) {
		return this.controller.getPlayers().stream().filter(player -> player.getName().equals(playerName)).findFirst()
				.get();
	}

	private Obtainable getPropertyByName(String propertyName) {
		return this.controller.getProperties().stream().filter(property -> property.getNameOf().equals(propertyName))
				.findFirst().get();
	}

	private boolean canPay(PlayerInfo player, int moneyAmount) {
		return player.canPay(moneyAmount);
	}

//	private void unbuild(final Obtainable property, final Player player) {
//		if (property instanceof AdapterBuildable) {
//			player.getPopertiesByColor().get(property.getColorOf()).stream().map(prop -> (AdapterBuildable) prop)
//					.forEach(p -> {
//						while (p.getBuildingNumber() != 0) {
//							this.decHouse(p, player);
//						}
//					});
//		}
//	}

//	private void decHouse(final AdapterBuildable property, final Player player) {
//		property.decBuildings();
//		player.gainMoney(property.getPriceForBuilding() / 2);
//	}
}
