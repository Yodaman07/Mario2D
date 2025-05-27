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
	private double maxJumpV;
	private Image forward, backward, crouch, jumping;
	private AffineTransform tx;
	
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private int width, height;				//collision detection (hit box)
	

	private boolean isJumping = false;
	private boolean isFalling = false;
	
	private boolean bottomCollison = false;
	
	private double accel = 3;
	private double dt = 0.5;
	

	public Mario(int x, int y, double jump){
		

		this.x = x;
		this.y = y;
		this.width = (int) (32*scaleWidth);
		this.height = (int) (64*scaleHeight);
		
		this.jump = jump;
		this.maxJumpV = jump;
		
		forward = getImage("/imgs/Mario_Right.png");
		
		tx = AffineTransform.getTranslateInstance(0,0);
		init(x,y);
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

	public void setVx(int vx) {this.vx = vx;}
	public int getVx() {return vx;}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height);}
	
	public Rectangle getBottomHitbox() {
		return new Rectangle(x+width/4, y+height, width/2, 1);
	}
	
	public Rectangle getTopHitbox() {
		return new Rectangle(x+width/4, y, width/2, 1);
	}
	
	public Rectangle getRightHitbox() {
		return new Rectangle(x+width, y+(height/8), 1, (3*height/4));
	}
	
	public Rectangle getLeftHitbox() {
		return new Rectangle(x, y+(height/8), 1, (3*height/4));
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		x+=vx;
		
		init(x, y);
		g2.drawImage(forward, tx, null);
		
		
		
//		if (!grounded) {isFalling = true;}
//		if (grounded) {isFalling = false;}
		
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
		
		
		
		
		if (Frame.debugging) {
			g2.setColor(Color.red);
			g2.drawRect(x, y, width, height);
			
			//Bottom hitbox
			g2.setColor(Color.green);
			g2.drawRect(x+width/4, y+height, width/2, 1);
		
			//Top hitbox
			g2.setColor(Color.orange);
			g2.drawRect(x+width/4, y, width/2, 1);
			
			//Right Hitbox
			g2.setColor(Color.blue);
			g2.drawRect(x+width, y+(height/4), 1, (height/2));
			
			g2.setColor(Color.pink);
			g2.drawRect(x, y+(height/8), 1, (3*height/4));
			
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
	public void setY(int y) {this.y = y;}

	public int getX() {return x;}
	public int getHeight() {return height;}
	public int getWidth() {return width;}

	
	public boolean isFalling() {return isFalling;}
	public void setFalling(boolean isFalling) {this.isFalling = isFalling;}
	
	public boolean isJumping() {return isJumping;}
	public void setJumping(boolean isJumping) {this.isJumping = isJumping;}
	
	public boolean isBottomCollison() {return bottomCollison;}
	public void setBottomCollison(boolean bottomCollison) {this.bottomCollison = bottomCollison;}
	
	
	

}
