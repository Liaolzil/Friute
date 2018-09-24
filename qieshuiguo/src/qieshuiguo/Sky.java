package qieshuiguo;

import java.awt.image.BufferedImage;

public class Sky extends FlyingObject{
	private static BufferedImage image;
	static {
		image = loadImage("background.jpg");
	}
	Sky(){
		super(400,700,0,0);
	}

	public BufferedImage getImage() {
		return image;
	}
	public void step() {
		y=0;
	}
}
