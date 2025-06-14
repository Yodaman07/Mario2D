import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class Frame extends JPanel implements ActionListener, KeyListener {

	public static boolean debugging = false;
	public static SCREEN currentScreen = SCREEN.MENU;
	public static Level level = new LevelLoader().load("src/levels/testing.json");
	public static boolean gameOver = false;
	// private static Level level = new LevelLoader().load("Mario2D/src/levels/testing.json");

	private int cameraX;
	private int marioStartingX;
	private boolean mainMenu = false;
	
	private EndScreen endScreen = new EndScreen(0, 0, false);

	private MainMenu mm = new MainMenu();
	private LevelSelect ls = new LevelSelect();

	public static int width = 800;// 25 tiles
	public static int height = 512; // 16 tiles

	public void paint(Graphics g) {
		super.paintComponent(g);

		// sets camera view on mario
//		if (!gameOver) {
		
//		}
		g.translate(-cameraX, 0);

		if (currentScreen == SCREEN.MENU) {
			mm.paint(g);
		} else if (currentScreen == SCREEN.LEVEL_SELECT) {
			ls.paint(g);
		} else if (currentScreen == SCREEN.GAME) {
			// paints level, main menu, and end screen
			if (gameOver) {
				cameraX = 0;
				endScreen.paint(g);
			} else {
				if (level.mario.getX() > 350 && !level.mYoshi.isPlaying()) {
					cameraX = -350 + level.mario.getX();
				} else if (level.mYoshi.getX() > 350 && level.mYoshi.isPlaying()) {
					cameraX = -350 + level.mYoshi.getX();
				}
				
				level.paint(g, 0);
			}
		}
		
		


		// g.setColor(Color.black);//tile size is 32x32

		// sets bottom collision to false
		if (level.mario != null) {
			level.mario.setBottomCollison(false);
		}
		if (level.mYoshi != null) {
			level.mYoshi.setBottomCollison(false);
		}
		if (level.yoshi != null) {
			level.yoshi.setBottomCollison(false);
		}

		// End game, winner
		if (level.mario.getHitbox().intersects(level.flag.getHitbox())) {
			// System.out.println("Winner");
			gameOver = true;
			endScreen.setLose(false);
		}
		
		if (level.mYoshi.getHitbox().intersects(level.flag.getHitbox())) {
			// System.out.println("Winner");
			gameOver = true;
			endScreen.setLose(false);
		}

		// top collision with blocks
		for (int i = 0; i < level.getBlocks().size(); i++) {
			if (level.mario.getTopHitbox().intersects(level.getBlocks().get(i).getBottomHitbox())
					&& level.mario.isJumping() && level.getBlocks().get(i).getPath().equals("/imgs/Orange_Brick.png")) {
				level.getBlocks().remove(i);
				i--;
				level.mario.fall();
			}

			if (level.mYoshi.getTopHitbox().intersects(level.getBlocks().get(i).getBottomHitbox())
					&& level.mYoshi.isJumping()
					&& level.getBlocks().get(i).getPath().equals("/imgs/Orange_Brick.png")) {
				level.getBlocks().remove(i);
				i--;
				level.mYoshi.fall();
			}
		}

		// collision with blocks
		level.getBlocks().forEach((block) -> { // Collision

			if (level.mario.getBottomHitbox().intersects(block.getHitbox())) {
				level.mario.setFalling(false);
				level.mario.setBottomCollison(true);
				level.mario.setY(block.getY() - level.mario.getHeight()); // AI assisted help for repositioning
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

			if (level.yoshi.getBottomHitbox().intersects(block.getHitbox())) {
				level.yoshi.setFalling(false);
				level.yoshi.setBottomCollison(true);
				level.yoshi.setY(block.getY() - level.yoshi.getHeight()); // AI assisted help for repositioning
			}

			if (level.mYoshi.getTopHitbox().intersects(block.getHitbox())) {
				level.mYoshi.setJumping(false);
				level.mYoshi.setFalling(true);
			}

			if (level.mYoshi.getBottomHitbox().intersects(block.getHitbox())) {

				level.mYoshi.setFalling(false);
				level.mYoshi.setBottomCollison(true);
				level.mYoshi.setY(block.getY() - level.mYoshi.getHeight()); // AI assisted help for repositioning
			}

		});
		
		
		if (level.mario.getY() > 512 || (level.mYoshi.getY() > 512 && level.mYoshi.isPlaying())) {
			gameOver = true;
			endScreen.setLose(true);
		}
		
		
		// jumping on enemies
		for (int i = 0; i < level.getEnemies().size(); i++) {
			if (level.mario.getBottomHitbox().intersects(level.getEnemies().get(i).getTopHitbox())
					&& level.mario.isFalling()) {
				level.getEnemies().remove(i);
				i--;
				level.mario.setFalling(false);
				level.mario.jump();
				break;
			}

			// End game, lost because hit enemies
			if (level.mario.getRightHitbox().intersects(level.getEnemies().get(i).getHitbox())) {
				level.mario.setX(level.mario.getX() - 15);
				System.out.println("Damage");
				gameOver = true;
				endScreen.setLose(true);
			}

			if (level.mario.getLeftHitbox().intersects(level.getEnemies().get(i).getHitbox())) {
				level.mario.setX(level.mario.getX() + 15);
				System.out.println("Damage");
				gameOver = true;
				endScreen.setLose(true);
			}
			
			if (level.mYoshi.getRightHitbox().intersects(level.getEnemies().get(i).getHitbox())) {
				level.mYoshi.setX(level.mYoshi.getX() - 15);
				System.out.println("Damage");
				gameOver = true;
				endScreen.setLose(true);
			}

			if (level.mYoshi.getLeftHitbox().intersects(level.getEnemies().get(i).getHitbox())) {
				level.mYoshi.setX(level.mYoshi.getX() + 15);
				System.out.println("Damage");
				gameOver = true;
				endScreen.setLose(true);
			}
			

		}

		// jumping on the enemies as mario riding Yoshi
		for (int i = 0; i < level.getEnemies().size(); i++) {
			if (level.mYoshi.getBottomHitbox().intersects(level.getEnemies().get(i).getTopHitbox())
					&& level.mYoshi.isFalling()) {
				level.getEnemies().remove(i);
				i--;
				level.mYoshi.setFalling(false);
				level.mYoshi.jump();
			}
		}

		// makes mario ride Yoshi
		if (level.mario.getBottomHitbox().intersects(level.yoshi.getTopHitbox()) && level.mario.isFalling()) {
			level.makeMarYoshi();
		}

		// makes mario fall when he isn't standing on a block
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

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
		// Editor e = new Editor();
	}

	public Frame() {
		JFrame f = new JFrame("Game");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(true);
		f.addKeyListener(this);
		f.addMouseListener(mm.getListener());

		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		marioStartingX = level.mario.getStartingX();
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
		if (!gameOver) {
			switch (e.getKeyCode()) {
				case (87): // w
					System.out.println("up");
					if (y.isPlaying()) {
						y.jump();
					} else {
						m.jump();
					}
					break;
				case (65): // a
					System.out.println("left");
					if (y.isPlaying()) {
						y.setVx(-3);
					} else {
						m.setVx(-5);
					}
					break;
				case (68): // d
					System.out.println("right");
					if (y.isPlaying()) {
						y.setVx(3);
					} else {
						m.setVx(5);
					}
					break;
				case (32): // space bar
					System.out.println("Release Yoshi");
					if (y.isPlaying()) {
						level.remakeMario();
					}
					break;
			}
		}else {
			
			switch (e.getKeyCode()) {
				case (82): // back to main menu
					System.out.println("Return to Main Menu");
					if (gameOver) {
						currentScreen = SCREEN.MENU;
					}
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Mario m = level.mario;
		MarioYoshi y = level.mYoshi;
		if (!gameOver) {
			switch (e.getKeyCode()) {
				case (65): // a
					if (y.isPlaying()) {
						y.setVx(0);
					} else {
						m.setVx(0);
					}
					break;
				case (68): // d
					if (y.isPlaying()) {
						y.setVx(0);
					} else {
						m.setVx(0);
					}
					break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

}

enum SCREEN {
	GAME,
	MENU,
	LEVEL_SELECT
}
