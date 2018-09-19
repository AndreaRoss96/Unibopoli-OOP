package application;

import view.View;
import view.ViewImpl;

public final class Application {
	
	public static void main(String[] args) {
		View view = new ViewImpl();
		
		view.startView();
	}
}
