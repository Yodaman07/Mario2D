
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
	private StaticTexture brick;
	

	private Level level = new LevelLoader().load("src/levels/testing.json");
	

	public static int width = 800;//25 tiles
	public static int height = 512;  
  
	public void paint(Graphics g) {
		super.paintComponent(g);
		
//		g.setColor(Color.black);//tile size is 32x32
		level.paint(g);
		level.getBlocks().forEach((block)->{ //Collision
			if (level.mario.getHitbox().intersects(block.getHitbox())) {
//				level.mario
			}
		});
		
//		if(mario.getX() + mario.getWidth() >= goomba.getX() && mario.getX() <= goomba.getX()) {
//			mario.setX(0);
//		}
//		
//		//check right side collision
//		if(mario.getX() <= goomba.getX() + goomba.getWidth() && mario.getX() + mario.getWidth() >= goomba.getX() + goomba.getWidth()) {
//			mario.setX(0);
//		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
//		Editor e = new Editor();
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
		switch (e.getKeyCode()) {
			case (87): //w
				System.out.println("up");
				m.jump();
				break;
			case (65): //a
				System.out.println("left");
				m.setX((int)(m.getX() + 1));
				break;
			case (68): //d
				System.out.println("right");
				m.setX((int)(m.getX() + 1));
				break;
			case (83): //s
				System.out.println("down");
				break;
			case (16): //shift
				System.out.println("crouch");	
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	
}
