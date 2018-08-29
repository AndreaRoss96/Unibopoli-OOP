package model.player;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.exceptions.NotEnoughMoneyException;
import model.tiles.Buildable;
import model.tiles.Obtainable;
import controller.DialogController;

import utilities.enumerations.Color;
import view.Icon;

public class RealPlayer implements Player {

	private static final long serialVersionUID = 7360356929546552980L;

	private static final double UNMORTGAGE_FEE = 10 / 100;

	private int position;
	private final String name;
	private Map<Color, List<Obtainable>> playersProperties;
	private int money;
	private int housesNumber; // togli
	private int hotelsNumber;
	private String iconPath;
	private Prison status = Prison.NOT_PRISON;

	/**
	 * This constructor is used by GameInitializer class.
	 * 
	 * @param name
	 *            player's name
	 * @param money
	 *            initial player's money
	 */
	public RealPlayer(final String name, int money, final String iconPath) {
		this.name = name;
		this.position = 0;
		this.money = money;
		this.iconPath = iconPath;
		this.playersProperties = new HashMap<>();
	}

	/**
	 * This constructor is used by a load game.
	 * 
	 * @param name
	 * @param money
	 */
	public RealPlayer(final String name, final int position, final Map<Color, List<Obtainable>> playersProperties,
			final int totMoney, final List<Obtainable> mortgagedProperties /* ... */) {
		this.name = name;
		this.money = totMoney;
		this.position = position;
		this.housesNumber = 0;
		this.hotelsNumber = 0;
		this.playersProperties = new HashMap<>();
		this.playersProperties.putAll(playersProperties);

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
		// getProperties().stream().filter(property -> property instanceof
		// Buildable).collect(Collectors.toList());
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
	public boolean isInJail() {
		return this.status == Prison.PRISON;
	}
	
	public void decMoney(int moneyAmount) {
		this.money -= moneyAmount;
	}

	@Override
	public void payments(Integer moneyAmount) {// per evitare di fare dei thread sposterei il richiamp del toMortgage
												// nel metodo can pay, in modo che, una volta guadagnati i soldi possa
												// comunquecontinuare con il pagamento
		if (!this.canPay(moneyAmount)) {
			if (moneyAmount > totalAssets()) {//in questo caso il giocatore va in bancarotta e deve essere eliminato
				throw new NotEnoughMoneyException(moneyAmount);
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
		return getProperties().stream().mapToInt(Obtainable::getMortgage).sum() + getProperties().stream().filter(value -> value instanceof Buildable)
				.map(value -> (Buildable) value).mapToInt(value -> value.getPriceForBuilding() / 2).sum() + this.money;
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
		if (canPay(property.getPrice())) {
			payments(property.getPrice());
			this.addProperty(property);
		} else {
			// dovrebbe aprirsi un'asta -- Controller lunchDialog()
			throw new NotEnoughMoneyException(property.getMortgage());
		}

	}

	@Override
	public void addProperty(Obtainable property) {
		this.playersProperties.merge(property.getColorOf(), Arrays.asList(property),
				(list1, list2) -> Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList()));
		property.setOwner(Optional.of(this.getName()));
	}

	@Override
	public void mortgageProperties(List<Obtainable> mortgaged) {
		DialogController.getDialogController()
				.accumulatedMoney(mortgaged.stream().map(Obtainable::getNameOf).collect(Collectors.toList()));
		// gainMoney(mortgaged.stream().mapToInt(Obtainable::getMortgage).sum());
		// DialogController.getController().getTotalSpend(mortgaged)
	}

	public void unmortgageProperty(Obtainable property) {
		this.payments((int) (property.getMortgage() * UNMORTGAGE_FEE));
		// property.setMortgage();
	}

	public boolean canPay(Integer moneyAmount) {
		return this.money >= moneyAmount;
	}

	@Override
	public void toMortgage(Integer minimumAmount) {

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
		return null;
	}
	
	@Override
	public Obtainable removeProperty(Obtainable property) {
		this.playersProperties.remove(property.getColorOf(), property);
		return property;
	}

	@Override
	public String getIconPath() {
		return this.iconPath;
	}
}
