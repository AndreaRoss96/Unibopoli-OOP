package model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.exceptions.NotEnoughMoneyException;
import model.tiles.*;
import utilities.enumerations.Color;

public class RealPlayer implements Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360356929546552980L;
	
	private int position;
	private final String name;
	// private final Pawn sprite;
	private Map<Color, List<Obtainable>> playersProperties;
	private List<Obtainable> mortgagedProperties;
	private int money;
	private int housesNumber;//togli
	private int hotelsNumber;
	private Prison status = Prison.NOT_PRISON;

	/**
	 * This constructor is used by GameInitializer class
	 * 
	 * @param name
	 * @param money
	 */
	public RealPlayer(final String name, /*avatar*/ int money) {
		this.name = name;
		this.position = 0; //chiedi a matti
		this.money = money;
		this.playersProperties = new HashMap<>();
		this.mortgagedProperties = new ArrayList<>();
	}
	
	public RealPlayer(final String name, final int position,
			final Map<Color, List<Obtainable>> playersProperties, final int totMoney,
			final List<Obtainable> mortgagedProperties /* ... */) {
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
	public int getMoney() {
		return this.money;
	}

	@Override
	public int getHouseNumber() {
//		getProperties().stream().filter(property -> property instanceof Buildable).collect(Collectors.toList());
		return 0;
	}

	@Override
	public int getHotelNumber() {
		return this.hotelsNumber;
	}

	@Override
	public void setPosition(int newPosition) {
		this.position = newPosition;
	}

	@Override
	public int getPosition() {
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
				// BANKAROTTA! - da gestire - model.getModel().removePlayer(This);

			}
			toMortgage(moneyAmount - this.money);
			// in questo caso il giocatore non è in grado di pagare con i liquidi
			// ed è quindi costretto ad ipotecare qualche proprietà
		} else {
			this.money -= moneyAmount;
		}
	}

	public Integer totalAssets() {
		// mi servirebbe anche il valore di case/alberghi
		return getProperties().stream().mapToInt(Obtainable::getMortgage).sum() + getProperties().stream().map(value -> (Buildable) value).mapToInt(value -> value.getPriceForBuilding()/2).sum() + this.money;
	}

	private void bankroupt(Integer moneyAmount) {
		// rimuovere il giocatore dalla lista tutti i suoi possedimenti all'asta
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
		} else {
			// dovrebbe aprirsi un'asta -- Controller lunchDialog()
			throw new NotEnoughMoneyException(property.getMortgage());
		}

	}

	@Override
	public void addProperty(Obtainable property) {
		List<Obtainable> tmpList = new ArrayList<>();
		tmpList.add(property);
		this.playersProperties.merge(property.getColorOf(), tmpList,
				(list1, list2) -> Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList()));
		property.setOwner(Optional.of(this.getName()));
	}

	@Override
	public void mortgageProperties(List<Obtainable> mortgaged) {
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
		this.status = Prison.PRISON;
	}

	@Override
	public void exitFromJail() {
		this.status = Prison.NOT_PRISON;
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

	@Override
	public void addMoney(int moneyAmount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeMoney(int moneyAmount) {
		// TODO Auto-generated method stub
		
	}
}
