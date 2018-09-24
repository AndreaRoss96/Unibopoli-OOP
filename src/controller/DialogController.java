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
import utilities.enumerations.ClassicType;
import view.View;
import view.gameDialog.AuctionDialog;
import view.gameDialog.CardDialog;
import view.gameDialog.MortgageDialog;
import view.gameDialog.TradeDialog;

public class DialogController implements DialogObserver {

	private static final DialogController SINGLETON = new DialogController();
	private static final double UNMORTGAGE_FEE = 10 / 100.0;

	private final ControllerImpl controller;
	private Model model;
	private SoundController sound;
	private View view;

	public static DialogController getDialogController() {
		return SINGLETON;
	}

	private DialogController() {
		this.controller = ControllerImpl.getController();
	}

	@Override
	public void setDialogController(Model model, SoundController sound, View view) {
		if (this.model == null) {
			this.model = model;
		}
		if (this.sound == null) {
			this.sound = sound;
		}
		if (this.view == null) {
			this.view = view;
		}
	}

	@Override
	public void executeAuction() {
		final List<String> betsList = AuctionDialog.getAuctionDialog().getBetsList();
		final Obtainable property = AuctionDialog.getAuctionDialog().getAuctionedProperty();
		final List<PlayerInfo> playerList = this.controller.getPlayers();
		try {
			if (betsList.stream().distinct().count() != betsList.size()) {
				this.view.createInformationAlert("Get a plan together",
						"Two or more player have entered the same value");
			} else {
				int moneyAmount = betsList.stream().map(Integer::parseInt).max(Comparator.comparing(Integer::valueOf))
						.get();
				betsList.forEach(bet -> {
					if (Integer.parseInt(bet) == moneyAmount) {
						if (canPay(playerList.get(betsList.indexOf(bet)), moneyAmount)) {
							this.model.playerAddProperty(playerList.get(betsList.indexOf(bet)), property);
							this.controller.playerPayments(playerList.get(betsList.indexOf(bet)), moneyAmount);
						} else {
							this.view.createInformationAlert("Ehi ", playerList.get(betsList.indexOf(bet)).getName()
									+ ", you don't have all those money.");
						}
					}
				});
			}
		} catch (NumberFormatException ex) {
			this.view.createErrorAlert("Speak (type) as you eat",
					"Someone did not enter a number!\nPlease, use only numbers...");
		}
		this.controller.updateView(false);
	}

	@Override
	public void incHouseClick() {
		final BuildableImpl property = (BuildableImpl) CardDialog.getCardDialog().getProperty();
		final List<Boolean> areOtherMortgaged = this.controller.getCurrentPlayer().getPopertiesByColor()
				.get(property.getColorOf()).stream().map(Obtainable::hasMortgage).collect(Collectors.toList());
		if (areOtherMortgaged.stream().filter(e -> e.booleanValue()).count() == 0) {
			if (canPay(this.controller.getCurrentPlayer(), property.getPriceForBuilding())) {
				this.sound.playSound(ClassicType.Music.GeneralMusicMap.getPlasticDropOnPlaying());
				property.incBuildings();
				this.controller.playerPayments(this.controller.getCurrentPlayer(), property.getPriceForBuilding());
			} else {
				this.view.createErrorAlert("Nope", "You don't have enought money\nyou have only: "
						+ this.controller.getCurrentPlayer().getMoney() + "$");
			}
		} else {
			this.view.createErrorAlert("Nope", "One or more property of this color are mortgaged!");
		}
		this.controller.updateView(false);
		this.view.updateCardDialog();
	}

	@Override
	public void decHouseClick() {
		final AdapterBuildable property = (AdapterBuildable) CardDialog.getCardDialog().getProperty();
		if (property.getBuildingNumber() != 0) {
			this.sound.playSound(ClassicType.Music.GeneralMusicMap.getPlasticDropOnPlaying());
			property.decBuildings();
			this.model.playerGainMoney(this.controller.getCurrentPlayer(), property.getPriceForBuilding() / 2);
		}
		this.controller.updateView(false);
		this.view.updateCardDialog();
	}

