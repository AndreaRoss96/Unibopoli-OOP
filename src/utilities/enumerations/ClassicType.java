 package utilities.enumerations;

/**
 * 
 * @author Matteo Alesiani 
 */
public class ClassicType {
	public enum Files{
	    GeneralFilesMap("res/mode/staticValuesTile/BuildableValues.txt", "res/mode/staticValuesTile/NotBuildableValues.txt"); 
	 
	    private final String staticBuildableValuesInitFile;
	    private final String staticNotBuildableValuesInitFile;
	    
	    
	    private Files(final String staticBuildableValuesInitFile, final String staticNotBuildableValuesInitFile) {
	        this.staticBuildableValuesInitFile = staticBuildableValuesInitFile;
	        this.staticNotBuildableValuesInitFile = staticNotBuildableValuesInitFile;
	    }
	
	    /**
	     * @return the path for the initialization tile's values of the map. 
	     */
	    public String getStaticBuildableValuesInitFile() {
	        return this.staticBuildableValuesInitFile;
	    }
	
	    /**
	     * @return TODO: da fare.
	     */
	    public String getStaticNotBuildableValuesInitFile() {
	    	return this.staticNotBuildableValuesInitFile;
	    }
	    
	    /**
	     * @return TODO: da fare. Ricordarsi di cambiare il nome della cartella
	     */
	    public String getModeGame(String mode) {
	    	return "res/mode/" + mode + "/ClassicMode.txt";
	    }
	}
	
	
	public enum Board{
		GeneralImagesMap("images/Icons/Via.png", "images/Icons/Prigione.png", "images/Icons/Poliziotto.png", "images/Icons/Treno.png",
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
	    
	    public String getGoImagePath() {
	    	return this.goImage;
	    }
	    
	    public String getTransitJailPath() {
	    	return this.transitJail;
	    }
	    
	    public String getGoJailPath() {
	    	return this.goJail;
	    }
	    
	    public String getTransitTrainPath() {
	    	return this.transitCar;
	    }
	    
	    public String getRingImagePath() {
	    	return this.ringImage;
	    }
	    
	    public String getTrainImagePath() {
	    	return this.trainImage;
	    }
	    public String getBulbImagePath() {
	    	return this.bulbImage;
	    }
	    
	    public String getWaterImagePath() {
	    	return this.waterImage;
	    }
	    
	    public String getCofferImagePath() {
	    	return this.cofferImage;
	    }
	    
	    public String getUnexpectedImage() {
	    	return this.unexpectedImage;
	    }
	}
	
	public enum Avatar{
		GeneralAvatarsMap("mode/classic/avatars/Boot.png");
		
		private final String boot;
		
		private Avatar(final String boot) {
			this.boot = boot;
		}
		
		public String getBootImage() {
			return this.boot;
		}
	}
	
	public enum Other{
		GeneralOthersMap("images/Logo/Mr_Monopoly.png", "images/Icons/Music.png", "images/Icons/Sound.png", "images/Icons/No_Music.png",
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
		
		  
	    public String getIconWindows() {
	    	return this.iconWindows;
	    }
	    
		public String getMusicImage() {
			return this.music;
		}
		
		public String getSoundImage() {
			return this.sound;
		}
		
		public String getNoMusicImage() {
			return this.noMusic;
		}
		
		public String getNoSoundImage() {
			return this.noSound;
		}
		
		public String getPlusImage() {
			return this.plus;
		}
		
		public String getDiceImage() {
			return this.dice;
		}
		
		public String getGearImage() {
			return this.gear;
		}
		
		public String getSaveImage() {
			return this.save;
		}
	}
}
