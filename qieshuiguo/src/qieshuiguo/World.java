package qieshuiguo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;




import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
/** 整个游戏世界 */
public class World extends JPanel {
	public static final int WIDTH =640;
	public static final int HEIGHT =480; 

	public static final int START = 0;     
	public static final int RUNNING = 1;   
	public static final int PAUSE = 2;     
	public static final int GAME_OVER = 3; 
	private int state = START; 
	private FlyingObject[] enemies = {};  
	private Back[] backs={};
	private Dao dao = new Dao();
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	static {
		start = FlyingObject.loadImage("gamestart.png");
		pause = FlyingObject.loadImage("pause.png");
		gameover = FlyingObject.loadImage("GameOVer.png");
	}
	private Sky sky = new Sky();


	int enterIndex = 0;
	public void enterAction() { 
		enterIndex++;
		if(enterIndex%40==0) { 
			FlyingObject obj = nextOne(); 
			enemies = Arrays.copyOf(enemies,enemies.length+1);
			enemies[enemies.length-1] = obj;
		}
	}
	public FlyingObject nextOne() {
		Random rand = new Random(); //随机数对象
		int type = rand.nextInt(50); //0到50之间
		if(type<10) { 
			return new Apple();
		}else if(type<20) { 
			return new Back();}
		else if(type<30) { 
			return new Banana();
		}else if(type<40) {
			return new Peach();
		}else {
			return new Sandia();
		}

	}
	public void stepAction() { 
		for(int i=0;i<enemies.length;i++) { 
			enemies[i].step(); 
		}
	
	}
	int score = 0;
	public void heroBangAction() { //每10毫秒走一次
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			FlyingObject f = enemies[i]; //获取每一个敌人
			if(dao.isLife() && f.isLife() && f.hit(dao)) { //撞上了
				f.goDead();
				if(f instanceof Back) {
					dao.subtractlife();
				}
				if(f instanceof Enemy) {
					Enemy e = (Enemy)f;
					score+=e.getScore();
				}
			}
		}
	}
	/** 刀与炸弹的碰撞 */
	/** 英雄机与敌人的碰撞 */
//	public void daoBangAction() { //每10毫秒走一次
//		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
//			FlyingObject f = enemies[i]; //获取每一个敌人
////			for(int j=0;j<backs.length;i++) { //遍历所有敌人
////				Back b = backs[j]; //获取每一个敌人
////			if(dao.isLife() && f.isLife() && f.hit(dao)) { //撞上了
////
////			}
//			if(f instanceof Back&dao.isLife() && f.isLife() && f.hit(dao)) {
//				Back b = (Back)f;
//				f.goDead(); //敌人去死
//				dao.subtractlife();
//				
//			}
//
//	
//		}
//	}
	public void checkGameOverAction() { //每10毫秒走一次
		if(dao.getLife()<=0) {
			state=GAME_OVER; //当前状态修改为游戏结束状态
		}
	}
	public void action() {
//		if(state==GAME_OVER) {
//			HighScoreRecord h=new HighScoreRecord();
//			h.saveScore(100);
//			System.out.println("写入完毕");
//		}

				MouseAdapter l = new MouseAdapter() {
					public void mouseMoved(MouseEvent e) {
							int x = e.getX(); //获取鼠标的x坐标
							int y = e.getY(); //获取鼠标的y坐标
							dao.moveTo(x, y); //英雄机随着鼠标移动
					}
					/** 重写mouseClicked()鼠标点击 */
					public void mouseClicked(MouseEvent e) {
						switch(state) { //根据当前状态做不同的处理
						case START:        //启动状态时
							state=RUNNING; //修改为运行状态
							break;
						case GAME_OVER:  //游戏结束状态时
							HighScoreRecord h=new HighScoreRecord();
							h.saveScore(score);
							System.out.println("写入完毕");
							score = 0; 
							//清理现场
							sky = new Sky();
							dao = new Dao();
							enemies = new FlyingObject[0];
							backs = new Back[0];
							state=START; //修改为启动状态
							break;
						}
					}
					/** 重写mouseExited()鼠标移出 */
					public void mouseExited(MouseEvent e) {
						if(state==RUNNING) { //运行状态时
							state=PAUSE;     //修改为暂停状态
						}
					}
					/** 重写mouseEntered()鼠标移入 */
					public void mouseEntered(MouseEvent e) {
						if(state==PAUSE) { //暂停状态时
							state=RUNNING; //修改为运行状态
						}
					}
				};
				this.addMouseListener(l); 
				this.addMouseMotionListener(l);
		Timer timer = new Timer(); 
		int intervel = 10; 
		timer.schedule(new TimerTask() {
			public void run() {
				if(state==RUNNING) {
				enterAction(); 
				stepAction();
				heroBangAction();
				//daoBangAction();
				checkGameOverAction();
				System.out.println(dao.getLife());
				}
				repaint();    
			}
		},intervel,intervel); 
	}
	public void paint(Graphics g) {
		sky.paintObject(g);
		dao.paintObject(g);
		for(int i=0;i<enemies.length;i++) { 
			enemies[i].paintObject(g); 
		}
		g.drawLine(100,100,200,200);
		Font f=new Font(null,Font.PLAIN,30);
		g.setFont(f);
		g.drawString("得分: "+score,10,25); //画分
		
		g.drawString("最高分:"+score,10,50);

		switch(state) { //在不同状态下画不同的图
		case START: //启动状态下画启动图
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //暂停状态下画暂停图
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER: //游戏结束状态下画游戏结束图
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	//加载背景音乐


	
	@SuppressWarnings("deprecation")
	public static void playMusic(){
		try {
			URL cb;
			File f = new File("C:\\Users\\Administrator\\Desktop\\Java学习\\workspace\\eclipse-workspace\\qieshuiguo\\src\\qieshuiguo\\Ariari.wav"); 
			cb = f.toURL();
			AudioClip aau;
			aau = Applet.newAudioClip(cb);
		
			aau.play();	
			aau.loop();//循环播放
			System.out.println("可以播放");
			// 循环播放 aau.play()
			//单曲 aau.stop()停止播放
 
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public int getScore() {
		return score;
	}

	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		playMusic();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setTitle("是程序员就来玩切水果");
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		world.action();
		
		
	}
}

