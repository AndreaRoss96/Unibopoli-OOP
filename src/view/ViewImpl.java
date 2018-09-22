package view;

import controller.ControllerImpl;
import javafx.application.Application;
import view.gameDialog.CardDialog;

public class ViewImpl implements View{
	
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
	public void updateDiceLabel(final int firstResult, final int secondResult) {
		RightInormationPane.getRinghtInformationPane().updateDiceLabel(firstResult, secondResult);
	}
	
	@Override
	public void updateLabels() {
		RightInormationPane.getRinghtInformationPane().updateLabels();
		RightInormationPane.getRinghtInformationPane().updateJailButton(ControllerImpl.getController().getCurrentPlayer().isInJail());
		LeftPlayersPane.getLeftPlayersPane().updatePane();
		this.updateGamePane();
	}
	
	@Override
	public void updateCardDialog() {
		CardDialog.getCardDialog().updateCardDialog();
	}
	
	@Override
	public void createErrorAlert(final String title, final String contentText) {
		AlertFactory.createErrorAlert(title, contentText);
	}
	
	@Override
	public void createInformationAlert(final String title, final String contentText) {
		AlertFactory.createInformationAlert(title, contentText);
	}
	
	@Override
	public void createConfirmationAlert(final String title, final String contentText) {
		AlertFactory.createConfirmationAlert(title, contentText);
	}
	
	@Override
	public void createCardConsequencePane(final String text) {
		this.updateGamePane();
		GamePane.get().getRoot().getChildren().add(ProbabUnexAnimation.getProbabilityDialog().createProbabilityDialog(text));
	}
	
	@Override
	public void removePlayer(final String player) {
		GamePane.get().deletePlayer(player);
	}
	
	private void updateGamePane() {
		GamePane.get().updateContractPane();
		GamePane.get().getRoot().getChildren().removeIf(node -> node instanceof ProbabUnexAnimation);
	}
}
