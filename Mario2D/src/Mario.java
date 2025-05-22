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

public class Mario{
	
	private int x, y;
	private int vx, vy;						//movement variables
	private double size;
	private double jump;
	private Image forward, backward, crouch, jumping;
	private AffineTransform tx;
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;				//collision detection (hit box)
	

	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private double accel = 3;
	private double dt = 0.5;
	

	public Mario(int x, int y, double jump){
		

		this.x = x;
		this.y = y;
		this.width = (int) (24*scaleWidth);
		this.height = (int) (32*scaleHeight);
		
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

	public void setVX(int vx) {this.vx = vx;}
	public void setVY(int vy) {this.vy = vy;}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height);}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		g2.drawImage(forward, tx, null);
		
		
		if (Frame.debugging) {
			g2.setColor(Color.red);
			g2.drawRect(x, y, width, height);
		}
		
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
	
	public void setX(int x) {this.x = x;}
	public int getX() {return x;}

}
