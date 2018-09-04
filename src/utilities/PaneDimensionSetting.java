package utilities;

import java.awt.Toolkit;

public class PaneDimensionSetting {

	private static final double OFFSET = 80.0;
	private static final double CommandBridgeWIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - OFFSET;
    private static final double CommandBridgeHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - OFFSET;
    private static final double GamePaneDIMENSION = CommandBridgeHEIGHT;
    private static final double LateralPaneWIDTH = (CommandBridgeWIDTH - GamePaneDIMENSION)/2 - OFFSET;
    private static final double LateralPaneHEIGHT = CommandBridgeHEIGHT;
    
	private static PaneDimensionSetting PANEDIMENSION;
	
	private PaneDimensionSetting() {}
	
	public static PaneDimensionSetting getInstance() {
		if(PaneDimensionSetting.PANEDIMENSION == null) {
			PaneDimensionSetting.PANEDIMENSION = new PaneDimensionSetting();
		}
		
		return PaneDimensionSetting.PANEDIMENSION;
	}
	
	public double getCommandBridgeWidth() {
		return CommandBridgeWIDTH;
	}
	
	public double getCommandBridgeHeight() {
		return CommandBridgeHEIGHT;
	}
	
	public double getGamePaneWidth() {
		return GamePaneDIMENSION;
	}
	
	public double getGamePaneHeight() {
		return GamePaneDIMENSION;
	}
	
	public double getLateralPaneWidth() {
		return LateralPaneWIDTH;
	}
	
	public double getLateralPaneHeight() {
		return LateralPaneHEIGHT;
	}
}
