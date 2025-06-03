import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Yoshi extends Enemy{
	
	private int walkDistance;
	private Image forward, backward;
	private AffineTransform tx;
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;
	private int walked = 0;
	
	private boolean isFalling = true;
	
	private boolean bottomCollison = false;
	
	private double accel = 3;
	private double dt = 0.5;
	private double jump = 0;
	
	public Yoshi(int x, int y, double v, int d) {
		super(x, y, v);
		this.width = (int) (32*scaleWidth);
		this.height = (int) (50*scaleHeight);
		walkDistance = d;
		
		forward = getImage("/imgs/Short Pipe.png");
		backward = getImage("/imgs/Short Pipe.png");

		tx = AffineTransform.getTranslateInstance(0, 0);
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		if(Math.abs(this.getWalked()) >= this.getWalkDistance()) {
			super.setVelocity(super.getVelocity() * -1);
			this.setWalked(0);
		}
		
		if(velocity > 0) {
			g2.drawImage(forward, tx, null);
		}else {
			g2.drawImage(backward, tx, null);
		}
		
		if(bottomCollison) {
			x += velocity;
			walked += velocity;
		}
		
		if (Frame.debugging) {
			g2.setColor(Color.red);
			g2.drawRect(x, y, width, height);
			
			g2.setColor(Color.green);
			g2.drawRect(x, y, width, 10);
		}
		
		
		if(isFalling) {
			jump += accel * dt;
			y = (int) (y + (dt * jump) + (0.5 * accel * dt * dt));
			
			if (bottomCollison) {
				isFalling = false;
				jump = 0;
			}
		}

	}
	
	
	public boolean isBottomCollison() {return bottomCollison;}
	public void setBottomCollison(boolean bottomCollison) {this.bottomCollison = bottomCollison;}
	
	public double getAccel() {
		return accel;
	}
	
	public Rectangle getBottomHitbox() {
		return new Rectangle(x+width/4, y+height, width/2, 1);
	}

	public void setAccel(double accel) {
		this.accel = accel;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public boolean isFalling() {return isFalling;}
	public void setFalling(boolean isFalling) {this.isFalling = isFalling;}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height);}
	
	public Rectangle getTopHitbox() {
		return new Rectangle(x, y, width, 10);
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
