package controller;

import java.io.File;

import model.Model;
import view.View;

public class ControllerImpl implements Controller{

	private static final ControllerImpl SINGLETON = new ControllerImpl();
	private Model model; 
	private View view;
	
	public ControllerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void gameInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGameFromFile(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showContract(String contractName, String currentPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void diceClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void settingsClick() {
		// TODO Auto-generated method stub
		
	}

}
