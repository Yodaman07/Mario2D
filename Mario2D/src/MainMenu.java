import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.security.Key;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel{
	
	
	private Image bgImage, levelBtn, editorBtn;
	private Rectangle levelRect, editorRect;
	private AffineTransform tx;
	private MouseListener mouseListener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			Point p = e.getPoint();
			
			//Buttons are both 100x50
			if (new Rectangle(levelRect.width, levelRect.height, 100, 50).contains(p)) {
				System.out.println("Level");
			}else if (new Rectangle(editorRect.width, editorRect.height, 100, 50).contains(p)) {
				Editor editor = new Editor();
			}

		}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
	};
	
	
	public MainMenu() {
		
		levelRect = new Rectangle(650, 350);
		editorRect = new Rectangle(475, 350);
		
		bgImage = getImage("/imgs/Main_Menu.png");
		editorBtn = getImage("/imgs/EditorButton.png");
		
		levelBtn = getImage("/imgs/LevelsButton.png");
		tx = AffineTransform.getTranslateInstance(0, 0);
		init(0, 0); 				//initialize the location of the image
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		init(0,0);
		g2.drawImage(bgImage, tx, null);
		
		
		init(levelRect.width,levelRect.height);
		g2.drawImage(levelBtn, tx, null);
		
		
		init(editorRect.width,editorRect.height);
		g2.drawImage(editorBtn, tx, null);
		
	}
	
	
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = StaticTexture.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
	}	
	
	public MouseListener getListener() {
		return this.mouseListener;
	}
}
