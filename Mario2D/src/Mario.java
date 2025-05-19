import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Toolkit;

public class Mario {
	
	private int x, y;
	private double size;
	private double stepSize;
	private double jump;
	private Image forward, backward, crouch, jumping;
	private AffineTransform tx;
	
	public Mario(int x, int y, double size, double stepSize, double jump) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.stepSize = stepSize;
		this.jump = jump;
		
		forward = Toolkit.getDefaultToolkit().getImage("H:\\git\\Mario2D\\Mario2D\\src\\pixilart-drawing (2).png");
		
		tx = AffineTransform.getTranslateInstance(0,0);
		
		init(x,y);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		init(x, y);
		
		g2.drawImage(forward, tx, null);
		
		
	}
	
	private void init(int a, int b) {
		tx.setToTranslation(a, b);
		tx.scale(size, size);
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
