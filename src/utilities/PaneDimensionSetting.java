package utilities;

import java.awt.Toolkit;

public class PaneDimensionSetting {

	private static final double OFFSET_WIDTH = 0.042 * Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final double OFFSET_HEIGHT = 0.074 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static final double CommandBridgeWIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - OFFSET_WIDTH;
    private static final double CommandBridgeHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - OFFSET_HEIGHT;
    private static final double GamePaneDIMENSION = CommandBridgeHEIGHT;
    private static final double LateralPaneWIDTH = (CommandBridgeWIDTH - GamePaneDIMENSION)/2 - OFFSET_WIDTH;
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
