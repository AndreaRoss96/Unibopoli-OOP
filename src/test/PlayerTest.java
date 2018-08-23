package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.ControllerImpl;
import controller.DialogController;
import model.ModelImpl;
import model.player.Player;
import model.player.RealPlayer;
import model.tiles.Buildable;
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import model.tiles.Rents;
import utilities.enumerations.Color;

public class PlayerTest {
	
	private static Player player1;

	@Test
	public void moneyTest() {
		Player player1 = new RealPlayer("Player1", 2500);
		player1.gainMoney(500);
		assertEquals(3000, player1.getMoney());
		
		List<Integer> rentList = new ArrayList<>();
		rentList.add(25);
		rentList.add(50);
		rentList.add(120);
		rentList.add(250);
		rentList.add(500);
		Buildable property = new BuildableImpl(10, 200, 100, new Rents(rentList), Color.BLUE, 150);
		
		player1.buyProperty(property);
		
		assertEquals(2800, player1.getMoney());
		assertEquals(2900, (int) player1.totalAssets());
		DialogController.getDialogController().incHouse(property);
		
		player1.payments(4000);
//		assertEquals(0, model.getModel().getPlayer().size());
		
	}

}
