import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Toolkit;
import java.time.*;

public class EndScreen {
	
	private Image death, win;
	private AffineTransform tx;
	
	private int x, y;
	private double scaleWidth = 1;		
	private double scaleHeight = 1; 		
	private int width, height;	
	
	private boolean lose;
	
	public EndScreen(int x, int y, boolean f) {
		this.width = (int) (800*scaleWidth);
		this.height = (int) (512*scaleHeight);
		
		lose = false;
		
		this.x = x;
		this.y = y;
		
		win = getImage("/imgs/WinScreen.png");
		death = getImage("/imgs/Death.png");
		tx = AffineTransform.getTranslateInstance(0,0);
		init(x,y);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		if(lose) {
			g2.drawImage(death, tx, null);
		}else {
			g2.drawImage(win, tx, null);
		}
	}
	
	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Mario.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(0.99, 0.95);
	}

	
	
	
}
