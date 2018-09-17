 package utilities.enumerations;

/**
 * @version 2.13.0
 * 
 * @author Matteo Alesiani 
 */
 
public class ClassicType {
	public enum Files{
	    GENERALFILEMAP("res/mode/staticValuesTile/BuildableValues.txt", "res/mode/staticValuesTile/NotBuildableValues.txt",
	    				"res/mode/staticValuesTile/NotObtainableValues.txt", "res/mode/staticValuesTile/Probability.txt",
	    				"res/mode/staticValuesTile/Unexpected.txt"); 
	 
	    private final String staticBuildableValuesInitFile;
	    private final String staticNotBuildableValuesInitFile;
	    private final String staticNotObtainableValuesInitFile;
	    private final String probabilityFile;
	    private final String unexpectedFile;
	    
	    private Files(final String staticBuildableValuesInitFile, final String staticNotBuildableValuesInitFile,
	    			  final String staticNotObtainableValuesInitFile, final String probabilityFile, 
	    			  final String unexpectedFile) {
	        this.staticBuildableValuesInitFile = staticBuildableValuesInitFile;
	        this.staticNotBuildableValuesInitFile = staticNotBuildableValuesInitFile;
	        this.staticNotObtainableValuesInitFile = staticNotObtainableValuesInitFile;
	        this.probabilityFile = probabilityFile;
	        this.unexpectedFile = unexpectedFile;
	    }
	
	    /**
	     *	@return the path for the initialization Buildable value tiles of the map. 
	     */
	    public String getStaticBuildableValuesInitFile() {
	        return this.staticBuildableValuesInitFile;
	    }
	
	    /**
	     *	@return the path for the initialization NotBuildable value tiles of the map.
	     */
	    public String getStaticNotBuildableValuesInitFile() {
	    	return this.staticNotBuildableValuesInitFile;
	    }
	    
	    /**
	     *	@param String mode: current game mode.
	     * 
	     *	@return the path contains the Buildable tile's name in base of mode game.
	     */
	    public String getModeGame(String mode) {
	    	return "res/mode/" + mode + "/ClassicMode.txt";
	    }
	    
	    /**
	     *	@return the path for the initialization NotObtainable value tiles of the map.
	     */
	    public String getStaticNotObtainableValuesInitFile(){
	    	return this.staticNotObtainableValuesInitFile;
	    }
	    
	    /**
	     *	@return the path for the initialization probability values to use during the match. 
	     */
	    public String getProbabilityFile() {
	    	return this.probabilityFile;
	    }
	    
	    /**
	     *	@return the path for the initialization unexpected values to use during the match.
	     */
	    public String getUnexpectedFile() {
	    	return this.unexpectedFile;
	    }
	}
	
	public enum Board{
		GENERALIMAGEMAP("images/Icons/Via.png", "images/Icons/Prigione.png", "images/Icons/Poliziotto.png", "images/Icons/Treno.png",
						 "images/Icons/Anello.png", "images/Icons/Train.png", "images/Icons/Lampadina.png", "images/Icons/Rubinetto.png", 
						 "images/Icons/Scrigno.png", "images/Icons/PuntoInterrogativo.png");
		
	    private final String goImage;
	    private final String transitJail;
	    private final String goJail;
	    private final String transitCar;
	    private final String ringImage;
	    private final String trainImage;
	    private final String bulbImage;
	    private final String waterImage;
	    private final String cofferImage;
	    private final String unexpectedImage;
	    
	    private Board(final String goImage, final String transitJail, final String goJail, final String transitCar, 
	    		final String ringImage, final String trainImage, final String bulbImage, final String waterImage, 
	    		final String cofferImage, final String unexpectedImage) {
	        this.goImage = goImage;
	        this.transitJail = transitJail;
	        this.goJail = goJail;
	        this.transitCar = transitCar;
	        this.ringImage = ringImage;
	        this.trainImage = trainImage;
	        this.bulbImage = bulbImage;
	        this.waterImage = waterImage;
	        this.cofferImage = cofferImage;
	        this.unexpectedImage = unexpectedImage;
	    }
	    
	    /**
	     *	@return GO IMAGE path.
	     */
	    public String getGoImagePath() {
	    	return this.goImage;
	    }
	    
	    /**
	     *	@return FREE TRANSIT IMAGE path.
	     */
	    public String getFreeTransitPath() {
	    	return this.transitJail;
	    }
	    
	    /**
	     *	@return GO TO JAIL IMAGE path.
	     */
	    public String getGoJailPath() {
	    	return this.goJail;
	    }
	    
	    /**
	     *	@return GO FREE PARKING IMAGE path.
	     */
	    public String getFreeParkingPath() {
	    	return this.transitCar;
	    }
	    
	    /**
	     *	@return RING IMAGE path. 
	     */
	    public String getRingImagePath() {
	    	return this.ringImage;
	    }
	    
	    /**
	     *	@return TRAIN IMAGE path. 
	     */
	    public String getTrainImagePath() {
	    	return this.trainImage;
	    }
	    
	    /**
	     *	@return LIGHT COMPANY IMAGE path. 
	     */
	    public String getBulbImagePath() {
	    	return this.bulbImage;
	    }
	    
	    /**
	     *	@return WATER COMPANY IMAGE path.  
	     */
	    public String getWaterImagePath() {
	    	return this.waterImage;
	    }
	    
	    /**
	     *	@return PROBABILITY IMAGE path. 
	     */
	    public String getProbabilityImagePath() {
	    	return this.cofferImage;
	    }
	    
