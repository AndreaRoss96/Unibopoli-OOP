package utilities;

import java.awt.Toolkit;

public class PaneDimensionSetting {

	private static final int OFFSET = 85;
	private static final int CommandBridgeWIDTH = ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) - OFFSET;
    private static final int CommandBridgeHEIGHT = ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) - OFFSET;
    
    private static final int GamePaneDIMENSION = CommandBridgeHEIGHT;
	
    private static final int LateralPaneWIDTH = (CommandBridgeWIDTH - GamePaneDIMENSION)/2;
    private static final int LateralPaneHEIGHT = CommandBridgeHEIGHT;
    
    private static final double WRONGRISOLUTION = 4/3;
    
	private static PaneDimensionSetting PANEDIMENSION;
	
	private PaneDimensionSetting() {
		
	}
	
	public static PaneDimensionSetting getInstance() {
		if(PaneDimensionSetting.PANEDIMENSION == null) {
			PaneDimensionSetting.PANEDIMENSION = new PaneDimensionSetting();
		}
		
		return PaneDimensionSetting.PANEDIMENSION;
	}
	
	public boolean isRegular() {
		return CommandBridgeWIDTH/CommandBridgeHEIGHT == WRONGRISOLUTION;
	}
	
	public int getCommandBridgeWidth() {
		return CommandBridgeWIDTH;
	}
	
	public int getCommandBridgeHeight() {
		return CommandBridgeHEIGHT;
	}
	
	public int getGamePaneWidth() {
		return GamePaneDIMENSION;
	}
	
	public int getGamePaneHeight() {
		return GamePaneDIMENSION;
	}
	
	public int getLateralPaneWidth() {
		return LateralPaneWIDTH;
	}
	
	public int getLateralPaneHeight() {
		return LateralPaneHEIGHT;
	}
}
