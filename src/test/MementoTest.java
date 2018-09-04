package test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import controller.ControllerImpl;
import model.Board;
import model.BoardImpl;
import model.CareMementoTaker;
import model.ModelMemento;
import model.Turn;
import model.TurnImpl;
import model.player.Player;
import model.player.PlayerInfo;
import model.player.RealPlayer;
import model.tiles.Obtainable;

public class MementoTest {
	private static final String GAME_MODE = "CLASSIC";

	@Test
	public void MementoTestRun() {
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(null, null));
		final ModelMemento mementoEmpty = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNull(mementoEmpty.getGameBoard());
		assertNull(mementoEmpty.getPlayers());
		
		final Board board = new BoardImpl(GAME_MODE);
		List<Player> playerList = new ArrayList<>();
		playerList.add(new RealPlayer("Uncle Pennybags", 2000, "/mode/classic/avatars/Boot.png"));
		playerList.add(new RealPlayer("Mr. Pringles", 2700, "/mode/classic/avatars/Iron.png"));
		playerList.add(new RealPlayer("Mr. pOOR", 200,  "/mode/classic/avatars/Wine.png"));
		final Turn players = new TurnImpl(playerList);
		
		CareMementoTaker.getMementoInstance().setMemento(new ModelMemento(board, players));
		final ModelMemento memento1 = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNotNull(memento1.getGameBoard());
		assertNotNull(memento1.getPlayers());
		assertEquals(3, memento1.getPlayers().getPlayers().size());
		assertEquals("Uncle Pennybags", memento1.getPlayers().getCurrentPlayer().getName());
		assertEquals(2700, memento1.getPlayers().getPlayers().get(1).getMoney());
		assertEquals(GAME_MODE, memento1.getGameBoard().getModeGame());
		assertEquals(28, memento1.getGameBoard().getTiles(tile -> tile instanceof Obtainable).size());
		assertEquals(board.getTileOf(0), memento1.getGameBoard().getTileOf(0));
		assertEquals(new ArrayList<>(), memento1.getPlayers().getPlayers().get(2).getProperties());
		
		//fai un altro test un pelo più complesso, ad esempio inizializzando la partita
		List<String> nameList = Arrays.asList("Uncle Scrooge", "Donald T.", "Mr. Burns");
		List<String> iconList = Arrays.asList("/mode/classic/avatars/Boot.png", "/mode/classic/avatars/Iron.png", "/mode/classic/avatars/Wine.png");
		
		ControllerImpl.getController().newGameInit(GAME_MODE, nameList, iconList);
		ControllerImpl.getController().saveGame();
		
		PlayerInfo currPlayer = ControllerImpl.getController().getCurrentPlayer();
		
		final ModelMemento memento2 = CareMementoTaker.getMementoInstance().getMemento();
		
		assertNotNull(memento2.getPlayers().getCurrentPlayer().getProperties());
		assertEquals(utilities.enumerations.InitialDistribution.THREE.getContractNumber(), memento2.getPlayers().getCurrentPlayer().getProperties().size());
		assertNotEquals(0, memento2.getPlayers().getCurrentPlayer().getMoney());
		assertFalse(memento2.getPlayers().getCurrentPlayer().isInJail());
		assertEquals(currPlayer, memento2.getPlayers().getCurrentPlayer());
	}

}
