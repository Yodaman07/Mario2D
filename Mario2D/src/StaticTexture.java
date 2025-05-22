import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class StaticTexture{
	

	private Image forward; 	
	private AffineTransform tx;
	
	private int width, height;				//collision detection (hit box)
	private int x, y;						//position of the object
	private int vx, vy;						//movement variables
	private double scaleWidth = 1;		//change to scale image
	private double scaleHeight = 1; 		//change to scale image
	private boolean dangerous = false;

	public StaticTexture(String path) {
		super();
		
		forward 	= getImage(path); //load the image

		width = (int) (32*scaleWidth); // All static textures are 32x32
		height = (int) (32*scaleHeight);
		//used for placement on the JFrame
		x = 0;
		y = 0;
		
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		init(x, y); 				//initialize the location of the image
	}
	
	
	//2nd constructor - allow setting x and y during construction
	public StaticTexture(int x, int y, String path) {
		//call the default constructor for all the normal stuff
		this(path); // invokes default constructor
		
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
	}
	
	//3nd constructor - allow for setting texture danger (does the player die when hitting?
	public StaticTexture(int x, int y, String path, boolean dangerous) {
		//call the default constructor for all the normal stuff
		this(path); // invokes default constructor
		
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
		this.dangerous = dangerous;
		
	}
	
	public Rectangle getHitbox() { return new Rectangle(x, y, width, height); }
	public boolean isDangerous() {return dangerous;}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;
		
		init(x,y);
		g2.drawImage(forward, tx, null);
		
		//draw hit box based on x, y, width, height
		//for collision detection
		if (Frame.debugging) {
			//draw hitbox only if debugging
			g.setColor(Color.green);
			g.drawRect(x, y, width, height);
		}
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
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
	
	@Override
	public String toString() {
		return "StaticTexture [x=" + x + ", y=" + y + "]";
	}
}
