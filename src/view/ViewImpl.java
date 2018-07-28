package view;

import javafx.application.Application;

public class ViewImpl implements View{

	@Override
	public void startView() {
		Application.launch(InitialWindows.class);
	}

}
