import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Yoshi {
	private int x, y;
	private double jump;
	private Image forward, backward;
	private AffineTransform tx;
	private int height;
	private int width;
	private double scaleWidth = 1;		
	private double scaleHeight = 1;
	private double vx;
	
	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private boolean bottomCollison = false;
	
	private double accel = 3;
	private double dt = 0.5;
	private double maxJumpV;
	
	public Yoshi(int x, int y, double jump) {
		this.x = x;
		this.y = y;
		this.jump = jump;
		maxJumpV = jump;
		this.width = (int) (32*scaleWidth);
		this.height = (int) (32*scaleHeight);
		
		forward = getImage("/imgs/Yoshi.png");
		
		tx = AffineTransform.getTranslateInstance(0, 0);

	}
	
	public void jump() {
		if(isJumping || isFalling) {
			return;
		}
		isJumping = true;
		jump = maxJumpV;
	}
	
	public void fall() {
		isFalling = true;
		isJumping = false;
		jump = 0;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(forward, tx, null);
		
		x+=vx;
		
		init(x, y);
		
		if(isJumping) {
			
			jump -= accel * dt;
			y = (int) (y - (dt * jump) - (0.5 * accel * dt * dt));
			if(jump <= 0) {
				fall();
			}
		}
		
		if(isFalling) {
			jump += accel * dt;
			y = (int) (y + (dt * jump) + (0.5 * accel * dt * dt));
			
			if (bottomCollison) {
				isFalling = false;
			}
		}
		
		
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
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
	
	public boolean isFalling() {return isFalling;}
	public void setFalling(boolean isFalling) {this.isFalling = isFalling;}
	
	public boolean isJumping() {return isJumping;}
	public void setJumping(boolean isJumping) {this.isJumping = isJumping;}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getJump() {
		return jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getScaleWidth() {
		return scaleWidth;
	}

	public void setScaleWidth(double scaleWidth) {
		this.scaleWidth = scaleWidth;
	}

	public double getScaleHeight() {
		return scaleHeight;
	}

	public void setScaleHeight(double scaleHeight) {
		this.scaleHeight = scaleHeight;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height);}
	
	public Rectangle getBottomHitbox() {
		return new Rectangle(x+width/4, y+height, width/2, 1);
	}
	
	public boolean isBottomCollison() {return bottomCollison;}
	public void setBottomCollison(boolean bottomCollison) {this.bottomCollison = bottomCollison;}
}
