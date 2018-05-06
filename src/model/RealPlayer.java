package model;

import java.util.ArrayList;
import java.util.List;
import utilities.enumerations.Status.Prison;

public class RealPlayer implements Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360356929546552980L;

	private final String name;
	// private Tile position;
	// private final Icon sprite;
	// Map<Color, List<Obtainable>> playersProperties
	private List<Obtainable> propertiesList;
	private List<Obtainable> mortgagedProperties;
	private Integer money;
	private Integer housesNumber;
	private Integer hotelsNumber;
	private Prison status = utilities.enumerations.Status.Prison.NOT_PRISON;

	public RealPlayer(final String name, /* ... , */ final Integer totMoney) {
		this.name = name;
		this.money = totMoney;
		this.housesNumber = 0;
		this.hotelsNumber = 0;
		this.propertiesList = new ArrayList<>();
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
		//con una stream in futuro calcolerò la quantità di case presente in ogni proprietà del giocatore
		return this.housesNumber;
	}

	@Override
	public Integer getHotelNumber() {
		return this.hotelsNumber;
	}

	@Override
	public Integer getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prison isInJail() {
		return this.status;
	}

	@Override
	public void payments(Integer moneyAmount) {
		if (moneyAmount > this.money) {
			if (moneyAmount > totalAssets()) { // in questo caso il giocatore non è in alcun modo in grado di pagare
												// l'affitto
				//BANKAROTTA!

			}
			toMortgage(moneyAmount - this.money);
			// in questo caso il giocatore non è in grado di pagare con i liquidi
			// ed è quindi costretto ad ipotecare qualche proprietà
		} else {
			this.money -= moneyAmount;
		}
	}

	public Integer totalAssets() {
		return propertiesList.stream().mapToInt(Obtainable::getMortgage).sum() + this.money;
	}

	private void bankroupt(Integer moneyAmount) {

	}

	@Override
	public void gainMoney(Integer moneyAmount) {
		this.money += moneyAmount;
	}

	@Override
	public void buyProperty(Obtainable property) {
		if (canPay(property.getMortgage())) {
			payments(property.getMortgage());
			this.propertiesList.add(property);
		} else {
			// dovrebbe aprirsi un'asta
			throw new NotEnoughMoneyException(property.getMortgage());
		}

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
		//List tmpList = new ArrayList<>();
		//(List) playersProperties.values()
		return this.propertiesList;
	}
	
	/*AGGIUINGI GET_PROPERTIES_BY_COLOR QUANDO POSSIBILE che mi ritorna la mappa***********************************/

	@Override
	public List<Obtainable> getMortgagedProperties() {
		return this.mortgagedProperties;
	}

}
