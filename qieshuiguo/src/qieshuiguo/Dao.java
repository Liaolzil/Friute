package qieshuiguo;
import java.awt.image.BufferedImage;
public class Dao extends FlyingObject {
	private int life;
	private static BufferedImage image;
	static {
		image = loadImage("d1.png");
	}
	public Dao(){
		super(22,19 ,300,100);
        life=3;
		
	}
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;  
		this.y = y-this.height/2; 
	}
	public BufferedImage getImage() {
		if(isLife()) { 
			return image;
		}
		return null;
    }
	public int getLife() {
		return life;
	}

	public int subtractlife() {
		return life--;
	}
	public boolean hit(Back b) {
		return true;
	}

}


















