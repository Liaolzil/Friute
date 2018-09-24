package qieshuiguo;

import java.awt.image.BufferedImage;

public class Score extends FlyingObject{
	private static BufferedImage image;
	static {
		image = loadImage("score.png");
	}
	public Score() {
		super(31, 29, 10,25);

	}
	public BufferedImage getImage() {

		return image;
	}
}
