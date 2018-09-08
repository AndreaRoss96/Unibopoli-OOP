package model.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Optional;

import model.tiles.AdapterBuildable;
import model.tiles.Obtainable;

import utilities.enumerations.Color;

public class RealPlayer implements Player {

	private static final long serialVersionUID = 7360356929546552980L;

	private final String name;
	private Integer position;
	private Map<Color, List<Obtainable>> playersProperties;
	private Integer money;
	private Integer housesNumber; // togli
	private Integer hotelsNumber;
	private String iconPath;
	private Prison status = Prison.NOT_PRISON;

	/**
	 * This constructor is used by GameInitializer class.
	 * 
	 * @param name
	 *            player's name
	 * @param money
	 *            initial player's money
	 * @param iconPath
	 * 			  the path where is located the icon's image of the player
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
	public void setPosition(final int newPosition) {
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

	@Override
	public void payments(final Integer moneyAmount) {// per evitare di fare dei thread sposterei il richiamp del toMortgage
												// nel metodo can pay, in modo che, una volta guadagnati i soldi possa
												// comunquecontinuare con il pagamento
//		if (!this.canPay(moneyAmount)) {
//			if (moneyAmount > totalAssets()) {//in questo caso il giocatore va in bancarotta e deve essere eliminato
//				throw new NotEnoughMoneyException(moneyAmount);
//			}
//			toMortgage(moneyAmount - this.money);
//			// in questo caso il giocatore non è in grado di pagare con i liquidi
//			// ed è quindi costretto ad ipotecare qualche proprietà
//		} else {
			this.money -= moneyAmount;
//		}
	}

	public Integer totalAssets() {
		// mi servirebbe anche il valore di case/alberghi
		return getProperties().stream().mapToInt(Obtainable::getMortgage).sum() + getProperties().stream().filter(value -> value instanceof AdapterBuildable)
				.map(value -> (AdapterBuildable) value).mapToInt(value -> value.getPriceForBuilding() / 2).sum() + this.money;
	}

	@Override
	public void gainMoney(Integer moneyAmount) {
		this.money += moneyAmount;
	}

//	@Override
//	public void buyProperty(final Obtainable property) {
//		if (canPay(property.getPrice())) {
//			payments(property.getPrice());
//			this.addProperty(property);
//		} else {
//			// dovrebbe aprirsi un'asta -- Controller lunchDialog()
//			throw new NotEnoughMoneyException(property.getMortgage());
//		}
//	}

	@Override
	public void addProperty(final Obtainable property) {
		this.playersProperties.merge(property.getColorOf(), new ArrayList<Obtainable>(Arrays.asList(property)),
				(list1, list2) -> Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList()));
		property.setOwner(Optional.of(this.getName()));
	}

	@Override
//	public void mortgageProperties(List<Obtainable> mortgaged) {
//		DialogController.getDialogController()
//				.accumulatedMoney(mortgaged.stream().map(Obtainable::getNameOf).collect(Collectors.toList()));
//	}

	public boolean canPay(final Integer moneyAmount) {
		return this.money >= moneyAmount;
	}

	@Override
	public void toMortgage(final Integer minimumAmount) {

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
	public Obtainable removeProperty(final Obtainable property) {
		property.setOwner(Optional.absent());
		this.getPopertiesByColor().get(property.getColorOf()).remove(property);
		return property;
	}

	@Override
	public String getIconPath() {
		return this.iconPath;
	}
}
