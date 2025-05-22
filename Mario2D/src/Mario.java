import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Toolkit;
import java.time.*;

public class Mario {
	
	private int x, y;
	private double size;
	private double stepSize;
	private double jump;
	private Image forward, backward, crouch, jumping;
	private AffineTransform tx;
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;				//collision detection (hit box)
	
	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private double accel = 1.5;
	private double dt = 0.5;
	
	public Mario(int x, int y, double stepSize, double jump) {
		this.x = x;
		this.y = y;
		this.width = (int) (24*scaleWidth);
		this.height = (int) (32*scaleHeight);

		this.stepSize = stepSize;
		this.jump = jump;
		
		forward = getImage("/imgs/Mario.png");
		
		tx = AffineTransform.getTranslateInstance(0,0);
		init(x,y);
	}
	
	public void jump() {
		if(isJumping || isFalling) {
			return;
		}
		isJumping = true;
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
		
		g2.setColor(Color.red);
		g2.drawRect(x, y, width, height);
		
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
			if(y >= 256) {
				isFalling = false;
			}
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
			URL imageURL = Mario.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}
	
	public void keyPressed(KeyEvent e) {
		return;
	}

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

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getStepSize() {
		return stepSize;
	}

	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}

	public double getJump() {
		return jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
	}
	
	
	
	
	
}
