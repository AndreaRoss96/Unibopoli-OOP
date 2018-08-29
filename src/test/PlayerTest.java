package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.ControllerImpl;
import controller.DialogController;
import javafx.scene.image.Image;
import model.Icon;
import model.ModelImpl;
import model.player.Player;
import model.player.RealPlayer;
import model.tiles.Buildable;
import model.tiles.BuildableImpl;
import model.tiles.NotBuildable;
import model.tiles.NotBuildableImpl;
import model.tiles.Obtainable;
import model.tiles.Rents;
import utilities.enumerations.Color;

public class PlayerTest {
	
	private Obtainable buildProperty (int value, Color color, boolean buildable) {
		List<Integer> rentList = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			rentList.add(value*i);
		}
		return buildable ? new BuildableImpl(1, value * 10, value * 5, new Rents(rentList), color, (int) (value * 0.5)) : new NotBuildableImpl(value, value * 10, value * 5, color);
	}

	@Test
	public void moneyTest() {
		Player player1 = new RealPlayer("Player1", 2500);
		player1.gainMoney(500);
		assertEquals(3000, player1.getMoney());

		Buildable bProperty1 = (Buildable) buildProperty(25, Color.BLUE, true);
		NotBuildable nbProperty1 = (NotBuildable) buildProperty(14, Color.STATIONS, false);
		
		player1.buyProperty(bProperty1);
		
		assertEquals(2750, player1.getMoney());
		assertEquals(2875, (int) player1.totalAssets()); //errore
		DialogController.getDialogController().incHouse(bProperty1);
		
		player1.setPosition(13);
		assertEquals(13, player1.getPosition());
		
		/* player 2 join the party */
		Player player2 = new RealPlayer("Player2", 2500);
		player2.buyProperty(nbProperty1);
		
		player1.addProperty(player2.removeProperty(nbProperty1));
		assertEquals(0, player2.getProperties().size());
		assertEquals(2, player1.getPopertiesByColor().keySet().size());
		
		if(player1.canPay(3000)) {
			player1.payments(3000);
		}
		assertEquals(2750, player1.getMoney());
		
		
//		player1.prope
//		player1.payments(4000);
		
	}

}
