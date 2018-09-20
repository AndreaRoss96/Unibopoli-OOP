package view;

public interface View {

	/**
	 * TODO(Improve description): It starts the application and shows the main menu.
	 */
	void startView();

	/**
	 * allows the movement of the icon of the player.
	 * 
	 * @param exitDice
	 *            the dice result
	 */
	void movement(final int exitDice);

	/**
	 * Update the lateral labels with the information about the game, like the
	 * current player, his/her money, net worth.
	 */
	void updateLabels();

	/**
	 * Update the button that decide if the player can end turn or if he/she have to
	 * rolls the dice again.
	 * 
	 * @param diceThrown
	 *            True if the results of the dice are different, false instead (the
	 *            player have to rolls again)
	 */
	void updateButton(boolean diceThrown);

	/**
	 * update the values in the card dialog, in particular the number of the houses
	 * in the property.
	 */
	void updateCardDialog();

	/**
	 * Generate an error Alert if the game has got an error or if the player do
	 * something illegal.
	 * 
	 * @param title
	 *            the title of the alert
	 * @param contentText
	 *            the content of the alert
	 */
	void createErrorAlert(String title, String contentText);

	/**
	 * Generate an information Alert to say something to the player or players.
	 * 
	 * @param title
	 *            the title of the alert
	 * @param contentText
	 *            the content of the alert
	 */
	void createInformationAlert(String title, String contentText);

	/**
	 * Generate an Alert that wait the confirm of the player.
	 * 
	 * @param title
	 *            the title of the alert
	 * @param contentText
	 *            the content of the alert
	 */
	void createConfirmationAlert(String title, String contentText);
}
