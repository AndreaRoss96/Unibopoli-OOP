package view;

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
}
