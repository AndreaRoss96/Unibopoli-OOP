package controller;

import java.util.List;

import com.google.common.base.Optional;

import model.player.PlayerInfo;

/**
 * This interface contains all method used by Dialog's classes.
 */
public interface DialogObserver {
		
	/**
	 * metodi per AuctionDialog 
	 */
	void executeAuction();
			
	/**
	 * metodi per cardDialog
	 */
	/**
	 * Increseases
	 * @param property
	 */
	boolean incHouse();
	boolean decHouse();
	void mortgageDialogClick();
	void buyPropertyClick();
	
	/**
	 * metodi per mortgageDialog
	 */
	int accumulatedMoney(List<String> list);//altrimenti una lista di stringhe
	void setMortgage(List<Optional<String>> list); //se la proprietà non è ipotecata la ipoteca, il contrario altirmenti --> chiedi conferma

	
	/**
	 * metodi per TradeDialog
	 */
	void executeTrade();
	
	PlayerInfo getPlayerByName(String playerName);


	



	

	
}
