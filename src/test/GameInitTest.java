package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Board;
import model.GameInitializer;
import model.Icon;
import model.Model;
import utilities.enumerations.InitialDistribution;

public class GameInitTest {
	
	private static final String SEP = File.separator;
	private final GameInitializer gameInit = GameInitializer.getInstance();
	private Map<String, Icon> players;
	
	private void buildPlayers() {
		players = new HashMap<>();
		players.put("Uncle Pennybags", new Icon("mode" + SEP + "classic" + SEP + "avatars" + SEP + "Boot"));
        players.put("Mr. Pringles", new Icon("mode" + SEP + "classic" + SEP + "avatars" + SEP + "Car"));
        players.put("Uncle Scrooge", new Icon("mode" + SEP + "classic" + SEP + "avatars" + SEP + "Wine"));
		
	}
	
    /**
     * Tests what happens if the game have already been initialized.
     */
    @Test(expected = IllegalStateException.class)
    public void testAlreadyInitialized() {
        try {
        	buildPlayers();
            gameInit.newGame(this.players);
            gameInit.newGame(this.players);
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
    	final Model model = gameInit.newGame(players);
    	
    	// Check that all players are present
    	assertEquals(model.getPlayers().size(), 3);

    	// Check number of properties for each player
    	model.getPlayers().forEach(player -> {
    		assertTrue(player.getMoney() > 0);
    		assertEquals(player.getProperties().size(), InitialDistribution.THREE.getContractNumber());
    	});
    	
    	// Check if all player are in starting position
    	assertTrue(model.getPlayers().stream().filter(player -> player.getPosition() == 0).count() == 1);
    	
    	//aggiungi robe tipo getTurn, nextTurn ecc
    }
}
