package model;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import controller.ControllerImpl;
import model.player.Player;

public enum Consequences {

	MOVING((player, values) -> {
		if(player.getPosition() > Integer.parseInt(values.get(0))) {
			Consequences.valueOf(Consequences.class, "RECEIVE").exec(Arrays.asList(values.get(1)));
		}
		
		ControllerImpl.getController().exitDice(Integer.parseInt(values.get(0)));
	}),
	
	SIMPLE_PAYMENT((player, values) -> {
		player.payments(Integer.valueOf(values.get(0)));
	}),
	
	PAYMENT((player, values) -> { 
		Integer payment = player.getHotelNumber() * Integer.valueOf(values.get(0)) + 
						  player.getHouseNumber() * Integer.valueOf(values.get(1));
		
		Consequences.valueOf(Consequences.class, "SIMPLE_PAYMENT").exec(Arrays.asList(String.valueOf(payment.intValue())));
	}), 
	
	RECEIVE((player, values) -> {
		player.gainMoney(Integer.parseInt(values.get(0)));
	}),
	
	PLAYER_TRADE((player, values) -> {
		Consequences.valueOf(Consequences.class, "SIMPLE_PAYMENT").exec(Arrays.asList(values.get(1)));
		
		((Player) ControllerImpl.getController().getPlayers()
								.stream().filter(searchPlayer -> searchPlayer.getName().equals(values.get(0)))
								.findFirst().get()).gainMoney(Integer.parseInt(values.get(1)));
		
	});
	
	private BiConsumer<Player, List<String>> consumer;
	
	private Consequences(BiConsumer<Player, List<String>> consumer) {
		this.consumer = consumer;
	}
	
	public void exec(List<String> values) {
		this.consumer.accept(((Player) ControllerImpl.getController().getCurrentPlayer()), values);
	}
}
