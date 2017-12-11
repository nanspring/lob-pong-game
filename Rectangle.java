import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/*
 * Ansheng Xu
 * WF 12:30
 * Jeremiah
 */
public class Rectangle extends JPanel implements KeyListener, ActionListener{
	protected int width;
	protected int height;
	protected static int recWidth=100;
	protected static int recHeight=15;
	protected int x,y;
	protected static Timer time;
	protected static int cx=1,cy,v0=80,circley;
	protected static int vx,vy,scorevalue=0,acceleration=0,lifevalue=3,timevalue=60,levelvalue=1,reclevel=12,factor=0;
	protected double t=2;
	protected AudioInputStream background;
	protected AudioStream BGM;
	protected AudioData MD;
	protected ContinuousAudioDataStream loop=null;
	protected static JLabel score,life,labeltime,level;
	protected int i = 0,random1,random2,random3,random4,r5=0,r6=0;
	protected int seconds = 60,cloudx,cloudy,radians=50,degree=0;
	private BufferedImage christmas,cloud,cuteface,cuterect,star,star2;

	public Rectangle(){
		time=new Timer(50,this);
		width=getWidth();
		height=getHeight();
		x=100;
		y=410;
		random1=80;
		random2=280;
		random3=300;
		random4=230;
		vx=(int)(v0*Math.cos(Math.toRadians(85)));
		vy=(int)(v0*Math.sin(Math.toRadians(80)));
		setFocusable(true);
		addKeyListener(this);
		try{//extra credit
			File yourFile;
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(new File("background.wav"));
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}  catch(Exception e){
			System.out.println(e);
		}

		try{
			christmas=ImageIO.read(new File("christmas.jpg"));
			cloud=ImageIO.read(new File("cloud.png"));
			cuteface=ImageIO.read(new File("cuteface.png"));
			cuterect=ImageIO.read(new File("cuterect.png"));
			star=ImageIO.read(new File("star.png"));
			star2=ImageIO.read(new File("star.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		cy=height-20;
		time.start();
		score=new JLabel("Score: "+scorevalue);
		score.setForeground(Color.white);
		add(score);
		life=new JLabel("Life: "+lifevalue);
		life.setForeground(Color.white);
		add(life);
		labeltime=new JLabel("Time "+timevalue);
		labeltime.setForeground(Color.white);
		add(labeltime);
		level=new JLabel("Level: "+levelvalue);
		level.setForeground(Color.WHITE);
		add(level);

		setBackground(Color.pink);
	}


	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(x>=0){
				x-=20;
				repaint();
			}else{
				x+=0;
				repaint();
			}
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(x<=getWidth()-80){
				x+=20;
				repaint();
			}else{
				x+=0;
				repaint();
			}

		}

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		circley=getHeight()-cy;

		g.drawImage(christmas,0,0,getWidth(),getHeight(),null);
		g.drawImage(cuteface,cx,circley,40,40,null);
		g.drawImage(cloud,150+cloudx,230+cloudy,60,60,null);
		g.drawImage(cuterect,x-80,y-15,(recWidth-reclevel*factor)+170,recHeight+75,null);
		g.drawImage(star,random1,random2,50,50,null);//extra credit
		g.drawImage(star2,random3,random4,50,50,null);//extra credit
		g.setColor(Color.white);
		//g.fillRect(x, y, recWidth-reclevel*factor, recHeight);
		g.setColor(Color.pink);
		//g.fillOval(cx, circley, 20, 20);
		//		if (collision(x,y,cy)==true){
		//			g.drawString("Nice Job", getWidth()/2-15, getHeight()/2);
		//		}
		if(lifevalue<0){
			g.drawString("Game Over", getWidth()/2, getHeight()/2);
			time.stop();
		}
		if(timevalue==0){
			repaint();
			cx=0;
			circley=0;

		}
		if(levelvalue==5){
			g.drawString("You have reach the highest level!!Good job!", getWidth()/2, getHeight()/2);
			time.stop();
		}
	}


	public void actionPerformed(ActionEvent e) {
		Random random=new Random();
		i++;
		t+=0.2;
		cloudx=(int)(radians*Math.cos(Math.toRadians(degree)));
		cloudy=(int)(radians*Math.sin(Math.toRadians(degree)));
		degree+=5;
		if(i%20==0){
			if(seconds>0){
				seconds--;
			}else{
				seconds=60;
				levelvalue++;
				factor++;
				scorevalue+=10;
				level.setText("Level: "+levelvalue);
				score.setText("Score: "+scorevalue);
				t=2;
				random1=random.nextInt(getWidth()/2);
				random3=random.nextInt(getWidth()-40);
				random2=random.nextInt(getHeight()-400)+200;
				random4=random.nextInt(getHeight()-400)+200;
				r5=random.nextInt(100);
				r6=random.nextInt(100);
			}
			labeltime.setText("Time: "+seconds);
		}
		cy=(int)(v0*Math.sin(Math.toRadians(90))*t-0.5*9.8*t*t);

		//acceleration+=.8;
		v0+=acceleration;
		if(cx<=0||cx>=getWidth()-30){
			vx=-vx;
		}
		else if(cx>=random1-30&&cx<=random1+30&&circley>=random2-30&&circley<=random2+30){
			vx=-vx;
		}
		else if(cx>=random3-30&&cx<=random3+30&&circley>=random4-30&&circley<=random4+30){
			vx=-vx;
		}
		else if(cx>=150+cloudx-30&&cx<=150+cloudx+40&&circley>=250+cloudy-50&&circley<=230+cloudy+50){
			vx=-vx;
		}
		else if(cy<0+40||cy>=getHeight()){
			t=0.85;	
			lifevalue--;
			if(lifevalue>=0){
				life.setText("Life: "+lifevalue);
			}


			acceleration=0;
		}
		else if (collision(x,y,cy)==true){
			t=1.5;
			acceleration=0;
			scorevalue+=1;
			score.setText("Score: "+scorevalue);
		}
		cx=cx + vx;
		repaint();

	}



	public static boolean collision(int x,int y,int circley){
		if(cx>=x-25&&cx<=(x+recWidth-15)){
			if(circley>=90&&circley<=100){
				return true;
			}else{
				return false;
			}
		}
		else{
			return false;
		}
	}


	public static void main(String[] args){
		Rectangle board=new Rectangle();
		JFrame frame=new JFrame();
		frame.add(board);
		frame.setSize(400,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
