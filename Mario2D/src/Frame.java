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
	private Goomba goomba;
	public static int width = 800;//25 tiles
	public static int height = 512;  
  
	public Frame() {
		JFrame f = new JFrame("Game");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(true);
		f.addKeyListener(this);
		
		
//		goomba = new Goomba(10, 10, 32, 32, 2);
		mario = new Mario(width/2, height/2, 32, 32, 1);
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.repaint();

		f.revalidate();
		f.setVisible(true);
		


		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
//		LevelLoader a = new LevelLoader();
//		
//		a.load("src/levels/testing.json");
//		
//		System.out.println("hello world");
	}
	
	
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
//		g.setColor(Color.black);//tile size is 32x32
		int size = 32;
		

		
		for (int j = 0; j < height/size; j++) {

			for (int i = 0; i < width/size; i++) {
				g.setColor(Color.black);
				if (i % 2 == 0) {g.setColor(Color.blue);}
				g.fillRect(size*i, size*j, size, size);
			}
		}
		mario.paint(g);
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
