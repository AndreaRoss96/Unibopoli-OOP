package application;

import controller.ControllerImpl;
import view.View;
import view.ViewImpl;

public final class Application {
	
	public static void main(String[] args) {
		View view = new ViewImpl();
		
		ControllerImpl.getController().setView(view);
		view.startView();
	}
}
