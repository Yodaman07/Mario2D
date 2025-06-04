import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Flag {
	
	private int x, y;
	private Image image;
	private AffineTransform tx;
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;
	
	
	public Flag(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = (int) (64*scaleWidth);
		this.height = (int) (224*scaleHeight); 
		image = getImage("/imgs/FLAG.png");
		tx = AffineTransform.getTranslateInstance(0, 0);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(image, tx, null);
		
		if (Frame.debugging) {
			g2.setColor(Color.green);
			g2.drawRect(x, y, width, height);
		}
	}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height);}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Goomba.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}
	
}
