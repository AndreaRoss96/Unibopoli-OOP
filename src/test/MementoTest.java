package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ControllerImpl;
import model.Board;
import model.CareMementoTaker;
import model.ModelMemento;
import model.Turn;
import model.TurnImpl;
import model.player.Player;
import model.player.RealPlayer;
import model.tiles.Obtainable;

public class MementoTest {

	public MementoTest() {
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(null, null));
		final ModelMemento mementoEmpty = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNull(mementoEmpty.getGameBoard());
		assertNull(mementoEmpty.getPlayers());
		
		final Board board = new Board("Classic");
		List<Player> playerList = new ArrayList<>();
		playerList.add(new RealPlayer("Uncle Pennybags", 2000));
		playerList.add(new RealPlayer("Mr. Pringles", 2700));
		playerList.add(new RealPlayer("Mr. pOOR", 200));
		final Turn players = new TurnImpl(playerList);
		
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(board, players));
		final ModelMemento memento1 = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNotNull(memento1.getGameBoard());
		assertNotNull(memento1.getPlayers());
		assertEquals(3, memento1.getPlayers().getPlayers().size());
		assertEquals("Uncle Pennybags", memento1.getPlayers().getCurrentPlayer().getName());
		assertEquals(2700, memento1.getPlayers().getPlayers().get(1));
		assertEquals("Classic", memento1.getGameBoard().getModeGame());
		assertEquals(22, memento1.getGameBoard().getTiles(tile -> tile instanceof Obtainable).size());
		assertNull(memento1.getPlayers().getPlayers().get(2).getProperties());
		ControllerImpl.getController().saveGame();
		
		//fai un altro test un pelo più complesso, ad esempio inizializzando la partita
		List<String> nameList = Arrays.asList("Uncle Scrooge", "Donald T.", "Mr. Burns");
		List<String> iconList = Arrays.asList("/mode/classic/avatars/Boot.png", "/mode/classic/avatars/Iron.png", "/mode/classic/avatars/Wine.png");
		ControllerImpl.getController().newGameInit(nameList, iconList);
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(board, players));
		final ModelMemento memento2 = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNotNull(memento2.getPlayers().getCurrentPlayer().getProperties());
		assertEquals(utilities.enumerations.InitialDistribution.THREE.getContractNumber(), memento2.getPlayers().getCurrentPlayer().getProperties());
		assertEquals(utilities.enumerations.InitialDistribution.THREE.getMoneyAmount(), memento2.getPlayers().getCurrentPlayer().getMoney());
		assertFalse(memento2.getPlayers().getCurrentPlayer().isInJail());
		assertEquals(memento2.getPlayers().getPlayers().get(1), memento2.getPlayers().getPlayers().get(2));
	}

}
