package ui.swing.tray;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.apache.commons.lang.UnhandledException;

public class ImageUtils {

	
	public static BufferedImage replaceImageTrasparentByColor(ImageIcon image,
			int transparentColor) {
		int imageWidth = image.getIconWidth();
		int imageHeight = image.getIconHeight();
		BufferedImage buffered = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D graphics = buffered.createGraphics();
		graphics.drawImage(image.getImage(), null, null);
		
		for (int x =0; x<imageWidth;x++){
			for (int y =0; y<imageHeight;y++){
				int rgb = buffered.getRGB(x, y);
				boolean transparent = rgb == 0;
				if (transparent){
					buffered.setRGB(x, y, transparentColor);
				}
			}	
		}
		return buffered;
	}

	public static int getTrayColor() {
		
		Robot robot = getRobotOrCry();
		Rectangle topLeftPixel = new Rectangle(1, 1);
		BufferedImage topLeftPixelCapture = robot.createScreenCapture(topLeftPixel);
		
		return topLeftPixelCapture.getRGB(0, 0);
	}

	private static Robot getRobotOrCry() {
		try {
			return new Robot();
		} catch (AWTException e) {
			throw new UnhandledException(e);
		}
	}
	
}