	@Override
	public void setMortgage(List<Optional<String>> list) {
		list.stream().map(property -> property.get()).map(propertyName -> getPropertyByName(propertyName))
				.forEach(property -> {
					if (!property.hasMortgage()) {
						this.model.playerGainMoney(this.controller.getCurrentPlayer(), property.getMortgage());
						this.model.unbuild(property, (Player) this.controller.getCurrentPlayer());
					} else {
						this.controller.playerPayments(this.controller.getCurrentPlayer(),
								(int) (property.getMortgage() + Math.ceil(property.getMortgage() * UNMORTGAGE_FEE)));
					}
					property.changeMortgageStatus();
				});
		this.controller.updateView(false);
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
					this.view.createInformationAlert("I'm sorry", "You don't have enough money...");
				}
			}
		} else {
			this.view.createInformationAlert("Ehi", "Do it in your turn!");
		}
	}

	@Override
	public void buyPropertyClick() {
		final Obtainable property = CardDialog.getCardDialog().getProperty();
		try {
			this.model.buyProperty(this.controller.getCurrentPlayer(), property);
			this.sound.playSound(ClassicType.Music.GeneralMusicMap.getCashRegister());
		} catch (NotEnoughMoneyException e) {
			this.controller.startMortgage(property.getPrice(), this.controller.getCurrentPlayer());
		}
		this.controller.updateView(false);
	}

	@Override
	public void accumulatedMoney() {
		final List<Obtainable> obtainableList = MortgageDialog.getMortgageDialog().getSelected().stream()
				.map(propertyName -> getPropertyByName(propertyName)).collect(Collectors.toList());
		MortgageDialog.getMortgageDialog()
				.updateObtainedMoney(obtainableList.stream().mapToInt(property -> property.getMortgage()).sum()
						+ obtainableList.stream().filter(property -> property instanceof AdapterBuildable)
								.map(property -> (AdapterBuildable) property)
								.mapToInt(value -> value.getPriceForBuilding() / 2 * value.getBuildingNumber()).sum());
	}

	@Override
	public void dialogTradeClick() {
		final TradeDialog tradeDialog = TradeDialog.getTradeDialog();
		try {
			final int firstMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getFirst());
			final int secondMoney = Integer.parseInt(tradeDialog.getPlayersMoneyToTrade().getSecond());

			final PlayerInfo firstPlayer = this.controller.getCurrentPlayer();
			final PlayerInfo secondPlayer = getPlayerByName(tradeDialog.getSecondPlayer());
			final List<Obtainable> firstProperties = tradeDialog.getSelectedProperties().getFirst().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());
			final List<Obtainable> secondProperties = tradeDialog.getSelectedProperties().getSecond().stream()
					.map(p -> p.get()).map(p -> this.getPropertyByName(p)).collect(Collectors.toList());

			if (!this.canPay(firstPlayer, firstMoney) && this.canPay(secondPlayer, secondMoney)) {
				this.view.createErrorAlert("He's trying to cheat you!",
						firstPlayer.getName() + " doesn't have enought money!");
			} else if (!this.canPay(secondPlayer, secondMoney) && this.canPay(firstPlayer, firstMoney)) {
				this.view.createErrorAlert("He's trying to cheat you!",
						secondPlayer.getName() + " doesn't have enought money!");
			} else if (!this.canPay(firstPlayer, firstMoney) && !this.canPay(secondPlayer, secondMoney)) {
				this.view.createErrorAlert("Nope ",
						secondPlayer.getName() + " and " + firstPlayer.getName() + " check your wallets.");
			} else {
				this.model.executeTrade((Player) secondPlayer, firstMoney, secondMoney, firstProperties,
						secondProperties);
			}
		} catch (NumberFormatException ex) {
			this.view.createErrorAlert("Speak (type) as you eat",
					"Someone did not enter a number!\nPlease, use only numbers...");
		}
		this.controller.updateView(false);
	}

	private PlayerInfo getPlayerByName(String playerName) {
		return this.controller.getPlayers().stream().filter(player -> player.getName().equals(playerName)).findFirst()
				.get();
	}

	private Obtainable getPropertyByName(String propertyName) {
		return this.model.getProperties().stream().filter(property -> property.getNameOf().equals(propertyName))
				.findFirst().get();
	}

	private boolean canPay(PlayerInfo player, int moneyAmount) {
		return player.canPay(moneyAmount);
	}
}
