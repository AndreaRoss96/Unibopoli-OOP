package utilities.enumerations;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import controller.ControllerImpl;
import model.player.Player;

/**
 * 
 *	@author Matteo Alesiani
 */
public enum Consequences {

	MOVING((player, values) -> {
		ControllerImpl.getController().goToJail();
	}),
	
	SIMPLE_PAYMENT((player, values) -> {
		ControllerImpl.getController().playerPayments(player, Integer.valueOf(values.get(0)));
	}),
	
	PAYMENT((player, values) -> { 
		Integer payment = player.getHotelNumber() * Integer.valueOf(values.get(0)) + 
						  player.getHouseNumber() * Integer.valueOf(values.get(1));
		
		Consequences.valueOf(Consequences.class, "SIMPLE_PAYMENT").exec(player, Arrays.asList(String.valueOf(payment.intValue())));
	}), 
	
	RECEIVE((player, values) -> {
		player.gainMoney(Integer.parseInt(values.get(0)));
	}),
	
	EACH_PLAYER((player, values) -> {
		ControllerImpl.getController().getPlayers().stream()
					  .filter(playerV -> !playerV.getName().equals(player.getName()))
					  .forEach(playerInfo -> Consequences.valueOf(Consequences.class, "SIMPLE_PAYMENT")
							  				.exec(((Player)playerInfo), Arrays.asList(values.get(0))));
	}),
	
	NO_CONSEQUENCE((player, values) -> {}),
	
	PLAYER_TRADE((player, values) -> {
		Consequences.valueOf(Consequences.class, "SIMPLE_PAYMENT").exec(player, Arrays.asList(values.get(1)));
		
		Consequences.valueOf(Consequences.class, "RECEIVE").exec(((Player) ControllerImpl.getController().getPlayers()
								.stream().filter(searchPlayer -> searchPlayer.getName().equals(values.get(0)))
								.findFirst().get()), Arrays.asList(values.get(1)));
	});
	
	private BiConsumer<Player, List<String>> consumer;
	
	private Consequences(BiConsumer<Player, List<String>> consumer) {
		this.consumer = consumer;
	}
	
	public void exec(List<String> values) { //passargli il model per eseguire il pagamento
		this.consumer.accept(((Player) ControllerImpl.getController().getCurrentPlayer()), values);
	}
	
	private void exec(final Player player, List<String> values) { 
		this.consumer.accept(player, values);
	}
}
