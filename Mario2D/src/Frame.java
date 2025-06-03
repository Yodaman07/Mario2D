import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, KeyListener{


	public static boolean debugging = true;
	
//	private Goomba goomba = new Goomba(100, height/2, 2, 100);
//	private Mario mario = new Mario(500, height/2, 100);


	private Level level = new LevelLoader().load("Mario2D/src/levels/testing.json");
	//private Level level = new LevelLoader().load("src/levels/newLevel.json");
	

	public static int width = 800;//25 tiles
	public static int height = 512;  
  
	public void paint(Graphics g) {
		super.paintComponent(g);
		
//		g.setColor(Color.black);//tile size is 32x32
		level.paint(g);
		if(level.mario != null) {
			level.mario.setBottomCollison(false);
		}
		if(level.mYoshi != null) {
			level.mYoshi.setBottomCollison(false);
		}
		if(level.yoshi != null) {
			level.yoshi.setBottomCollison(false);
		}
		level.getBlocks().forEach((block)->{ //Collision
			
			if (level.mario.getBottomHitbox().intersects(block.getHitbox())) {
				level.mario.setFalling(false);
				level.mario.setBottomCollison(true);
			       level.mario.setY(block.getY() - level.mario.getHeight()); //AI assisted help for repositioning
			}
				
			
			if (level.mario.getRightHitbox().intersects(block.getHitbox())) {
				level.mario.setX(block.getX() - level.mario.getWidth());
			}
				
			if (level.mario.getLeftHitbox().intersects(block.getHitbox())) {
				level.mario.setX(block.getX() + level.mario.getWidth());
			}
				
				
			if (level.mario.getTopHitbox().intersects(block.getHitbox())) {
				level.mario.setJumping(false);
				level.mario.setFalling(true);
			}
					
			
			
			if(level.yoshi.getBottomHitbox().intersects(block.getHitbox())) {
				level.yoshi.setFalling(false);
				level.yoshi.setBottomCollison(true);
		        level.yoshi.setY(block.getY() - level.yoshi.getHeight()); //AI assisted help for repositioning
			}
			
			
			if (level.mYoshi.getBottomHitbox().intersects(block.getHitbox())) {
					
					level.mYoshi.setFalling(false);
					level.mYoshi.setBottomCollison(true);
			        level.mYoshi.setY(block.getY() - level.mYoshi.getHeight()); //AI assisted help for repositioning
			}
			
		});
		
		for(int i = 0; i < level.getEnemies().size(); i++) {
			if(level.mario.getBottomHitbox().intersects(level.getEnemies().get(i).getTopHitbox()) && level.mario.isFalling()) {
				level.getEnemies().remove(i);
				i--;
				level.mario.setFalling(false);
				level.mario.jump();
			}
			
			
		}
			
		if(level.mario.getBottomHitbox().intersects(level.yoshi.getTopHitbox()) && level.mario.isFalling()) {
			level.makeMarYoshi();
		}
			
		
		
		
		if (!level.mario.isBottomCollison()) {
			if (!level.mario.isJumping()) {
				level.mario.setFalling(true);
			}
		}
		
		
		
		if (!level.mYoshi.isBottomCollison()) {
			if (!level.mYoshi.isJumping()) {
				level.mYoshi.setFalling(true);
			}
		}
			
		for(int i = 0; i < level.getEnemies().size(); i++) {
			if(level.mYoshi.getBottomHitbox().intersects(level.getEnemies().get(i).getTopHitbox()) && level.mYoshi.isFalling()) {
				level.getEnemies().remove(i);
				i--;
				level.mYoshi.setFalling(false);
				level.mYoshi.jump();
			}
		}
			
			
		
	}
	
	public static void main(String[] args) {
//		 TODO Auto-generated method stub
		Frame frame = new Frame();
		//Editor e = new Editor();
	}
	
	
	public Frame() {
		JFrame f = new JFrame("Game");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(true);
		f.addKeyListener(this);
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
				
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

 
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyCode());
		Mario m = level.mario;
		MarioYoshi y = level.mYoshi;
		switch (e.getKeyCode()) {
			case (87): //w
				System.out.println("up");
				if(y.isPlaying()) {
					y.jump();
				}else {
					m.jump();
				}
				break;
			case (65): //a
				System.out.println("left");
				if(y.isPlaying()) {
					y.setVx(-3);
				}else {
					m.setVx(-5);
				}
				break;
			case (68): //d
				System.out.println("right");
				if(y.isPlaying()) {
					y.setVx(3);
				}else {
					m.setVx(5);
				}
				break;
			case (32): //space bar
				System.out.println("Release Yoshi");
				if(y.isPlaying()) {
					level.remakeMario();
				}
				break;
			case (16): //shift
				System.out.println("crouch");	
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Mario m = level.mario;
		MarioYoshi y = level.mYoshi;
		switch (e.getKeyCode()) {
			case (65): //a
				if(y.isPlaying()) {
					y.setVx(0);
				}else {
					m.setVx(0);
				}
				break;
			case (68): //d
				if(y.isPlaying()) {
					y.setVx(0);
				}else {
					m.setVx(0);
				}
				break;	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	
}
