package qieshuiguo;
import java.awt.image.BufferedImage;
public class Back extends FlyingObject{
	private int y1;
	private int life;
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[3];
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("boom"+i+".png");
		}
	}
	public Back(){
		super(66,68);
		

	}
	int index=1;
	public BufferedImage getImage() { //每10毫秒走一次
		if(isLife()) {        //若活着的
			return images[0]; //直接返回images[0]
		}else if(isDead()) { //若死了的
			BufferedImage img = images[index++]; //获取第2张到第5张图
			if(index==images.length) { //到最后一张图了
				state = REMOVE; //则将当前修改修改为删除的状态
			}
			return img;
		}
		return null; //删除状态时，返回null
	}


}
