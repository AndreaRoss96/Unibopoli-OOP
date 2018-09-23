package application;

import controller.ControllerImpl;
import view.View;
import view.ViewImpl;

public final class Application {
	
	/**
	 * TODO: qunado un contrato viene ipotecato come funziona ?? gli lo acquista, lo acquista ipotecato ??
	 * Coma fa un gioctore a comprarlo ?? Il giocatore che l'ha ipotecato è l'unico che può toglier l'ipoteca ??
	 * */
	
	public static void main(String[] args) {
		View view = new ViewImpl();
		
		ControllerImpl.getController().setView(view);
		view.startView();
	}
}
