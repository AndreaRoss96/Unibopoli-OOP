package utilities;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontFactory {

	public static Font getDefaultFont(final int size) {
		return Font.loadFont(ClassLoader.getSystemResource("font/kabel.ttf").toExternalForm(), size);
	}
	
	public static Font getFontBold(final String type, final double size) {
		return Font.font(type, FontWeight.BOLD, size);
	}
	
	public static Font getFontItalic(final String type, final double size) {
		return Font.font(type, FontPosture.ITALIC, size);
	}
}
