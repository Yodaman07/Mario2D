import java.awt.Color;
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
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;
	private int walked = 0;
	
	public Goomba(int x, int y, double v, int d) {
		super(x, y, v);
		this.width = (int) (32*scaleWidth);
		this.height = (int) (32*scaleHeight);
		walkDistance = d;
		
		forward = getImage("/imgs/Goomba_2.png");

		tx = AffineTransform.getTranslateInstance(0, 0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		if(Math.abs(this.getWalked()) >= this.getWalkDistance()) {
			this.setVelocity(this.getVelocity()*-1);
			this.setWalked(0);
		}
		
		g2.drawImage(forward, tx, null);
		
		x += velocity;
		walked += velocity;
		
		
		if (Frame.debugging) {
			g2.setColor(Color.red);
			g2.drawRect(x, y, width, height);
		}

	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
		tx.scale(scaleWidth, scaleHeight);
	}

	public int getWalked() {
		return walked;
	}

	public void setWalked(int walked) {
		this.walked = walked;
	}

	public int getWalkDistance() {
		return walkDistance;
	}

	public void setWalkDistance(int walkDistance) {
		this.walkDistance = walkDistance;
	}
	
	
	
	
	
	
	
	
	
	
	
}
