package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.base.Optional;

import controller.ControllerImpl;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.AdapterBuildable;
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;
import utilities.enumerations.TileTypes;

public class ModelImpl implements Model {

	private static final int JAIL = 30;
	private static final int JAIL_FEE = 125;

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
		} else {
			this.getProperties().stream().forEach(tile -> {
				if (tile.getOwner().get().equals(player.getName())) {
					tile.setOwner(Optional.absent());
					ControllerImpl.getController().startAuciton(tile);
				}
			});
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
			this.supplierConsequence();
		}
	}

	private void supplierConsequence() {
		final Tile tile = this.board.getTileOf(this.turnPlayer.getCurrentPlayer().getPosition());
		
		if(!((Obtainable) tile).getOwner().isPresent()) {
			ControllerImpl.getController().showContract((Obtainable) tile);
		}else if(tile.getTileType() == TileTypes.STATION ) {
			((Obtainable) tile).setConsequence(new ConsequencesImpl(Consequences.PLAYER_TRADE, "Prova", new ArrayList<>(Arrays.asList(((Obtainable) tile).getOwner().get()))));
		}else if(tile.getTileType() == TileTypes.BUILDABLE || tile.getTileType() == TileTypes.WATER_AGENCY || tile.getTileType() == TileTypes.LIGHT_AGENCY){	
			if(this.turnPlayer.getCurrentPlayer().getPopertiesByColor().get(((Obtainable) tile).getColorOf()).size() == ((Obtainable) tile).getColorOf().getNumTiles()) {
				((Obtainable) tile).setConsequence(new ConsequencesImpl(Consequences.PLAYER_TRADE, "Prova", new ArrayList<>(Arrays.asList(String.valueOf(2)))));
			}
		}
		
		tile.doConsequence();
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
			this.getCurrentPlayer().payments(JAIL_FEE);
		}
		this.getCurrentPlayer().exitFromJail();
	}

	/*******************************************************************************/
	@Override
	public void playerPayment(final PlayerInfo player, final int moneyAmount) {
		if (!player.canPay(moneyAmount)) {
			if (moneyAmount > player.totalAssets()) {
				this.removePlayer(player);
			}
			ControllerImpl.getController().startMortgage(moneyAmount, player);
		} else {
			((Player) player).payments(moneyAmount);
		}
	}

	@Override
	public void buyProperty(PlayerInfo player, Obtainable property) {
		this.playerPayment(player, property.getPrice());
		this.playerAddProperty(player, property);
	}

	@Override
	public void executeTrade(final Player secondPlayer, final int firstMoney, final int secondMoney,
			final List<Obtainable> firstProperties, final List<Obtainable> secondProperties) {
		final Player firstPlayer = this.getCurrentPlayer();
		secondProperties.forEach(prop -> {
			this.unbuild(prop, secondPlayer);
			firstPlayer.addProperty(secondPlayer.removeProperty(prop));
		});
		firstPlayer.gainMoney(secondMoney);
		firstPlayer.payments(firstMoney);

		firstProperties.forEach(prop -> {
			this.unbuild(prop, firstPlayer);
			secondPlayer.addProperty(firstPlayer.removeProperty(prop));
		});
		secondPlayer.gainMoney(firstMoney);
		secondPlayer.payments(secondMoney);
	}

	@Override
	public void unbuild(final Obtainable property, final Player player) {
		if (property.getTileType() == TileTypes.BUILDABLE) {
			player.getPopertiesByColor().get(property.getColorOf()).stream().map(prop -> (BuildableImpl) prop)
					.forEach(p -> {
						while (p.getBuildingNumber() != 0) {
							p.decBuildings();
//							player.gainMoney(p.getPriceForBuilding() / 2);
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
	
	/**
	 * OSSERVAZIONI: - LA gestione del turno dei giocatori dove va ?? Si potrebbe
	 * sapere il curruntPlayer tramite quella classe. Ciò non significa eliminare
	 * l'attributo, bensì inizializzrlo continuamente tramite quella classe. -
	 * Aggiungere un metodo che passa il turno al giocatore successivo. FATTO, ma
	 * verificare -
	 */
	/**
	 * se può essere utile alle conseguenze del movimento si può mettere
	 * "buyProperty" nel model, in modo da rendere anche più efficente la
	 * generazione di aste
	 */
}
