package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.base.Optional;

import controller.ControllerImpl;
import model.exceptions.NotEnoughMoneyException;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.AdapterBuildable;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;
import utilities.enumerations.Consequences;
import utilities.enumerations.TileTypes;

public class ModelImpl implements Model {

	private static final int JAIL = 10;
	private static final int JAIL_FEE = 125;
	private static final String TEXT_CONSEQUENCE_OTHER_PLAYER = "SEI FINITO IN PROPRIETA\' ALTRUI.\nPAGATE QUANTO DOVETE.";
	
	private final Board board;
	private final Turn turnPlayer;

	public ModelImpl(final Board board, final Turn players) {
		this.board = board;
		this.turnPlayer = players;
	}

	@Override
	public List<PlayerInfo> getPlayers() {
		return this.turnPlayer.getPlayers();
	}

	@Override
	public void saveGame() {
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(this.board, this.turnPlayer));
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	@Override
	public Pair<Integer> exitDice() {
		final Pair<Integer> temp = Dice.getInstance().getDice();
		if (this.turnPlayer.hasDone()) {
			return new Pair<>(0, 0);
		}
		if (this.turnPlayer.isThrows()) {
			this.turnPlayer.thrown(temp.areSame());
			if (this.turnPlayer.isInJail()) {
				this.turnPlayer.turnInJail();
				if (temp.areSame() || this.turnPlayer.exitFromJail()) {
					this.exitFromJail(true);
				}
			}
			return temp;
		} else {
			this.goToJail();
			return temp;
		}
	}

	@Override
	public Set<Tile> getBoard() {
		return this.board.getTiles(tile -> true).stream().collect(Collectors.toSet());
	}

	@Override
	public Player getCurrentPlayer() {
		return this.turnPlayer.getCurrentPlayer();
	}

	@Override
	public Set<Obtainable> getProperties() {
		return this.board.getTiles(tile -> tile instanceof Obtainable).stream().map(tile -> (Obtainable) tile)
				.collect(Collectors.toSet());
	}

	@Override
	public void removePlayer(PlayerInfo player) {
		this.turnPlayer.remove(player);
		if (this.turnPlayer.getPlayers().size() == 1) {
			ControllerImpl.getController().endGame();
		}
	}

	@Override
	public void endTurn() {
		this.turnPlayer.nextPlayer();
	}

	@Override
	public void movement(int value) {
		if (!this.getCurrentPlayer().isInJail()) {
			this.setNewPosition((this.turnPlayer.getCurrentPlayer().getPosition() + value) % this.board.getTilesNumber());
		}
	}

	private List<String> getvalues(final Obtainable tile, final int mul){
		return Arrays.asList(tile.getOwner().get(), String.valueOf(mul));
	}

	private ConsequencesImpl factory(boolean condition, Obtainable tile, int value){
		return condition ? new ConsequencesImpl(Consequences.PLAYER_TRADE, TEXT_CONSEQUENCE_OTHER_PLAYER, getvalues(tile, value)) : new ConsequencesImpl(Consequences.PLAYER_TRADE, "Prova", getvalues(tile, 1));
	}
	
