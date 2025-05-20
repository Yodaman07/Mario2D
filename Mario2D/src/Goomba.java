import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Goomba extends Enemy{
	
	private int walkDistance;
	private Image forward, backward;
	private AffineTransform tx;
	
	public Goomba(int x, int y, double size, double v, int d) {
		super(x, y, size, v);
		walkDistance = d;
		forward = getImage("/imgs/Goomba.png");
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(forward, tx, null);
		
		int walked = 0;

		
		x += velocity;
		walked += velocity;
		if(walked >= walkDistance) {
			velocity *= -1;
			walked = 0;
		}
		
		
	
	}
	
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
		tx.scale(size, size);
	}

	public int getWalkDistance() {
		return walkDistance;
	}

	public void setWalkDistance(int walkDistance) {
		this.walkDistance = walkDistance;
	}
	
	
	
	
	
	
	
	
	
}