	    /**
	     *	@return UNEXPECTED IMAGE path. 
	     */
	    public String getUnexpectedImage() {
	    	return this.unexpectedImage;
	    }
	}
	
	public enum Other{
		GENERALOTHERIMAGEMAP("images/Logo/Mr_Monopoly.png", "images/Icons/Music.png", "images/Icons/Sound.png", "images/Icons/No_Music.png",
						 "images/Icons/No_sounds.png", "images/Icons/Plus.png", "images/Icons/dices.png", "images/Icons/gear.png", "images/Icons/save.png");
		
		private final String iconWindows;
		private final String music;
		private final String sound;
		private final String noMusic;
		private final String noSound;
		private final String plus;
		private final String dice;
		private final String gear;	
		private final String save;
		
		private Other(final String iconWindows, final String music, final String sound, final String noMusic, 
					  final String noSound, final String plus, final String dice, final String gear, final String save) {
			this.iconWindows = iconWindows;
			this.music = music;
			this.sound = sound;
			this.noMusic = noMusic;
			this.noSound = noSound;
			this.plus = plus;
			this.dice = dice;
			this.gear = gear;
			this.save = save;
		}
		
		/**
		 *	@return WINDOWS ICON IMAGE path
		 */
	    public String getIconWindows() {
	    	return this.iconWindows;
	    }
	    
	    /**
		 *	@return MUSIC IMAGE path 
		 */
		public String getMusicImage() {
			return this.music;
		}
		
		/**
		 *	@return SOUND IMAGE path 
		 */
		public String getSoundImage() {
			return this.sound;
		}
		
		/**
		 *	@return NO MUSIC IMAGE path
		 */
		public String getNoMusicImage() {
			return this.noMusic;
		}
		
		/**
		 *	@return NO SOUND IMAGE path
		 */
		public String getNoSoundImage() {
			return this.noSound;
		}
		
		/**
		 *	@return PLUS IMAGE path
		 */
		public String getPlusImage() {
			return this.plus;
		}
		
		/**
		 *	@return DICE IMAGE path
		 */
		public String getDiceImage() {
			return this.dice;
		}
		
		/**
		 *	@return GEAR IMAGE path
		 */
		public String getGearImage() {
			return this.gear;
		}
		
		/**
		 *	@return SAVE IMAGE path
		 */
		public String getSaveImage() {
			return this.save;
		}
	}
	
	public enum Dialog {
		GENERALDIALOGMAP("images/Icons/dialog/Auction.png", "images/Icons/dialog/ruined_house.png", "images/Icons/dialog/shopping_cart.png");
		
		private final String auction;
		private final String mortgage;
		private final String trade;
		
		private Dialog (final String auction, final String mortgage, final String trade) {
			this.auction = auction;
			this.mortgage = mortgage;
			this.trade = trade;
		}
		
		/**
		 *	@return AUCTION IMAGE path
		 */
		public String getAuctionImage() {
			return this.auction;
		}
		
		/**
		 *	@return MORTGAGE IMAGE path 
		 */
		public String getMortgageImage() {
			return this.mortgage;
		}
		
		/**
		 *	@return TRADE IMAGE path 
		 */
		public String getTradeImage() {
			return this.trade;
		}
	}
	
	public enum Music{
		GeneralMusicMap("/music/MonopolyMainMusic.wav", "/music/CashRegister.wav", "/music/DiceRoll.wav",
						"/music/GameWin.wav", "/music/JailDoorEffect.wav", "/music/LoseGame.wav", 
						"/music/PlasticDropOnPlaying.wav");
		
		private final String monopolyMainMusic;
		private final String cashRegister;
		private final String diceRoll;
		private final String gameWin;
		private final String jailDoorEffect;
		private final String loseGame;
		private final String plasticDropOnPlaying;
		
		private Music(final String monopolyMainMusic, final String cashRegister, final String diceRoll, 
					  final String gameWin, final String jailDoorEffect, final String loseGame, 
					  final String plasticDropOnPlaying) {
			this.monopolyMainMusic = monopolyMainMusic;
			this.cashRegister = cashRegister;
			this.diceRoll = diceRoll;
			this.gameWin = gameWin;
			this.jailDoorEffect = jailDoorEffect;
			this.loseGame = loseGame;
			this.plasticDropOnPlaying = plasticDropOnPlaying;
			
		}
		
		/**
		 *	@return MAIN MUSIC path 
		 */
		public String getMonopolyMainMusic() {
			return monopolyMainMusic;
		}
		
		/**
		 *	@return CASH REGISTER MUSIC path 
		 */
		public String getCashRegister() {
			return cashRegister;
		}
		
		/**
		 *	@return DICE ROLL MUSIC path 
		 */
		public String getDiceRoll() {
			return diceRoll;
		}
		
		/**
		 *	@return GAME WIN MUSIC path 
		 */
		public String getGameWin() {
			return gameWin;
		}

		/**
		 *	@return CLOSING JAIL DOOR MUSIC path 
		 */
		public String getJailDoorEffect() {
			return jailDoorEffect;
		}

		/**
		 *	@return GAME LOSE MUSIC path 
		 */
		public String getLoseGame() {
			return loseGame;
		}

		/**
		 *	@return TODO: aggiungere documentazione corretta MUSIC path 
		 */
		public String getPlasticDropOnPlaying() {
			return plasticDropOnPlaying;
		}
	}
}
