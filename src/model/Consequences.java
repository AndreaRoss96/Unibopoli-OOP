package model;

import java.util.List;
import java.util.function.BiConsumer;
import controller.ControllerImpl;
import model.player.Player;

public enum Consequences {

	MOVING((controller, values) -> {
		
		/**
		 * Controllare che si pasi dal via.
		 * */
		controller.exitDice(Integer.parseInt(values.get(0)));
		
		
	}),
	SIMPLE_PAYMENT((controller, values) -> {}),
	PAYMENT((controller, values) -> { ((Player) controller.getCurrentPlayer()).payments(Integer.valueOf(values.get(0)));
	}), 
	RECEIVE((controller, values) -> {
		((Player) controller.getCurrentPlayer()).gainMoney(Integer.parseInt(values.get(0)));
	});
	
	private BiConsumer<ControllerImpl, List<String>> consumer;
	
	private Consequences(BiConsumer<ControllerImpl, List<String>> consumer) {
		this.consumer = consumer;
	}
	
	public void exec(List<String> values) {
		this.consumer.accept(ControllerImpl.getController(), values);
	}
}
