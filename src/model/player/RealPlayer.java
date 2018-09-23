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
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

public class RealPlayer implements Player {

	private static final long serialVersionUID = 7360356929546552980L;

	private final String name;
	private Integer position;
	private Map<Color, List<Obtainable>> playersProperties;
	private Integer money;
	private String iconPath;
	private Prison status;

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
		this.status = Prison.NOT_PRISON;
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
		return this.getProperties().stream().filter(prop -> prop.getTileType() == TileTypes.BUILDABLE)
					.map(prop -> (AdapterBuildable) prop).filter(prop -> prop.getBuildingNumber() > 0)
					.filter(prop -> prop.getBuildingNumber() <= 4).mapToInt(AdapterBuildable::getBuildingNumber).sum();
	}

	@Override
	public int getHotelNumber() {
		return (int) this.getProperties().stream().filter(prop -> prop.getTileType() == TileTypes.BUILDABLE)
				.map(prop -> (AdapterBuildable) prop).filter(prop -> prop.getBuildingNumber() == 5).count();
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
	public void payments(final int moneyAmount) {
			this.money -= moneyAmount;
	}

	public int totalAssets() {
		return (getProperties().stream().mapToInt(Obtainable::getMortgage).sum() + getProperties().stream().filter(value -> value.getTileType().equals(TileTypes.BUILDABLE))
				.map(value -> (BuildableImpl) value).mapToInt(value -> value.getPriceForBuilding() / 2).sum() + this.money);
	}

	@Override
	public void gainMoney(int moneyAmount) {
		this.money += moneyAmount;
	}

	@Override
	public void addProperty(final Obtainable property) {
		this.playersProperties.merge(property.getColorOf(), new ArrayList<Obtainable>(Arrays.asList(property)),
				(list1, list2) -> Stream.of(list1, list2).flatMap(Collection::stream).collect(Collectors.toList()));
		property.setOwner(Optional.of(this.getName()));
	}
	public boolean canPay(final int moneyAmount) {
		return this.money >= moneyAmount;
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
