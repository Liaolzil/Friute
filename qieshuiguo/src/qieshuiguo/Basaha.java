package qieshuiguo;

import java.awt.image.BufferedImage;

public class Basaha extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("basaha"+i+".png");
		}
	}
	

	public Basaha(){
		super(69,99);
	}

	int index = 1;
	
	public BufferedImage getImage() { 
		if(isLife()) {      
			return images[0];
		}else if(isDead()) { 
			BufferedImage img = images[1]; 
		
			return img;
		}
		return null;
	
	}
	public int getScore() {
		return 3; 
	}
}