	@Override
	public Optional<ConsequencesImpl> supplierConsequence() {
		final Tile tile = this.board.getTileOf(this.turnPlayer.getCurrentPlayer().getPosition());
		ConsequencesImpl consequenceRet = ConsequencesImpl.emptyConsequence();
		
		if(tile instanceof Obtainable && !((Obtainable) tile).getOwner().isPresent()) {
			return Optional.absent();
		}else if(tile instanceof Obtainable) {
			final PlayerInfo player = this.turnPlayer.getPlayers().stream()
												     .filter(playerV -> playerV.getName().equals(((Obtainable) tile).getOwner().get()))
												     .findFirst().get();
										
			if(!((Obtainable) tile).getOwner().get().equals(this.getCurrentPlayer().getName())) {
				if(tile.getTileType() == TileTypes.STATION) {
					consequenceRet = new ConsequencesImpl(Consequences.PLAYER_TRADE, TEXT_CONSEQUENCE_OTHER_PLAYER, getvalues(((Obtainable) tile), player.getPopertiesByColor().get(((Obtainable) tile).getColorOf()).size()));
				}else if(tile.getTileType() == TileTypes.BUILDABLE){
					consequenceRet = this.factory(player.getPopertiesByColor().get(((Obtainable) tile).getColorOf()).size() == ((Obtainable) tile).getColorOf().getNumTiles(), ((Obtainable) tile), 2);
				}else {
					consequenceRet = this.factory(player.getPopertiesByColor().get(((Obtainable) tile).getColorOf()).size() == ((Obtainable) tile).getColorOf().getNumTiles(), ((Obtainable) tile), 4);
				}
			} 
		}else {
			switch (tile.getTileType()) {
			case GO_JAIL: 
				consequenceRet = new ConsequencesImpl(Consequences.MOVING, "ANDATE IN PRIGIONE", new ArrayList<>(Arrays.asList(String.valueOf(JAIL)))); break;
			case GO: 
				consequenceRet = new ConsequencesImpl(Consequences.RECEIVE, "PASSI DAL VIA\n\tRITIRA 200$!", new ArrayList<>(Arrays.asList(String.valueOf(200)))); break;
			case LUXURY_TAX: 
				consequenceRet = new ConsequencesImpl(Consequences.SIMPLE_PAYMENT, "TASSA DI LUSSO", new ArrayList<>(Arrays.asList(String.valueOf(200)))); break;
			case INCOME_TAX: 
				consequenceRet = new ConsequencesImpl(Consequences.SIMPLE_PAYMENT, "TASSA DI PROPRIETA\'", new ArrayList<>(Arrays.asList(String.valueOf(100)))); break;
			case PROBABILITY: 
				consequenceRet = CardEffectSupplier.get().getNextProbability(); break;
			case UNEXPECTED:
				consequenceRet = CardEffectSupplier.get().getNextUnexpected(); break;
			default: 
				consequenceRet = ConsequencesImpl.emptyConsequence();
			}			
		}
		
		return Optional.of(consequenceRet);
	}

	private void setNewPosition(int value) {
		this.getCurrentPlayer().setPosition(value);
	}

	@Override
	public void goToJail() {
		this.setNewPosition(JAIL);
		this.getCurrentPlayer().goToJail();
	}

	@Override
	public void exitFromJail(boolean withFee) {
		if (withFee) {
			if(this.getCurrentPlayer().getMoney() >= JAIL_FEE) {
				this.getCurrentPlayer().payments(JAIL_FEE);
			} else {
				this.getCurrentPlayer().payments(this.getCurrentPlayer().getMoney());
			}
		}
		this.getCurrentPlayer().exitFromJail();
	}

	
	@Override
	public boolean playerPayment(final PlayerInfo player, final int moneyAmount) {
		if (!player.canPay(moneyAmount)) {
			if (moneyAmount > player.totalAssets()) {
				throw new NotEnoughMoneyException(moneyAmount);
			}
			return false;
		}
		
		((Player) player).payments(moneyAmount);
		return true;
	}

	@Override
	public void buyProperty(PlayerInfo player, Obtainable property) {
		((Player) player).payments(property.getPrice());
		this.playerAddProperty(player, property);
	}

	@Override
	public void executeTrade(final Player secondPlayer, final int firstMoney, final int secondMoney,
			final List<Obtainable> firstProperties, final List<Obtainable> secondProperties) {
		final Player firstPlayer = this.getCurrentPlayer();
		secondProperties.forEach(prop -> {
			this.unbuild(prop, secondPlayer);
			this.playerAddProperty(firstPlayer, secondPlayer.removeProperty(prop));
		});
		this.playerGainMoney(firstPlayer, secondMoney);
		firstPlayer.payments(firstMoney);

		firstProperties.forEach(prop -> {
			this.unbuild(prop, firstPlayer);
			this.playerAddProperty(secondPlayer, firstPlayer.removeProperty(prop));
		});
		this.playerGainMoney(secondPlayer, firstMoney);
		secondPlayer.payments(secondMoney);
	}

	@Override
	public void unbuild(final Obtainable property, final Player player) {
		if (property.getTileType() == TileTypes.BUILDABLE) {
			player.getPopertiesByColor().get(property.getColorOf()).stream().map(prop -> (AdapterBuildable) prop)
					.forEach(p -> {
						while (p.getBuildingNumber() != 0) {
							p.decBuildings();
							this.playerGainMoney(player, p.getPriceForBuilding() / 2);
						}
					});
		}
	}

	@Override
	public void playerGainMoney(final PlayerInfo player, final int moneyAmount) {
		((Player) player).gainMoney(moneyAmount);
	}

	@Override
	public void playerAddProperty(final PlayerInfo player, final Obtainable property) {
		((Player) player).addProperty(property);
	}

	@Override
	public Tile getTileOf(int position) {
		return this.board.getTileOf(position);
	}
}
