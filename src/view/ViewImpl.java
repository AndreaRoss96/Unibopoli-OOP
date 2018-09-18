package view;

import controller.ControllerImpl;
import javafx.application.Application;

public class ViewImpl implements View{
	
	public ViewImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void startView() {
		Application.launch(InitialWindows.class);
	}

	@Override
	public void movement(int exitDice) {
		GamePane.get().movement(exitDice);
	}
	
	@Override
	public void updateButton(boolean diceThrown) {
		RightInormationPane.getRinghtInformationPane().updateButton(diceThrown);
	}
	
	@Override
	public void updateLabels() {
		RightInormationPane.getRinghtInformationPane().updateLabels();
		RightInormationPane.getRinghtInformationPane().updateJailButton(ControllerImpl.getController().getCurrentPlayer().isInJail());
		LeftPlayersPane.getLeftPlayersPane().updatePane();
		GamePane.get().updateContractPane();
	}
	
}
