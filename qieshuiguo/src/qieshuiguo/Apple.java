package qieshuiguo;

import java.awt.image.BufferedImage;


/** 小敌机: 是飞行物，也是敌人能得分 */
public class Apple extends FlyingObject implements Enemy  {
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("apple"+i+".png");
		}
	}
	
	private int speed; //移动速度
	/** 构造方法 */
	public Apple(){
		super(66,66);
		speed = 2;
	}
	
	
	int index = 1;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() { //每10毫秒走一次
		if(isLife()) {        //若活着的
			return images[0]; //直接返回images[0]
		}else if(isDead()) { //若死了的
			BufferedImage img = images[1]; 
			return img;
		}
		return null; //删除状态时，返回null
	}
	public int getScore() {
		return 3; 
	}
}
	