package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utilities.enumerations.Color;

import utilities.enumerations.Status.Prison;

public class RealPlayer implements Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360356929546552980L;

	private final String name;
	private TileInterface position;
	// private final Icon sprite;
	Map<Color, List<Obtainable>> playersProperties;
	private List<Obtainable> mortgagedProperties;
	private Integer money;
	private Integer housesNumber;
	private Integer hotelsNumber;
	private Prison status = utilities.enumerations.Status.Prison.NOT_PRISON;

	public RealPlayer(final String name, final TileInterface position,
			final Map<Color, List<Obtainable>> playersProperties, final Integer totMoney,
			final List<Obtainable> mortgagedProperties /*...*/) {
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
	public void setPosition(TileInterface newPosition) {
		this.position = newPosition;
	}

	@Override
	public TileInterface getPosition() {
		return this.position;
	}

	@Override
	public Prison isInJail() {
		return this.status;
	}

	@Override
	public void payments(Integer moneyAmount) {
		if (!this.canPay(moneyAmount)) {
			if (moneyAmount > totalAssets()) { // in questo caso il giocatore non è in alcun modo in grado di pagare
												// l'affitto
				// BANKAROTTA!

			}
			toMortgage(moneyAmount - this.money);
			// in questo caso il giocatore non è in grado di pagare con i liquidi
			// ed è quindi costretto ad ipotecare qualche proprietà
		} else {
			this.money -= moneyAmount;
		}
	}

	public Integer totalAssets() {
		//mi servirebbe anche il valore di case/alberghi
		return getProperties().stream().mapToInt(Obtainable::getMortgage).sum() + this.money;
	}

	private void bankroupt(Integer moneyAmount) {
//rimuovere il giocatore dalla lista tutti i suoi possedimenti all'asta
	}

	@Override
	public void gainMoney(Integer moneyAmount) {
		this.money += moneyAmount;
	}

	@Override
	public void buyProperty(Obtainable property) {
		if (canPay(property.getMortgage())) {
			payments(property.getMortgage());
			this.addProperty(property);
			property.setOwner(Optional.of(this.getName()));
		} else {
			// dovrebbe aprirsi un'asta
			throw new NotEnoughMoneyException(property.getMortgage());
		}

	}
	
	private void addProperty(Obtainable property) {
		List<Obtainable> tmpList = new ArrayList<>();
		tmpList.add(property);
		this.playersProperties.merge(property.getColorOf(), tmpList, (list1, list2) ->  
		  Stream.of(list1, list2)
		    .flatMap(Collection::stream)
		    .collect(Collectors.toList()));
	}
	
	@Override
	public void mortgageProperties(List<Obtainable> mortgaged) {
		int total = 0;
		for (Obtainable t : mortgaged) {
			total += t.getMortgage();
		}
		gainMoney(total);
		this.mortgagedProperties.addAll(mortgaged);
	}

	private boolean canPay(Integer moneyAmount) {
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
		this.status = utilities.enumerations.Status.Prison.PRISON;

	}

	@Override
	public void exitFromJail() {
		this.status = utilities.enumerations.Status.Prison.NOT_PRISON;
	}

	@Override
	public List<Obtainable> getProperties() {
		return this.playersProperties.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public Map<Color, List<Obtainable>> getPopertiesByColor() {
		return this.playersProperties;
	}

	@Override
	public List<Obtainable> getMortgagedProperties() {
		return this.mortgagedProperties;
	}

}
