package model;

public class RealPlayer implements Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360356929546552980L;

	private final String name;
	// private Tile position;
	// private final Icon sprite;
	// private List<Properties> propertiesList;
	// private List<Properties> mortgagedProperties;
	private Integer money;
	private boolean status;
	private Integer housesNumber;
	private Integer hotelsNumber;
	private Player.Status statusEnum = Player.Status.FREE;

	public RealPlayer(final String name, /* ... , */ final Integer totMoney) {
		this.name = name;
		this.money = totMoney;
		this.status = true;
		this.housesNumber = 0;
		this.hotelsNumber = 0;
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
		return this.housesNumber;
	}

	@Override
	public Integer getHotelNumber() {
		return this.housesNumber;
	}

	@Override
	public Integer getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInJail() {
		return this.statusEnum.getValue();
//		return this.status;
	}

	@Override
	public void payments(Integer moneyAmount) {
		if (moneyAmount > this.money) {
			bankroupt(moneyAmount - this.money);
		}
		this.money -= moneyAmount;
	}

	private void bankroupt(Integer quantity) {

	}

	@Override
	public void gainMoney(Integer moneyAmount) {
		this.money += moneyAmount;
	}

	@Override
	public void buyProperty(Integer propertyPrice, String propertyName) {
		// propertyList.add(properties)

	}

	@Override
	public void toMortgage(Integer minimumAmount) {
		// TODO Auto-generated method stub
		// non lo so fare ^_^

	}

	@Override
	public void goToJail() {
		//move (...);
		this.status = false;
		
	}

}
