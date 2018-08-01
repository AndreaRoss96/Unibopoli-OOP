package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.tiles.*;
import utilities.enumerations.Color;

public class RealPlayer implements Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360356929546552980L;


	private Tile position;
	private final String name;
	// private final Pawn sprite;
	private Map<Color, List<ObtainableImpl>> playersProperties;
	private List<ObtainableImpl> mortgagedProperties;
	private Integer money;
	private Integer housesNumber;
	private Integer hotelsNumber;
	private Prison status = Prison.NOT_PRISON;


	public RealPlayer(final String name, final Tile position,
			final Map<Color, List<ObtainableImpl>> playersProperties, final Integer totMoney,
			final List<ObtainableImpl> mortgagedProperties /* ... */) {
		this.name = name;
		this.money = totMoney;
		this.position = position;
		this.housesNumber = 0;
		this.hotelsNumber = 0;
		this.playersProperties = new HashMap<>();
		this.playersProperties.putAll(playersProperties);
		this.mortgagedProperties = new ArrayList<>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getMoney() {
		return this.money;
	}

	@Override
	public Integer getHouseNumber() {
		// con una stream in futuro calcolerò la quantità di case presente in ogni
		// proprietà del giocatore
		return this.housesNumber;
	}

	@Override
	public Integer getHotelNumber() {
		return this.hotelsNumber;
	}

	@Override
	public void setPosition(Tile newPosition) {
		this.position = newPosition;
	}

	@Override
	public Tile getPosition() {
		return this.position;
	}

	@Override
	public Prison isInJail() {
		return this.status;
	}

	@Override
	public void payments(Integer moneyAmount) {//per evitare di fare dei thread sposterei il richiamp del toMortgage nel metodo can pay, in modo che, una volta guadagnati i soldi possa comunquecontinuare con il pagamento
		if (!this.canPay(moneyAmount)) {
			if (moneyAmount > totalAssets()) { // in questo caso il giocatore non è in alcun modo in grado di pagare
												// l'affitto
				// BANKAROTTA! - da gestire

			}
			toMortgage(moneyAmount - this.money);
			// in questo caso il giocatore non è in grado di pagare con i liquidi
			// ed è quindi costretto ad ipotecare qualche proprietà
		} else {
			this.money -= moneyAmount;
		}
	}

	public Integer totalAssets() {
		// mi servirebbe anche il valore di case/alberghi -- ora è nel controller
		return getProperties().stream().mapToInt(ObtainableImpl::getMortgage).sum() + this.money;
	}

	private void bankroupt(Integer moneyAmount) {
		// rimuovere il giocatore dalla lista tutti i suoi possedimenti all'asta
	}

	@Override
	public void gainMoney(Integer moneyAmount) {
		this.money += moneyAmount;
	}

	@Override
	public void buyProperty(ObtainableImpl property) {
		if (canPay(property.getMortgage())) {
			payments(property.getMortgage());
			this.addProperty(property);
		} else {
			// dovrebbe aprirsi un'asta -- Controller lunchDialog()
			throw new NotEnoughMoneyException(property.getMortgage());
		}

	}

	private void addProperty(ObtainableImpl property) {
		List<ObtainableImpl> tmpList = new ArrayList<>();
		tmpList.add(property);
		this.playersProperties.merge(property.getColorOf(), tmpList,
				(list1, list2) -> Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList()));
		property.setOwner(Optional.of(this.getName()));
	}

	@Override
	public void mortgageProperties(List<ObtainableImpl> mortgaged) {
		gainMoney(mortgaged.stream().mapToInt(Obtainable::getMortgage).sum()); //DialogController.getController().getTotalSpend(mortgaged)
		this.mortgagedProperties.addAll(mortgaged);
	}
	//è il caso di fare un "List Calculator" o troppo eccessivo?

	public boolean canPay(Integer moneyAmount) {
		return this.money >= moneyAmount;
	}

	@Override
	public void toMortgage(Integer minimumAmount) {
		// TODO Auto-generated method stub
		// "alberizzatore" nelle utilities e metodo nella view

	}

	@Override
	public void goToJail() {
		// move (...);
		this.status = Prison.PRISON;

	}

	@Override
	public void exitFromJail() {
		this.status = Prison.NOT_PRISON;
	}

	@Override
	public List<ObtainableImpl> getProperties() {
		return this.playersProperties.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public Map<Color, List<ObtainableImpl>> getPopertiesByColor() {
		return this.playersProperties;
	}

	@Override
	public List<ObtainableImpl> getMortgagedProperties() {
		return this.mortgagedProperties;
	}
}
