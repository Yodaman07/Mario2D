import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, KeyListener{

	private Mario mario;

	public static int width = 800;//25 tiles
	public static int height = 512;  
	
	public Frame() {
		JFrame f = new JFrame("Game");
		f.setSize(width, height);
		f.setBackground(Color.white);
		f.setResizable(false);
		f.add(this);
		f.addKeyListener(this);
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		mario = new Mario(50, 50, 10, 10, 20);
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		mario.paint(g);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Frame frame = new Frame();
		LevelLoader a = new LevelLoader();
		
		a.load("src/levels/testing.json");
		
		System.out.println("hello world");
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		
		case (87): //w
			System.out.println("up");
			break;
		case (65): //a
			System.out.println("left");
			break;
		case (83): //d
			System.out.println("down");
			break;
		case (68): //s
			System.out.println("right");
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
		
	}
	
}
