package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import model.player.Player;
import model.player.RealPlayer;
import model.tiles.BuildableImpl;
import model.tiles.NotBuildableImpl;
import model.tiles.Obtainable;
import model.tiles.Rents;
import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

public class PlayerTest {
	
	private Obtainable buildProperty (int value, Color color, boolean buildable) {
		final List<Integer> rentList = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			rentList.add(value*i);
		}
		final Rents rent = new Rents(rentList);
		rent.setPriceForBuilding(value*2);
		return buildable ? new BuildableImpl(value, value * 10, value * 5, rent, color, TileTypes.BUILDABLE) : new NotBuildableImpl(value, value * 10, value * 5, TileTypes.STATION, color);
	}

	@Test
	public void moneyTest() {
		Player player1 = new RealPlayer("Player1", 2500, "res/mode/CLASSIC/avatars/Boot.png");
		player1.gainMoney(500);
		assertEquals(3000, player1.getMoney());

		BuildableImpl bProperty1 = (BuildableImpl) buildProperty(25, Color.BLUE, true);
		NotBuildableImpl nbProperty1 = (NotBuildableImpl) buildProperty(14, Color.STATION, false);
		
		player1.addProperty(bProperty1);
		player1.payments(bProperty1.getPrice());
		
		assertEquals(2750, player1.getMoney());
		assertEquals(2900, player1.totalAssets());
	
		bProperty1.incBuildings();
		player1.payments(bProperty1.getPriceForBuilding());
		
		player1.setPosition(13);
		assertEquals(13, player1.getPosition());
		
		/* player 2 join the party */
		Player player2 = new RealPlayer("Player2", 2500, "res/mode/CLASSIC/avatars/Car.png");
		
		player2.addProperty((Obtainable) nbProperty1);
		player2.payments(nbProperty1.getPrice());
		
		player1.addProperty(player2.removeProperty(nbProperty1));
		assertEquals(0, player2.getProperties().size());
		assertEquals(2, player1.getPopertiesByColor().keySet().size());
		
		player2.payments(bProperty1.getRent(bProperty1.getBuildingNumber()));
		assertEquals(2500 - 140 - 50, player2.getMoney());
		assertEquals(2500 - 140 - 50, player2.totalAssets());
		
		if(player1.canPay(3000)) {
			player1.payments(3000);
		}
		assertEquals(2700, player1.getMoney());
		
	}

}
