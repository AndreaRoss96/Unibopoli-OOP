package view;

public interface View {

	/**
     * TODO(Improve description): It starts the application and shows the main menu.
     */
	void startView();
	
	void movement(final int exitDice);

	void updateLabels();
}
