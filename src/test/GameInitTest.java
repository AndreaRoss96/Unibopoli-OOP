package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import model.GameInitializer;
import model.Model;
import model.exceptions.NotEnoughMoneyException;
import model.player.Player;
import model.tiles.Obtainable;
import utilities.enumerations.InitialDistribution;

public class GameInitTest {
	
	private static final String SEP = File.separator;
	private static final String MODE = "CLASSIC";
	private final GameInitializer gameInit = GameInitializer.getInstance();
	private Map<String, String> players;
	
	private void buildPlayers() {
		players = new HashMap<>();
		players.put("Uncle Pennybags", "mode" + SEP + "classic" + SEP + "avatars" + SEP + "Boot");
        players.put("Mr. Pringles", "mode" + SEP + "classic" + SEP + "avatars" + SEP + "Car");
        players.put("Uncle Scrooge", "mode" + SEP + "classic" + SEP + "avatars" + SEP + "Wine");
		
	}
	
    /**
     * Tests what happens if the game have already been initialized.
     */
    @Test(expected = IllegalStateException.class)
    public void testAlreadyInitialized() {
        try {
        	buildPlayers();
            gameInit.newGame(MODE, this.players);
            gameInit.newGame(MODE, this.players);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Tests GameInit.
     * 
     * @throws IOException
     *             when can't load the board initialization file
     */
    @Test
    public void testGameInit() throws IOException {
    	buildPlayers();
    	final Model model = gameInit.newGame(MODE, this.players);
    	
    	// Check that all players are present
    	assertEquals(model.getPlayers().size(), 3);

    	// Check number of properties for each player
    	model.getPlayers().forEach(player -> {
    		assertTrue(player.getMoney() > 0);
    		assertEquals(player.getProperties().size(), InitialDistribution.THREE.getContractNumber());
    	});
    	
    	// Check if all player are in starting position
    	assertTrue(model.getPlayers().stream().filter(player -> player.getPosition() == 0).count() == 3);
    	
    	// Check player position in the game's turns
    	assertTrue(model.getCurrentPlayer().getName().equals(this.players.keySet().stream().findFirst().get()));
    	model.endTurn();
    	assertTrue(model.getCurrentPlayer().getName().equals(this.players.keySet().stream().collect(Collectors.toList()).get(1)));
    	
    	model.endTurn();
    	model.endTurn();
    	
    	//absurd trade
    	Player playerToRemove = (Player) model.getPlayers().get(1);
    	model.executeTrade(playerToRemove, 0, playerToRemove.getMoney(), new ArrayList<Obtainable>(), playerToRemove.getProperties());
    	//player on the pavement
    	assertTrue(playerToRemove.getMoney() == 0);
    	
    	try {
    		model.playerPayment(playerToRemove, 1000);
    	} catch (NotEnoughMoneyException ex) {
 			model.removePlayer(playerToRemove);
		}
    	
    	//if playerToRemove doesn't have any kind of capital value is removed
    	assertTrue(model.getPlayers().size() == 2);
    	
    	model.endTurn();  	
    	assertFalse(model.getCurrentPlayer().getName().equals(playerToRemove.getName()));
    	assertFalse(model.playerPayment(model.getCurrentPlayer(), model.getCurrentPlayer().getMoney() + 1));
    }
}
